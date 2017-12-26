package com.fh.controller.system.PPPprojects;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray; 
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.service.projects.ProjectService;
import com.fh.service.system.PPPprojectService.PPPProjectScheduleService;
import com.fh.service.system.PPPprojectService.PPPProjectService;
import com.fh.service.system.dictionaries.DictionariesService; 
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/PPPproSche" })
public class PPPProjectScheduleController extends BaseController { 
	
	@Resource(name = "PPPprojectScheduleService")
	private PPPProjectScheduleService PPPproScheService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@Resource(name = "PPPprojectService")
	private PPPProjectService PPPprojectService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/PPPprojects/PPPproject_schedule_list");
		return mv;
	}
	
	@RequestMapping(value={"/queryPageList"}) 
	@ResponseBody
	public String queryPageList(Page page) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();

		JSONObject getObj = new JSONObject();
		String sEcho = "0";// 记录操作的次数  每次加1  
		String iDisplayStart = "0";// 起始  
		String iDisplayLength = "10";// size  
		String mDataProp = "mDataProp_";
		String sortName = "";//排序字段
		String iSortCol_0 = "";//排序索引
		String sSortDir_0 = "";//排序方式
		//获取jquery datatable当前配置参数  
		JSONArray jsonArray = JSONArray.fromObject(pd.getString("aoData"));  
		for (int i = 0; i < jsonArray.size(); i++)  
		{  
		    try  
		    {  
		        JSONObject jsonObject = (JSONObject)jsonArray.get(i);  
		        if (jsonObject.get("name").equals("sEcho")){ 
		            sEcho = jsonObject.get("value").toString();  
		        }
		        else if (jsonObject.get("name").equals("iDisplayStart")){
		            iDisplayStart = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iDisplayLength")){ 
		            iDisplayLength = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("sSortDir_0")){
		        	sSortDir_0 = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iSortCol_0")){
		        	iSortCol_0 = jsonObject.get("value").toString();
		        	mDataProp = mDataProp+""+iSortCol_0;
		        	for (int j = 0; j < jsonArray.size(); j++)  {
		        		JSONObject jsonObject1 = (JSONObject)jsonArray.get(j);  
		        		if(jsonObject1.get("name").equals(mDataProp)){
		        			sortName = jsonObject1.get("value").toString(); 
		        		}
		        	}
		        }
		        
		    }  
		    catch (Exception e) { 
		    	e.printStackTrace();
		        break;  
		    }  
		}   
		 
		String PROJECT_NAME = pd.getString("PROJECT_NAME"); 
		if ((PROJECT_NAME != null) && (!"".equals(PROJECT_NAME))) { 
			//String param=new String(PROJECT_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("PROJECT_NAME",PROJECT_NAME);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List<PageData> data = this.PPPproScheService.listByParam(page);
		Object	total = this.PPPproScheService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:data){  
			 pro.put("DATE",pro.get("DATE")==null?"":pro.get("DATE").toString());
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
	
	 
	 @RequestMapping(value={"/save"}) 
	 @SystemLog(methods="进度跟踪",type="编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd=getPageData(); 
	     PageData data = new PageData(); 
	     try {  
	    	 pd.put("DATE",new Date());
	    	 pd.put("OPER_BY",getUser().getID());
	    	 data.put("ID",pd.getString("PROJECT_ID"));
	    	 data.put("PROJECT_STAGE_ID",pd.getString("SCHEDULE"));
	    	 data.put("MODIFY_BY",getUser().getID());
	    	 data.put("MODIFY_DATE",new Date());
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID()); 
	    		this.PPPproScheService.save(pd); 
    	   }else{//修改    
    	 	  this.PPPproScheService.edit(pd); 
    	   }
	       this.PPPproScheService.edit(data);//修改项目进度
	     } catch (Exception e) {
	           this.logger.error(e.toString(), e); 
	     }  
	    JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	   }  
	 
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData(); 
		List userList=null;
		try {
			pd = getPageData();  
			pd = this.PPPproScheService.findById(pd);  
			List stageList=this.dicService.queryByPBM("pppstage"); 
			List projectList=this.PPPprojectService.listAll(null);
			mv.addObject("stageList", stageList); 
			mv.addObject("projectList", projectList); 
			mv.addObject("pd", pd); 
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		mv.addObject("msg", "edit");
	    mv.setViewName("system/PPPprojects/PPPproject_schedule_edit");  
		return mv;
	}
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="进度跟踪",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData();
			//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
			this.PPPproScheService.delete(pd); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser");
		
		return user;
	}
	
} 
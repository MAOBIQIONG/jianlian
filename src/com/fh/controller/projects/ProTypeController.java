package com.fh.controller.projects;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray; 
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.projects.ProjectBidderService;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.service.projects.ProjectService;
import com.fh.service.projects.XmLxService;
import com.fh.service.system.dictionaries.DictionariesService; 
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/proType" })
public class ProTypeController extends BaseController {  
	
	@Resource(name = "xmLxService")
	private XmLxService xmLxService; 
	
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView(); 
		mv.setViewName("projects/type_pro_list");
		return mv;
	}
	
	@RequestMapping(value={"/queryList"}) 
	@ResponseBody
	public String queryList(Page page) throws Exception {
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
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List<PageData> data = this.xmLxService.querylistPage(page);
		Object	total = this.xmLxService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData lx:data){ 
		   if(lx.get("DATE")==null||"".equals(lx.get("DATE"))){
			   lx.put("DATE","");
		   }else{ 
			  lx.put("DATE",lx.get("DATE").toString());
		   }
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}  
	 
	
	@RequestMapping(value={"/toChild"})
	public ModelAndView toChildPage() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd =getPageData(); 
		mv.addObject("PID", pd.get("PID")); 
		mv.setViewName("projects/type_child_list");
		return mv;
	}  
	
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="项目类型管理",type="编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData();
	     Object ob=null;
	     try { 
	    	 pd=getPageData(); 
	    	 pd.put("DATE",new Date());
	    	 if(pd.getString("ID")!=null&&!"".equals(pd.getString("ID"))){//修改
	    		ob= this.xmLxService.edit(pd);  
	    	 }else{//新增
	    		pd.put("ID", get32UUID()); 
	    		ob=this.xmLxService.save(pd); 
	    	 } 
	      }catch (Exception e) {
	           this.logger.error(e.toString(), e); 
	      } 
		  JSONObject getObj = new JSONObject();
		  if(Integer.parseInt(ob.toString())>0){
			  getObj.put("status", "1");//成功
		  }else{
			  getObj.put("status", "0");//失败
		  } 
		  return getObj.toString(); 
	  }  
	 
	@RequestMapping({ "/toEdit" })
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();   
			PageData type=this.xmLxService.queryById(pd);
			if(type!=null){
				mv.addObject("pd",type);
			}else{ 
				mv.addObject("pd",pd);
			} 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
	    mv.setViewName("projects/type_pro_edit");  
		return mv;
	}
	
	@RequestMapping({ "/delete" })
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.xmLxService.delete(pd); 
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
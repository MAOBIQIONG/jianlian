package com.fh.controller.system.sysrelation;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.Recom.RecomService;
import com.fh.service.system.sysrelation.SysrelationService;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/relation" })
public class SysrelationController extends BaseController{
	
	@Resource(name = "sysrelationService")
	private SysrelationService sysrelationService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/sysRelation/Relation_list");
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
		for (int i = 0; i < jsonArray.size(); i++){  
		    try {  
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
		
		List<PageData>	projectsList = this.sysrelationService.queryByStatus(page);
		Object total = this.sysrelationService.findCountByStatus(page).get("counts");//查询记录的总行数 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"CREATE_DATE"}); 
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}   
	 
	//关联
	 @RequestMapping(value={"/save"}) 
	 @SystemLog(methods="关联",type="关联")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd=getPageData(); 
	     PageData data = new PageData(); 
	     JSONObject getObj = new JSONObject();
	     try {  
	    	if(pd.getString("ID")!=null){ 
		    	PageData usrela=this.sysrelationService.usrela(pd);
		    	if(usrela!=null){//删除
		    		pd.put("ID", usrela.getString("ID"));
		    		 this.sysrelationService.relaDel(pd); 
		    		 getObj.put("statusCode", "300");//取消关联成功
		    	}else{
		    		 data.put("ID",get32UUID()); 
			    	 data.put("APPU_ID",pd.getString("ID"));
			    	 data.put("SYSU_ID",getUser().getID());
			    	 this.sysrelationService.save(data);  
			    	 getObj.put("statusCode", "200");//关联成功
		    	} 
	    	}else{
	    		getObj.put("statusCode", "400");//ID为空
	    	}
	     } catch (Exception e) {
	           this.logger.error(e.toString(), e); 
	     }   
		
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

package com.fh.controller.system.jl_cache;

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
import com.fh.service.jl_cache.cacheService;  
import com.fh.util.PageData;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog; 

@Controller
@RequestMapping({ "/jl_cachelist" })
public class cacheController extends BaseController { 
	
	@Resource(name = "cacheService")
	private cacheService cacheService;
	
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/jl_cache/jl_cache_list");
		return mv;
	}
	
//	@RequestMapping({"/toBidList"})
//	public ModelAndView toBidList() throws Exception {
//		ModelAndView mv = getModelAndView(); 
//		mv.setViewName("projects/pro_jiedan_list"); 
//		return mv;
//	}
	
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
		Object  total =null;//查询记录的总行数
		List<PageData> projectsList = this.cacheService.listByParam(page);
		total = this.cacheService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"MODIFY_DATE"});
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}  
	
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
	
	
	 @RequestMapping({"/toAdd"})
	 public ModelAndView toAdd(){ 
		ModelAndView mv = getModelAndView();   
	    mv.setViewName("system/jl_cache/jl_cache_add"); 
	    return mv;
	 } 
	 
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="缓存管理",type="缓存编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 pd=getPageData();   
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("MODIFY_DATE",new Date());
	    		//保存项目信息
	    		Object ob=this.cacheService.save(pd);
    	  }else{
    		  	PageData xgdata=new PageData();
    		  	pd.put("ID",pd.getString("ID"));
	    		pd.put("MODIFY_DATE",new Date());
    		  	Object xgob= this.cacheService.edit(pd);  
	   	 		 
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	   }  
	 
	 
	@RequestMapping({ "/delete" })
	@SystemLog(methods="缓存管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.cacheService.delete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	@RequestMapping({ "/update" })
	@SystemLog(methods="缓存管理",type="更新")
	@ResponseBody
	public String update() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.cacheService.update(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("code", "200");//
		return getObj.toString(); 
	}
	
	
	
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.cacheService.findById(pd); 
				mv.addObject("pd",pd); 
				mv.addObject("msg", "edit");
			    mv.setViewName("system/jl_cache/jl_cache_edit"); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
	} 
} 
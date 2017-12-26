package com.fh.controller.system.pyq;


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
import com.fh.service.system.pyq.PyqServices;
import com.fh.service.system.user.TbUserService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/friends" })
public class PyqController extends BaseController { 
	
	@Resource(name = "PyqServices")
	private PyqServices PyqServices;
	
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/pyq/pyq_list");
		return mv;
	}
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
//	@RequestMapping({"/toBidList"})
//	public ModelAndView toBidList() throws Exception {
//		ModelAndView mv = getModelAndView(); 
//		mv.setViewName("projects/pro_jiedan_list"); 
//		return mv;
//	}
	
	@RequestMapping(value={"/toPL"})
	public ModelAndView toPL() {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		pd.put("ID",pd.get("PID"));
		mv.addObject("PID", pd.get("PID")); 
		mv.setViewName("system/pyq/pyq_pl_list");
		return mv;
	}
	
	@RequestMapping(value={"/toDz"})
	public ModelAndView toDz() {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		pd.put("ID",pd.get("PID"));
		mv.addObject("PID", pd.get("PID")); 
		mv.setViewName("system/pyq/pyq_dz_list");
		return mv;
	}
	
	@RequestMapping(value={"/queryPLList"}) 
	@ResponseBody
	public String queryPLList(Page page) throws Exception {
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
		 
		String NAME = pd.getString("NAME"); 
		if ((NAME != null) && (!"".equals(NAME))) { 
			pd.put("REAL_NAME",NAME);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List<PageData> data = this.PyqServices.lsitplParam(page);
		Object	total = this.PyqServices.lsitplcounts(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:data){ 
			ResultUtil.resetRes(pro,new String[]{"COMMENT_DATE"});
			Object user=pro.get("USER_ID");
			if(user!=null&&!"".equals(user)){ 
			}else{
				pro.put("REAL_NAME","匿名用户");	
			} 
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping(value={"/queryDzList"}) 
	@ResponseBody
	public String queryDzList(Page page) throws Exception {
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
		 
		String NAME = pd.getString("NAME"); 
		if ((NAME != null) && (!"".equals(NAME))) { 
			pd.put("REAL_NAME",NAME);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List<PageData> data = this.PyqServices.lsitDZParam(page);
		Object	total = this.PyqServices.lsitDZcounts(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:data){ 
			ResultUtil.resetRes(pro,new String[]{"DATE"});
			Object user=pro.get("USER_ID");
			if(user!=null&&!"".equals(user)){ 
			}else{
				pro.put("REAL_NAME","匿名用户");	
			} 
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
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
		Object  total =null;//查询记录的总行数
		List<PageData> projectsList = this.PyqServices.listByParam(page);
		total = this.PyqServices.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"PUBLISH_DATE"});
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
	    mv.setViewName("system/pyq/pyq_add"); 
	    return mv;
	 } 
	 
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="朋友圈管理",type="朋友圈编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 pd=getPageData();   
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("MODIFY_DATE",new Date());
	    		//保存项目信息
	    		Object ob=this.PyqServices.save(pd);
    	  }else{
    		  	PageData xgdata=new PageData();
    		  	pd.put("ID",pd.getString("ID"));
	    		pd.put("MODIFY_DATE",new Date());
    		  	Object xgob= this.PyqServices.edit(pd);  
	   	 		 
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	   }  
	 
	 
	@RequestMapping({ "/delete" })
	@SystemLog(methods="朋友圈管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.PyqServices.delete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	
	
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.PyqServices.findById(pd); 
				mv.addObject("pd",pd); 
				mv.addObject("msg", "edit");
			    mv.setViewName("system/pyq/pyq_edit"); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
	}
	

	
	@RequestMapping({ "/user_list" })
	public ModelAndView user_list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/pyq/user_list");
		return mv;
	} 
	
	
	@RequestMapping({ "/querytbuser" })
	@ResponseBody
	public String querytbuser(Page page)throws Exception{
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
		 
		String contractName = pd.getString("REAL_NAME"); 
		if ((contractName != null) && (!"".equals(contractName))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("REAL_NAME",contractName);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> data = this.tbuserService.listShopUserByParam(page);
		Object  total = this.tbuserService.listShopCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(int i=0;i<data.size();i++){
			java.sql.Date duedate=(java.sql.Date)((PageData)data.get(i)).get("DUE_DATE");
			Object reddate=data.get(i).get("REGISTER_DATE");
			Object lldate=data.get(i).get("LAST_LOGIN_DATE");
		   if(duedate==null||"".equals(duedate)){
			   ((PageData)data.get(i)).put("DUE_DATE","");
		   }else{
			   String DUE_DATE =DateUtil.getTime(new Date(duedate.getTime()));
			   ((PageData)data.get(i)).put("DUE_DATE",DUE_DATE);
		   }
		   if(reddate==null||"".equals(reddate)){
			   ((PageData)data.get(i)).put("REGISTER_DATE","");
		   }else{
			   String REGISTER_DATE =reddate+"";
			   ((PageData)data.get(i)).put("REGISTER_DATE",REGISTER_DATE);
		   }
		   if(lldate==null||"".equals(lldate)){
			   ((PageData)data.get(i)).put("LAST_LOGIN_DATE","");
		   }else{
			   String LAST_LOGIN_DATE =lldate+"";
			   ((PageData)data.get(i)).put("LAST_LOGIN_DATE",LAST_LOGIN_DATE);
		   }
		   
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	
	@RequestMapping({ "/pldelete" })
	@SystemLog(methods="朋友圈管理",type="删除评论")
	@ResponseBody
	public String pldelete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.PyqServices.pldelete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
} 
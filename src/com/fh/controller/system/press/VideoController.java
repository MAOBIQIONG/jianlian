package com.fh.controller.system.press;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User; 
import com.fh.service.system.press.VideoService; 
import com.fh.util.PageData; 
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog; 

@Controller
@RequestMapping({ "/video" })
public class VideoController extends BaseController { 

	@Resource(name = "videoService")
	private VideoService videoService;  
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/press/video_list");
		return mv;
	}
	
	//查询所有视频信息
	@RequestMapping({ "/queryList" })
	@ResponseBody
	public String queryList(Page page)throws Exception{
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
		
		List<PageData> data = this.videoService.listPageByParam(page);
		Object  total = this.videoService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(PageData vedio:data){
			ResultUtil.resetRes(vedio,new String[]{"CREATE_DATE","MODIFY_DATE","AUDIT_DATE"});
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
		
	@RequestMapping({ "/toEdit" })
	public ModelAndView querymediaid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		PageData data=new PageData();
		if (pd.get("ID")!= null&&!"".equals(pd.get("ID"))) { 
			data=this.videoService.queryById(pd);    
		}  
		mv.addObject("data", data); 
		mv.setViewName("system/press/video_edit");  
		return mv;
	}
		 
	@RequestMapping({ "/edit" }) 
	@SystemLog(methods="视频管理",type="编辑")
	@ResponseBody
	public JSONObject edit()throws Exception{
		JSONObject obj = new JSONObject();
		PageData pd = new PageData(); 
		pd = getPageData();
		boolean op=false;
		if(pd.get("ID")!=null&&!"".equals(pd.get("ID"))){ //修改
			pd.put("MODIFY_DATE",new Date());
		  	pd.put("MODIFY_BY", getUser().getID()); 
		  	op=this.videoService.edit(pd);
	    }else{//新增
	    	pd.put("ID", get32UUID());
		  	pd.put("CREATE_DATE",new Date());
		  	pd.put("CREATE_BY", getUser().getID());
		  	op=this.videoService.save(pd);  
	    } 
		if(op){
			obj.put("statusCode", "200");  
		}else{
			obj.put("statusCode", "400");  
		}   
		return obj; 
	}  
		 
	//根据ID进行删除
	@RequestMapping({ "/delete" })
	@SystemLog(methods="视频管理",type="删除")
	@ResponseBody
	public JSONObject delete() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData(); 
			boolean op=this.videoService.delete(pd);
			if(op){
				obj.put("statusCode", "200");  
			}else{
				obj.put("statusCode", "400");  
			}   
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	}
		
	//上下线新闻
	@RequestMapping({ "/updates" })
	@SystemLog(methods="新闻管理",type="新闻上下线")
	@ResponseBody
	public JSONObject updates() { 
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData(); 
			boolean op=this.videoService.editStatus(pd);
			if(op){
				obj.put("statusCode", "200");  
			}else{
				obj.put("statusCode", "400");  
			}   
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	}
		
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	}  
}


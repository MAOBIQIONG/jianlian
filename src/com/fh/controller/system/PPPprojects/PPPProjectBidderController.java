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
import com.fh.service.system.PPPprojectService.PPPProjectBidderService;
import com.fh.service.system.PPPprojectService.PPPProjectService;
import com.fh.service.system.dictionaries.DictionariesService; 
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/PPPproBidder" })
public class PPPProjectBidderController extends BaseController { 
	
	@Resource(name = "PPPprojectBidderService")
	private PPPProjectBidderService PPPproBidderService;
	
	@Resource(name = "PPPprojectService")
	private PPPProjectService PPPprojectService;
	
	@RequestMapping(value={"/toBidder"})
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		pd.put("ID",pd.get("PROJECT_ID"));
		PageData data = this.PPPprojectService.findById(pd);
		mv.addObject("PROJECT_ID", pd.get("PROJECT_ID")); 
		mv.addObject("BIDDER_ID", pd.get("BIDDER_ID"));
		mv.addObject("IS_SIGNED", data.get("IS_SIGNED"));
		mv.setViewName("system/PPPprojects/PPPproject_bidder_list");
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
		
		List<PageData> data = this.PPPproBidderService.listByParam(page);
		Object	total = this.PPPproBidderService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData bidder:data){ 
			bidder.put("BID_DATE",bidder.get("BID_DATE")==null?"":bidder.get("BID_DATE").toString()); 
			Object user=bidder.get("USER_ID");
			if(user!=null&&!"".equals(user)){ 
			}else{
				bidder.put("REAL_NAME","匿名用户");	
			} 
		} 
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}  
	
	@RequestMapping({ "/delete" })
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData();
			//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
			this.PPPproBidderService.delete(pd); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	@RequestMapping({ "/update" })
	@SystemLog(methods="中标管理",type="确定签署合同")
	@ResponseBody
	public String updateStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData(); 
			pd.put("IS_SIGNED","1");
			pd.put("SIGNE_DATE",new Date());
			Object ob=this.PPPprojectService.updateSigned(pd);
			if(Integer.parseInt(ob.toString())>=1){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	@RequestMapping({ "/editStatus" })
	@SystemLog(methods="中标管理",type="编辑中标信息")
	@ResponseBody
	public String editStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData();  
	        pd.put("PHONE_DATE",new Date());
			Object ob=this.PPPproBidderService.edit(pd);
			if(Integer.parseInt(ob.toString())>=1){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	@RequestMapping({ "/editGroup" })
	@SystemLog(methods="中标管理",type="编辑讨论组成员信息")
	@ResponseBody
	public String editGroup() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData();  
			String res=this.PPPproBidderService.editGroup(pd); 
			if("success".equals(res)){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 
package com.fh.controller.system.clan;
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
import com.fh.service.system.clan.ApplicantService;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/applicant" })
public class ApplicantController extends BaseController { 
	
	@Resource(name = "applicantService")
	private ApplicantService applyService;   
	 
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData pd=null;
		this.applyService.updatedelhd(pd);
		mv.setViewName("system/clan/applicant_list");
		return mv;
	}

	//查询所有申请者信息
	@RequestMapping({ "/queryPageList" })
	@ResponseBody
	public String queryClan(Page page)throws Exception{
		
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
		
		List<PageData> applys = this.applyService.listByParam(page);
		Object  total = this.applyService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(PageData data:applys){ 
			 data.put("APPLY_DATE",data.get("APPLY_DATE")==null?"":data.get("APPLY_DATE").toString());
			 data.put("OPER_DATE",data.get("OPER_DATE")==null?"":data.get("OPER_DATE").toString());
		} 
		getObj.put("aaData", applys);//要以JSON格式返回
		return getObj.toString(); 
	}  
	
	//通过ID获取数据
	@RequestMapping({ "/queryById" })
	public ModelAndView queryById()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData Cert=this.applyService.findById(pd);
		mv.setViewName("system/clan/applicant-check");
		mv.addObject("data", Cert);  
		return mv;
	}
	
	@RequestMapping({ "/updates" })
	@SystemLog(methods="审核",type="修改")
	@ResponseBody
	public String updates() throws Exception {
		PageData pd = new PageData();
		JSONObject getObj = new JSONObject();
		pd = getPageData();
		pd.put("OPER_BY",getUser().getID()); 
		pd.put("OPER_DATE",new Date()); 
		Object ob=this.applyService.edit(pd);
		if(Integer.parseInt(ob.toString())>=1){ 
			getObj.put("statusCode","200");
		}else{
			getObj.put("statusCode","400");
		}  
		return getObj.toString();
	}
		
	@RequestMapping({ "/delById" })
	@SystemLog(methods="合伙人申请者管理",type="删除")
	@ResponseBody
	public JSONObject delById() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			 pd = getPageData(); 
			 Object ob=this.applyService.delete(pd);
			 if(Integer.parseInt(ob.toString())>=1){
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


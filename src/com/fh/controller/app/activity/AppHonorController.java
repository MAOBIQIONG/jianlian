package com.fh.controller.app.activity;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.activity.AppActivityHonorService;
import com.fh.service.app.activity.AppHonorService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appHonor" })
public class AppHonorController extends BaseController {  
	
	@Resource(name="appHonorService")
	private AppHonorService appHonorService;  
	
	@Resource(name="appActivityHonorService")
	private AppActivityHonorService appActHonorService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	 
	 @RequestMapping(value={"/addHonor"}) 
	 @ResponseBody
	 public PageData addHonor() throws Exception {
		 PageData _result=new PageData();
		 PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		 String NAME=pa.getString("NAME");
			if ((NAME != null) && (!"".equals(NAME))) { 
				NAME=new String(NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
				pa.put("NAME",NAME);
			}  
			String COMPANY_NAME=pa.getString("COMPANY_NAME");
			if ((COMPANY_NAME != null) && (!"".equals(COMPANY_NAME))) { 
				COMPANY_NAME=new String(COMPANY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
				pa.put("COMPANY_NAME",COMPANY_NAME);
			}  
			String POSITION=pa.getString("POSITION");
			if ((POSITION != null) && (!"".equals(POSITION))) { 
				POSITION=new String(POSITION.getBytes("ISO-8859-1"),"UTF-8").trim();
				pa.put("POSITION",POSITION);
			}
			
			Object AGE=pa.get("AGE"); 
			if(AGE==null||"".equals(AGE)){
				pa.put("AGE",0);
			}else{ 
				int amount=Integer.parseInt(AGE+"");
				pa.put("AGE",amount);
			}
			Object ob=new Object(); 
			if(pa.getString("ID")!=null&&!"".equals(pa.getString("ID"))){//修改嘉宾信息
				ob=this.appHonorService.edit(pa);
				if(Integer.valueOf(ob.toString()) >= 1){   
					_result = AppUtil.ss("edit", "01", "成功","true",null); 
			    }else{
			    	_result = AppUtil.ss(null, "90", "失败","false",null);
			    }
			}else{//新增嘉宾信息 
				pa.put("ID", get32UUID());
				pa.put("CREATE_BY", pa.getString("userid"));
				pa.put("CREATE_DATE", new Date());
				ob=this.appHonorService.save(pa);
				if(Integer.valueOf(ob.toString()) >= 1){
					PageData data=new PageData();
					data.put("ID", get32UUID());
					data.put("ACTIVITY_ID",pa.getString("ACTIVITY_ID"));
					data.put("HONOR_ID",pa.getString("ID"));
					Object obj=this.appActHonorService.save(data); 
					if(Integer.valueOf(obj.toString()) >= 1){
						_result = AppUtil.ss("add", "01", "成功","true",null);
					}else{
						_result = AppUtil.ss(null, "90", "失败","false",null);
					} 
			    }else{
			    	_result = AppUtil.ss(null, "90", "失败","false",null);
			    }
			}  
		    return _result; 
	} 
	 
    //通过ID获取详细信息
	@RequestMapping({ "/queryById" })
	@ResponseBody
	public PageData queryById()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData honor = this.appHonorService.queryById(pa); 
		_result = AppUtil.ss(honor, "01", "成功","true", null);
		return _result; 
	}
	
	 @RequestMapping({ "/delById" })
	 @ResponseBody
	 public PageData delById()throws Exception{
		 PageData _result=new PageData();
		 PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		 PageData pd=new PageData();
		 pd.put("HONOR_ID",pa.getString("ID"));
		 pd.put("ACTIVITY_ID",pa.getString("ACTIVITY_ID"));
		 Object ob= this.appHonorService.delete(pa); 
		 Object obj=this.appActHonorService.delete(pd); 
		 if(Integer.parseInt(ob+"")>=1&&Integer.parseInt(obj+"")>=1){
			 _result = AppUtil.ss(null, "01", "成功","true", null);
		 }else{
			 _result = AppUtil.ss(null, "90", "失败","true", null);
		 } 
		 return _result; 
	 }


	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
}


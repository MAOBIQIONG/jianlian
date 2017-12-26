package com.fh.controller.system.user;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.PUT;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.clan.ClanService;
import com.fh.service.system.company.TbcompanyService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.user.TbUserService;
import com.fh.service.system.user.UserService;
import com.fh.util.ChineseToEnglish;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

@Controller
@RequestMapping({ "/userEndtime" })
public class UserEndtimeController extends BaseController { 
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;  
	
	@Resource(name = "clanService")
	private ClanService clanService;
	
	@Resource(name = "tbcompanyService")
	private TbcompanyService comService;
	
	ChineseToEnglish  cte=new ChineseToEnglish();

	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/company/user-endtime");
		return mv;
	}
	
	@RequestMapping({ "/listPage" })
	@ResponseBody
	public String listPage(Page page)throws Exception{
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
		
		List<PageData> data = this.tbuserService.listPageByEndtime(page);
		Object  total = this.tbuserService.findCountByEndtime(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(int i=0;i<data.size();i++){
			java.sql.Date duedate=(java.sql.Date)((PageData)data.get(i)).get("DUE_DATE");
			Object reddate=data.get(i).get("REGISTER_DATE");
			Object lldate=data.get(i).get("LAST_LOGIN_DATE");
			Object UDATE=data.get(i).get("UPGRADE_DATE");
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
		   if(UDATE==null||"".equals(UDATE)){
			   data.get(i).put("UPGRADE_DATE","");
		   }else{
			   String UPGRADE_DATE =UDATE+"";
			   data.get(i).put("UPGRADE_DATE",UPGRADE_DATE);
		   }
		   
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
}


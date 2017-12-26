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
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.press.PressService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/Press" })
public class PressController extends BaseController {
	String menuUrl = "Press/listPress.do";

	@Resource(name = "pressservice")
	private PressService pressservice;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;  
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/press/column");
		return mv;
	}
	//查询所有公司信息
		@RequestMapping({ "/querypress" })
		@ResponseBody
		public String querypress(Page page)throws Exception{
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
			 
			String contractName = pd.getString("TITLE_CATEGORY_NAME");
			
//			if ((contractType != null) && (!"".equals(contractType))) { 
//				//String param=new String(contractType.getBytes("ISO-8859-1"),"UTF-8").trim();
//				pd.put("MODEL_TYPE",contractType);
//			} 
			if ((contractName != null) && (!"".equals(contractName))) { 
				//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
				pd.put("TITLE_CATEGORY_NAME",contractName);
			} 
//			if ((StartDate != null) && (!"".equals(StartDate))) {
//				StartDate = StartDate + " 00:00:00";
//				pd.put("START_DATE", StartDate);
//			}
//			if ((EndDate != null) && (!"".equals(EndDate))) {
//				EndDate = EndDate + " 00:00:00";
//				pd.put("END_DATE", EndDate);
//			}
		 
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			
			List data = this.pressservice.listByParam(page);
			Object  total = this.pressservice.findCount(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			for(int i=0;i<data.size();i++){
			   java.sql.Date cardate=(java.sql.Date)((PageData)data.get(i)).get("CREATE_DATE");
			   java.sql.Date moddate=(java.sql.Date)((PageData)data.get(i)).get("MODIFY_DATE");
			   if(cardate==null||"".equals(cardate)){
				   ((PageData)data.get(i)).put("CREATE_DATE","");
			   }else{
				   String CREATE_DATE =DateUtil.getTime(new Date(cardate.getTime()));
				   ((PageData)data.get(i)).put("CREATE_DATE",CREATE_DATE);
			   } 
			   if(moddate==null||"".equals(moddate)){
				   ((PageData)data.get(i)).put("MODIFY_DATE","");
			   }else{
				   String MODIFY_DATE =DateUtil.getTime(new Date(moddate.getTime()));
				   ((PageData)data.get(i)).put("MODIFY_DATE",MODIFY_DATE);
			   } 
			   
			}
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString();
		}
		
		
		@RequestMapping({ "/querypressid" })
		public ModelAndView querypressid()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			PageData pressmap=this.pressservice.querypressid(pd); 
			mv.setViewName("system/press/add-Press"); 
			mv.addObject("pressData", pressmap); 
			return mv;
		}
		
		
		@RequestMapping({ "/addpress" })
		@SystemLog(methods="栏目管理",type="编辑")
		@ResponseBody
		public String addpress()throws Exception{
			PageData pd = new PageData();
			try {
				pd = getPageData();
				String ID=pd.getString("ID");
				
				  if(ID==null||"".equals(ID)){ 
					  	pd.put("ID", get32UUID()); 
					  	pd.put("CREATE_DATE",new Date());
					  	pd.put("CREATE_BY", getUser().getID());
			    		//添加
			    		this.pressservice.addpress(pd); 
		    	  }else{ 
		    		//修改
		    		  pd.put("MODIFY_DATE",new Date()); 
		    	 	 pd.put("MODIFY_BY", getUser().getID());
		    	 	 //修改
		    	 	this.pressservice.uppress(pd);
		    	  } 
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
				JSONObject getObj = new JSONObject();
				getObj.put("statusCode", 200);
				return getObj.toString();
		}
		
		//批量删除
		@RequestMapping({ "/delpress" })
		@SystemLog(methods="栏目管理",type="批量删除")
		@ResponseBody
		public String delpress() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.pressservice.delpress(auserId);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
				} catch (Exception e) {
					this.logger.error(e.toString(), e);
				}
			JSONObject getObj = new JSONObject();
			getObj.put("status", "1");
			return getObj.toString();
		}
		
		//根据栏目名称进行查找
		@RequestMapping({ "/querypressname" })
		public String querypressname()throws Exception{
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();
			pd = getPageData();
			List dataList=this.pressservice.querypressname(pd);
			mv.setViewName("system/press/column");
			mv.addObject("pressmap", dataList);
			return "redirect:/Press/querypress";
		}
		
		//是否显示与关闭
		@RequestMapping({ "/upis_show" })
		@ResponseBody
		public String upis_show() {
			PageData page = new PageData();
			try {
				PageData pd = getPageData();
				PageData pagedata=(PageData) this.pressservice.querypressid(pd);
				if("1".equals(pagedata.get("IS_SHOW").toString())){ 
					page.put("IS_SHOW", 2);//关闭
				}
				else if("2".equals(pagedata.get("IS_SHOW").toString())){
					page.put("IS_SHOW", 1); //显示
				}
				page.put("ID", pagedata.getString("ID")); 
				this.pressservice.upis_show(page);
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode",200);
			System.out.println(getObj.get("statusCode"));
			return getObj.toString();
		}
		
		//根据ID进行删除
		@RequestMapping({ "/delpressByid" })
		@SystemLog(methods="栏目管理",type="删除")
		@ResponseBody
		public JSONObject delpressByid() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				this.pressservice.delpressByid(pd);
				obj.put("statusCode", "200");
				obj.put("message", "操作成功");
				obj.put("closeCurrent", false);
			    	
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


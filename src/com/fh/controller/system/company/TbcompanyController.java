package com.fh.controller.system.company;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.company.CopyService;
import com.fh.service.system.company.TbcompanyService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;

@Controller
@RequestMapping({ "/tbcompany" })
public class TbcompanyController extends BaseController {
	String menuUrl = "tbcompany/listtbcompany.do";

	@Resource(name = "tbcompanyService")
	private TbcompanyService tbcompanyService;
	@Resource(name = "copyService")
	private CopyService copyService;
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/company/business");
		return mv;
	}
	

	@RequestMapping({ "/querypanyid" })
	public ModelAndView querypanyid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		//String userId = pd.getString("userId");
		/*if ((panyId == null) || ("".equals(panyId))) {
		       pd.put("userId",get32UUID());
		       
		}*/
		PageData company=this.tbcompanyService.querypanyid(pd);
		List hyist=this.tbcompanyService.querybyhangye(pd);
		PageData data=new PageData();
		data.put("pid","0");
		List<PageData> typeList=this.copyService.queryByParam(data);
		List<PageData> lxList = this.copyService.queryByPid(data);   
		mv.setViewName("system/company/add-pany");
		mv.addObject("hyist", hyist); 
		mv.addObject("typeList", typeList); 
		mv.addObject("lxList", lxList); 
		mv.addObject("pageData", company); 
		return mv;
	}
	//查询所有公司信息
	@RequestMapping({ "/querysuoyoupany" })
	@ResponseBody
	public String querysuoyoupany(Page page)throws Exception{
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
		 
		String contractName = pd.getString("COMPANY_NAME");
		
//		if ((contractType != null) && (!"".equals(contractType))) { 
//			//String param=new String(contractType.getBytes("ISO-8859-1"),"UTF-8").trim();
//			pd.put("MODEL_TYPE",contractType);
//		} 
		if ((contractName != null) && (!"".equals(contractName))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("COMPANY_NAME",contractName);
		} 
//		if ((StartDate != null) && (!"".equals(StartDate))) {
//			StartDate = StartDate + " 00:00:00";
//			pd.put("START_DATE", StartDate);
//		}
//		if ((EndDate != null) && (!"".equals(EndDate))) {
//			EndDate = EndDate + " 00:00:00";
//			pd.put("END_DATE", EndDate);
//		}
	 
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> data = this.tbcompanyService.listByParam(page);
		Object  total = this.tbcompanyService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(int i=0;i<data.size();i++){
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date cardate=sdf.parse(data.get(i).getString("CREATE_DATE"));
			Date moddate=sdf.parse(data.get(i).getString("MODIFY_DATE"));
			//Date moddate=sdf.parse(data.get(i).getString("MODIFY_DATE"));
		   if(cardate==null||"".equals(cardate)){
			  data.get(i).put("CREATE_DATE","");
		   }else{
			   String CREATE_DATE =DateUtil.getTime(new Date(cardate.getTime()));
			   data.get(i).put("CREATE_DATE",CREATE_DATE);
		   }  
		   if(moddate==null||"".equals(moddate)){
			  data.get(i).put("MODIFY_DATE","");
		   }else{
			   String MODIFY_DATE =DateUtil.getTime(new Date(moddate.getTime()));
			   data.get(i).put("MODIFY_DATE",MODIFY_DATE);
		   }  
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
		
	}
	
	
	
	//批量删除
		@RequestMapping({ "/delpany" })
		@SystemLog(methods="企业管理",type="批量删除")
		@ResponseBody
		public String delpany() {
			PageData pd = new PageData();
			JSONObject obj = new JSONObject();
			try {
				pd = getPageData();
				//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				//this.projectService.delete(pd);
				String dasid = pd.getString("ID");
				String[] auserId= dasid.split(",");
				this.tbcompanyService.delpany(auserId);
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
	
	
	@RequestMapping({ "/addsss" })
	public ModelAndView addsss()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		//String userId = pd.getString("userId");
		/*if ((panyId == null) || ("".equals(panyId))) {
		       pd.put("userId",get32UUID());
		}*/
		pd.put("ID", get32UUID());
		mv.setViewName("system/company/add-pany");
		return mv;
	}
	
	//查看公司LOGO
	@RequestMapping({ "/look" })
	public ModelAndView look()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData com=this.tbcompanyService.querypanyid(pd);
		mv.setViewName("system/company/look-com-logo");
		mv.addObject("com", com); 
		return mv;
	}
	
	@RequestMapping({ "/addcompany" })
	@SystemLog(methods="企业管理",type="编辑")
	@ResponseBody
	public String addcompany()throws Exception{
		PageData pd = new PageData();
		try {
			pd = getPageData();
			  if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){ 
				  	pd.put("ID", get32UUID());
				  	pd.put("CREATE_DATE",new Date());
				  	pd.put("CREATE_BY", getUser().getID());
		    		//添加
		    		this.tbcompanyService.addcompany(pd);
	    	  }else{ 
	    		//修改
	    	 	 pd.put("ID",pd.getString("ID"));
	    	 	pd.put("CREATE_DATE",new Date());
			  	pd.put("CREATE_BY", getUser().getID());
	    	 	 //修改
	    	 	this.tbcompanyService.upcom(pd);
	    	  } 
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode", 200);
			return getObj.toString();
	}
	
	@RequestMapping({ "/upcom" })
	public ModelAndView upcom() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		
		try {
			pd = getPageData();
			pd.put("ID",get32UUID());
			this.tbcompanyService.upcom(pd);
			
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping({ "/delcompanybyid" })
	@SystemLog(methods="企业管理",type="删除")
	@ResponseBody
	public JSONObject delcompanybyid() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			this.tbcompanyService.delcompanybyid(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	//根据企业名称进行查找
	@RequestMapping({ "/querycompanyname" })
	public String querycompanyname()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		List dataList=this.tbcompanyService.querycompanyname(pd);
		mv.setViewName("system/company/business");
		mv.addObject("panymap", dataList);
		return "redirect:/tbcompany/querysuoyoupany";
	}
	
	
}


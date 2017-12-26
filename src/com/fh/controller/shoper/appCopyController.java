package com.fh.controller.shoper;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fh.controller.app.AppController; 
import com.fh.entity.Page;
import com.fh.service.system.company.CopyService; 
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/appcopy" })
public class appCopyController extends AppController { 

	@Resource(name = "copyService")
	private CopyService copyService;
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	public static String serverImgPath = fileConfig.getString("serverImgPath"); 
		
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("shoper/business");
		return mv;
	}
	

	@RequestMapping({ "/querypanyid" })
	public ModelAndView querypanyid()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		String COMPANY_ID = pd.getString("COMPANY_ID");
		PageData company = new PageData();
		if(COMPANY_ID != null && !"".equals(COMPANY_ID)){
			company=this.copyService.findGs(pd);
		}
		
		List hyist=this.copyService.querybyhangye(pd);
		PageData data=new PageData();
		data.put("pid","0");
		List<PageData> typeList=this.copyService.queryByParam(data);
		List<PageData> lxList = this.copyService.queryByPid(data);    
		mv.addObject("typeList", typeList);   
		mv.addObject("lxList", lxList); 
		mv.setViewName("shoper/add-pany");
		mv.addObject("hyist", hyist); 
		mv.addObject("pageData", company); 
		mv.addObject("typeList", typeList); 
		mv.addObject("lxList", lxList); 
		return mv;
	}
	
	@RequestMapping({ "/toaddcyl" })
	public ModelAndView toaddcyl()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		
		mv.setViewName("shoper/business_gs");
		return mv;
	} 
	
	@RequestMapping({ "/findGsdByname" })
	@ResponseBody
	public String findGsd()throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		List<PageData> company=(List<PageData>) this.copyService.findGsbuname(pd);
		JSONObject getObj = new JSONObject();
		getObj.put("comList", company);
		return getObj.toString();
		
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
		if ((contractName != null) && (!"".equals(contractName))) { 
			pd.put("COMPANY_NAME",contractName);
		} 
		pd.put("USERID",getUser().getID());
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List data = this.copyService.listByParam1(page);
		Object  total = this.copyService.findCount1(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
		
	}
	
	@RequestMapping({ "/addsss" })
	public ModelAndView addsss()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		pd.put("ID", get32UUID());
		mv.setViewName("shoper/add-pany");
		return mv;
	}
	
	//查看公司LOGO
	@RequestMapping({ "/look" })
	public ModelAndView look()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData com=this.copyService.querypanyid(pd);
		mv.setViewName("shoper/look-com-logo");
		mv.addObject("com", com); 
		return mv;
	}
	
	@RequestMapping({ "/addcompany" })
	@SystemLog(methods="公司管理",type="编辑")
	@ResponseBody
	public String addcompany()throws Exception{
		PageData pd = new PageData();
		try {
			pd = getPageData();
			  if(pd.getString("COMPANY_ID")==null||"".equals(pd.getString("COMPANY_ID"))){ 
				  this.copyService.addGs(pd,getUser().getID());
	    	  }else{ 
	    		//修改公司信息，
			  	pd.put("CREATE_BY", getUser().getID());
	    	 	 //修改
	    	 	this.copyService.editGs(pd);
	    	  } 
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
			JSONObject getObj = new JSONObject();
			getObj.put("statusCode", 200);
			return getObj.toString();
	}
	
	@RequestMapping({ "/saveCyl" })
	@ResponseBody
	public String saveCyl()throws Exception{
		PageData pd = new PageData();
		try {
			pd = getPageData();
			this.copyService.addGscy(pd,getUser().getID());
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
			this.copyService.upcom(pd);
			
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping({ "/delcompanybyid" })
	@SystemLog(methods="公司管理",type="删除")
	@ResponseBody
	public JSONObject delcompanybyid() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			this.copyService.delcompanybyid(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		    	
			} catch (Exception e) {
				this.logger.error(e.toString(), e);
			}
		return obj;
	}
	
	
	//荣誉管理
	@RequestMapping({ "/querybywsxx" })
	@SystemLog(methods="公司管理",type="荣誉管理")
	@ResponseBody
	public ModelAndView querybywsxx()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData data = new PageData();
		data = getPageData();
		List<PageData> ru=(List<PageData>) this.copyService.queryRy(data);
		mv.setViewName("shoper/wsxx-members");
		mv.addObject("ru", ru); 
		mv.addObject("data", data); 
		return mv;
	} 
	
	/**
	 * 保存公司荣誉信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/adduserxx" })
	@ResponseBody 
	public JSONObject adduserxx() throws Exception{ 
		PageData pd = new PageData(); 
		JSONObject obj = new JSONObject();
		pd = getPageData();
		//添加
		this.copyService.editXx(pd);  
		obj.put("status", "1"); 
		return obj; 
	}
	
	//上传logo
	@RequestMapping({ "/addLogo" })
	@ResponseBody 
	public JSONObject addLogo(MultipartHttpServletRequest request) throws Exception {
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();  
		MultipartFile file=request.getFile("LOGO_IMG");
		String fileName = "";
		if ((file != null) && (!file.isEmpty())) {
			String filePath =serverBasePath; 
			fileName = FileUpload.fileUp(file,filePath,get32UUID());
		}  
		obj.put("fileName", serverImgPath+fileName);  
		return obj;
	}
	//上传封面
		@RequestMapping({ "/addCOVER" })
		@ResponseBody 
		public JSONObject addCOVER(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			MultipartFile file=request.getFile("COVER_IMG");
			String fileName = "";
			if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				fileName = FileUpload.fileUp(file,filePath,get32UUID());
			}  
			obj.put("fileName", serverImgPath+fileName);  
			return obj;
		}
		
		//上传营业执照
		@RequestMapping({ "/addBUSINESS" })
		@ResponseBody 
		public JSONObject addBUSINESS(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			MultipartFile file=request.getFile("BUSINESS_IMG");
			String fileName = "";
			if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				fileName = FileUpload.fileUp(file,filePath,get32UUID());
			}  
			obj.put("fileName", serverImgPath+fileName);  
			return obj;
		}
		
		//查询可添加到产业链的所有公司信息
		@RequestMapping({ "/queryc" })
		@ResponseBody
		public String queryc(Page page)throws Exception{
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
			if ((contractName != null) && (!"".equals(contractName))) { 
				pd.put("COMPANY_NAME",contractName);
			} 
			pd.put("USERID",getUser().getID());
			page.setPd(pd);
			page.setOrderDirection(sSortDir_0);
			page.setOrderField(sortName);
			page.setPageSize(Integer.valueOf(iDisplayLength));
			page.setPageCurrent(Integer.valueOf(iDisplayStart));
			int initEcho = Integer.parseInt(sEcho) + 1;  
			
			List data = this.copyService.listByParam1(page);
			Object  total = this.copyService.findCount1(page).get("counts");//查询记录的总行数
			getObj.put("sEcho", initEcho);// 不知道这个值有什么用
			getObj.put("iTotalRecords", total);//实际的行数
			getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
			getObj.put("aaData", data);//要以JSON格式返回
			return getObj.toString();
			
		} 
}


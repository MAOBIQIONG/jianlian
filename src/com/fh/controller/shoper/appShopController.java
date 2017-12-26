package com.fh.controller.shoper;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.AppController;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User; 
import com.fh.service.shop.ShopService;
import com.fh.service.shoper.ShopsService;
import com.fh.service.system.user.TbUserService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.SystemLog; 
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/appshop" })
public class appShopController extends AppController {  
	
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@Resource(name = "shopsService")
	private ShopsService shopsService; 
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	 
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("shoper/shop_list");
		return mv;
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
		pd.put("USERID",getUser().getID());
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> shops = this.shopsService.listPageByParam(page);
		Object	total = this.shopsService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData data:shops){ 
			data.put("CREATE_DATE",data.get("CREATE_DATE")==null?"":data.get("CREATE_DATE").toString()); 
		} 
		getObj.put("aaData", shops);//要以JSON格式返回
		return getObj.toString();
	}  
	
	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData pd = new PageData();  
		pd.put("USER_ID",getUser().getID());
		PageData uscom=this.shopsService.queryByUsercomp(pd);
		mv.addObject("pd",uscom);  
	    mv.setViewName("shoper/shop_add");  
		return mv;
	}
	/*@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView(); 
	    mv.setViewName("shoper/shop_edit");  
		return mv;
	}*/
	 
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.shopsService.queryById(pd);   
			mv.addObject("pd",pd);  
			mv.setViewName("shoper/shop_edit"); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
	}
		
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="商城管理",type="店铺编辑")
	 @ResponseBody
	 public String save(MultipartHttpServletRequest request)throws Exception{ 
	     PageData pd = new PageData();
	     JSONObject getObj = new JSONObject();
	     Object ob=0;
	     try {  
	    	 pd=getPageData();   
	    	 MultipartFile file=request.getFile("SHOP_PORTRALT");
	    	 MultipartFile IMGS=request.getFile("SHOP_THEME");  
	    	 String ID=request.getParameter("ID"); 
	    	 pd.put("SHOP_NAME", request.getParameter("SHOP_NAME"));
	    	 pd.put("USER_ID", getUser().getID());
	    	 pd.put("ADDR", request.getParameter("ADDR"));
	    	 pd.put("SHOP_DESC", request.getParameter("SHOP_DESC")); 
	    	 if ((file != null) && (!file.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("SHOP_PORTRALT", fileName); 
	    	 } 
	    	 if ((IMGS != null) && (!IMGS.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(IMGS,filePath,get32UUID());
				pd.put("SHOP_THEME", fileName); 
	    	 } 
	    	 if(ID==null||"".equals(ID)){//新增  
	    		 pd.put("ID",get32UUID()); 
	    		 pd.put("CREATE_DATE",new Date());  
	    		 //保存店铺信息
	    		 ob=this.shopsService.save(pd);  
	    	 }else{//修改 
	    		 pd.put("ID",ID); 
	    		 ob=this.shopsService.edit(pd);  
	    	 }  
		 } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		 }   
	     if(Integer.parseInt(ob.toString())>=1){
	    	 getObj.put("statusCode", 200);
	     }else{
	    	 getObj.put("statusCode", 400);
	     } 
		 return getObj.toString(); 
	 }  
	 
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="店铺管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		JSONObject getObj = new JSONObject();
		Object ob=0;
		try {
			pd = getPageData(); 
			ob=this.shopsService.delete(pd);  
			if(Integer.parseInt(ob.toString())>=1){
				getObj.put("statusCode", 200);
			}else{
				getObj.put("statusCode", 400);
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return getObj.toString(); 
	} 
	 
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
	
	@RequestMapping({ "/user_list" })
	public ModelAndView user_list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("shoper/user_list");
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
} 
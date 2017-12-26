package com.fh.controller.link;

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

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.link.LinkService; 
import com.fh.service.system.operLog.OperLogService;
import com.fh.util.FileUpload;
import com.fh.util.PageData; 
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog; 
import com.fh.util.fileConfig; 

@Controller
@RequestMapping({ "/link" })
public class LinkController extends BaseController {  
	
	@Resource(name = "linkService")
	private LinkService linkService;  
	
	@Resource(name = "operLogService")
	private OperLogService operLogService;  
	
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("link/link_list");
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
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		List<PageData> links =this.linkService.listByParam(page);
		Object  total =this.linkService.findCount(page).get("counts");//查询记录的总行数  
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData link:links){ 
			ResultUtil.resetRes(link,new String[]{"MODIFY_DATE","CREATE_DATE"});
		} 
		getObj.put("aaData", links);//要以JSON格式返回
		return getObj.toString();
	}  
	
	 @RequestMapping({"/toEdit"})
	 public ModelAndView toEdit(){ 
		ModelAndView mv = getModelAndView();   
		try { 
			PageData pd=new PageData();
			pd = getPageData();
			if(pd.get("ID")!=null&&!"".equals(pd.get("ID"))){
				PageData data=this.linkService.findById(pd);
				mv.addObject("data", data);  
			}  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("link/link_edit"); 
	    return mv;
	 }   
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="产业链接管理",type="链接编辑")
	 @ResponseBody
	 public String save(MultipartHttpServletRequest request){ 
	     PageData pd = new PageData(); 
	     JSONObject getObj = new JSONObject();
		 try {  
			 pd=getPageData();
			 Object ID=pd.get("ID");
			 MultipartFile LOGO=request.getFile("LOGO");  
			 String COMPANY_NAME=new String(pd.getString("COMPANY_NAME").getBytes("ISO-8859-1"),"UTF-8");
			 pd.put("COMPANY_NAME",COMPANY_NAME); 
			 if ((LOGO != null) && (!LOGO.isEmpty())) {
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(LOGO,filePath,get32UUID());
				pd.put("LOGO", fileName); 
			 }  
	    	 boolean op=false;
	         if(ID==null||"".equals(ID)){//新增  
		    		pd.put("ID",get32UUID()); 
		    		pd.put("CREATE_DATE",new Date());  
		    		pd.put("CREATE_BY",getUser().getID());  
		    		op=this.linkService.save(pd);  
	         }else{//修改  
	        	  pd.put("ID",ID);
	        	  pd.put("MODIFY_DATE",new Date());
		  		  pd.put("MODIFY_BY", getUser().getID());  
	    	 	  op=this.linkService.edit(pd);  
    	  	 } 
	         if(op){
	        	  getObj.put("statusCode", "200");  
	  		 }else{
	  			  getObj.put("statusCode", "400");  
	  		 }   
		  } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		  }
		  return getObj.toString(); 
	   }  
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="产业链接管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData();
		JSONObject getObj = new JSONObject();
		try {
			pd = getPageData(); 
			boolean resu=this.linkService.delete(pd);   
			if(resu){
				getObj.put("statusCode", "200"); 
			}else{
				getObj.put("statusCode", "400"); 
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
} 
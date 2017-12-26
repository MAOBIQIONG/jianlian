package com.fh.controller.system.activity;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.AppCitiesService;
import com.fh.service.system.activity.ActivityHonorService;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.activity.HonorService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/honor" })
public class HonorController extends BaseController { 
	
	@Resource(name = "activityService")
	private ActivityService activityService;  
	
	@Resource(name="honorService")
	private HonorService honorService;  
	
	@Resource(name="activityHonorService")
	private ActivityHonorService actHonorService;
	
	//上传文件存放路径
	public static String serverBasePath = fileConfig.getString("serverBasePath");
		
	//活动嘉宾页面
	@RequestMapping({ "/toHonor"})
	public ModelAndView toHonor()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		mv.setViewName("system/activity/activity-honor-list"); 
		mv.addObject("ACTIVITY_ID", pd.get("ID")); 
		return mv;
	}
		
	//查询所有公司信息
	@RequestMapping({ "/queryHonor" })
	@ResponseBody
	public String queryHonor(Page page)throws Exception{
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
		
		List data = this.honorService.listPageByParam(page);
		Object  total = this.honorService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		for(int i=0;i<data.size();i++){ 
		   java.sql.Date createdate=(java.sql.Date)((PageData)data.get(i)).get("CREATE_DATE"); 
		   if(createdate==null||"".equals(createdate)){
			   ((PageData)data.get(i)).put("CREATE_DATE","");
		   }else{
			   String CREATE_DATE =DateUtil.getTime(new Date(createdate.getTime()));
			   ((PageData)data.get(i)).put("CREATE_DATE",CREATE_DATE);
		   }  
		}
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	} 
	

	 @RequestMapping({"/toEdit"})
	 public ModelAndView toAdd() throws Exception{ 
		ModelAndView mv = getModelAndView();  
		PageData pd =getPageData(); 
		PageData honor=honorService.queryById(pd);
		mv.addObject("honor",honor);
		mv.addObject("ACTIVITY_ID", pd.getString("ACTIVITY_ID"));
		mv.setViewName("system/activity/honor-edit"); 
	    return mv;
	 }
	 
	 @RequestMapping(value={"/save"}) 
	 @ResponseBody
	 public String save(MultipartHttpServletRequest request) throws Exception {
			JSONObject obj = new JSONObject();
			PageData pd = new PageData();  
			MultipartFile file=request.getFile("IMG_PATH");
			String ID=request.getParameter("ID"); 
			String ACTIVITY_ID=request.getParameter("ACTIVITY_ID");  
			String SEX=request.getParameter("SEX");
			int AGE=Integer.parseInt(request.getParameter("AGE"));
			String NAME=new String(request.getParameter("NAME").getBytes("ISO_8859-1"),"UTF-8");
			String COMPANY_NAME=new String(request.getParameter("COMPANY_NAME").getBytes("ISO_8859-1"),"UTF-8");
			String POSITION=new String(request.getParameter("POSITION").getBytes("ISO_8859-1"),"UTF-8");  
			
			pd.put("SEX", SEX);
			pd.put("AGE", AGE);//未审核
	   		pd.put("NAME",NAME); 
	   		pd.put("COMPANY_NAME", COMPANY_NAME);
			pd.put("POSITION",POSITION);
			pd.put("CREATE_BY", getUser().getID());  
			pd.put("CREATE_DATE", new Date());  
			if ((file != null) && (!file.isEmpty())) { 
				String filePath =serverBasePath; 
				String fileName = FileUpload.fileUp(file,filePath,get32UUID());
				pd.put("IMG_PATH", fileName); 
			}  
			if(ID==null||"".equals(ID)){ //添加
			  	pd.put("ID", get32UUID());  
	    		this.honorService.save(pd);
	    		PageData data=new PageData();
	    		data.put("ID", get32UUID());
	    		data.put("ACTIVITY_ID",ACTIVITY_ID );
	    		data.put("HONOR_ID",pd.getString("ID"));
	    		this.actHonorService.save(data);
	 	    }else{  //修改 
	 		   pd.put("ID",ID);
	 	 	   this.honorService.edit(pd);
	 	    } 
			obj.put("statusCode", 200);  
			return obj.toString();
		} 
	 
	 
	 @RequestMapping({ "/delHonorById" })
	 @ResponseBody
	 public String delHonorById() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData();
			pd.put("HONOR_ID",pd.getString("ID"));
			this.honorService.delete(pd); 
			this.actHonorService.delete(pd);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("statusCode", "200");//
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


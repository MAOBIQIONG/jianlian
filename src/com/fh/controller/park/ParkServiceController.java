package com.fh.controller.park;

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
import com.fh.service.park.ParkService;
import com.fh.service.park.ParkServiceService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.operLog.OperLogService;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog; 
import com.fh.util.fileConfig;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/parkService" })
public class ParkServiceController extends BaseController {  
	
	@Resource(name = "parkSerService")
	private ParkServiceService parkService;  
	
	@Resource(name = "operLogService")
	private OperLogService operLogService; 
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService; 
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("park/service_list");
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
		List<PageData> services =this.parkService.listByParam(page);
		Object  total =this.parkService.findCount(page).get("counts");//查询记录的总行数  
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData service:services){ 
			ResultUtil.resetRes(service,new String[]{"AUDIT_DATE","MODIFY_DATE","CREATE_DATE"});
		} 
		getObj.put("aaData", services);//要以JSON格式返回
		return getObj.toString();
	}  
	
	 @RequestMapping({"/toEdit"})
	 public ModelAndView toEdit(){ 
		ModelAndView mv = getModelAndView();   
		try { 
			PageData pd=new PageData();
			pd = getPageData();
			if(pd.get("ID")!=null&&!"".equals(pd.get("ID"))){
				PageData data=this.parkService.findById(pd);
				mv.addObject("data", data);  
			} 
			List<PageData> types=this.dicService.queryByPBM("Service_type");//查询园区类型 
			mv.addObject("types", types);   
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("park/service_edit"); 
	    return mv;
	 }   
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="园区服务管理",type="园区服务编辑")
	 @ResponseBody
	 public String save(MultipartHttpServletRequest request){ 
	     PageData pd = new PageData(); 
	     JSONObject getObj = new JSONObject();
		 try {  
			 pd=getPageData();
			 Object ID=pd.get("ID");
			 MultipartFile LOGO=request.getFile("LOGO");  
			 String COMPANY_NAME=new String(pd.getString("COMPANY_NAME").getBytes("ISO-8859-1"),"UTF-8");
			 String YQ_NAME=new String(pd.getString("YQ_NAME").getBytes("ISO-8859-1"),"UTF-8");
			 String INTRODUCTION=new String(pd.getString("COMPANY_INTRODUCTION").getBytes("ISO-8859-1"),"UTF-8");
			 pd.put("COMPANY_NAME",COMPANY_NAME);
			 pd.put("YQ_NAME",YQ_NAME);
			 pd.put("COMPANY_INTRODUCTION",INTRODUCTION); 
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
		    		op=this.parkService.save(pd);  
	         }else{//修改  
	        	  pd.put("ID",ID);
	        	  pd.put("MODIFY_DATE",new Date());
		  		  pd.put("MODIFY_BY", getUser().getID());  
	    	 	  op=this.parkService.edit(pd);  
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
	@SystemLog(methods="园区服务管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData();
		JSONObject getObj = new JSONObject();
		try {
			pd = getPageData(); 
			boolean resu=this.parkService.delete(pd);   
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
	
	@RequestMapping({ "/toCheck" })
	public ModelAndView toCheck(){
		ModelAndView mv = getModelAndView();   
		try { 
			PageData pd=new PageData();
			pd = getPageData();
			if(pd.get("ID")!=null&&!"".equals(pd.get("ID"))){
				PageData data=this.parkService.findById(pd);
				mv.addObject("data", data);  
			} 
			List<PageData> types=this.dicService.queryByPBM("Service_type");//查询园区类型 
			mv.addObject("types", types);   
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		mv.setViewName("park/service_check"); 
		return mv;
	}  
	
	@RequestMapping({ "/updateStatus" })
	@SystemLog(methods="园区服务管理",type="审核")
	@ResponseBody
	public String updateStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData();  
			pd.put("AUDIT_DATE",new Date());
	  		pd.put("AUDIT_BY", getUser().getID()); 
			boolean resu=this.parkService.updateStatus(pd);
			if(!resu){
				obj.put("statusCode","400"); 
				return obj.toString();
			} 
			PageData user=this.parkService.queryBySid(pd); 
			if(user!=null){
				String content="";
				if("02".equals(pd.getString("STATUS"))){
					content="您提交的园区服务申请审核未通过，快去消息中心查看原因吧！";
				}else{
					content="您提交的园区服务申请已通过审核！";
				}
				PageData notif=new PageData(); 
			  	notif.put("ID", get32UUID()); 
				notif.put("CREATE_DATE", new Date());
	   	 		notif.put("TABLE_NAME","ZS_PARK_SERVICE");
	   	 		notif.put("OBJECT_ID",pd.getString("ID")); 
	   	 	    notif.put("CONTENT",content); 
			    notif.put("USER_ID",user.getString("ID"));
			    notif.put("STATUS",pd.getString("STATUS"));   
			    sysNotifService.save(notif); 
			    push(user,content);
			}  
			obj.put("statusCode","200"); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	}
	
	public void push(PageData user,String content){   
//		NotificationTemplate template = null;
//		if( user.getString("PLATFORM") == "1" ){
//			template = PushUtil.notificationTemplateDemo(); 
//			template.setTitle("上海建联"); 
//			template.setText(content);
//		}
		String bt = "上海建联";
		String jsonStr = "{'type':'media','title':'"+bt+"','content':'"+content+"'}";//透传内容
		TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
		PushUtil pushApp=new PushUtil();
		String alias = user.getString("ID");
		try {
			pushApp.pushToSingle(tmTemplate,alias);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	} 
	
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 
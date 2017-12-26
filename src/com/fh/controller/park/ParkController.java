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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.park.ParkService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.operLog.OperLogService;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog; 
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/park" })
public class ParkController extends BaseController {  
	
	@Resource(name = "parkService")
	private ParkService parkService;    
	
	@Resource(name = "operLogService")
	private OperLogService operLogService; 
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService; 
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("park/park_list");
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
		List<PageData> parks =this.parkService.listByParam(page);
		Object  total =this.parkService.findCount(page).get("counts");//查询记录的总行数  
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData park:parks){ 
			ResultUtil.resetRes(park,new String[]{"PARK_DATE","AUDIT_DATE","MODIFY_DATE","CREATE_DATE"});
		} 
		getObj.put("aaData", parks);//要以JSON格式返回
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
			List<PageData> types=this.dicService.queryByPBM("park_type");//查询园区类型
			List<PageData> levels=this.dicService.queryByPBM("Industrial_Park_level");//查询园区级别
			mv.addObject("types", types);  
			mv.addObject("levels", levels);  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("park/park_edit"); 
	    return mv;
	 }   
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="产业园区管理",type="园区编辑")
	 @ResponseBody
	 public String save(){ 
	     PageData pd = new PageData(); 
	     JSONObject getObj = new JSONObject();
		 try {  
	    	  pd=getPageData(); 
	    	  boolean op=false;
	          if(pd.get("ID")==null||"".equals(pd.get("ID"))){//新增  
		    		pd.put("ID",get32UUID()); 
		    		pd.put("CREATE_DATE",new Date());  
		    		pd.put("CREATE_BY",getUser().getID());  
		    		op=this.parkService.save(pd);  
	          }else{//修改   
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
	@SystemLog(methods="产业园区管理",type="删除")
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
			List<PageData> types=this.dicService.queryByPBM("park_type");//查询园区类型
			List<PageData> levels=this.dicService.queryByPBM("Industrial_Park_level");//查询园区级别
			mv.addObject("types", types);  
			mv.addObject("levels", levels);  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		mv.setViewName("park/park_check"); 
		return mv;
	}  
	
	@RequestMapping({ "/updateStatus" })
	@SystemLog(methods="园区管理",type="审核")
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
			PageData user=this.parkService.queryByINid(pd); 
			if(user!=null){
				String content="";
				if("02".equals(pd.getString("STATUS"))){
					content="您提交的"+user.getString("PARK_NAME")+"审核未通过，快去消息中心查看原因吧！";
				}else{
					content="您提交的"+user.getString("PARK_NAME")+"已通过审核！";
				}
				PageData notif=new PageData(); 
			  	notif.put("ID", get32UUID()); 
				notif.put("CREATE_DATE", new Date());
	   	 		notif.put("TABLE_NAME","ZS_INVESTMENT");
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
		TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo(bt,content,jsonStr);
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
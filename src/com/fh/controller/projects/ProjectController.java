package com.fh.controller.projects;

import io.rong.RongCloud;
import io.rong.models.CodeSuccessResult;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ctc.wstx.util.StringUtil;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.projects.ProjectBackupService;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.service.projects.ProjectService;
import com.fh.service.projects.XmLxService;
import com.fh.service.projects.XmtpService;
import com.fh.service.system.area.AreaService;
import com.fh.service.system.company.CategoryService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.operLog.OperLogService;
import com.fh.service.system.user.TbUserService;
import com.fh.service.system.user.UserService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog;
import com.fh.util.excelexception.Exceldworup; 
import com.fh.util.generate_on.ProNoUtil;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/project" })
public class ProjectController extends BaseController{ 
	String appKey = "vnroth0kvfo6o";//替换成您的appkey
	String appSecret = "fEPFfFSclDYo";//替换成匹配上面key的secret
	RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "operLogService")
	private OperLogService operLogService;
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@Resource(name = "projectBackupService")
	private ProjectBackupService proBackupService;
	
	@Resource(name = "projectScheduleService")
	private ProjectScheduleService proScheService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService; 
	
	@Resource(name = "areaService")
	private AreaService areaService;
	
	@Resource(name = "xmLxService")
	private XmLxService xmlxService;  
	 
	@Resource(name = "categoryService")
	private CategoryService cateService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("projects/pro_xiadan_list");
		return mv;
	}
	
	@RequestMapping({"/toBidList"})
	public ModelAndView toBidList() throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData pd=null;
		this.projectService.updatedelhd(pd);
		mv.setViewName("projects/pro_jiedan_list"); 
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
		List<PageData> projectsList =null;
		Object  total =null;//查询记录的总行数
		if(pd.getString("STATUS")!=null&&!"".equals(pd.getString("STATUS"))){
			projectsList = this.projectService.queryByStatus(page);
			total = this.projectService.findCountByStatus(page).get("counts");//查询记录的总行数
		}else{
			projectsList = this.projectService.listByParam(page);
			total = this.projectService.findCount(page).get("counts");//查询记录的总行数
		} 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"DUE_DATE","BID_DATE","AUDIT_DATE","MODIFY_DATE","RELEASE_DATE","START_DATE","CREATE_DATE"});
			 if(pro.get("TYPE_NAME")==null || "".equals(pro.get("TYPE_NAME"))){
				 pro.put("TYPE_NAME", pro.get("FNAME"));
			 }
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}  
	
	 @RequestMapping({"/toAdd"})
	 public ModelAndView toAdd(){ 
		ModelAndView mv = getModelAndView();   
		try {
			List<PageData> userList = this.userService.listManager();
			PageData pd=new PageData();
			pd.put("pid","0");
			List<PageData> areaList = this.areaService.queryAllParent(pd);
			List<PageData> cityList = this.areaService.querybyPid(pd); 
			List<PageData> typeList=this.xmlxService.queryByParam(pd);
			List<PageData> lxList = this.xmlxService.queryByPid(pd);  
			List<PageData> cateList = this.cateService.listByPId("0"); 
			List<PageData> gcflList=this.dicService.queryByPBM("epcproject");
			mv.addObject("userList", userList);
			mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList);  
			mv.addObject("typeList", typeList);  
			mv.addObject("gcflList", gcflList); 
			mv.addObject("lxList", lxList);  
			mv.addObject("cateList", cateList);  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("projects/project_add"); 
	    return mv;
	 }  
	 
	 @RequestMapping({"/toxqAdd"})
	 public ModelAndView toxqAdd(){ 
		ModelAndView mv = getModelAndView();   
		try {
			List<PageData> userList = this.userService.listManager();
			PageData pd=new PageData();
			pd.put("pid","0");
			/*List<PageData> areaList = this.areaService.queryAllParent(pd);
			List<PageData> cityList = this.areaService.querybyPid(pd); */
			List<PageData> typeList=this.xmlxService.queryByParam(pd);
			List<PageData> lxList = this.xmlxService.queryByPid(pd);  
			mv.addObject("userList", userList);
			/*mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList);  */
			mv.addObject("typeList", typeList);   
			mv.addObject("lxList", lxList);  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("projects/demand_add"); 
	    return mv;
	 }  
	 
	 @RequestMapping({ "/goEdit" })
		public ModelAndView goEdit() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();  
			try {
				pd = getPageData();  
				pd = this.projectService.findById(pd);  
				if(pd.get("IS_RESOURCE").equals("1")){
					mv.addObject("pd",pd); 
					mv.addObject("msg", "edit");
				    mv.setViewName("projects/demand_edit"); 
				}else{
					List<PageData> userList = this.userService.listManager();
					PageData data=new PageData();
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> typeList=this.xmlxService.queryByParam(data);
					List<PageData> lxList = this.xmlxService.queryByPid(data);  
					List<PageData> cateList = this.cateService.listByPId("0");  
					List<PageData> gcflList=this.dicService.queryByPBM("epcproject");
					mv.addObject("userList", userList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("typeList", typeList);   
					mv.addObject("lxList", lxList); 
					mv.addObject("gcflList", gcflList); 
					mv.addObject("cateList", cateList);
					mv.addObject("pd",getPro(pd)); 
					mv.addObject("msg", "edit");
				    mv.setViewName("projects/project_edit"); 
				}
			} catch (Exception e) { 
				e.printStackTrace();
			}  
			return mv;
		}
		
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="项目管理",type="项目编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 String groupName = "";
	    	 pd=getPageData();  
	    	 ResultUtil.resetResInt(pd,new String[]{"PART_COUNT","START_PRICE","FLOOR_AREA","BUILD_LAYERS","BUILD_AREA","PROJECT_TYPE_ID"});
	    	 if(pd.getString("PROJECT_TYPE_IDS") != null && !"0".equals(pd.getString("PROJECT_TYPE_IDS"))){
	    		 pd.put("PROJECT_TYPE_ID",pd.getString("PROJECT_TYPE_IDS")==null||"".equals(pd.getString("PROJECT_TYPE_IDS"))?0:pd.getString("PROJECT_TYPE_IDS"));
	    	 } 
	         pd.put("MODIFY_BY",getUser().getID());
    		 pd.put("MODIFY_DATE",new Date());
	         PageData data = new PageData();//用来保存返回信息  
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("STATUS",1);//待审核
	    		pd.put("COLLECT_COUNT",0);
	    		pd.put("CREATE_DATE",new Date());
	    		getProNo(pd);
	    		/*if("1".equals(pd.getString("DISTINGUISH"))){
	    			
	    		}
	    		if("2".equals(pd.getString("DISTINGUISH"))){
	    			String pro_no=ProNoUtil.getPartProNo("EPC");
	    			PageData maxNo =projectService.queryMaxProNo(data);
		 	   	    if(maxNo!=null&&maxNo.getString("PRO_NO")!=null&&!"".equals(maxNo.getString("PRO_NO"))){
		 	   		   data.put("PRO_NO",pro_no+ProNoUtil.getProNo(maxNo.getString("PRO_NO")));  
		 	   	    }else{
		 	   		   pd.put("PRO_NO",pro_no+"0001");
		 	   	    }  
	    		} */
	    		
	    		if("0".equals(pd.getString("CITY"))){
	    			pd.put("CITY", pd.getString("AREA_ID"));
	    		}
	    		pd.put("IS_RESOURCE","0");//不是需求发布
	    		//保存项目信息
	    		Object ob=this.projectService.save(pd);
	    		/*//向项目阶段表中添加信息
	    		PageData sc=getPageData(); 
	    		sc.put("ID",get32UUID()); 
	    		sc.put("DATE",new Date());  
	    		sc.put("OPER_BY",getUser().getID());
	    		sc.put("PROJECT_ID",pd.getString("ID"));
	    		sc.put("DESCRIPTION","项目处于筹备阶段！");
	    		sc.put("SCHEDULE",pd.getString("PROJECT_STAGE_ID")); 
	    		//保存阶段信息
		    	this.proScheService.save(sc);  */
		    	
	    		data.put("STATUS",pd.get("STATUS")==null?1:Integer.parseInt(pd.get("STATUS").toString()));
	    		data.put("ID", get32UUID());
    		    data.put("PROJECT_ID", pd.getString("ID")); 
    	   		data.put("USER_ID","");
    	   		data.put("DATE", new Date()); 
    	   		data.put("OPER_BY",pd.getString("CREATE_BY"));
    	   		data.put("DESCRIPTION","项目审核中！");  
	    		data.put("TARGET_ID","");  
	    		//保存项目反馈信息  
    			this.proBackupService.save(data);  
    	  }else{//修改
    		  	PageData notif=new PageData();
    		  	PageData no=new PageData();
    		  	notif.put("ID", get32UUID()); 
    			no.put("ID", get32UUID()); 
    			
    			Integer flag = 0;
    			if( "".equals(pd.get("STATUS")) || pd.get("STATUS") == null ){
    				flag = 1;
    			}else{
    				flag = Integer.parseInt(pd.get("STATUS").toString());
    			}
    		    data.put("STATUS",pd.get("STATUS")==null?1:Integer.parseInt(pd.get("STATUS").toString()));
		    	  String content = "";	
		    	  String content2 = "";
		    	  String pName = pd.getString("PROJECT_NAME");
	    		  if(flag==5){// 
	    			  content = "您下单的"+pName+"项目正在确认中！";
	    			  content2 = "您接单的"+pName+"项目正在确认中！";
	    			  data.put("DESCRIPTION","交易确认中！"); 
	    			  data.put("TARGET_ID",pd.getString("BID_WINNER_ID")); 
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("STATUS","05"); 
	    			  no.put("CONTENT",content2); 
	    			  no.put("USER_ID",pd.getString("BID_WINNER_ID")); 
	    			  no.put("STATUS","05"); 
	    		  }else if(flag==2){ 
	    			  content = "您下单的"+pName+"项目审核未通过！";
	    			  data.put("DESCRIPTION","审核未通过！"); 
	    			  data.put("TARGET_ID","");
	    			  pd.put("AUDIT_BY",getUser().getID());
	    			  pd.put("AUDIT_DATE",new Date());
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("DESCRIPTION",pd.getString("NO_PASS_REASON")); 
	    			  notif.put("STATUS","02"); 
	    		  }else if(flag==3){ 
	    			  content = "您下单的"+pName+"项目审核通过！";
	    			  data.put("DESCRIPTION","审核通过！");
	    			  data.put("TARGET_ID","");
	    			  pd.put("AUDIT_BY",getUser().getID());
	    			  pd.put("AUDIT_DATE",new Date());
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("STATUS","03"); 
	    		  }else if(flag==4){ 
	    			  groupName = pd.getString("GROUP_NAME");
	    			  content = "您下单的"+pName+"项目已成功分配项目经理";
	    			  data.put("DESCRIPTION","项目经理分配成功！");
	    			  data.put("TARGET_ID","");
	    			  pd.put("RELEASE_DATE", new Date());
	    			  pd.put("GROUP_NAME",pd.getString("PROJECT_NAME"));
	    			  pd.put("PROJECT_STAGE_ID","13");//默认属于工程筹备阶段
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID")); 
	    			  notif.put("STATUS","04"); 
	    			  PageData sc=getPageData(); 
	  	    		  sc.put("ID",get32UUID()); 
	  	    		  sc.put("DATE",new Date());  
	  	    		  sc.put("OPER_BY",getUser().getID());
	  	    		  sc.put("PROJECT_ID",pd.getString("ID"));
	  	    		  sc.put("DESCRIPTION","项目处于筹备阶段！");
	  	    		  sc.put("SCHEDULE",pd.getString("PROJECT_STAGE_ID")); 
	  	    		  //保存阶段信息
	  		    	  this.proScheService.save(sc); 
	    		  }else{ 
	    			  data.put("DESCRIPTION","待审核！");  
	    			  data.put("TARGET_ID",""); 
	    		  } 
	    	 	 pd.put("ID",pd.getString("ID"));
	    	 	if("0".equals(pd.getString("CITY"))){
	    			pd.put("CITY", pd.getString("AREA_ID"));
	    		}
	    	 	 //修改项目信息
	    	 	 this.projectService.edit(pd); 
	    	 	 
	    	 	 //创建项目讨论群组
	    	 	 if( flag == 4 &&  ( "".equals(groupName) || "null".equals(groupName) || groupName == null) ){
	    	 		String[] groupCreateUserId = {pd.getString("PROJECT_MANAGER_ID")};
	    	 		CodeSuccessResult groupCreateResult = rongCloud.group.create(groupCreateUserId, pd.getString("ID"), pd.getString("PROJECT_NAME"));
	    	 		System.out.println("***************创建项目讨论群****************");
	    	 		System.out.println("创建项目讨论群组:  " + groupCreateResult.toString());
	    	 		if(groupCreateResult.getCode()==200){
	    	 			PageData xz=new PageData(); 
	    	 			xz.put("ID", get32UUID());
	    	 			xz.put("PRO_ID", pd.getString("ID"));
	    	 			xz.put("USER_ID",pd.getString("PROJECT_MANAGER_ID"));
	    	 			xz.put("DATE", new Date());
	    	 			xz.put("IS_ADMIN","1");
	    	 			xz.put("IS_APPUSER","0"); 
	    	 			projectService.saveXzcy(xz);
	    	 		} 
	    	 	 } 
	    	 	 data.put("PROJECT_ID", pd.getString("ID")); 
		   		 data.put("USER_ID",pd.getString("USER_ID"));
		   		 data.put("DATE", new Date());
		   		 data.put("STATUS",flag);
		   		 data.put("OPER_BY",pd.getString("CREATE_BY"));
		   		 //修改项目反馈信息
				 this.proBackupService.edit(data); 
				 
				 notif.put("CREATE_DATE", new Date());
	   	 		 notif.put("TABLE_NAME","project");
	   	 		 notif.put("OBJECT_ID",pd.getString("ID")); 
	   	 		 
				 //推送消息
	   	 		 PageData pagedata = new PageData();
		 		 pagedata.put("ID", pd.getString("USER_ID"));
			     PageData user = this.appusersService.queryById(pagedata);
			     //NotificationTemplate template = null;
			     String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
			     TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
			     PushUtil pushApp=new PushUtil();
				 String alias = pd.getString("USER_ID");
	 	 		 //if( user != null ){
	 	 		 	//推送审核消息
					//if( user.getString("PLATFORM") == "1" ){
						//template = PushUtil.notificationTemplateDemo(); 
						//template.setTitle("上海建联");
						//template.setText(content);
					//}
	 	 		 //}
	 	 		 
	   	 		 if(notif.getString("USER_ID")!=null&&!"".equals(notif.getString("USER_ID"))){
	   	 			 sysNotifService.save(notif); 
	   	 			 
	   	 		     //推送
					 String[] id = {notif.getString("USER_ID")};
					 pushApp.pushToSingle(tmTemplate,alias); 
	   	 		 }
	   	 		 no.put("CREATE_DATE", new Date());
	 	 		 no.put("TABLE_NAME","project");
	 	 		 no.put("OBJECT_ID",pd.getString("ID")); 
	   	 		 if(no.getString("USER_ID")!=null&&!"".equals(no.getString("USER_ID"))){
		 			 sysNotifService.save(no); 
		 			 
		 			 //推送审核消息
		 			 if( content2 != "" ){
						 String[] id = {no.getString("USER_ID")};
						 pushApp.pushToMany(tmTemplate,id);  
		 			 }
		 		 } 
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	   }  
	 
	 
	 @RequestMapping(value={"/savexq"})
	 @SystemLog(methods="项目管理",type="需求编辑")
	 @ResponseBody
	 public String savexq()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 String groupName = "";
	    	 pd=getPageData();   
	    	 pd.put("PART_COUNT",pd.getString("PART_COUNT")==null||"".equals(pd.getString("PART_COUNT"))?0:pd.getString("PART_COUNT"));
    	     pd.put("START_PRICE",pd.getString("START_PRICE")==null||"".equals(pd.getString("START_PRICE"))?0:pd.getString("START_PRICE"));
    	     pd.put("MODIFY_BY",getUser().getID());
    		 pd.put("MODIFY_DATE",new Date());
	         PageData data = new PageData();//用来保存返回信息  
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("STATUS",1);//待审核
	    		pd.put("COLLECT_COUNT",0);
	    		pd.put("CREATE_DATE",new Date());
	    		//pd.put("PROJECT_STAGE_ID","13");//默认属于工程筹备阶段
	    		pd.put("IS_RESOURCE","1");//是需求发布  
    			
    			String pro_no=ProNoUtil.getPartProNo("XMXQ");
    			PageData maxNo =projectService.queryMaxProNo(data);
	 	   	    if(maxNo!=null&&maxNo.getString("PRO_NO")!=null&&!"".equals(maxNo.getString("PRO_NO"))){
	 	   	    	pd.put("PRO_NO",pro_no+ProNoUtil.getProNo(maxNo.getString("PRO_NO")));  
	 	   	    }else{
	 	   	    	pd.put("PRO_NO",pro_no+"0001");
	 	   	    }  
	 	   	    
	    		//保存项目信息
	    		Object ob=this.projectService.save(pd);
		    	
	    		data.put("STATUS",pd.get("STATUS")==null?1:Integer.parseInt(pd.get("STATUS").toString()));
	    		data.put("ID", get32UUID());
    		    data.put("PROJECT_ID", pd.getString("ID")); 
    	   		data.put("USER_ID","");
    	   		data.put("DATE", new Date()); 
    	   		data.put("OPER_BY",pd.getString("CREATE_BY"));
    	   		data.put("DESCRIPTION","项目审核中！");  
	    		data.put("TARGET_ID","");  
	    		//保存项目反馈信息  
    			this.proBackupService.save(data);  
    	  }else{//修改
    		  	PageData notif=new PageData();
    		  	PageData no=new PageData();
    		  	notif.put("ID", get32UUID()); 
    			no.put("ID", get32UUID()); 
    			
    			Integer flag = 0;
    			if( "".equals(pd.get("STATUS")) || pd.get("STATUS") == null ){
    				flag = 1;
    			}else{
    				flag = Integer.parseInt(pd.get("STATUS").toString());
    			}
    		    data.put("STATUS",pd.get("STATUS")==null?1:Integer.parseInt(pd.get("STATUS").toString()));
		    	  String content = "";	
		    	  String content2 = "";
		    	  String pName = pd.getString("PROJECT_NAME");
	    		  if(flag==5){// 
	    			  content = "您下单的"+pName+"需求正在确认中！";
	    			  content2 = "您接单的"+pName+"需求正在确认中！";
	    			  data.put("DESCRIPTION","交易确认中！"); 
	    			  data.put("TARGET_ID",pd.getString("BID_WINNER_ID")); 
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("STATUS","05"); 
	    			  no.put("CONTENT",content2); 
	    			  no.put("USER_ID",pd.getString("BID_WINNER_ID")); 
	    			  no.put("STATUS","05"); 
	    		  }else if(flag==2){ 
	    			  content = "您下单的"+pName+"需求审核未通过！";
	    			  data.put("DESCRIPTION","审核未通过！"); 
	    			  data.put("TARGET_ID","");
	    			  pd.put("AUDIT_BY",getUser().getID());
	    			  pd.put("AUDIT_DATE",new Date());
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("DESCRIPTION",pd.getString("NO_PASS_REASON")); 
	    			  notif.put("STATUS","02"); 
	    		  }else if(flag==3){ 
	    			  content = "您下单的"+pName+"需求审核通过！";
	    			  data.put("DESCRIPTION","审核通过！");
	    			  data.put("TARGET_ID","");
	    			  pd.put("AUDIT_BY",getUser().getID());
	    			  pd.put("AUDIT_DATE",new Date());
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID"));
	    			  notif.put("STATUS","03"); 
	    		  }else if(flag==4){ 
	    			  groupName = pd.getString("GROUP_NAME");
	    			  content = "您下单的"+pName+"需求已成功分配项目经理";
	    			  data.put("DESCRIPTION","需求经理分配成功！");
	    			  data.put("TARGET_ID","");
	    			  pd.put("RELEASE_DATE", new Date());
	    			  pd.put("GROUP_NAME",pd.getString("PROJECT_NAME"));
	    			  pd.put("PROJECT_STAGE_ID","13");//默认属于工程筹备阶段
	    			  notif.put("CONTENT",content); 
	    			  notif.put("USER_ID",pd.getString("USER_ID")); 
	    			  notif.put("STATUS","04"); 
	    			//向项目阶段表中添加信息
	  	    		PageData sc=getPageData(); 
	  	    		sc.put("ID",get32UUID()); 
	  	    		sc.put("DATE",new Date());  
	  	    		sc.put("OPER_BY",getUser().getID());
	  	    		sc.put("PROJECT_ID",pd.getString("ID"));
	  	    		sc.put("DESCRIPTION","项目处于筹备阶段！");
	  	    		sc.put("SCHEDULE",pd.getString("PROJECT_STAGE_ID")); 
	  	    		//保存阶段信息
	  		    	this.proScheService.save(sc);  
	    		  }else{ 
	    			  data.put("DESCRIPTION","待审核！");  
	    			  data.put("TARGET_ID",""); 
	    		  } 
	    	 	 //修改项目信息
	    	 	 this.projectService.edit(pd); 
	    	 	 
	    	 	 //创建项目讨论群组
	    	 	 if( flag == 4 &&  ( "".equals(groupName) || "null".equals(groupName) || groupName == null) ){
	    	 		String[] groupCreateUserId = {pd.getString("PROJECT_MANAGER_ID")};
	    	 		CodeSuccessResult groupCreateResult = rongCloud.group.create(groupCreateUserId, pd.getString("ID"), pd.getString("PROJECT_NAME"));
	    	 		System.out.println("***************创建项目讨论群****************");
	    	 		System.out.println("创建项目讨论群组:  " + groupCreateResult.toString());
	    	 		if(groupCreateResult.getCode()==200){
	    	 			PageData xz=new PageData(); 
	    	 			xz.put("ID", get32UUID());
	    	 			xz.put("PRO_ID", pd.getString("ID"));
	    	 			xz.put("USER_ID",pd.getString("PROJECT_MANAGER_ID"));
	    	 			xz.put("DATE", new Date());
	    	 			xz.put("IS_ADMIN","1");
	    	 			xz.put("IS_APPUSER","0"); 
	    	 			projectService.saveXzcy(xz);
	    	 		} 
	    	 	 } 
	    	 	 data.put("PROJECT_ID", pd.getString("ID")); 
		   		 data.put("USER_ID",pd.getString("USER_ID"));
		   		 data.put("DATE", new Date());
		   		 data.put("STATUS",flag);
		   		 data.put("OPER_BY",pd.getString("CREATE_BY"));
		   		 //修改项目反馈信息
				 this.proBackupService.edit(data); 
				 
				 notif.put("CREATE_DATE", new Date());
	   	 		 notif.put("TABLE_NAME","project");
	   	 		 notif.put("OBJECT_ID",pd.getString("ID")); 
	   	 		 
				 //推送消息
	   	 		 PageData pagedata = new PageData();
		 		 pagedata.put("ID", pd.getString("USER_ID"));
			     PageData user = this.appusersService.queryById(pagedata);
//			     NotificationTemplate template = null;
			     String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
			     TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
			     PushUtil pushApp=new PushUtil();
				 String alias = pd.getString("USER_ID");
//	 	 		 if( user != null ){
//	 	 		 	//推送审核消息
//					if( user.getString("PLATFORM") == "1" ){
//						template = PushUtil.notificationTemplateDemo(); 
//						template.setTitle("上海建联");
//						template.setText(content);
//					}
//	 	 		 }
	 	 		 
	   	 		 if(notif.getString("USER_ID")!=null&&!"".equals(notif.getString("USER_ID"))){
	   	 			 sysNotifService.save(notif); 
	   	 			 
	   	 		     //推送
					 String[] id = {notif.getString("USER_ID")};
					 pushApp.pushToSingle(tmTemplate,alias); 
	   	 		 }
	   	 		 no.put("CREATE_DATE", new Date());
	 	 		 no.put("TABLE_NAME","project");
	 	 		 no.put("OBJECT_ID",pd.getString("ID")); 
	   	 		 if(no.getString("USER_ID")!=null&&!"".equals(no.getString("USER_ID"))){
		 			 sysNotifService.save(no); 
		 			 
		 			 //推送审核消息
		 			 if( content2 != "" ){
						 String[] id = {no.getString("USER_ID")};
						 pushApp.pushToMany(tmTemplate,id);  
		 			 }
		 		 } 
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	   }  
	 
	
	@RequestMapping({ "/delete" })
	@SystemLog(methods="项目管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.projectService.delete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	@RequestMapping({ "/toCheck" })
	public ModelAndView toCheck() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.projectService.findById(pd); 
			if(pd.get("IS_RESOURCE").equals("1")){
				List<PageData> userList = this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
				List<PageData> userLevelList=this.dicService.queryByPBM("viptype");
				List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
				mv.addObject("userList", userList); 
				mv.addObject("levelList", levelList); 
				mv.addObject("userLevelList", userLevelList); 
				mv.addObject("noPassList", noPassList); 
				mv.addObject("pd",pd); 
				mv.setViewName("projects/demand_check");  
				mv.addObject("msg", "check");
			}else{
				List<PageData> userList = this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				List<PageData> typeList=this.xmlxService.queryByParam(data);
				List<PageData> lxList = this.xmlxService.queryByPid(data);   
				List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
				List<PageData> userLevelList=this.dicService.queryByPBM("viptype");
				List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
				List<PageData> cateList = this.cateService.listByPId("0"); 
				List<PageData> gcflList=this.dicService.queryByPBM("epcproject");
				mv.addObject("userList", userList); 
				mv.addObject("areaList", areaList); 
				mv.addObject("cityList", cityList);
				mv.addObject("typeList", typeList);
				mv.addObject("lxList", lxList);
				mv.addObject("levelList", levelList); 
				mv.addObject("userLevelList", userLevelList); 
				mv.addObject("noPassList", noPassList); 
				mv.addObject("pd",getPro(pd)); 
				mv.addObject("cateList", cateList); 
				mv.addObject("gcflList", gcflList); 
				mv.setViewName("projects/project_check");  
				mv.addObject("msg", "check");
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}   
		return mv;
	}
	

	@RequestMapping({ "/toDis" })
	public ModelAndView toDis() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.projectService.findById(pd); 
			

			if(pd.get("IS_RESOURCE").equals("1")){
				List<PageData> userList =this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> managerList=this.userService.listManager();
				mv.addObject("managerList", managerList);
				mv.addObject("userList", userList); 
				mv.addObject("pd",pd); 
				mv.addObject("msg", "check"); 
				mv.setViewName("projects/demand_distribute");  
			}else{
				List<PageData> userList =this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				List<PageData> typeList=this.xmlxService.queryByParam(data);
				List<PageData> lxList = this.xmlxService.queryByPid(data);   
				List<PageData> managerList=this.userService.listManager();
				List<PageData> cateList = this.cateService.listByPId("0"); 
				mv.addObject("managerList", managerList);
				mv.addObject("userList", userList); 
				mv.addObject("areaList", areaList); 
				mv.addObject("cityList", cityList); 
				mv.addObject("typeList", typeList); 
				mv.addObject("lxList", lxList);
				mv.addObject("cateList", cateList); 
				mv.addObject("pd",getPro(pd)); 
				mv.addObject("msg", "check"); 
				mv.setViewName("projects/project_distribute");  
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}   
		
		return mv;
	}
	@RequestMapping({ "/gozhongbiao" })
	public ModelAndView gozhongbiao() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.projectService.findById(pd); 
			List<PageData> userList =this.userService.listManager();
			PageData data=new PageData();
			data.put("pid","0");
			List<PageData> areaList = this.areaService.queryAllParent(data);
			List<PageData> cityList = this.areaService.querybyPid(data); 
			List<PageData> typeList=this.xmlxService.queryByParam(data);
			List<PageData> lxList = this.xmlxService.queryByPid(data); 
			List<PageData> bidderList =projectService.findBidderByPid(pd);  
			List<PageData> cateList = this.cateService.listByPId("0"); 
			mv.addObject("cateList", cateList); 
			mv.addObject("userList", userList); 
			mv.addObject("areaList", areaList); 
			mv.addObject("cityList", cityList); 
			mv.addObject("typeList", typeList); 
			mv.addObject("lxList", lxList);   
			mv.addObject("bidderList", bidderList);  
			mv.addObject("pd",getPro(pd));  
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("projects/project_zhongbiao");  
		mv.addObject("msg", "zhongbiao"); 
		return mv;
	}
 
	@RequestMapping({ "/additionBidder" })
	public ModelAndView additionBidder() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			PageData data=new PageData();
			data.put("ID", pd.getString("USER_ID"));
			PageData user =tbuserService.queryById(data);
			pd = this.projectService.findById(pd);  
			
			List<PageData> userList =this.userService.listManager(); 
			data.put("pid","0");
			List<PageData> areaList = this.areaService.queryAllParent(data);
			List<PageData> cityList = this.areaService.querybyPid(data); 
			List<PageData> typeList=this.xmlxService.queryByParam(data);
			List<PageData> lxList = this.xmlxService.queryByPid(data);  
			List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
			List<PageData> userLevelList=this.dicService.queryByPBM("viptype");
			List<PageData> cateList = this.cateService.listByPId("0"); 
			mv.addObject("cateList", cateList); 
			mv.addObject("userList", userList); 
			mv.addObject("areaList", areaList); 
			mv.addObject("cityList", cityList); 
			mv.addObject("typeList", typeList); 
			mv.addObject("lxList", lxList);  
			mv.addObject("user", user);  
			mv.addObject("levelList", levelList); 
			mv.addObject("userLevelList", userLevelList);  
			mv.addObject("pd",getPro(pd));  
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("projects/select_bidder");  
		return mv;
	}
	
	@RequestMapping({ "/toLook" })
	public ModelAndView toLook() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			PageData data=new PageData();
			data.put("ID", pd.getString("USER_ID"));
			PageData user =tbuserService.queryById(data);
			pd = this.projectService.findById(pd);  
			
			
			if(pd.get("IS_RESOURCE").equals("1")){
				List<PageData> userList =this.userService.listManager(); 
				data.put("pid","0");
				List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
				List<PageData> userLevelList=this.dicService.queryByPBM("viptype");
				List<PageData> bidderList =projectService.findBidderByPid(pd);
				List<PageData> managerList=this.userService.listManager();
				mv.addObject("managerList", managerList);
				mv.addObject("userList", userList); 
				mv.addObject("user", user);  
				mv.addObject("levelList", levelList); 
				mv.addObject("userLevelList", userLevelList); 
				mv.addObject("bidderList", bidderList); 
				mv.addObject("pd",pd); 
				mv.setViewName("projects/demand_look"); 
			}else{
				List<PageData> userList =this.userService.listManager(); 
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				List<PageData> typeList=this.xmlxService.queryByParam(data);
				List<PageData> lxList = this.xmlxService.queryByPid(data);  
				List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
				List<PageData> userLevelList=this.dicService.queryByPBM("viptype");
				List<PageData> bidderList =projectService.findBidderByPid(pd);
				List<PageData> managerList=this.userService.listManager();
				List<PageData> cateList = this.cateService.listByPId("0");  
				List<PageData> gcflList=this.dicService.queryByPBM("epcproject");
				mv.addObject("managerList", managerList);
				mv.addObject("userList", userList); 
				mv.addObject("areaList", areaList); 
				mv.addObject("cityList", cityList); 
				mv.addObject("typeList", typeList); 
				mv.addObject("lxList", lxList);  
				mv.addObject("user", user);  
				mv.addObject("levelList", levelList); 
				mv.addObject("userLevelList", userLevelList); 
				mv.addObject("bidderList", bidderList); 
				mv.addObject("gcflList", gcflList); 
				mv.addObject("pd",getPro(pd));  
				mv.addObject("cateList", cateList); 
				mv.setViewName("projects/project_look"); 
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}     
		return mv;
	}
	
	@RequestMapping({ "/updateStatus" })
	@SystemLog(methods="接单管理",type="完成交易")
	@ResponseBody
	public String updateStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		PushUtil pushApp = new PushUtil();
		try {
			pd = getPageData(); 
			pd.put("COMPLETE_DATE",new Date());
			this.projectService.updateStatus(pd);
			PageData page=this.projectService.findById(pd);
			PageData data = new PageData(); 
			data.put("PROJECT_ID", pd.getString("ID")); 
	   		data.put("USER_ID",page.getString("USER_ID"));
	   		data.put("DATE", new Date());
	   		data.put("STATUS",pd.getString("STATUS"));
	   		data.put("OPER_BY",getUser().getID()); 
	   		data.put("DESCRIPTION","交易已完成！");  
    		data.put("TARGET_ID",page.getString("BID_WINNER_ID")); 
    		this.proBackupService.edit(data);
    	 	PageData notif=new PageData();
		  	PageData no=new PageData();
		  	notif.put("ID", get32UUID()); 
			notif.put("CREATE_DATE", new Date());
   	 		notif.put("TABLE_NAME","project");
   	 		notif.put("OBJECT_ID",pd.getString("ID")); 
   	 	    notif.put("CONTENT","您下单的项目已完成交易！"); 
		    notif.put("USER_ID",page.getString("USER_ID"));
		    notif.put("STATUS","06");   
		    
		    PageData pagedata = new PageData();
	 		pagedata.put("ID", page.getString("USER_ID"));
		    
		    //推送审核消息
		    TransmissionTemplate tmTemplate = null; 
   	 		if(notif.getString("USER_ID")!=null&&!"".equals(notif.getString("USER_ID"))){
   	 			sysNotifService.save(notif); 
   	 			
   	 			String content = "您下单的"+pd.getString("PROJECT_NAME")+"项目已完成交易！";
   	 		    //推送审核消息
   	 		    String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
				tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
				String alias = notif.getString("USER_ID");
				pushApp.pushToSingle(tmTemplate,alias); 
   	 		}
   	 		
   	 		no.put("ID", get32UUID()); 
   	 		no.put("CREATE_DATE", new Date());
 	 		no.put("TABLE_NAME","project");
 	 		no.put("OBJECT_ID",pd.getString("ID"));
 	 		no.put("CONTENT","您已确认为接单人，交易完成！"); 
 			no.put("USER_ID",page.getString("BID_WINNER_ID")); 
 			no.put("STATUS","06"); 
   	 		if(no.getString("USER_ID")!=null&&!"".equals(no.getString("USER_ID"))){
	 			sysNotifService.save(no); 
	 			//推送审核消息
	 			String content = "您已确认为"+pd.getString("PROJECT_NAME")+"项目接单人,交易完成！";
	 			String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
				tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
				String[] id = {no.getString("USER_ID")};
				pushApp.pushToMany(tmTemplate,id);
	 		} 
			obj.put("status",200);
			obj.put("message", "操作成功"); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	}
	
	@RequestMapping({ "/editStatus" })
	@SystemLog(methods="接单管理",type="审核和分配")
	@ResponseBody
	public String editStatus() {
		PageData pd = new PageData(); 
	     try { 
	    	 String groupName = "";
	    	 pd=getPageData();  
	         pd.put("MODIFY_BY",getUser().getID());
	         pd.put("MODIFY_DATE",new Date());
	         PageData data = new PageData();//用来保存返回信息   
   		  	PageData notif=new PageData();
   		  	PageData no=new PageData();
   		  	notif.put("ID", get32UUID()); 
   			no.put("ID", get32UUID());  
   			int flag = Integer.parseInt(pd.get("STATUS").toString()); 
   		    data.put("STATUS",pd.get("STATUS")==null?1:flag);
	    	  String content = "";	
	    	  String content2 = "";
	    	  String pName = pd.getString("PROJECT_NAME");
    		  if(flag==5){// 
    			  content = "您下单的"+pName+"项目正在确认中！";
    			  content2 = "您接单的"+pName+"项目正在确认中！";
    			  data.put("DESCRIPTION","交易确认中！"); 
    			  data.put("TARGET_ID",pd.getString("BID_WINNER_ID")); 
    			  notif.put("CONTENT",content); 
    			  notif.put("USER_ID",pd.getString("USER_ID"));
    			  notif.put("STATUS","05"); 
    			  no.put("CONTENT",content2); 
    			  no.put("USER_ID",pd.getString("BID_WINNER_ID")); 
    			  no.put("STATUS","05"); 
    		  }else if(flag==2){ 
    			  content = "您下单的"+pName+"项目审核未通过！";
    			  data.put("DESCRIPTION","审核未通过！"); 
    			  data.put("TARGET_ID","");
    			  pd.put("AUDIT_BY",getUser().getID());
    			  pd.put("AUDIT_DATE",new Date());
    			  notif.put("CONTENT",content); 
    			  notif.put("USER_ID",pd.getString("USER_ID"));
    			  notif.put("DESCRIPTION",pd.getString("NO_PASS_REASON")); 
    			  notif.put("STATUS","02"); 
    		  }else if(flag==3){ 
    			  content = "您下单的"+pName+"项目审核通过！";
    			  data.put("DESCRIPTION","审核通过！");
    			  data.put("TARGET_ID","");
    			  pd.put("AUDIT_BY",getUser().getID());
    			  pd.put("AUDIT_DATE",new Date());
    			  notif.put("CONTENT",content); 
    			  notif.put("USER_ID",pd.getString("USER_ID"));
    			  notif.put("STATUS","03"); 
    			  
    			  /**审核成功推送给所用用户**/
    			  String bt = "上海建联";
				  String nr = pName+"项目发布成功，快去了解吧";
    			  PageData all = new PageData();
    			  all.put("CREATE_DATE", new Date());
    			  all.put("TABLE_NAME","");
    			  all.put("OBJECT_ID","");
    			  all.put("STATUS","01"); 
    			  all.put("CONTENT",nr);
    			  all.put("ID", get32UUID());
    			  all.put("USER_ID", "jianlian");//默认为系统通知消息
    			  int val = (Integer)sysNotifService.save(all);
    			  if( val > 0 ){
    				  String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'"+bt+"','content':'"+nr+"'}";//透传内容
    				  TransmissionTemplate tmTemplates = PushUtil.transmissionTemplateDemo(bt,nr,jsonStr);
    				  //tmTemplates.setTransmissionContent(nr);
    				  PushUtil pushApp=new PushUtil();
    				  String result=pushApp.pushToAll(tmTemplates);
      			      System.out.println("审核成功，向全部用户推送："+result.toString());
    			  }
    		  }else if(flag==4){ 
    			  groupName = pd.getString("GROUP_NAME");
    			  content = "您下单的"+pName+"项目已成功分配项目经理";
    			  data.put("DESCRIPTION","项目经理分配成功！");
    			  data.put("TARGET_ID","");
    			  pd.put("RELEASE_DATE", new Date());
    			  pd.put("GROUP_NAME",pd.getString("PROJECT_NAME"));
    			  pd.put("PROJECT_STAGE_ID","13");//默认属于工程筹备阶段
    			  notif.put("CONTENT",content); 
    			  notif.put("USER_ID",pd.getString("USER_ID")); 
    			  notif.put("STATUS","04"); 
    			  PageData sc=getPageData(); 
  	    		  sc.put("ID",get32UUID()); 
  	    		  sc.put("DATE",new Date());  
  	    		  sc.put("OPER_BY",getUser().getID());
  	    		  sc.put("PROJECT_ID",pd.getString("ID"));
  	    		  sc.put("DESCRIPTION","项目处于筹备阶段！");
  	    		  sc.put("SCHEDULE",pd.getString("PROJECT_STAGE_ID")); 
  	    		  //保存阶段信息
  		    	  this.proScheService.save(sc); 
    		  }else{ 
    			  data.put("DESCRIPTION","待审核！");  
    			  data.put("TARGET_ID",""); 
    		  }  
    	 	 //修改项目信息
    	 	 this.projectService.edit(pd); 
    	 	 
    	 	 //创建项目讨论群组
    	 	 if( flag == 4 &&  ( "".equals(groupName) || "null".equals(groupName) || groupName == null) ){
    	 		String[] groupCreateUserId = {pd.getString("PROJECT_MANAGER_ID")};
    	 		CodeSuccessResult groupCreateResult = rongCloud.group.create(groupCreateUserId, pd.getString("ID"), pd.getString("PROJECT_NAME"));
    	 		System.out.println("***************创建项目讨论群****************");
    	 		System.out.println("创建项目讨论群组:  " + groupCreateResult.toString());
    	 		if(groupCreateResult.getCode()==200){
    	 			PageData xz=new PageData(); 
    	 			xz.put("ID", get32UUID());
    	 			xz.put("PRO_ID", pd.getString("ID"));
    	 			xz.put("USER_ID",pd.getString("PROJECT_MANAGER_ID"));
    	 			xz.put("DATE", new Date());
    	 			xz.put("IS_ADMIN","1");
    	 			xz.put("IS_APPUSER","0"); 
    	 			projectService.saveXzcy(xz);
    	 		} 
    	 	 } 
    	 	 data.put("PROJECT_ID", pd.getString("ID")); 
	   		 data.put("USER_ID",pd.getString("USER_ID"));
	   		 data.put("DATE", new Date());
	   		 data.put("STATUS",flag);
	   		 data.put("OPER_BY",pd.getString("CREATE_BY"));
	   		 //修改项目反馈信息
			 this.proBackupService.edit(data); 
			 
			 notif.put("CREATE_DATE", new Date());
   	 		 notif.put("TABLE_NAME","project");
   	 		 notif.put("OBJECT_ID",pd.getString("ID")); 
   	 		 
			 //推送消息
   	 		 PageData pagedata = new PageData();
	 		 pagedata.put("ID", pd.getString("USER_ID"));
		     PageData user = this.appusersService.queryById(pagedata);
		     NotificationTemplate template = null;
		     String jsonStr = "{'type':'project','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
		     TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
		     PushUtil pushApp=new PushUtil();
			 String alias = pd.getString("USER_ID");
 	 		 if( user != null ){
 	 		 	//推送审核消息
					if( user.getString("PLATFORM") == "1" ){
						template = PushUtil.notificationTemplateDemo(); 
						template.setTitle("上海建联");
						template.setText(content);
					}
 	 		 }
 	 		 
   	 		 if(notif.getString("USER_ID")!=null&&!"".equals(notif.getString("USER_ID"))){
   	 			 sysNotifService.save(notif); 
   	 			 
   	 		     //推送
				 String[] id = {notif.getString("USER_ID")};
				 pushApp.pushToSingle(tmTemplate,alias); 
   	 		 }
   	 		 no.put("CREATE_DATE", new Date());
 	 		 no.put("TABLE_NAME","project");
 	 		 no.put("OBJECT_ID",pd.getString("ID")); 
   	 		 if(no.getString("USER_ID")!=null&&!"".equals(no.getString("USER_ID"))){
	 			 sysNotifService.save(no); 
	 			 
	 			 //推送审核消息
	 			 if( content2 != "" ){
					 String[] id = {no.getString("USER_ID")};
					 pushApp.pushToMany(tmTemplate,id);  
	 			 }
	 		 }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	}
	
	//城市匹配
	public PageData getPro(PageData pd){
		String code=pd.getString("AREACODE");
		if("310000".equals(code)){
			pd.put("PARENTID","310000");
		}
		if("110000".equals(code)){
			pd.put("PARENTID","110000");
		}
		if("120000".equals(code)){
			pd.put("PARENTID","120000");
		}
		if("500000".equals(code)){
			pd.put("PARENTID","500000");
		}
		return pd;
	} 
	
	//获取项目编号
	public PageData getProNo(PageData data){ 
		try {
			PageData cate = projectService.queryNameById(data);
			String pro_no="";
			if("2".equals(data.getString("DISTINGUISH"))){
				pro_no=ProNoUtil.getPartProNo("XMXQ"); 
	    	}else{
	    		pro_no=ProNoUtil.getPartProNo(cate.getString("NAME")); 
	    	}  
			data.put("pro_no",pro_no);
	   	    PageData maxNo =projectService.queryMaxProNo(data);
	   	    if(maxNo!=null&&maxNo.getString("PRO_NO")!=null&&!"".equals(maxNo.getString("PRO_NO"))){
	   		   data.put("PRO_NO",pro_no+ProNoUtil.getProNo(maxNo.getString("PRO_NO")));  
	   	    }else{
	   		   data.put("PRO_NO",pro_no+"0001");
	   	    }  
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return data;
	} 
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
	 
	
 
	
	 	@RequestMapping(value = "exportfeedback")
	    @ResponseBody
	    public String exportFeedBack(HttpServletResponse response) throws Exception{
	    	JSONObject getObj = new JSONObject();
	        String fileName = "项目信息"+System.currentTimeMillis()+".xls"; //文件名 
	        String sheetName = "项目信息";//sheet名 
	        String []title = new String[]{"项目编号","项目名称","发布人","项目地址","项目内容","项目类型","企业资质","招标要求","起始时间","截止时间","项目经理","项目状态"};//标题
	        PageData pd=null;
	        List<PageData> list = this.projectService.doexlelist(pd);//内容list
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        String [][]values = new String[list.size()][];
	        for(int i=0;i<list.size();i++){
	            values[i] = new String[title.length];
	            //将对象内容转换成string
	            PageData obj = list.get(i);  
	            values[i][0] = obj.getString("PRO_NO")+"";
	            values[i][1] = obj.getString("PROJECT_NAME");
	            values[i][2] = obj.getString("CREATE_NAME");
	            values[i][3] = obj.getString("ADDR");
	            values[i][4] = obj.getString("PROJECT_CONTENT");
	            values[i][5] = obj.getString("TYPE_NAME");
	            values[i][6] = obj.getString("LEVEL_NAME");
	            values[i][7] = obj.getString("BID_NAME"); 
	            values[i][8] = sdf.format(obj.get("START_DATE"));
	            values[i][9] = sdf.format(obj.get("DUE_DATE"));
	            values[i][10] = obj.getString("MANAGER_NAME");
	            values[i][11] = obj.getString("PROJECT_STATUS");  
	            
	        }
	        
	        HSSFWorkbook wb = Exceldworup.getHSSFWorkbook(sheetName, title, values, null);
	        
	        //将文件存到指定位置  
	        try {  
	             this.setResponseHeader(response, fileName);  
	             OutputStream os = response.getOutputStream();  
	             wb.write(os);  
	             os.flush();  
	             os.close();  
	        } catch (Exception e) {  
	             e.printStackTrace();  
	        }  
	        return getObj.toString();
	    }
	    
	     public void setResponseHeader(HttpServletResponse response, String fileName) {  
	         try {  
	              try {  
	                   fileName = new String(fileName.getBytes(),"ISO8859-1");  
	              } catch (UnsupportedEncodingException e) {  
	                   // TODO Auto-generated catch block  
	                   e.printStackTrace();  
	              }  
	              response.setContentType("application/octet-stream;charset=ISO8859-1");  
	              response.setHeader("Content-Disposition", "attachment;filename="+ fileName);  
	              response.addHeader("Pargam", "no-cache");  
	              response.addHeader("Cache-Control", "no-cache");  
	         } catch (Exception ex) {  
	              ex.printStackTrace();  
	         }  
	    } 
	
} 
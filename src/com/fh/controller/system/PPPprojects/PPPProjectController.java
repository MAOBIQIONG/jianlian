package com.fh.controller.system.PPPprojects;

import io.rong.RongCloud;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.projects.ProjectBackupService;
import com.fh.service.projects.XmLxService;
import com.fh.service.system.PPPprojectService.PPPProjectService;
import com.fh.service.system.area.AreaService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.user.TbUserService;
import com.fh.service.system.user.UserService;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.ResultUtil;
import com.fh.util.SystemLog;
import com.fh.util.generate_on.ProNoUtil;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/PPPproject" })
public class PPPProjectController extends BaseController { 
	String appKey = "vnroth0kvfo6o";//替换成您的appkey
	String appSecret = "fEPFfFSclDYo";//替换成匹配上面key的secret
	RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	
	@Resource(name = "PPPprojectService")
	private PPPProjectService PPPprojectService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "areaService")
	private AreaService areaService;
	
	@Resource(name = "xmLxService")
	private XmLxService xmlxService; 
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "projectBackupService")
	private ProjectBackupService proBackupService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/PPPprojects/PPPpro_xiadan_list");
		return mv;
	}
	
	@RequestMapping({"/toBidList"})
	public ModelAndView toBidList() throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData pd=null;
		this.PPPprojectService.updatedelhd(pd);
		mv.setViewName("system/PPPprojects/PPPpro_jiedan_list"); 
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
			projectsList = this.PPPprojectService.queryByStatus(page);
			total = this.PPPprojectService.findCountByStatus(page).get("counts");//查询记录的总行数
		}else{
			projectsList = this.PPPprojectService.listByParam(page);
			total = this.PPPprojectService.findCount(page).get("counts");//查询记录的总行数
		} 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			 ResultUtil.resetRes(pro,new String[]{"LAUNCH_DATE","DUE_DATE","RELEASE_TIME","START_DATE","AUDIT_DATE","DUE_DATE","RELEASE_TIME","CREATE_DATE","COMPLETE_DATE","BIDDER_DATE","MODIFY_DATE"});
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}
	
	
	
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.PPPprojectService.findById(pd);  
			List<PageData> userList = this.userService.listManager();
			PageData data=new PageData();
			data.put("pid","0");
			List<PageData> areaList = this.areaService.queryAllParent(data);
			List<PageData> cityList = this.areaService.querybyPid(data); 
			List<PageData> typeList=this.xmlxService.queryByParam(data);
			List<PageData> lxList = this.xmlxService.queryByPid(data);  
			List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
			List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
			List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
			mv.addObject("userList", userList);
			mv.addObject("areaList", areaList);  
			mv.addObject("cityList", cityList);  
			mv.addObject("typeList", typeList);  
			mv.addObject("levelList", levelList);
			mv.addObject("lxList", lxList); 
			mv.addObject("gcflList", gcflList); 
			mv.addObject("cateList", cateList);
			mv.addObject("pd",getPro(pd)); 
			mv.addObject("msg", "edit");
		    mv.setViewName("system/PPPprojects/PPPproject_edit");  
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		return mv;
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
		
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="PPP项目管理",type="PPP项目编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	       pd=getPageData();   
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("STATUS",1);//待审核
	    		pd.put("CREATE_BY", getUser().getID());
	    		pd.put("CREATE_DATE",new Date());
	    		
    			String no=ProNoUtil.getPartProNos("PPP");  
	    		PageData sh=this.PPPprojectService.querPro_no(no);
	    		if(sh!=null&&sh.get("PRO_NO")!=null&&!"".equals(sh.get("PRO_NO"))){
	    			pd.put("PRO_NO",no+ProNoUtil.getProNo(sh.getString("PRO_NO")));
	    		}else{
	    			pd.put("PRO_NO",no+"0001");
	    		}  
	    		//保存项目信息
	    		this.PPPprojectService.save(pd);
    	  }else{ 
    		  	pd.put("ID",pd.getString("ID"));
    		  	pd.put("MODIFY_BY", getUser().getID());
	    		pd.put("MODIFY_DATE",new Date());
	    		Object status=pd.get("STATUS");
	    		if(status!=null&&!"".equals(status)){
	    			if(3==Integer.parseInt(status.toString())){
	    				pd.put("STAGE","125");//默认属于识别阶段
	    				//向项目阶段表中添加信息
			    		PageData sc=getPageData(); 
			    		sc.put("ID",get32UUID()); 
			    		sc.put("DATE",new Date());  
			    		sc.put("OPER_BY",getUser().getID());
			    		sc.put("PROJECT_ID",pd.getString("ID"));
			    		sc.put("DESCRIPTION","项目处于识别阶段！");
			    		sc.put("SCHEDULE",pd.getString("STAGE")); 
			    		//保存阶段信息
				    	this.PPPprojectService.insetsave(sc); 
	    			} 
	    		}
    		  	this.PPPprojectService.edit(pd);   
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
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
			List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
			List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
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
	    mv.setViewName("system/PPPprojects/PPPproject_add"); 
	    return mv;
	 }  
	 //查看项目
	 @RequestMapping({ "/toLook" })
		public ModelAndView toLook() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();  
			try {
				pd = getPageData();  
				PageData data=new PageData();
				data.put("ID", pd.getString("USER_ID"));
				pd = this.PPPprojectService.findById(pd);  
				
					List<PageData> userList =this.userService.listManager(); 
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> typeList=this.xmlxService.queryByParam(pd);
					List<PageData> lxList = this.xmlxService.queryByPid(pd);  
					List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
					List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
					List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
					List<PageData> jdList=this.dicService.queryByPBM("pppstage");
					List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
					mv.addObject("userList", userList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("typeList", typeList);  
					mv.addObject("gcflList", gcflList); 
					mv.addObject("noPassList", noPassList); 
					mv.addObject("levelList", levelList); 
					mv.addObject("lxList", lxList);  
					mv.addObject("cateList", cateList); 
					mv.addObject("jdList", jdList); 
					mv.addObject("pd",getPro(pd)); 
					mv.setViewName("system/PPPprojects/PPPproject_look"); 
			} catch (Exception e) { 
				e.printStackTrace();
			}     
			return mv;
		}
	 
	 
	 @RequestMapping({ "/toCheck" })
		public ModelAndView toCheck() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();  
			try {
				pd = getPageData();  
				pd = this.PPPprojectService.findById(pd); 
					List<PageData> userList = this.userService.listManager();
					PageData data=new PageData();
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> typeList=this.xmlxService.queryByParam(pd);
					List<PageData> lxList = this.xmlxService.queryByPid(pd);  
					List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
					List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
					List<PageData> jdList=this.dicService.queryByPBM("pppstage");
					List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
					List<PageData> levelList=this.dicService.queryByPBM("projectlevel");
					mv.addObject("userList", userList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("typeList", typeList);  
					mv.addObject("gcflList", gcflList); 
					mv.addObject("noPassList", noPassList); 
					mv.addObject("levelList", levelList); 
					mv.addObject("lxList", lxList);  
					mv.addObject("cateList", cateList); 
					mv.addObject("jdList", jdList); 
					mv.addObject("pd",getPro(pd));
					mv.setViewName("system/PPPprojects/PPPproject_check");  
					mv.addObject("msg", "check");
			} catch (Exception e) { 
				e.printStackTrace();
			}   
			return mv;
		}
	 
	 
	 @RequestMapping({ "/delete" })
	@SystemLog(methods="项目管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.PPPprojectService.delete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	 
	 
	 @RequestMapping({ "/toDis" })
		public ModelAndView toDis() throws Exception {
			ModelAndView mv = getModelAndView();
			PageData pd = new PageData();  
			try {
				pd = getPageData();  
				pd = this.PPPprojectService.findById(pd); 
				
					List<PageData> userList =this.userService.listManager();
					PageData data=new PageData();
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> typeList=this.xmlxService.queryByParam(pd);
					List<PageData> lxList = this.xmlxService.queryByPid(pd);  
					List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
					List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
					List<PageData> jdList=this.dicService.queryByPBM("pppstage");
					List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
					List<PageData> managerList=this.userService.listManager();
					mv.addObject("userList", userList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("typeList", typeList);  
					mv.addObject("gcflList", gcflList); 
					mv.addObject("noPassList", noPassList); 
					mv.addObject("managerList", managerList); 
					mv.addObject("lxList", lxList);  
					mv.addObject("cateList", cateList); 
					mv.addObject("jdList", jdList); 
					mv.addObject("pd",getPro(pd));
					mv.addObject("msg", "check"); 
					mv.setViewName("system/PPPprojects/PPPproject_distribute");  
			} catch (Exception e) { 
				e.printStackTrace();
			}   
			
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
				pd = this.PPPprojectService.findById(pd);  
				
				List<PageData> userList =this.userService.listManager(); 
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				List<PageData> typeList=this.xmlxService.queryByParam(pd);
				List<PageData> lxList = this.xmlxService.queryByPid(pd);  
				List<PageData> cateList = this.dicService.queryByPBM("industry_owned"); 
				List<PageData> gcflList=this.dicService.queryByPBM("demonstration_level");
				List<PageData> jdList=this.dicService.queryByPBM("pppstage");
				List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
				List<PageData> managerList=this.userService.listManager();
				mv.addObject("userList", userList);
				mv.addObject("areaList", areaList);  
				mv.addObject("cityList", cityList);  
				mv.addObject("typeList", typeList);  
				mv.addObject("gcflList", gcflList); 
				mv.addObject("noPassList", noPassList); 
				mv.addObject("managerList", managerList); 
				mv.addObject("lxList", lxList);  
				mv.addObject("cateList", cateList); 
				mv.addObject("jdList", jdList); 
				mv.addObject("user", user); 
				mv.addObject("pd",getPro(pd)); 
			} catch (Exception e) { 
				e.printStackTrace();
			}    
			mv.setViewName("system/PPPprojects/PPPselect_bidder");  
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
				this.PPPprojectService.updateStatus(pd);
				PageData page=this.PPPprojectService.findById(pd);
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
	   	 		notif.put("TABLE_NAME","PPPproject");
	   	 		notif.put("OBJECT_ID",pd.getString("ID")); 
	   	 	    notif.put("CONTENT","您下单的项目已完成交易！"); 
	   	 	    notif.put("DESCRIPTION","交易已完成！");  
			    notif.put("USER_ID",page.getString("USER_ID"));
			    notif.put("STATUS","06");  
			    
		 		PageData pagedata = new PageData();
		 		pagedata.put("ID", page.getString("USER_ID"));
		 		
		 		//推送审核消息
			    TransmissionTemplate tmTemplate = null;
	   	 		if(notif.getString("USER_ID")!=null&&!"".equals(notif.getString("USER_ID"))){
	   	 			this.sysNotifService.save(notif); 
		   	 		String content = "您下单的"+pd.getString("PROJECT_NAME")+"项目已完成交易！";
	   	 		    //推送审核消息
	   	 		    String jsonStr = "{'type':'ppp','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
					tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
					String alias = notif.getString("USER_ID");
					pushApp.pushToSingle(tmTemplate,alias); 
	   	 		}
	   	 		
	   	 		no.put("ID", get32UUID()); 
	   	 		no.put("CREATE_DATE", new Date());
	 	 		no.put("TABLE_NAME","project");
	 	 		no.put("OBJECT_ID",pd.getString("ID"));
	 	 		no.put("CONTENT","您已确认为接单人，交易完成！"); 
	 	 		no.put("DESCRIPTION","交易已完成！");
	 			no.put("USER_ID",page.getString("BID_WINNER_ID")); 
	 			no.put("STATUS","06"); 
	   	 		if(no.getString("USER_ID")!=null&&!"".equals(no.getString("USER_ID"))){
		 			sysNotifService.save(no); 
	   	 		    //推送审核消息
		 			String content = "您已确认为"+pd.getString("PROJECT_NAME")+"项目接单人,交易完成！";
		 			String jsonStr = "{'type':'ppp','ID':'"+pd.getString("ID")+"','title':'上海建联','content':'"+content+"'}";//透传内容
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
} 
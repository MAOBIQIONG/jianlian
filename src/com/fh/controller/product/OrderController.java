package com.fh.controller.product;

import io.rong.RongCloud;
import io.rong.models.CodeSuccessResult;

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
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.Govers.GoversService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.product.OrderService;
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
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/productOrder" })
public class OrderController extends BaseController { 
	String appKey = "vnroth0kvfo6o";//替换成您的appkey
	String appSecret = "fEPFfFSclDYo";//替换成匹配上面key的secret
	RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "areaService")
	private AreaService areaService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("product/Order_list");
		return mv;
	}
	
	@RequestMapping({"/toBidList"})
	public ModelAndView toBidList() throws Exception {
		ModelAndView mv = getModelAndView(); 
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
		Object  total =null;//查询记录的总行数
		List<PageData> projectsList = this.orderService.listByParam(page);
		total = this.orderService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData pro:projectsList){ 
			ResultUtil.resetRes(pro,new String[]{"DELIVER_DATE","CREATE_DATE"});
		} 
		getObj.put("aaData", projectsList);//要以JSON格式返回
		return getObj.toString();
	}  
	
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
	
	
	 @RequestMapping({"/toAdd"})
	 public ModelAndView toAdd(){ 
		ModelAndView mv = getModelAndView();   
		try {
			List<PageData> userList = this.userService.listManager(); 
			PageData pd=new PageData();
			pd.put("pid","0");
			List<PageData> cityList = this.areaService.querybyPid(pd); 
			List<PageData> areaList = this.areaService.queryAllParent(pd);
			mv.addObject("userList", userList);
			mv.addObject("cityList", cityList);
			mv.addObject("areaList", areaList);
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	    mv.setViewName("product/Order_add"); 
	    return mv;
	 } 
	 
	 
	 @RequestMapping(value={"/save"})
	 @SystemLog(methods="政府招商管理",type="政府招商编辑")
	 @ResponseBody
	 public String save()throws Exception{ 
	     PageData pd = new PageData(); 
	     try { 
	    	 pd=getPageData();   
	       if(pd.getString("ID")==null||"".equals(pd.getString("ID"))){//新增  
	    		pd.put("ID",get32UUID());
	    		pd.put("CREATE_DATE",new Date());
	    		//保存项目信息
	    		Object ob=this.orderService.save(pd);
    	  }else{
    		  	pd.put("ID",pd.getString("ID"));
    		  	Object xgob= this.orderService.edit(pd);  
	   	 		 
    	  	  }  
		   } catch (Exception e) {
		        this.logger.error(e.toString(), e); 
		   }  
	       JSONObject getObj = new JSONObject();
		   getObj.put("status", "1");
		   return getObj.toString(); 
	   }  
	 
	 
	@RequestMapping({ "/delete" })
	@SystemLog(methods="政府招商管理",type="删除")
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData(); 
			this.orderService.delete(pd);  
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	
	
	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.orderService.findById(pd); 
				List<PageData> userList = this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				mv.addObject("userList", userList);
				mv.addObject("areaList", areaList);  
				mv.addObject("cityList", cityList);  
				mv.addObject("pd",getPro(pd)); 
				mv.addObject("msg", "edit");
			    mv.setViewName("product/Order_edit"); 
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
	
	
	@RequestMapping({ "/toLook" })
	public ModelAndView toLook() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			pd = this.orderService.findById(pd); 
				List<PageData> userList = this.userService.listManager();
				PageData data=new PageData();
				data.put("pid","0");
				List<PageData> areaList = this.areaService.queryAllParent(data);
				List<PageData> cityList = this.areaService.querybyPid(data); 
				List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
				mv.addObject("userList", userList);
				mv.addObject("areaList", areaList);  
				mv.addObject("cityList", cityList);  
				mv.addObject("noPassList", noPassList); 
				mv.addObject("pd",getPro(pd));  
				mv.setViewName("system/Goverstment/Govers_look"); 
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
				pd = this.orderService.findById(pd); 
					List<PageData> userList = this.userService.listManager();
					PageData data=new PageData();
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
					mv.addObject("userList", userList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("noPassList", noPassList); 
					mv.addObject("pd",getPro(pd));  
					mv.setViewName("system/Goverstment/Govers_check");  
					mv.addObject("msg", "check");
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
				pd = this.orderService.findById(pd); 
				
					List<PageData> userList =this.userService.listManager();
					PageData data=new PageData();
					data.put("pid","0");
					List<PageData> areaList = this.areaService.queryAllParent(data);
					List<PageData> cityList = this.areaService.querybyPid(data); 
					List<PageData> noPassList=this.dicService.queryByPBM("pronopass");
					List<PageData> managerList=this.userService.listManager();
					mv.addObject("userList", userList);
					mv.addObject("managerList", managerList);
					mv.addObject("areaList", areaList);  
					mv.addObject("cityList", cityList);  
					mv.addObject("noPassList", noPassList); 
					mv.addObject("pd",getPro(pd));  
					mv.addObject("msg", "check"); 
					mv.setViewName("system/Goverstment/Govers_distribute");  
			} catch (Exception e) { 
				e.printStackTrace();
			}   
			
			return mv;
		}
} 
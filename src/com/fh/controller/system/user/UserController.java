package com.fh.controller.system.user;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject; 
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.projects.ProjectService;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.club.ClubService;
import com.fh.service.system.company.CertificatesService;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil; 
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.SystemLog;
import com.fh.util.Tools;

@Controller
@RequestMapping({ "/user" })
public class UserController extends BaseController {
	String menuUrl = "user/listUsers.do";

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "roleService")
	private RoleService roleService;

	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Resource(name = "clubService")
	private ClubService clubService;
	
	@Resource(name = "activityService")
	private ActivityService activityService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name = "certificatesservice")
	private CertificatesService certservice;

	@RequestMapping 
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView(); 
		mv.setViewName("system/user/list");  
		return mv;
	}
	
	@RequestMapping({ "/queryByParam" })
	@ResponseBody
	public String queryByParam(Page page) throws Exception {
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
		 
		String USERNAME = pd.getString("USERNAME");

		if ((USERNAME != null) && (!"".equals(USERNAME))) {
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}

		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");

		if ((lastLoginStart != null) && (!"".equals(lastLoginStart))) {
			lastLoginStart = lastLoginStart + " 00:00:00";
			pd.put("lastLoginStart", lastLoginStart);
		}
		if ((lastLoginEnd != null) && (!"".equals(lastLoginEnd))) {
			lastLoginEnd = lastLoginEnd + " 00:00:00";
			pd.put("lastLoginEnd", lastLoginEnd);
		}
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		List usersList =this.userService.listPdPageUser(page);
		Object  total =this.userService.findUserCount(page).get("counts");//查询记录的总行数 
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		getObj.put("aaData", usersList);//要以JSON格式返回
		return getObj.toString();
	} 
	
	@RequestMapping({ "/saveU" })
	@SystemLog(methods="用户管理",type="新增")
	@ResponseBody
	public JSONObject saveU() throws Exception {
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();
		pd = getPageData(); 
		Role role = this.roleService.getRoleById(pd.getString("ROLE_ID")); 
		pd.put("RIGHTS",role.getRIGHTS());
		pd.put("ID", get32UUID());
		pd.put("STATUS", "0"); 
		pd.put("PASSWORD",new SimpleHash("SHA-1",pd.getString("USERNAME"),"123456").toString()); 
		//pd.put("PASSWORD",new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		pd.put("CREATE_BY",getUser().getID());
		pd.put("CREATE_DATE",new Date()); 
		if (this.userService.findByUId(pd) == null) {
			if (Jurisdiction.buttonJurisdiction(this.menuUrl, "add"))
			this.userService.saveU(pd);
			obj.put("tabid", "user");
		    obj.put("statusCode", "200");
		} else { 
		    obj.put("statusCode", "300");
		}
		return obj;
	}
	
	@RequestMapping({ "/editU" })
	@SystemLog(methods="用户管理",type="编辑")
	@ResponseBody
	public JSONObject editU() throws Exception {
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();
		pd = getPageData();
		Role role = this.roleService.getRoleById(pd.getString("ROLE_ID"));
		pd.put("RIGHTS",role.getRIGHTS()); 
		pd.put("PASSWORD", new SimpleHash("SHA-1",pd.getString("USERNAME"),"123456").toString());
		pd.put("MODIFY_BY",getUser().getID());
		pd.put("MODIFY_DATE",new Date()); 
		if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit"))
			this.userService.editU(pd);
		obj.put("tabid", "user");
	    obj.put("statusCode", "200");
		return obj;
	}

	@RequestMapping({ "/goEditU" })
	public ModelAndView goEditU() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		String fx = pd.getString("fx");

		if ("head".equals(fx))
			mv.addObject("fx", "head");
		else {
			mv.addObject("fx", "user");
		}

		List roleList = this.roleService.listAllERRoles();
		pd = this.userService.findByUiId(pd);
		mv.setViewName("system/user/edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);

		return mv;
	}
	
	@RequestMapping({ "/toEditPWD" })
	public ModelAndView toEditPWD() {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.setViewName("system/user/editPWD"); 
		mv.addObject("pd", pd);  
		return mv;
	} 
	
	@RequestMapping({ "/editPWD" })
	@ResponseBody
	public JSONObject editPWD() throws Exception {
		JSONObject obj = new JSONObject();
		PageData pd = new PageData();
		pd = getPageData();
		pd.put("PASSWORD",new SimpleHash("SHA-1",pd.getString("USERNAME"),pd.getString("PASSWORD")).toString());
		PageData page=this.userService.getUserByNameAndPwd(pd);
		if(page!=null){
			pd.put("pwd",new SimpleHash("SHA-1",pd.getString("USERNAME"),pd.getString("pwd")).toString());
			Object ob=this.userService.updatePwd(pd);
			if(Integer.parseInt(ob.toString())>=1){
				obj.put("statusCode", "200");
				obj.put("msg", "密码修改成功！");
			}else{
				obj.put("statusCode", "100");
				obj.put("msg", "密码修改失败！");
			} 
		}else{
			 obj.put("msg", "原密码错误！");
			 obj.put("statusCode", "1");
		} 
		return obj;
	}

	@RequestMapping({ "/goAddU" })
	public ModelAndView goAddU() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		List roleList = this.roleService.listAllERRoles();

		mv.setViewName("system/user/edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);

		return mv;
	}
	
	@RequestMapping({ "/deleteU" })
	@SystemLog(methods="用户管理",type="删除")
	@ResponseBody
	public JSONObject deleteU() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();
		try {
			pd = getPageData();
			if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
				this.userService.deleteU(pd);
			obj.put("statusCode", "200");
			obj.put("message", "操作成功");
			obj.put("closeCurrent", false);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj;
	}

	@RequestMapping({ "/deleteAllU" })
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map map = new HashMap();
		try {
			pd = getPageData();
			List pdList = new ArrayList();
			String USER_IDS = pd.getString("USER_IDS");

			if ((USER_IDS != null) && (!"".equals(USER_IDS))) {
				String[] ArrayUSER_IDS = USER_IDS.split(",");
				if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
					this.userService.deleteAllU(ArrayUSER_IDS);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}

			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		} finally {
			logAfter(this.logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 禁用
	 */
	@RequestMapping({ "/jinYong" })
	@ResponseBody
	public String jinYong() throws Exception {
		PageData pd = new PageData();
		pd = getPageData(); 
		String STATUS=pd.getString("STATUS");
		String param=new String(STATUS.getBytes("ISO-8859-1"),"UTF-8").trim();
		if("禁用".equals(param)){
			pd.put("STATUS",1);
		}else{
			pd.put("STATUS",0);
		}
		pd.put("MODIFY_BY",getUser().getID());
		pd.put("MODIFY_DATE",new Date()); 
		this.userService.editU(pd);
		JSONObject getObj = new JSONObject();
		getObj.put("statusCode",200);//
		return getObj.toString();
	}
	

	@RequestMapping({ "/hasU" })
	@ResponseBody
	public Object hasU() {
		Map map = new HashMap();
		map.put("ok", "");
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = getPageData();
			if (this.userService.findByUId(pd) != null)
				map.put("error", "用户名已存在");
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return map;
	}

	@RequestMapping({ "/hasE" })
	@ResponseBody
	public Object hasE() {
		Map map = new HashMap();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = getPageData();

			if (this.userService.findByUE(pd) != null)
				errInfo = "error";
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping({ "/hasN" })
	@ResponseBody
	public Object hasN() {
		Map map = new HashMap();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = getPageData();
			if (this.userService.findByUN(pd) != null)
				errInfo = "error";
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

//	@RequestMapping({ "/listUsers" })
//	@Log(operationType="add操作",operationName="添加用户")  
//	public ModelAndView listUsers(Page page) throws Exception {
//		ModelAndView mv = getModelAndView();
//		PageData pd = new PageData();
//		pd = getPageData();
//
//		String USERNAME = pd.getString("USERNAME");
//
//		if ((USERNAME != null) && (!"".equals(USERNAME))) {
//			USERNAME = USERNAME.trim();
//			pd.put("USERNAME", USERNAME);
//		}
//
//		String lastLoginStart = pd.getString("lastLoginStart");
//		String lastLoginEnd = pd.getString("lastLoginEnd");
//
//		if ((lastLoginStart != null) && (!"".equals(lastLoginStart))) {
//			lastLoginStart = lastLoginStart + " 00:00:00";
//			pd.put("lastLoginStart", lastLoginStart);
//		}
//		if ((lastLoginEnd != null) && (!"".equals(lastLoginEnd))) {
//			lastLoginEnd = lastLoginEnd + " 00:00:00";
//			pd.put("lastLoginEnd", lastLoginEnd);
//		}
//
//		page.setPd(pd);
//		List userList = this.userService.listPdPageUser(page);
//		List roleList = this.roleService.listAllERRoles();
//
//		mv.setViewName("system/user/user_list");
//		mv.addObject("userList", userList);
//		mv.addObject("roleList", roleList);
//		mv.addObject("pd", pd);
//		mv.addObject("QX", getHC());
//		return mv;
//	}

	@RequestMapping({ "/listtabUsers" })
	public ModelAndView listtabUsers(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		List userList = this.userService.listAllUser(pd);
		mv.setViewName("system/user/user_tb_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("QX", getHC());
		return mv;
	}

	@RequestMapping({ "/excel" })
	public ModelAndView exportExcel() {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(this.menuUrl, "cha")) {
				String USERNAME = pd.getString("USERNAME");
				if ((USERNAME != null) && (!"".equals(USERNAME))) {
					USERNAME = USERNAME.trim();
					pd.put("USERNAME", USERNAME);
				}
				String lastLoginStart = pd.getString("lastLoginStart");
				String lastLoginEnd = pd.getString("lastLoginEnd");
				if ((lastLoginStart != null) && (!"".equals(lastLoginStart))) {
					lastLoginStart = lastLoginStart + " 00:00:00";
					pd.put("lastLoginStart", lastLoginStart);
				}
				if ((lastLoginEnd != null) && (!"".equals(lastLoginEnd))) {
					lastLoginEnd = lastLoginEnd + " 00:00:00";
					pd.put("lastLoginEnd", lastLoginEnd);
				}

				Map dataMap = new HashMap();
				List titles = new ArrayList();

				titles.add("用户名");
				titles.add("编号");
				titles.add("姓名");
				titles.add("职位");
				titles.add("手机");
				titles.add("邮箱");
				titles.add("最近登录");
				titles.add("上次登录IP");

				dataMap.put("titles", titles);

				List userList = this.userService.listAllUser(pd);
				List varList = new ArrayList();
				for (int i = 0; i < userList.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1",
							((PageData) userList.get(i)).getString("USERNAME"));
					vpd.put("var2",
							((PageData) userList.get(i)).getString("NUMBER"));
					vpd.put("var3",
							((PageData) userList.get(i)).getString("NAME"));
					vpd.put("var4",
							((PageData) userList.get(i)).getString("ROLE_NAME"));
					vpd.put("var5",
							((PageData) userList.get(i)).getString("PHONE"));
					vpd.put("var6",
							((PageData) userList.get(i)).getString("EMAIL"));
					vpd.put("var7", ((PageData) userList.get(i))
							.getString("LAST_LOGIN"));
					vpd.put("var8",
							((PageData) userList.get(i)).getString("IP"));
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();
				mv = new ModelAndView(erv, dataMap);
			}
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/goUploadExcel" })
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}

	@RequestMapping({ "/downExcel" })
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath()
				+ "uploadFiles/file/" + "Users.xls", "Users.xls");
	}

	@RequestMapping({ "/readExcel" })
	public ModelAndView readExcel(
			@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(this.menuUrl, "add"))
			return null;
		if ((file != null) && (!file.isEmpty())) {
			String filePath = PathUtil.getClasspath() + "uploadFiles/file/";
			String fileName = FileUpload.fileUp(file, filePath, "userexcel");

			List<Object> listPd = ObjectExcelRead.readExcel(filePath, fileName,
					2, 0, 0);

			pd.put("RIGHTS", "");
			pd.put("LAST_LOGIN", "");
			pd.put("IP", "");
			pd.put("STATUS", "0"); 

			List roleList = this.roleService.listAllERRoles();

			pd.put("ROLE_ID", ((Role) roleList.get(0)).getROLE_ID());

			for (int i = 0; i < listPd.size(); i++) {
				pd.put("USER_ID", get32UUID());
				pd.put("NAME", ((PageData) listPd.get(i)).getString("var1"));

				String USERNAME = GetPinyin.getPingYin(((PageData) listPd
						.get(i)).getString("var1"));
				pd.put("USERNAME", USERNAME);
				if (this.userService.findByUId(pd) != null) {
					USERNAME = GetPinyin.getPingYin(((PageData) listPd.get(i))
							.getString("var1")) + Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				pd.put("BZ", ((PageData) listPd.get(i)).getString("var4"));
				if (Tools.checkEmail(((PageData) listPd.get(i))
						.getString("var3"))) {
					pd.put("EMAIL",
							((PageData) listPd.get(i)).getString("var3"));
					if (this.userService.findByUE(pd) == null) {
						pd.put("NUMBER",
								((PageData) listPd.get(i)).getString("var0"));
						pd.put("PHONE",
								((PageData) listPd.get(i)).getString("var2"));

						pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME,
								"123").toString());
						if (this.userService.findByUN(pd) == null) {
							this.userService.saveU(pd);
						}
					}
				}
			}
			mv.addObject("msg", "success");
		}

		mv.setViewName("save_result");
		return mv;
	}
	
	//查询未审核的记录数
	@RequestMapping({ "/msgCounts" })
	@ResponseBody
	public String msgCounts() throws Exception {
		PageData pd = new PageData();  
		//pd=clubService.queryMsgByStatus();//统计俱乐部未审核的记录数
		//PageData act=activityService.queryMsgByStatus();//统计活动未审核的记录数
		//PageData cert=certservice.queryMsgByStatus();//统计活动未审核的记录数
		//PageData stat_check = new PageData();  
		//stat_check.put("status","1");//未审核
		//PageData pro_check=projectService.queryMsgByStatus(stat_check);//统计项目未审核的记录数
		//stat_check.put("status","4");//待分配
		//PageData pro_dis=projectService.queryMsgByStatus(stat_check);//统计项目未分配的记录数
		
		/****************新红点*********************/
		//接单管理人数增加
		PageData jecount=userService.queryjdByStatus();
		
		//项目代发管理
		PageData xmdfcount=userService.queryxmdfByStatus();
		//证件上传
		PageData zjscount=userService.queryzjscByStatus();
		//支付入会
		PageData rhscount=userService.queryrhByStatus();
		//活动提交审核
		PageData hdtjscount=userService.queryhdtjByStatus();
		//加入城市建联
		PageData csjlscount=userService.querycsjlByStatus();
		//城市合伙人申请
		PageData cshhrcount=userService.querycshhrByStatus();
		//场地预订
		PageData cdydcount=userService.querycdydByStatus();
		//反馈通知
		PageData fkcount=userService.queryfkByStatus();
		//ppp人数增加  
		PageData pppcycount=userService.querypppcyByStatus();
		
		JSONObject getObj = new JSONObject();
		/*getObj.put("club_counts", pd.get("club_counts"));//
		getObj.put("activity_counts", act.get("activity_counts"));
		getObj.put("cert_counts", cert.get("cert_counts"));//
		getObj.put("procheck_counts", pro_check.get("project_counts"));
		getObj.put("prodis_counts", pro_dis.get("project_counts"));// */
		getObj.put("jecounts", jecount.get("jy_counts"));
		getObj.put("xmdfcounts", xmdfcount.get("xmdf_counts"));
		getObj.put("zjscounts", zjscount.get("zjsc_counts"));
		getObj.put("rhscounts", rhscount.get("rh_counts"));
		getObj.put("hdtjscounts", hdtjscount.get("hdtj_counts"));
		getObj.put("csjlscounts", csjlscount.get("csjl_counts"));
		getObj.put("cshhrcounts", cshhrcount.get("cshhr_counts"));
		getObj.put("cdydcounts", cdydcount.get("cdyd_counts"));
		getObj.put("fkcounts", fkcount.get("fk_counts"));
		getObj.put("pppcycounts", pppcycount.get("pppcy_counts"));
		return getObj.toString();  
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}

	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Map) session.getAttribute("QX");
	}
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	}
} 
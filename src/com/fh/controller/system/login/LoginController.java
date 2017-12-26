package com.fh.controller.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController; 
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.wangyi.WyUtil;

@Controller
public class LoginController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "menuService")
	private MenuService menuService;

	@Resource(name = "roleService")
	private RoleService roleService;  
 
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null)
			ip = request.getRemoteAddr();
		else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		this.userService.saveIP(pd);
	}  

	@RequestMapping({ Const.LOGIN })
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		mv.setViewName("system/admin/login"); 
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = { "/login_login" }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object login() throws Exception {
		PageData _result = AppUtil.success();
		PageData pd = new PageData();
		pd = getPageData();
		String errInfo = "";
		String[] KEYDATA = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").replaceAll("QQ978336446fh", "").split(",fh,");

		if ((KEYDATA != null) && (KEYDATA.length == 3)) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();  
			String code = KEYDATA[2];
			if ((code == null) || ("".equals(code))) {
				errInfo = "nullcode";
			} else {
				String USERNAME = KEYDATA[0].trim();
				String PASSWORD = KEYDATA[1].trim();
				pd.put("USERNAME", USERNAME); 
				String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD)
						.toString();
				pd.put("PASSWORD", passwd);
				pd = this.userService.getUserByNameAndPwd(pd);
				if (pd != null) {
					pd.put("LAST_LOGIN", DateUtil.getTime().toString());
					this.userService.updateLastLogin(pd);
					User user = new User();
					user.setID(pd.getString("ID"));
					user.setUSERNAME(pd.getString("USERNAME"));
					user.setNICKNAME(pd.getString("NICKNAME"));
					user.setPASSWORD(pd.getString("PASSWORD"));
					user.setNAME(pd.getString("NAME"));
					user.setRIGHTS(pd.getString("RIGHTS"));
					user.setROLE_ID(pd.getString("ROLE_ID"));
					user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
					user.setIP(pd.getString("IP"));
					user.setSTATUS(pd.getString("STATUS"));
					
					//注册网易云信账号
					String wyToken = saveWyToken(pd);
					if( wyToken != null ){
						user.setWYTOKEN(wyToken);
					}else{
						user.setWYTOKEN(pd.getString("WYTOKEN"));
					}
					//设置缓存
					session.setAttribute("sessionUser", user);
					session.removeAttribute("sessionSecCode");

					Subject subject = SecurityUtils.getSubject();
					UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
					try {
						subject.login(token);
					} catch (AuthenticationException e) {
						errInfo = "身份验证失败！";
					}
				} else {
					errInfo = "usererror";
				}
				if (Tools.isEmpty(errInfo))
					errInfo = "success";
			}
		} else {
			errInfo = "error";
		}
		_result.put("result", errInfo);
		return _result;
	}

	@RequestMapping({ "/main/{changeMenu}" })
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu) { 
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			User user = (User) session.getAttribute("sessionUser");
			if (user != null) {
				User userr = (User) session.getAttribute("USERROL");
				if (userr == null) {
					user = this.userService.getUserAndRoleById(user.getID());
					session.setAttribute("USERROL", user);
				} else {
					user = userr;
				}
				Role role = user.getRole();
				String roleRights = role != null ? role.getRIGHTS() : "";

				session.setAttribute("sessionRoleRights", roleRights);
				session.setAttribute("USERNAME", user.getUSERNAME());
				session.setAttribute("role", role);

				List<Menu> allmenuList = new ArrayList<Menu>();

				if (session.getAttribute("allmenuList") == null) {
					allmenuList = this.menuService.listAllMenu();
					if (Tools.notEmpty(roleRights)) {
						for (Menu menu : allmenuList) {
							menu.setHasMenu(RightsHelper.testRights(roleRights,menu.getMENU_ID()));
							if (menu.isHasMenu()) {
								List<Menu> subMenuList = menu.getSubMenu();
								for (Menu sub : subMenuList) {
									sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
								}
							}
						}
					}
					session.setAttribute("allmenuList", allmenuList);
				} else {
					allmenuList = (List) session.getAttribute("allmenuList");
				}

				List menuList = new ArrayList(); 
				if ((session.getAttribute("menuList") == null)) {  
					for (int i = 0; i < allmenuList.size(); i++) {
						Menu menu = (Menu) allmenuList.get(i); 
					    menuList.add(menu); 
					} 
					session.removeAttribute("menuList");
					session.setAttribute("menuList", menuList);
					session.removeAttribute("changeMenu");
					session.setAttribute("changeMenu", "1"); 
				} else {
					menuList = (List) session.getAttribute("menuList");
				} 
				if (session.getAttribute("QX") == null) {
					session.setAttribute("QX", getUQX(session));
				} 
				mv.setViewName("system/admin/login");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			} else {
				mv.setViewName("system/admin/login");
			}
		} catch (Exception e) {
			mv.setViewName("system/admin/login");
			this.logger.error(e.getMessage(), e);
		} 
		mv.addObject("pd", pd);
		mv.setViewName("system/admin/index");  
		return mv;
	} 

	@RequestMapping({ "/logout" })
	public ModelAndView logout() {
		ModelAndView mv = getModelAndView(); 
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();

		session.removeAttribute("sessionUser");
		session.removeAttribute("sessionRoleRights");
		session.removeAttribute("allmenuList");
		session.removeAttribute("menuList");
		session.removeAttribute("QX");
		session.removeAttribute("userpds");
		session.removeAttribute("USERNAME");
		session.removeAttribute("USERROL");
		session.removeAttribute("changeMenu"); 
		
		subject.logout(); 
		mv.setViewName("system/admin/login"); 
		return mv;
	}
	
	/*@RequestMapping({ "/toEditPWD" })
	public ModelAndView toEditPWD() {
		ModelAndView mv = getModelAndView(); 
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();

		session.removeAttribute("sessionUser");
		session.removeAttribute("sessionRoleRights");
		session.removeAttribute("allmenuList");
		session.removeAttribute("menuList");
		session.removeAttribute("QX");
		session.removeAttribute("userpds");
		session.removeAttribute("USERNAME");
		session.removeAttribute("USERROL");
		session.removeAttribute("changeMenu"); 
		
		subject.logout(); 
		mv.setViewName("system/admin/login"); 
		return mv;
	}
	*/
	@RequestMapping({ "/noacess" })
	public ModelAndView noacess() {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		mv.setViewName("system/403");
		mv.addObject("pd", pd);
		return mv;
	}

	public Map<String, String> getUQX(Session session) {
		PageData pd = new PageData();
		Map map = new HashMap();
		try {
			String USERNAME = session.getAttribute("USERNAME").toString();
			pd.put("USERNAME", USERNAME);
			String ROLE_ID = this.userService.findByUId(pd).get("ROLE_ID").toString();

			pd.put("ROLE_ID", ROLE_ID);

			PageData pd2 = new PageData();
			pd2.put("USERNAME", USERNAME);
			pd2.put("ROLE_ID", ROLE_ID);

			//通过Id查询role
			pd = this.roleService.findObjectById(pd);
			//通过当前登录用的角色id获取管理权限数据 
			pd2 = this.roleService.findGLbyrid(pd2);
			if (pd2 != null) {
				map.put("FX_QX", pd2.get("FX_QX").toString());
				map.put("FW_QX", pd2.get("FW_QX").toString());
				map.put("QX1", pd2.get("QX1").toString());
				map.put("QX2", pd2.get("QX2").toString());
				map.put("QX3", pd2.get("QX3").toString());
				map.put("QX4", pd2.get("QX4").toString());

				pd2.put("ROLE_ID", ROLE_ID);
				//通过当前登录用的角色id获取用户权限数据
				pd2 = this.roleService.findYHbyrid(pd2);
				map.put("C1", pd2.get("C1").toString());
				map.put("C2", pd2.get("C2").toString());
				map.put("C3", pd2.get("C3").toString());
				map.put("C4", pd2.get("C4").toString());
				map.put("Q1", pd2.get("Q1").toString());
				map.put("Q2", pd2.get("Q2").toString());
				map.put("Q3", pd2.get("Q3").toString());
				map.put("Q4", pd2.get("Q4").toString());
			}

			map.put("adds", pd.getString("ADD_QX"));
			map.put("dels", pd.getString("DEL_QX"));
			map.put("edits", pd.getString("EDIT_QX"));
			map.put("chas", pd.getString("CHA_QX"));

			getRemortIP(USERNAME);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return map;
	}
	
	//修改获取网易云信TOKEN保存
	public String saveWyToken(PageData user) throws Exception {
		System.out.println("******************网易云信注册******************");
		if( user != null ){
			String wyToken = user.getString("WYTOKEN");
			if( "".equals(wyToken) || wyToken == null ){
				String USER_ID = user.getString("ID");
				String userImg = "";
				WyUtil wy = new WyUtil();
				String str = wy.createWyAccount(USER_ID, user.getString("REAL_NAME"), userImg);
				JSONObject obj = new JSONObject(str);
				String code = obj.getString("code");
				if( "200".equals(code) ){
					PageData im = new PageData();
					im.put("ID",USER_ID);
					im.put("WYTOKEN",obj.getJSONObject("info").getString("token"));
					int rs = (Integer) this.userService.editWyToken(im);
					if( rs > 0 ){
						System.out.println("创建网易账号成功：");
						//加入管理员群组
						List<String> ids = new ArrayList<String>();
				    	ids.add(USER_ID);
				    	JSONArray jsonArr = JSONArray.fromObject(ids);
				    	PageData pd = new PageData();
				    	pd.put("tid","204877827");
				    	pd.put("owner","f1e3cfff9686475aaa8383e959bf8f9c");
				    	pd.put("members",jsonArr.toString());
				    	pd.put("msg","建联超级管理员邀请您加入管理员群组。");
				    	String result = wy.addToGroup(pd);
				    	JSONObject jo = new JSONObject(result);
				    	if( "200".equals(jo.getString("code"))){
				    		System.out.println("加入管理群组成功！");
				    	}else{
				    		System.out.println("加入管理群组失败！");
				    	}
						return im.getString("WYTOKEN");
					}
					logger.info("修改用户消息推送的token，service：【userService】方法：【editWyToken】 参数："+im.toString()); 
				}else if( "414".equals(code) ){
					System.out.println("网易云信：已注册！");
				}
		    }
		}
		return null;
	}
}
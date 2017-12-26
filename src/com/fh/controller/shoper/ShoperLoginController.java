package com.fh.controller.shoper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.AppUrl;
import com.fh.controller.base.BaseController; 
import com.fh.entity.system.User;
import com.fh.service.shoper.ShopUsersService;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Controller
@RequestMapping({ "/appShoper" })
public class ShoperLoginController extends BaseController {
	
	@Autowired 
	// 允许app直接访问的 链接
	@Resource(name = "appurl")
	AppUrl appurl = null;

	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name ="shopUsersService")
	private ShopUsersService shopUser;

	@Resource(name = "menuService")
	private MenuService menuService;

	@Resource(name = "roleService")
	private RoleService roleService;   

	@RequestMapping({ Const.LOGIN })
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		mv.setViewName("shoper/shoper_login"); 
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = { "/tologin" })
	@ResponseBody
	public String login() throws Exception { 
		PageData pd = new PageData();
		pd = getPageData();
		String errInfo = ""; 
		if (pd != null) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();   
			String USERNAME = pd.getString("USERNAME").trim();
			String PASSWORD = pd.getString("password").trim(); 
			String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
			pd.put("PASSWORD", passwd);
			pd = this.shopUser.doLogin(pd);
			if(pd!=null){
//			if(pd.size()==1){
			if(passwd.equals(pd.getString("PASSWORD"))){ 
			User user = new User();
			user.setID(pd.getString("ID"));
			user.setUSERNAME(pd.getString("USER_NAME"));
			user.setNICKNAME(pd.getString("NICK_NAME"));
			user.setPASSWORD(pd.getString("PASSWORD"));
			user.setNAME(pd.getString("REAL_NAME"));
			user.setRIGHTS("888888");  
			user.setSTATUS(pd.getString("STATUS"));
			session.setAttribute("sessionUser", user);
			session.removeAttribute("sessionSecCode");

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				errInfo = "身份验证失败！";
			} 
			}else{
				//账号或密码错误
				errInfo = "02";
			}
//			}else{//多条记录
//				//用户名或卡号或手机号重复，请联系管理员！
//				errInfo = "03";
//			} 
			}else {
				//用户不存在，请注册!
				errInfo = "04";
			}
		if (Tools.isEmpty(errInfo))
			errInfo = "success"; 
		} else {
			errInfo = "error";
		} 
		return errInfo;
	}

	//手机登录
	@RequestMapping(value = { "/toPhonelogin" })
	@ResponseBody
	public String phonlogin() throws Exception { 
		PageData pd = new PageData();
		pd = getPageData();
		String errInfo = ""; 
		if (pd != null) {
			
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();   
			String USERNAME = pd.getString("USERNAME").trim();
			String PASSWORD = pd.getString("password").trim();
			pd = this.shopUser.doCheckLogin(pd);
			
			User user = new User();
			user.setID(pd.getString("ID"));
			user.setUSERNAME(pd.getString("USER_NAME"));
			user.setNICKNAME(pd.getString("NICK_NAME"));
			user.setPASSWORD(pd.getString("PASSWORD"));
			user.setNAME(pd.getString("REAL_NAME"));
			user.setRIGHTS("888888");  
			user.setSTATUS(pd.getString("STATUS"));
			session.setAttribute("sessionUser", user);
			session.removeAttribute("sessionSecCode");

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(USERNAME,PASSWORD);
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				errInfo = "身份验证失败！";
			} 
		if (Tools.isEmpty(errInfo))
			errInfo = "success"; 
		} else {
			errInfo = "error";
		} 
		return errInfo;
	}
	
	
	@RequestMapping({ "/menu" })
	public ModelAndView login_index() { 
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			User user = (User) session.getAttribute("sessionUser");
			if (user != null) { 
				session.setAttribute("USERROL", user);  
				session.setAttribute("sessionRoleRights", "888888");
				session.setAttribute("USERNAME", user.getUSERNAME()); 
				/*List<Menu> allmenuList = new ArrayList<Menu>();

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
				}*/

				/*List menuList = new ArrayList(); 
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
				}  */
				mv.addObject("user", user);
				//mv.addObject("menuList", menuList);
			} else {
				mv.setViewName("shoper/shoper_login");
			}
		} catch (Exception e) {
			mv.setViewName("shoper/shoper_login");
			this.logger.error(e.getMessage(), e);
		} 
		mv.addObject("pd", pd);
		mv.setViewName("shoper/index"); 
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
		mv.setViewName("shoper/shoper_login"); 
		return mv;
	}
	 
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
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return map;
	}
}
package com.fh.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class BaseController {
	protected Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 6357869213649815390L;

	@Autowired
	private HttpServletRequest request;

	@ModelAttribute
	private void validate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		for (Object key : pd.keySet()) {
			if (pd.get(key) instanceof String) {
				pd.put(key, pd.getString(key).trim());
			}
		}
	}

	protected User getUser() {
		User user = (User) this.request.getSession().getAttribute("sessionUser");
		return user;
	}

	protected boolean isTeacher() {
		User user = getUser();
		if (user != null
				&& "fd9af0ec7ed745a2b8c8121e5aad5132".equals(user.getROLE_ID())) {
			return true;
		}
		return false;
	}

	protected boolean isAdmin() {
		User user = getUser();
		if ("1".equals(user.getROLE_ID())
				|| "b2fc1ecba3f941fdaa0b7042f44374a9".equals(user.getROLE_ID())) {
			return true;
		}
		return false;
	}
	
	protected boolean isMaster() {
		User user = getUser();
		if ("1".equals(user.getROLE_ID())
				|| "e74f713314154c35bd7fc98897859fe3".equals(user.getROLE_ID())) {
			return true;
		}
		return false;
	}

	public PageData getPageData() {
		return new PageData(getRequest());
	}

	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		return request;
	}

	public String getBasePath() {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	public String get32UUID() {
		return UuidUtil.get32UUID();
	}
	
	public JSONObject resultSuccess(boolean closeCurrent,String tabid,String message){
		JSONObject obj = new JSONObject();
		obj.put("statusCode", "200");
		if(message == null){
			obj.put("message", "操作成功");
		}else{
			obj.put("message", message);
		}
		obj.put("closeCurrent", closeCurrent);
		obj.put("tabid", tabid);
		obj.put("forward", "");
		obj.put("forwardConfirm", "");
		return obj;
	}
	
	public JSONObject resultError(boolean closeCurrent,String tabid,String message){
		JSONObject obj = new JSONObject();
		obj.put("statusCode", "300");
		obj.put("message", message);
		obj.put("closeCurrent", closeCurrent);
		return obj;
	}

	public Page getPage() {
		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}
	
	//获取当前登录用户
	public User currentUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
}

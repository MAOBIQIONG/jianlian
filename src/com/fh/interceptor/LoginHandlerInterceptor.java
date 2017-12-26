 package com.fh.interceptor;
 
 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
 
 public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
	   
     response.setHeader("Access-Control-Allow-Origin", "*");
     response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
     response.setHeader("Access-Control-Max-Age", "3600");
     response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	 
     String path = request.getServletPath();
     if (path.matches(".*/((login)|(logout)|(code)|(app)|(quartz)|(weixin)|(static)|(H-ui)|(ueditor)|(images)|(websocket)|(main)|(websocket)).*")) {
       return true;
     }
     
     Subject currentUser = SecurityUtils.getSubject();
     Session session = currentUser.getSession();
     User user = (User)session.getAttribute("sessionUser");
     if (user != null) {
    	 if(user.getRIGHTS()!=null&&"888888".equals(user.getRIGHTS())){
    		 return true;
    	 }
       path = path.substring(1, path.length());
       boolean b = Jurisdiction.hasJurisdiction(path);
       if (!b) {
         response.sendRedirect(request.getContextPath() + Const.NOACESS);
       }
       return b;
     } 
     response.sendRedirect(request.getContextPath() + Const.LOGIN);
     return false;
   }
 }
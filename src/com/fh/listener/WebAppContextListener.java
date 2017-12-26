 package com.fh.listener;
 
 import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;
 
 public class WebAppContextListener
   implements ServletContextListener
 {
   public void contextDestroyed(ServletContextEvent event)
   {
   }
 
   public void contextInitialized(ServletContextEvent event)
   {
     com.fh.util.Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.listener.WebAppContextListener
 * JD-Core Version:    0.6.2
 */
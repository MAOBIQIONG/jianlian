 package com.fh.util;
 
 import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
 public class RequestUtil
 {
   public static String getValue()
   {
     HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
     PageData pd = new PageData(request);
 
     Map properties = request.getParameterMap();
     Map returnMap = new HashMap();
     Iterator entries = properties.entrySet().iterator();
 
     String name = "";
     String value = "";
 
     while (entries.hasNext()) {
       Map.Entry entry = (Map.Entry)entries.next();
       name = (String)entry.getKey();
     }
 
     return name;
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.RequestUtil
 * JD-Core Version:    0.6.2
 */
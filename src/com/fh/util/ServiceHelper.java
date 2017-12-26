 package com.fh.util;
 
 import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.service.system.user.UserService;
 
 public final class ServiceHelper
 {
   public static Object getService(String serviceName)
   {
     return Const.WEB_APP_CONTEXT.getBean(serviceName);
   }
 
   public static UserService getUserService() {
     return (UserService)getService("userService");
   }
 
   public static RoleService getRoleService() {
     return (RoleService)getService("roleService");
   }
 
   public static MenuService getMenuService() {
     return (MenuService)getService("menuService");
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.ServiceHelper
 * JD-Core Version:    0.6.2
 */
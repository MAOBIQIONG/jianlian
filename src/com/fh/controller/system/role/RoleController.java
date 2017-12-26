 package com.fh.controller.system.role;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.SystemLog;
import com.fh.util.Tools;

import net.sf.json.JSONArray;
 
 @Controller
 @RequestMapping({"/role"})
 public class RoleController extends BaseController
 {
   String menuUrl = "role.do";
 
   @Resource(name="menuService")
   private MenuService menuService;
 
   @Resource(name="roleService")
   private RoleService roleService;
 
  /* @RequestMapping({"/qx"})
   public ModelAndView qx() throws Exception { 
	 ModelAndView mv = getModelAndView();
     PageData pd = new PageData();
     try {
       pd = getPageData();
       String msg = pd.getString("msg");
       if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) 
    	   this.roleService.updateQx(msg, pd);
       mv.setViewName("save_result");
       mv.addObject("msg", "success");
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
     }
     return mv;
   }*/
 
  /* @RequestMapping({"/kfqx"})
   public ModelAndView kfqx()
     throws Exception
   {
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData();
     try {
       pd = getPageData();
       String msg = pd.getString("msg");
       if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) 
    	   this.roleService.updateKFQx(msg, pd);
       mv.setViewName("save_result");
       mv.addObject("msg", "success");
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
     }
     return mv;
   }*/
 
  /* @RequestMapping({"/gysqxc"})
   public ModelAndView gysqxc()
     throws Exception
   {
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData();
     try {
       pd = getPageData();
       String msg = pd.getString("msg");
       if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) 
    	   this.roleService.gysqxc(msg, pd);
       mv.setViewName("save_result");
       mv.addObject("msg", "success");
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
     }
     return mv;
   }*/
 
   @RequestMapping
   public ModelAndView list(Page page) throws Exception{
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData();
     pd = getPageData();
 
     String roleId = pd.getString("ROLE_ID");
     if ((roleId == null) || ("".equals(roleId))) {
       pd.put("ROLE_ID", "d6b8670d9ca44772b225e39527d79e1f");
     }
     List<Role> roleList = this.roleService.listAllRoles();
     List<Role> roleList_z = this.roleService.listAllRolesByPId(pd); 
     pd = this.roleService.findObjectById(pd);
     mv.addObject("pd", pd); 
     mv.addObject("roleList", roleList);
     mv.addObject("roleList_z", roleList_z);
     mv.setViewName("system/role/list"); 
     mv.addObject("QX", getHC());
 
     return mv;
   }
   
   @RequestMapping({"/listZ"})
   public ModelAndView listZ(Page page) throws Exception {
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData();
     pd = getPageData();
 
     String roleId = pd.getString("ROLE_ID");
     if ((roleId == null) || ("".equals(roleId))) {
       pd.put("ROLE_ID", "1");
     }
     List<Role> roleList = this.roleService.listAllRoles();
     List<Role> roleList_z = this.roleService.listAllRolesByPId(pd); 
     pd = this.roleService.findObjectById(pd);
     mv.addObject("pd", pd); 
     mv.addObject("roleList", roleList);
     mv.addObject("roleList_z", roleList_z);  
     mv.setViewName("system/role/list_z");
     mv.addObject("QX", getHC());
     return mv;
   }
 
   @RequestMapping({"/toAdd"})
   public ModelAndView toAdd(Page page) throws Exception {
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData(); 
     pd = getPageData(); 
     mv.addObject("pd", pd); 
     mv.setViewName("system/role/add_role"); 
     return mv;
   }
 
   @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   @SystemLog(methods="角色管理",type="新增")
   @ResponseBody
   public JSONObject add()throws Exception {
	 JSONObject obj = new JSONObject();
     PageData pd = new PageData();
     try {
       pd = getPageData(); 
       String parent_id = pd.getString("PARENT_ID");
       pd.put("ROLE_ID", parent_id);
       if ("0".equals(parent_id)) {
          pd.put("RIGHTS", "");
       } else {
         String rights = this.roleService.findObjectById(pd).getString("RIGHTS");
         pd.put("RIGHTS", rights == null ? "" : rights);
       } 
       pd.put("QX_ID", ""); 
       String UUID = get32UUID();  
       pd.put("ROLE_ID", UUID);
       pd.put("ADD_QX", "0");
       pd.put("DEL_QX", "0");
       pd.put("EDIT_QX", "0");
       pd.put("CHA_QX", "0");
       pd.put("SH_QX", "0"); 
      /* if (Jurisdiction.buttonJurisdiction(this.menuUrl, "add")){
    	  
       }*/
       this.roleService.add(pd);
       obj.put("tabid", "role");
       obj.put("status", "1");
       obj.put("msg", "success");
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
       obj.put("status", "");
       obj.put("msg", "failed");
     }
	 obj.put("message", "操作成功"); 
     return obj;
   }
 
   @RequestMapping({"/toEdit"})
   public ModelAndView toEdit(String ROLE_ID) throws Exception {
     ModelAndView mv = getModelAndView();
     PageData pd = new PageData(); 
     pd = getPageData();
     pd.put("ROLE_ID", ROLE_ID);
     pd = this.roleService.findObjectById(pd);
     mv.setViewName("system/role/edit_role");
     mv.addObject("pd", pd); 
     return mv;
   }
 
   @RequestMapping({"/edit"})
   @SystemLog(methods="角色管理",type="编辑")
   @ResponseBody
   public JSONObject edit() throws Exception{
     PageData pd = new PageData();
     JSONObject obj = new JSONObject();  
     pd = getPageData();
//       if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) 
     pd = this.roleService.edit(pd);
     obj.put("statusCode", "200");
     obj.put("message", "操作成功");
     obj.put("closeCurrent", true);
     obj.put("tabid", "role"); 
     return obj;
   }
 
   @RequestMapping({"/auth"})
   public ModelAndView auth(@RequestParam String ROLE_ID) throws Exception {
	   ModelAndView mv = getModelAndView();
     try{
       List<Menu> menuList = this.menuService.listAllMenu();
       Role role = this.roleService.getRoleById(ROLE_ID);
       String roleRights = role.getRIGHTS();
       if (Tools.notEmpty(roleRights)) {
         for (Menu menu : menuList) {
           menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
           if (menu.isHasMenu()) {
             List<Menu> subMenuList = menu.getSubMenu();
             for (Menu sub : subMenuList) {
               sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
             }
           }
         }
       }
       JSONArray arr = JSONArray.fromObject(menuList);
       String json = arr.toString();
       json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("PARENT_ID", "pid").replaceAll("subMenu", "children").replaceAll("hasMenu", "checked");
       mv.setViewName("system/role/button_menu");
       mv.addObject("zTreeNodes", json);
       mv.addObject("roleId", ROLE_ID);
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
     } 
     return mv;
   }
 
   @RequestMapping({"/button"})
   public ModelAndView button(@RequestParam String ROLE_ID, @RequestParam String msg, Model model) throws Exception {
     ModelAndView mv = getModelAndView();
     try {
       List<Menu> menuList = this.menuService.listAllMenu();
       Role role = this.roleService.getRoleById(ROLE_ID);
 
       String roleRights = "";
       if ("add_qx".equals(msg))
         roleRights = role.getADD_QX();
       else if ("del_qx".equals(msg))
         roleRights = role.getDEL_QX();
       else if ("edit_qx".equals(msg))
         roleRights = role.getEDIT_QX();
       else if ("cha_qx".equals(msg)) {
         roleRights = role.getCHA_QX();
       }
 
       if (Tools.notEmpty(roleRights)) {
         for (Menu menu : menuList) {
           menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
           if (menu.isHasMenu()) { 
             List<Menu> subMenuList = menu.getSubMenu();
             for (Menu sub : subMenuList) {
               sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
             }
           }
         }
       }
       JSONArray arr = JSONArray.fromObject(menuList);
       String json = arr.toString();
 
       json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("PARENT_ID", "pid").replaceAll("subMenu", "children").replaceAll("hasMenu", "checked");
       mv.addObject("zTreeNodes", json);
       mv.addObject("roleId", ROLE_ID);
       mv.addObject("msg", msg);
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
     }
     mv.setViewName("system/role/button_role");
     return mv;
   }
 
   @RequestMapping({"/auth/save"})
   @SystemLog(methods="角色管理",type="+组菜单权限")
   @ResponseBody
   public JSONObject saveAuth(@RequestParam String ROLE_ID, @RequestParam String menuIds) throws Exception {
     PageData pd = new PageData();
     JSONObject obj = new JSONObject();
     try {
//       if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) { 
         if ((menuIds != null) && (!"".equals(menuIds.trim()))) {
           BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
           Role role = this.roleService.getRoleById(ROLE_ID);
           role.setRIGHTS(rights.toString());
           this.roleService.updateRoleRights(role);
           pd.put("rights", rights.toString());
         } else {
           Role role = new Role();
           role.setRIGHTS("");
           role.setROLE_ID(ROLE_ID);
           this.roleService.updateRoleRights(role);
           pd.put("rights", "");
         } 
         pd.put("roleId", ROLE_ID);
         this.roleService.setAllRights(pd); 
	    obj.put("tabid", "role");
        obj.put("status", "1");
        obj.put("msg", "success"); 
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
       obj.put("status", "");
     }
     return obj;
   }
 
   @RequestMapping({"/roleButton/save"})
   @SystemLog(methods="角色管理",type="分配权限")
   @ResponseBody
   public JSONObject orleButton(@RequestParam String ROLE_ID, @RequestParam String menuIds, @RequestParam String msg) throws Exception{
     PageData pd = new PageData();
     JSONObject obj = new JSONObject();
     pd = getPageData();
     try {
      /* if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) {*/
         if ((menuIds != null) && (!"".equals(menuIds.trim()))) {
           BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
           pd.put("value", rights.toString());
         } else {
           pd.put("value", "");
         }
         pd.put("ROLE_ID", ROLE_ID);
         this.roleService.updateQx(msg, pd);
      /* }*/
       obj.put("tabid", "role");
       obj.put("status", "1");
       obj.put("msg", "success"); 
     } catch (Exception e) {
       this.logger.error(e.toString(), e);
       obj.put("status", "");
     }
     return obj;
   }
 
   @RequestMapping({"/delete"})
   @SystemLog(methods="角色管理",type="删除")
   @ResponseBody
   public JSONObject deleteRole(@RequestParam String ROLE_ID) throws Exception{
	 JSONObject obj = new JSONObject();
     PageData pd = new PageData();
     String errInfo = "";
     try {
         /*if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del")) {*/
         pd.put("ROLE_ID", ROLE_ID);
         List roleList_z = this.roleService.listAllRolesByPId(pd);
         if (roleList_z.size() > 0) {
           errInfo = "删除失败，请先删除此类别下的角色!";
         }
         else {
            List userlist = this.roleService.listAllUByRid(pd); 
            if ((userlist.size() > 0)){
               errInfo = "删除失败，请先删除此职位下的用户!";
            }else {
                this.roleService.deleteRoleById(ROLE_ID); 
                obj.put("statusCode", "200");
                errInfo = "success";
            }
        } 
     } catch (Exception e) {
         this.logger.error(e.toString(), e);
     }
	 obj.put("message", errInfo);
	 obj.put("closeCurrent", false);
     obj.put("result", errInfo);
     return obj; 
   }
 
   public Map<String, String> getHC(){
     Subject currentUser = SecurityUtils.getSubject();
     Session session = currentUser.getSession();
     return (Map)session.getAttribute("QX");
   }
 }
 package com.fh.service.system.menu;
 
 import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.PageData;
 
 @Service("menuService")
 public class MenuService
 {
 
   @Resource(name="daoSupport")
   private DaoSupport dao;
 
   public void deleteMenuById(String MENU_ID)
     throws Exception
   {
     this.dao.save("MenuMapper.deleteMenuById", MENU_ID);
   }
 
   public PageData getMenuById(PageData pd) throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.getMenuById", pd);
   }
 
   public PageData findMaxId(PageData pd)
     throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.findMaxId", pd);
   }
 
   public List<Menu> listAllParentMenu() throws Exception
   {
     return (List)this.dao.findForList("MenuMapper.listAllParentMenu", null);
   }
 
   public List<PageData> listPageByParam(Page page) throws Exception
   {
     return (List)this.dao.findForList("MenuMapper.listPageByParam",page);
   }
   
   public PageData findCount(Page page) throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.findCount", page);
   }
   
   public void saveMenu(PageData pd) throws Exception
   {
       this.dao.save("MenuMapper.insertMenu", pd); 
   }
 
   public List<Menu> listSubMenuByParentId(String parentId)
     throws Exception
   {
     return (List)this.dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
   }
 
   public List<Menu> listAllMenu() throws Exception
   {
     List<Menu> rl = listAllParentMenu();
     for (Menu menu : rl) {
       List subList = listSubMenuByParentId(menu.getMENU_ID());
       menu.setSubMenu(subList);
     }
     return rl;
   }
 
   public List<Menu> listAllSubMenu() throws Exception {
     return (List)this.dao.findForList("MenuMapper.listAllSubMenu", null);
   }
 
   public PageData edit(PageData pd)
     throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.updateMenu", pd);
   }
 
   public PageData editicon(PageData pd)
     throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.editicon", pd);
   }
 
   public PageData editType(PageData pd)
     throws Exception
   {
     return (PageData)this.dao.findForObject("MenuMapper.editType", pd);
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.service.system.menu.MenuService
 * JD-Core Version:    0.6.2
 */
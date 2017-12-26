 package com.fh.service.system.dictionaries;
 
 import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.PageData;
 
 @Service("dictionariesService")
 public class DictionariesService
 {
 
   @Resource(name="daoSupport")
   private DaoSupport dao;
 
   public void save(PageData pd)
     throws Exception
   {
     this.dao.save("DictionariesMapper.save", pd);
   }
 
   public void edit(PageData pd) throws Exception
   {
     this.dao.update("DictionariesMapper.edit", pd);
   } 
   
   public List<PageData> queryByPBM(String p_bm) throws Exception
   { 
     return (List)this.dao.findForList("DictionariesMapper.queryByPBM", p_bm);
   }

   public List<PageData> listAllParentDict() throws Exception
   {
     return (List)this.dao.findForList("DictionariesMapper.listAllParentDict", null);
   }
   
   public PageData findById(PageData pd) throws Exception
   {
     return (PageData)this.dao.findForObject("DictionariesMapper.findById", pd);
   }
 
   public PageData findCount(PageData pd) throws Exception
   {
     return (PageData)this.dao.findForObject("DictionariesMapper.findCount", pd);
   }
   
   public PageData findCountByParam(Page page) throws Exception
   {
     return (PageData)this.dao.findForObject("DictionariesMapper.findCountByParam", page);
   }
   
   public PageData findBmCount(PageData pd) throws Exception
   {
     return (PageData)this.dao.findForObject("DictionariesMapper.findBmCount", pd);
   }
 
   public List<PageData> dictlistPage(Page page) throws Exception
   {
     return (List)this.dao.findForList("DictionariesMapper.dictlistPage", page);
   }
 
   public List<PageData> dictlist(String parent_id)
     throws Exception
   {
     PageData dictPd = new PageData();
     dictPd.put("PARENT_ID", parent_id);
     return (List)this.dao.findForList("DictionariesMapper.dictlist", dictPd);
   }
 
   public List<PageData> findByPbm(String p_bm) throws Exception
   {
     PageData dictPd = new PageData();
     dictPd.put("P_BM", p_bm);
     return (List)this.dao.findForList("DictionariesMapper.findByPbm", dictPd);
   }
 
   public void delete(PageData pd)
     throws Exception
   {
     this.dao.delete("DictionariesMapper.delete", pd);
   }
 }
 package com.fh.service.app;
 
 import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.PageData;
 
 @Service("appDicService")
 public class AppDicService{
 
   @Resource(name="daoSupport")
   private DaoSupport dao; 
    
   public Object save(PageData pd) throws Exception
	   {
	     return this.dao.save("AppDicMapper.save", pd);
	   }
	 
	   public Object edit(PageData pd) throws Exception
	   {
		   return  this.dao.update("AppDicMapper.edit", pd);
	   } 
	   
	   public List<PageData> queryByPBM(String p_bm) throws Exception
	   { 
	     return (List)this.dao.findForList("AppDicMapper.queryByPBM", p_bm);
	   }

	   public List<PageData> listAllParentDict() throws Exception
	   {
	     return (List)this.dao.findForList("AppDicMapper.listAllParentDict", null);
	   }
	   
	   public PageData findById(PageData pd) throws Exception
	   {
	     return (PageData)this.dao.findForObject("AppDicMapper.findById", pd);
	   }
	 
	   public PageData findCount(PageData pd) throws Exception
	   {
	     return (PageData)this.dao.findForObject("AppDicMapper.findCount", pd);
	   }
	   
	   public PageData findByBianma(PageData pd) throws Exception
	   {
	     return (PageData)this.dao.findForObject("AppDicMapper.findByBianma", pd);
	   } 
	   
	   public PageData findCountByParam(Page page) throws Exception
	   {
	     return (PageData)this.dao.findForObject("AppDicMapper.findCountByParam", page);
	   }
	   
	   public PageData findBmCount(PageData pd) throws Exception
	   {
	     return (PageData)this.dao.findForObject("AppDicMapper.findBmCount", pd);
	   }
	 
	   public List<PageData> dictlistPage(Page page) throws Exception
	   {
	     return (List)this.dao.findForList("AppDicMapper.dictlistPage", page);
	   }
	 
	   public List<PageData> dictlist(String parent_id)
	     throws Exception
	   {
	     PageData dictPd = new PageData();
	     dictPd.put("PARENT_ID", parent_id);
	     return (List)this.dao.findForList("AppDicMapper.dictlist", dictPd);
	   }
	 
	   public List<PageData> findByPbm(String p_bm) throws Exception
	   {
	     PageData dictPd = new PageData();
	     dictPd.put("P_BM", p_bm);
	     return (List)this.dao.findForList("AppDicMapper.findByPbm", dictPd);
	   }
	 
	   public Object delete(PageData pd) throws Exception{
		   return this.dao.delete("AppDicMapper.delete", pd);
	   }
 }
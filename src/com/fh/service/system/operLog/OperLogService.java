package com.fh.service.system.operLog;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;  
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("operLogService")
public class OperLogService {
	
	  @Resource(name="daoSupport")
	  private DaoSupport dao;
	  
	  public List<PageData> listAllOperLog()  throws Exception {
	       return (List<PageData>)this.dao.findForList("OperLogMapper.listAllOperLog", null);
	  }
	  
	  public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("OperLogMapper.listPageByParam", page);
	  }
	  
	  public void saveOperLog(PageData pd)  throws Exception {
	      this.dao.save("OperLogMapper.saveOperLog", pd);
	  }
	  
	  public void updateOperLog(PageData pd) throws Exception {
		  this.dao.update("OperLogMapper.updateOperLog", pd);
	  }
	  
	  public void deleteOperLogById(PageData pd) throws Exception {
		  this.dao.delete("OperLogMapper.deleteOperLogById",pd);
	  } 
	  
	  public void delMulty(String[] Id) throws Exception {
		  this.dao.delete("OperLogMapper.delMulty",Id);
	  }

	public PageData findCount(Page page) throws Exception { 
		return (PageData)this.dao.findForObject("OperLogMapper.findCount",page);
	}

	 
}

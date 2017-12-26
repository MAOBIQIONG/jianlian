package com.fh.service.system.contractmodel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("contractModelService")
public class ContractModelService {

	 @Resource(name="daoSupport")
	  private DaoSupport dao;
	  
	  public List<PageData> listAllContractModel()  throws Exception {
	       return (List<PageData>)this.dao.findForList("ContractModelMapper.listAllContractModel", null);
	  }
	  
	  public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ContractModelMapper.listPageByParam", page);
	  }
	  
	  public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ContractModelMapper.findCount", page);
	  }
	  
	  public PageData findById(PageData pd)  throws Exception {
	       return (PageData)this.dao.findForObject("ContractModelMapper.findById", pd);
	  }
	  
	  public void saveContractModel(PageData pd)  throws Exception {
	      this.dao.findForList("ContractModelMapper.saveContractModel", pd);
	  }
	  
	  public void updateContractModel(PageData pd) throws Exception {
		  this.dao.update("ContractModelMapper.updateContractModel", pd);
	  }
	  
	  public void deleteContractModelById(PageData pd) throws Exception {
		  this.dao.delete("ContractModelMapper.deleteContractModelById",pd);
	  } 
	  
	  public void delMulty(String[] Id) throws Exception {
		  this.dao.delete("ContractModelMapper.delMulty",Id);
	  } 
	 
}

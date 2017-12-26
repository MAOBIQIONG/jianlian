package com.fh.service.park;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("settleService")
public class SettleParkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ZsSettledParkMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ZsSettledParkMapper.listPage",page);
	} 
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ZsSettledParkMapper.findById", pd);
	}
	
	public PageData queryBySid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ZsSettledParkMapper.queryBySid", pd);
	}
	 
	/*
	* 新增
	*/
	public boolean save(PageData pd)throws Exception{
		Object ob=dao.save("ZsSettledParkMapper.save", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}    
	}
	
	public boolean edit(PageData pd) throws Exception {
		Object ob=this.dao.update("ZsSettledParkMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	}
	

	public boolean updateStatus(PageData pd) throws Exception{
		Object ob=this.dao.update("ZsSettledParkMapper.updateStatus", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}   
	}   
	
	public boolean delete(PageData pd) throws Exception {
		Object ob=this.dao.update("ZsSettledParkMapper.delete", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	} 
}


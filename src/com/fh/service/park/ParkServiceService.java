package com.fh.service.park;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("parkSerService")
public class ParkServiceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ZsParkServiceMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ZsParkServiceMapper.listPage",page);
	} 
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ZsParkServiceMapper.findById", pd);
	}
	
	public PageData queryBySid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ZsParkServiceMapper.queryBySid", pd);
	}
	 
	/*
	* 新增
	*/
	public boolean save(PageData pd)throws Exception{
		Object ob=dao.save("ZsParkServiceMapper.save", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	}
	
	public boolean edit(PageData pd) throws Exception {
		Object ob=this.dao.update("ZsParkServiceMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}   
	}
	

	public boolean updateStatus(PageData pd) throws Exception{
		Object ob=this.dao.update("ZsParkServiceMapper.updateStatus", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	}   
	
	public boolean delete(PageData pd) throws Exception {
		Object ob=this.dao.update("ZsParkServiceMapper.delete", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}   
	} 
}


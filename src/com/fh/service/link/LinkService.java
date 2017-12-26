package com.fh.service.link;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("linkService")
public class LinkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("IndustryLinkMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IndustryLinkMapper.listPage",page);
	} 
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("IndustryLinkMapper.findById", pd);
	} 
	 
	/*
	* 新增
	*/
	public boolean save(PageData pd)throws Exception{
		Object ob=dao.save("IndustryLinkMapper.save", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	}
	
	public boolean edit(PageData pd) throws Exception {
		Object ob=this.dao.update("IndustryLinkMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}   
	} 
	
	public boolean delete(PageData pd) throws Exception {
		Object ob=this.dao.update("IndustryLinkMapper.delete", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}    
	} 
}


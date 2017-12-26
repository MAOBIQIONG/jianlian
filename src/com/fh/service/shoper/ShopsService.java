package com.fh.service.shoper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("shopsService")
public class ShopsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ShopsMapper.findCount", page);
	} 
	
	public List<PageData> listPageByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ShopsMapper.listPageByParam",page);
	} 
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopsMapper.queryById", pd);
	} 
	
	public PageData queryBySId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopsMapper.queryBySId", pd);
	}  
	 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("ShopsMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("ShopsMapper.edit", pd);
	}  
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("ShopsMapper.delete", pd);
	} 
	
	/*根据当前用户编号查询公司名称 */
	public PageData queryByUsercomp(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopsMapper.queryByUsercomp", pd);
	} 
}


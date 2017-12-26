package com.fh.service.shop;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("shopService")
public class ShopService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ShopMapper.findCount", page);
	} 
	
	public List<PageData> listPageByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ShopMapper.listPageByParam",page);
	} 
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopMapper.queryById", pd);
	} 
	
	public PageData queryBySId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopMapper.queryBySId", pd);
	}  
	 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("ShopMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("ShopMapper.edit", pd);
	}  
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("ShopMapper.delete", pd);
	} 
}


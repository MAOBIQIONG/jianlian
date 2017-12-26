package com.fh.service.app.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;



@Service("appHonorService")
public class AppHonorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppHonorMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("AppHonorMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("AppHonorMapper.delete", pd);
	}
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppHonorMapper.queryById", pd);
	} 
}


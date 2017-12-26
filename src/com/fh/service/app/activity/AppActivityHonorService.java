package com.fh.service.app.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;



@Service("appActivityHonorService")
public class AppActivityHonorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppActivityHonorMapper.save", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("AppActivityHonorMapper.delete", pd);
	} 
}


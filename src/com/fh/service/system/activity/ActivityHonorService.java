package com.fh.service.system.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;



@Service("activityHonorService")
public class ActivityHonorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ActivityHonorMapper.save", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.delete("ActivityHonorMapper.delete", pd);
	} 
}


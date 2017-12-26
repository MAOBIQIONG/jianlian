package com.fh.service.app.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appProjectBackupService")
public class AppProjectBackupService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppProjectBackupMapper.listAll", pd);
	}
	 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppProjectBackupMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("AppProjectBackupMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("AppProjectBackupMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectBackupMapper.findById", pd);
	}
}


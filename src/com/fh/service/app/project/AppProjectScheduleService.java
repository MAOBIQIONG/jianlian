package com.fh.service.app.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appProjectScheduleService")
public class AppProjectScheduleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppProjectScheduleMapper.listAll", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppProjectScheduleMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("AppProjectScheduleMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("AppProjectScheduleMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectScheduleMapper.findById", pd);
	} 
}


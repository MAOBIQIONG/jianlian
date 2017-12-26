package com.fh.service.projects;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("projectScheduleService")
public class ProjectScheduleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectScheduleMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ProjectScheduleMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectScheduleMapper.listPage",page);
	} 
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProjectScheduleMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception {
		this.dao.update("ProjectScheduleMapper.edit", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.update("ProjectScheduleMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProjectScheduleMapper.findById", pd);
	}
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("ProjectScheduleMapper.doexlelist", pd);
	}
}


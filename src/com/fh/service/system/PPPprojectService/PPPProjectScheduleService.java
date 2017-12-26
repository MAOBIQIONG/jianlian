package com.fh.service.system.PPPprojectService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("PPPprojectScheduleService")
public class PPPProjectScheduleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectScheduleMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PPPProjectScheduleMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectScheduleMapper.listPage",page);
	} 
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("PPPProjectScheduleMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception {
		this.dao.update("PPPProjectScheduleMapper.edit", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.update("PPPProjectScheduleMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PPPProjectScheduleMapper.findById", pd);
	} 
}


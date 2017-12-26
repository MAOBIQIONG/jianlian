package com.fh.service.projects;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("projectBackupService")
public class ProjectBackupService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectBackupMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ProjectBackupMapper.findCount", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectBackupMapper.listPage",page);
	} 
	 
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProjectBackupMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception {
		this.dao.update("ProjectBackupMapper.edit", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.delete("ProjectBackupMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProjectBackupMapper.findById", pd);
	}
}


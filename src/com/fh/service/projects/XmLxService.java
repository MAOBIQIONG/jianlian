package com.fh.service.projects;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("xmLxService")
public class XmLxService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;   
	
	//查询列表信息
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XmLxMapper.querylistPage", page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("XmLxMapper.findCount", page);
	}
	
	//查询列表信息
	public List<PageData> queryByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XmLxMapper.queryByParam",pd);
	}
	
	//查询列表信息
	public List<PageData> queryByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XmLxMapper.queryByPid",pd);
	} 
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("XmLxMapper.queryById", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("XmLxMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("XmLxMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.update("XmLxMapper.delete", pd);
	}
	
}


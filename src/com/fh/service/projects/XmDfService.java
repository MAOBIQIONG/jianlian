package com.fh.service.projects;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("xmdfService")
public class XmDfService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;   
 
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XmDfMapper.listPage", page);
	} 
	
	public PageData findCount(Page page)throws Exception{
		return (PageData)dao.findForObject("XmDfMapper.findCount", page);
	} 
	
	//查询列表信息
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XmDfMapper.findById", pd);
	}  
 
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("XmDfMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("XmDfMapper.delete", pd);
	}
	
	

	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("XmDfMapper.updatedelhd", pd);
	}
}


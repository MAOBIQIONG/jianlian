package com.fh.service.Govers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("goversDFService")
public class GoversDFService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;   
 
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoversDfMapper.listPage", page);
	} 
	
	public PageData findCount(Page page)throws Exception{
		return (PageData)dao.findForObject("GoversDfMapper.findCount", page);
	} 
	
	//查询列表信息
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoversDfMapper.findById", pd);
	}  
 
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("GoversDfMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("GoversDfMapper.delete", pd);
	}
	
}


package com.fh.service.system.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("honorService")
public class HonorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	 
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("HonorMapper.findCount", page);
	} 
	
	public List<PageData> listPageByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HonorMapper.listPageByParam",page);
	} 
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("HonorMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception {
		this.dao.update("HonorMapper.edit", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.delete("HonorMapper.delete", pd);
	}
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("HonorMapper.queryById", pd);
	} 
}


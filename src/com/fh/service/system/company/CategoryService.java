package com.fh.service.system.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("categoryService")
public class CategoryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CategoryMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("CategoryMapper.findCount", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CategoryMapper.listPage",page);
	} 
	 
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CategoryMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception {
		this.dao.update("CategoryMapper.edit", pd);
	} 
	
	public void delete(PageData pd) throws Exception {
		this.dao.update("CategoryMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("CategoryMapper.findById", pd);
	}
	
	public List<PageData> listByPId(String PID) throws Exception {
		return (List<PageData>) this.dao.findForList("CategoryMapper.listByPId", PID);
	}  
}


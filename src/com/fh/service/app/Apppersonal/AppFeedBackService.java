package com.fh.service.app.Apppersonal;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appFeedBackService")
public class AppFeedBackService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFeedBackMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("AppFeedBackMapper.findCount", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppFeedBackMapper.listPage",page);
	} 
	 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppFeedBackMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("AppFeedBackMapper.edit", pd);
	} 
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("AppFeedBackMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppFeedBackMapper.findById", pd);
	}
}


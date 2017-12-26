package com.fh.service.app.pay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appPayService")
public class AppPayService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppPayMapper.listPage", page);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(Page page) throws Exception {
		return (PageData) this.dao.findForObject("AppPayMapper.findCount",page);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppPayMapper.findById", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> findByUId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppPayMapper.findByUId", pd);
	} 
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppPayMapper.listAll", pd);
	} 
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return  dao.save("AppPayMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public Object delete(PageData pd)throws Exception{
		return dao.delete("AppPayMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public Object edit(PageData pd)throws Exception{
		return dao.update("AppPayMapper.edit", pd);
	}
}


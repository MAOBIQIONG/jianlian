package com.fh.service.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appTnpProjectService")
public class AppTnpProjectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 获取tnpproject列表
	*/
	public List<PageData> findOrderList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppTnpProjectMapper.findOrderList", page);
	}
	/**
	*获取tnpproject总数
	*/
	public PageData findOrderCount(Page page)throws Exception{
		return (PageData) dao.findForObject("AppTnpProjectMapper.findOrderCount", page);
	}
	/**
	* 新增tnpproject
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppTnpProjectMapper.save", pd);
	}
	/**
	*  修改tnpproject
	*/
	public Object updateOrder(PageData pd)throws Exception{
		return dao.update("AppTnpProjectMapper.updateOrder", pd);
	}
	
}

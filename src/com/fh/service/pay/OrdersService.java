package com.fh.service.pay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("ordersService")
public class OrdersService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrdersMapper.listPage", page);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(Page page) throws Exception {
		return (PageData) this.dao.findForObject("OrdersMapper.findCount",page);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrdersMapper.findById", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData queryByOid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrdersMapper.queryByOid", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrdersMapper.listAll", pd);
	} 
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OrdersMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.update("OrdersMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public Object edit(PageData pd)throws Exception{
		return dao.update("OrdersMapper.edit", pd);
	}
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("OrdersMapper.doexlelist", pd);
	}
}


package com.fh.service.pay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("payService")
public class PayService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PayMapper.listPage", page);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(Page page) throws Exception {
		return (PageData) this.dao.findForObject("PayMapper.findCount",page);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PayMapper.findById", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PayMapper.listAll", pd);
	} 
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("PayMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.update("PayMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("PayMapper.edit", pd);
	}
}


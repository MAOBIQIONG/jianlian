package com.fh.service.app.pay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appOrdersService")
public class AppOrdersService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppOrdersMapper.listPage", page);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(Page page) throws Exception {
		return (PageData) this.dao.findForObject("AppOrdersMapper.findCount",page);
	}
	
	/*
	* 根据订单号查询将要升级的等级
	*/
	public PageData queryToLevel(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppOrdersMapper.queryToLevel",pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppOrdersMapper.findById", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> findByUId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppOrdersMapper.findByUId", pd);
	} 
	/*
	 * 支付列表zong
	 */
	public PageData findByUIdzong(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppOrdersMapper.findByUIdzong", pd);
	} 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppOrdersMapper.listAll", pd);
	} 
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppOrdersMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public Object delete(PageData pd)throws Exception{
		return dao.delete("AppOrdersMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public Object edit(PageData pd)throws Exception{
		return dao.update("AppOrdersMapper.edit", pd);
	}
	
	/*
	* 绑定订单号
	*/
	public Object bindOrderNo(PageData pd)throws Exception{
		return dao.update("AppOrdersMapper.bindOrderNo", pd);
	} 
	
	public PageData queryCounts(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppOrdersMapper.queryCounts", pd);
	} 
	
}


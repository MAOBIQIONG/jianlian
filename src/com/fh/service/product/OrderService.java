package com.fh.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("orderService")
public class OrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("OrderMapper.findCountByStatus", page);
	}
	
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.listPageByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("OrderMapper.findCount", page);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("OrderMapper.save", pd);
	}
	
	/*
	 * 修改
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("OrderMapper.edit", pd);
	} 
	
	/*
	 * 删除
	 */
	public Object delete(PageData pd) throws Exception {
		//return this.dao.update("OrderMapper.delete", pd);
		return this.dao.delete("OrderMapper.delete", pd);
	}
	/*
	 * 根据编号查找详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("OrderMapper.querybyid", pd);
	}
}


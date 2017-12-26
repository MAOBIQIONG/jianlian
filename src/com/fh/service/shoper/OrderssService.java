package com.fh.service.shoper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("orderssService")
public class OrderssService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("OrderssMapper.findCountByStatus", page);
	}
	
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderssMapper.listPageByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderssMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("OrderssMapper.findCount", page);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("OrderssMapper.save", pd);
	}
	
	/*
	 * 修改
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("OrderssMapper.edit", pd);
	} 
	
	/*
	 * 删除
	 */
	public Object delete(PageData pd) throws Exception {
		//return this.dao.update("OrdersMapper.delete", pd);
		return this.dao.delete("OrderssMapper.delete", pd);
	}
	/*
	 * 根据编号查找详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("OrderssMapper.querybyid", pd);
	}
	
	/*
	 * 根据用户编号查找公司名称
	 */
	public PageData querybygsname(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("OrderssMapper.querybygsname", pd);
	}
}


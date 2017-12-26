package com.fh.service.Govers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("goversService")
public class GoversService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("GoversMapper.findCountByStatus", page);
	}
	
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoversMapper.listPageByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoversMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("GoversMapper.findCount", page);
	}
	
	/*
	*类型
	*/
	public List<PageData> listlxAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GoversMapper.listlxAll", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("GoversMapper.save", pd);
	}
	
	/*
	 * 修改
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("GoversMapper.edit", pd);
	} 
	
	/*
	 * 删除
	 */
	public Object delete(PageData pd) throws Exception {
		return this.dao.update("GoversMapper.delete", pd);
	}
	/*
	 * 根据编号查找详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("GoversMapper.querybyid", pd);
	}
}


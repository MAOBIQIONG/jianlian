package com.fh.service.jl_cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("cacheService")
public class cacheService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("cacheMapper.findCountByStatus", page);
	}
	
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("cacheMapper.listPageByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("cacheMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("cacheMapper.findCount", page);
	}
	
	/*
	*类型
	*/
	public List<PageData> listlxAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("cacheMapper.listlxAll", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("cacheMapper.save", pd);
	}
	
	/*
	 * 修改
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("cacheMapper.edit", pd);
	} 
	
	public void update(PageData pd) throws Exception {
		this.dao.update("cacheMapper.update", pd);
	}  
	
	/*
	 * 删除
	 */
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("cacheMapper.delete", pd);
	}
	/*
	 * 根据编号查找详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("cacheMapper.querybyid", pd);
	}
}


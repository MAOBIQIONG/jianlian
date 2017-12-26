package com.fh.service.system.pyq;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("PyqServices")
public class PyqServices {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PyqMapper.findCountByStatus", page);
	}
	
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PyqMapper.listPageByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PyqMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PyqMapper.findCount", page);
	}
	
	/*
	*类型
	*/
	public List<PageData> listlxAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PyqMapper.listlxAll", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("PyqMapper.save", pd);
	}
	
	/*
	 * 修改
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("PyqMapper.edit", pd);
	} 
	
	/*
	 * 删除
	 */
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("PyqMapper.delete", pd);
	}
	
	/*
	 * 删除评论
	 * 
	 */
	public Object pldelete(PageData pd) throws Exception {
		return this.dao.delete("PyqMapper.pldelete", pd);
	}
	/*
	 * 根据编号查找详情
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PyqMapper.querybyid", pd);
	}
	
	
	public List<PageData> lsitplParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PyqMapper.lsitplParam", page);
	}
	
	public PageData lsitplcounts(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PyqMapper.lsitplcounts", page);
	}
	
	public List<PageData> lsitDZParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PyqMapper.lsitDZParam", page);
	}
	
	public PageData lsitDZcounts(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PyqMapper.lsitDZcounts", page);
	}
	
	public PageData querybyplid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PyqMapper.querybyplid", pd);
	}
}


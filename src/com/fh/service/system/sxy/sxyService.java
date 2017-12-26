package com.fh.service.system.sxy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("sxyService")
public class sxyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SxyMapper.listPageByStatus", page);
	} 
	/**
	 * 管理列表条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("SxyMapper.findCountByStatus", page);
	}
	
	/**
	 * 新增 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object save(PageData pd)throws Exception{
		return dao.save("SxyMapper.save", pd);
	}
	
	/**
	 * 修改
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("SxyMapper.edit", pd);
	} 
	
	/**
	 * 根据id查询详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("SxyMapper.findById", pd);
	}
	
	/**
	 * 商学院上下线
	 * @param pd
	 * @throws Exception
	 */
	public void updates(PageData pd)throws Exception{
		this.dao.update("SxyMapper.updates", pd);
	}

	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delsxyByid(PageData pd)throws Exception{
		this.dao.update("SxyMapper.delsxyByid", pd);
	}
	
	
	
	/**
	 * 管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryByStatus1(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SxyMapper.listPageByStatus1", page);
	} 
	/**
	 * 管理列表条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findCountByStatus1(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("SxyMapper.findCountByStatus1", page);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void deljsByid(PageData pd)throws Exception{
		this.dao.update("SxyMapper.deljsByid", pd);
	}
	
	
	/**
	 * 教授新增 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object jssave(PageData pd)throws Exception{
		return dao.save("SxyMapper.jssave", pd);
	}
	
	/**
	 * 教授修改
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object jsedit(PageData pd) throws Exception {
		return this.dao.update("SxyMapper.jsedit", pd);
	} 
	
	/**
	 * 删除关联表信息
	 */
	public Object gldel(PageData pd)throws Exception{
		return dao.delete("SxyMapper.gldel", pd);
	}
	
	/**
	 * 新增关联表信息 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object glsave(PageData pd)throws Exception{
		return dao.save("SxyMapper.glsave", pd);
	}
	
	
	
	/**
	 * 根据id查询教授信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findjsById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("SxyMapper.findjsById", pd);
	}
}


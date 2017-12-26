package com.fh.service.system.Recom;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("recomService")
public class RecomService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RecomMapper.listPageByStatus", page);
	} 
	/**
	 * 管理列表条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("RecomMapper.findCountByStatus", page);
	}
	
	/**
	 * 新增 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object save(PageData pd)throws Exception{
		return dao.save("RecomMapper.save", pd);
	}
	
	/**
	 * 修改
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("RecomMapper.edit", pd);
	} 
	
	/**
	 * 根据id查询详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("RecomMapper.findById", pd);
	}
	
	/**
	 * 推荐上下线
	 * @param pd
	 * @throws Exception
	 */
	public void updates(PageData pd)throws Exception{
		this.dao.update("RecomMapper.updates", pd);
	}

	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delsxyByid(PageData pd)throws Exception{
		this.dao.update("RecomMapper.delsxyByid", pd);
	}
	
}


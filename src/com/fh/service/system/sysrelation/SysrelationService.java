package com.fh.service.system.sysrelation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("sysrelationService")
public class SysrelationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 系统人员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysRelationMapper.listPageByStatus", page);
	} 
	/**
	 * 系统人员条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("SysRelationMapper.findCountByStatus", page);
	} 
	
	//添加
	public Object save(PageData pd) throws Exception{
		return this.dao.save("SysRelationMapper.save", pd);
	}
	
	//删除
	public Object relaDel(PageData pd)throws Exception{
		return this.dao.delete("SysRelationMapper.relaDel", pd);
	}
	
	public PageData usrela(PageData pd)throws Exception {
		return (PageData)this.dao.findForObject("SysRelationMapper.usrela", pd);
	}
}


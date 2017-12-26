package com.fh.service.projects;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("projectService")
public class ProjectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ProjectMapper.findCount", page);
	}
	//根据项目ID查询项目所需的材料
	public List<PageData> queryCateByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.queryCateByPid", pd);
	} 
	
	public PageData queryMsgByStatus(PageData pd) throws Exception{
	     return (PageData)this.dao.findForObject("ProjectMapper.queryMsgByStatus", pd);
	} 
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ProjectMapper.findCountByStatus", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.listPage",page);
	} 
	
	public List<PageData> findBidderByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.findBidderByPid", pd);
	}
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.listPageByStatus", page);
	}
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("ProjectMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("ProjectMapper.edit", pd);
	} 
	
	public Object uploadCover(PageData pd) throws Exception {
		return this.dao.update("ProjectMapper.uploadCover", pd);
	}  
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.update("ProjectMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProjectMapper.findById", pd);
	}
	
	public Object updateStatus(PageData pd) throws Exception{
		return this.dao.update("ProjectMapper.updateStatus", pd);
	}  
	
	public Object updateSigned(PageData pd) throws Exception{
		return this.dao.update("ProjectMapper.updateSigned", pd);
	}  
	
	public List<PageData> querycategory(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.querycategory", pd);
	} 
	
	//添加项目经理到讨论小组当管理员
	public Object saveXzcy(PageData pd)throws Exception{
		return dao.save("ProjectMapper.addXz", pd);
	}
	
	public List<PageData> querycategorybyid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.querycategorybyid", pd);
	}
	
	//查询最大的项目编号
	public PageData queryMaxProNo(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProjectMapper.queryMaxProNo", pd);
	}
	
	//查询所属行业的名字
	public PageData queryNameById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProjectMapper.queryNameById", pd);
	} 
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("ProjectMapper.doexlelist", pd);
	}
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("ProjectMapper.updatedelhd", pd);
	}
}


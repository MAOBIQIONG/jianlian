package com.fh.service.system.PPPprojectService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("PPPprojectService")
public class PPPProjectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectMapper.listAll", pd);
	}
	//ppp首页
	
	public List<PageData> queryByStatus(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectMapper.listppp", page);
	}
	
	public PageData findCountByStatus(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PPPProjectMapper.listpppStatus", page);
	}
	
	public Object uploadCover(PageData pd) throws Exception {
		return this.dao.update("PPPProjectMapper.uploadCover", pd);
	}  
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectMapper.listPage",page);
	} 
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PPPProjectMapper.findCount", page);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PPPProjectMapper.querybyid", pd);
	}
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("PPPProjectMapper.save", pd);
	}
	
	/*
	* 存储项目阶段
	*/
	public Object insetsave(PageData pd)throws Exception{
		return dao.save("PPPProjectMapper.insetsave", pd);
	}
	
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("PPPProjectMapper.edit", pd);
	} 
	
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.update("PPPProjectMapper.delete", pd);
	}
	
	
	public Object updateSigned(PageData pd) throws Exception{
		return this.dao.update("PPPProjectMapper.updateSigned", pd);
	} 
	
	public Object updateStatus(PageData pd) throws Exception{
		return this.dao.update("PPPProjectMapper.updateStatus", pd);
	}
	
	
	
	public PageData querPro_no(String no) throws Exception{
		return (PageData) this.dao.findForObject("PPPProjectMapper.querPro_no", no);
	}
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("PPPProjectMapper.updatedelhd", pd);
	}
}


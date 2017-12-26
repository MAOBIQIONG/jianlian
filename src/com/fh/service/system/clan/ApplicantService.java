package com.fh.service.system.clan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("applicantService")
public class ApplicantService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ApplicantMapper.listPage", page);
	} 
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ApplicantMapper.findCount", page);
	} 
	
	public PageData findById(PageData pd)  throws Exception {
	       return (PageData)this.dao.findForObject("ApplicantMapper.findById", pd);
	}
	
	public Object delete(PageData pd)  throws Exception {
	       return this.dao.delete("ApplicantMapper.delete", pd);
	}
	
	public Object edit(PageData pd)  throws Exception {
	       return this.dao.update("ApplicantMapper.edit", pd);
	} 
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("ApplicantMapper.updatedelhd", pd);
	}
}

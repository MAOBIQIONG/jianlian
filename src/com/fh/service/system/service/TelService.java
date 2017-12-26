package com.fh.service.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("telService")
public class TelService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	 
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ServiceTelMapper.listPageByParam", page);
	} 
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ServiceTelMapper.findCount", page);
	}
	
	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ServiceTelMapper.queryById", pd);
	} 
	
	// 添加
	public void save(PageData pd) throws Exception {
		this.dao.save("ServiceTelMapper.save", pd);
	} 

	// 更新
	public void edit(PageData pd) throws Exception {
		this.dao.update("ServiceTelMapper.edit", pd);
	} 
	 
	// 删除
	public void delete(PageData pd) throws Exception {
		this.dao.delete("ServiceTelMapper.delete", pd);
	}
	
	//查询
	public List<PageData> findBmCount(PageData pd)  throws Exception {
	    return (List<PageData>)this.dao.findForList("ServiceTelMapper.findBmCount", pd);
	} 
}

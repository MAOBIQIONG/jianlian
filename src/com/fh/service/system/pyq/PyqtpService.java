package com.fh.service.system.pyq;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("pyqtpService")
public class PyqtpService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;   
	
	//查询列表信息
	public List<PageData> queryByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("pyqtpMapper.queryByPid", pd);
	} 
	
	public PageData queryMaxOrderby(PageData pd)throws Exception{
		return (PageData)dao.findForObject("pyqtpMapper.queryMaxOrderby", pd);
	} 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("pyqtpMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("pyqtpMapper.edit", pd);
	} 
	
	public Object delete(String[] ids) throws Exception {
		return this.dao.update("pyqtpMapper.del", ids);
	}
	
}


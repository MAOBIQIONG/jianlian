package com.fh.service.app.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;



@Service("appCategoryService")
public class AppCategoryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppCategoryMapper.listAll", pd);
	} 
 
	public List<PageData> listByPId(String PID) throws Exception {
		return (List<PageData>) this.dao.findForList("AppCategoryMapper.listByPId", PID);
	}  
}


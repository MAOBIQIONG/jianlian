package com.fh.service.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;


@Service("appCitiesService")
public class AppCitiesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	 
	/*
	* 查询城市
	*/
	public List<PageData> queryCitiesByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppCitiesMapper.queryCitiesByParam", pd);
	} 
}


package com.fh.service.app.Apppoints;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("apppointsService")
public class AppPointsService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//总积分获取
	public PageData querytotal(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ApppointsMapper.querytotal", pd);
	}
	//积分获取明细列表
	public List<PageData> queryDetailed(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("ApppointsMapper.queryDetailed", pd);
	}
	
	//积分总
	public PageData queryDetailedjifenzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ApppointsMapper.queryDetailedjifenzong", pd);
	}
}

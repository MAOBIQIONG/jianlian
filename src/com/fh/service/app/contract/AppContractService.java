package com.fh.service.app.contract;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appcontractService")
public class AppContractService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//合同列表
	public List<PageData> querContractlist(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppContractMapper.querContractlist", pd);
	}
	
	//总积分获取
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppContractMapper.findById", pd);
	}
}

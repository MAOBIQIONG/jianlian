package com.fh.service.app.Sxy;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appsxyService")
public class AppSxyService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//根据ID查询新闻详情
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppSxyMapper.queryById",pd);
	}
	
	//根据ID查询新闻详情
	public List<PageData> queryByjs(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppSxyMapper.queryByjs",pd);
	}
}


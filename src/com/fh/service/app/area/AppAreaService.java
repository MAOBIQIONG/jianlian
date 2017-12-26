package com.fh.service.app.area;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appareaService")
public class AppAreaService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> queryAllParent(PageData pd) throws Exception {
		return (List) this.dao.findForList("AppSysAreaMapper.queryAllParent",pd);
	}
	
}

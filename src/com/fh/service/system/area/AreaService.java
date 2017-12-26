package com.fh.service.system.area;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("areaService")
public class AreaService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> queryAllParent(PageData pd) throws Exception {
		return (List) this.dao.findForList("SysAreaMapper.queryAllParent",pd);
	}
	
	public List<PageData> querybyPid(PageData pd) throws Exception {
		return (List) this.dao.findForList("SysAreaMapper.querybyPid",pd);
	}
	
	public List<PageData> queryAll() throws Exception {
		return (List) this.dao.findForList("SysAreaMapper.queryAll",null);
	} 
}

package com.fh.service.app.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appcompanyService")
public class AppcompanyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public Object save(PageData pd) throws Exception {
		return this.dao.save("AppCompanyMapper.adduserbycompanyid", pd);
	}
	
	public PageData queryByUId(PageData pd) throws Exception {
		return (PageData)this.dao.findForObject("AppCompanyMapper.queryByUId", pd);
	} 
	
	public Object editCompany(PageData pd) throws Exception {
		return this.dao.update("AppCompanyMapper.editCompany", pd);
	}
	
	public Object editPOSITION(PageData pd) throws Exception {
		return this.dao.update("AppCompanyMapper.editPOSITION", pd);
	}
}

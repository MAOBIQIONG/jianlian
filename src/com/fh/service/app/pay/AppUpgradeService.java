package com.fh.service.app.pay; 
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport; 
import com.fh.util.PageData; 
@Service("appUpgradeService")
public class AppUpgradeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	/*
	* 修改
	*/
	public Object edit(PageData pd)throws Exception{
		return dao.update("AppUpgradeMapper.editUpStatus", pd);
	} 
}


package com.fh.service.system.push;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("PushService")
public class PushService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> listByParam(Page page) throws Exception {
		return  (List<PageData>) this.dao.findForList("AppPushMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("AppPushMapper.findCount", page);
	}
}

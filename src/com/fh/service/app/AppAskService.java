package com.fh.service.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("AppAskService")
public class AppAskService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//保存提问数据
	public void saveA(PageData pd) throws Exception {
		this.dao.save("AppAskMapper.saveA", pd);
	}
	
	//查找一个群组的同一天的疑问
	public List findByClazzidandDay(PageData pd) throws Exception {
		return (List) this.dao.findForList("AppAskMapper.findByClazzidandDay", pd);
	}
	
}

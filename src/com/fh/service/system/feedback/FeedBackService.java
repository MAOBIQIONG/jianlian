package com.fh.service.system.feedback;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("feedBackService")
public class FeedBackService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao; 
	
	public List<PageData> listPageByParam(Page page) throws Exception{
	    return (List)this.dao.findForList("FeedBackMapper.listPageByParam", page);
	} 

	public PageData findCount(Page page) throws Exception{
	    return (PageData)this.dao.findForObject("FeedBackMapper.findCount", page);
	} 
	
	public PageData queryById(PageData pd) throws Exception{
	    return (PageData)this.dao.findForObject("FeedBackMapper.queryById", pd);
	} 
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("FeedBackMapper.updatedelhd", pd);
	}
} 
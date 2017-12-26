package com.fh.service.app.media;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appmediaService")
public class AppMediaService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//媒体分类获取 
	public List<PageData>	queryByfenlei(PageData pd)throws Exception {
		return (List<PageData>) dao.findForList("AppMediaMapper.queryByfenlei", pd);
	}
	//媒体首页
	public List<PageData> queryByshouye(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryByshouye", pd);
	}
	public PageData queryByshouyezong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppMediaMapper.queryByshouyezong", pd);
	}
	//媒体首页置顶信息
	public List<PageData> queryShouYeTopNews(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryShouYeTopNews", pd);
	}
	
	
	//新闻置顶信息
	public List<PageData> queryTopNews(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryTopNews", pd);
	}  
	
	//媒体首页新闻
	public List<PageData> queryByxinwen(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryByxinwen", pd);
	} 

	//新闻总
	public PageData queryByxinwenzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppMediaMapper.queryByxinwenzong", pd);
	}
	
	//媒体首页新闻
//	@Cacheable(value="qhCache")
	public List<PageData> queryHot() throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryHot",null);
	} 
	
	//根据ID查询新闻详情
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppMediaMapper.queryById", pd);
	}
	
	//修改新闻的收藏、分享、评论收藏次数
	public Object updateCounts(PageData pd) throws Exception {
		return  this.dao.update("AppMediaMapper.updateCounts", pd);
	} 
	
	//修改新闻的收藏、分享、评论取消次数
	public Object updateminus(PageData pd) throws Exception {
		return  this.dao.update("AppMediaMapper.updateminus", pd);
	} 
	
	//根据参数查询，分页
	public List<PageData> queryPageByParam(Page page) throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.queryPageByParam", page);
	}
	//分页总数
	public PageData queryPageByParamzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppMediaMapper.queryPageByParamzong", pd);
	}
	//根据标题模糊查询
	public List<PageData> findMediaByTitle(PageData pd)throws Exception {
		return (List<PageData>) this.dao.findForList("AppMediaMapper.findMediaByTitle", pd);
	}
}


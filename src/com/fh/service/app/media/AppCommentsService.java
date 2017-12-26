package com.fh.service.app.media;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appCommentsService")
public class AppCommentsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
 
	//根据位置编号查找要显示的轮换大图
	public List<PageData> queryComByMediaId(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppCommentsMapper.queryComByMediaId",pd);
	} 
	
	//添加评论 
	public Object save(PageData pd)throws Exception{
		return this.dao.save("AppCommentsMapper.save", pd);
	}
	//添加分享 
	public Object addShare(PageData pd) throws Exception {
		return this.dao.save("AppCommentsMapper.addShare", pd);
	}  
	//查询当前新闻的所有评论 
	public List<PageData> queryBypinluns(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppCommentsMapper.queryBypinluns",pd);
	} 
	//查询当前新闻的所有回复
	public List<PageData> queryByhuifus(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppCommentsMapper.queryByhuifus",pd);
	} 
	
	//查询当前评论是第几条
	public PageData quseronpin(PageData pd)  throws Exception {
	       return  (PageData) this.dao.findForObject("AppCommentsMapper.quseronpin",pd);
	} 
	//查询当前用户回复的第几条
	public PageData queryusercoms(PageData pd)  throws Exception {
	       return (PageData) this.dao.findForObject("AppCommentsMapper.queryusercoms",pd);
	}  
}

package com.fh.service.app.clan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appClanService")
public class AppClanService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询
	public List<PageData> queryAll(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppClanMapper.queryAll",pd);
	} 
	//城市建联总
	public PageData queryAllzong(PageData pd)  throws Exception {
	       return (PageData) this.dao.findForObject("AppClanMapper.queryAllzong",pd);
	} 
	
	//获取某个城市建联的关注用户的信息
	public List<PageData> queryByCId(PageData pd) throws Exception {
	       return (List<PageData>)this.dao.findForList("AppClanMapper.queryByCId",pd);
	} 
	 
	//获取某个用户关注的城市建联信息 
	public List<PageData> queryByUId(PageData pd) throws Exception {
	       return (List<PageData>)this.dao.findForList("AppClanMapper.queryByUId",pd);
	} 
	
	//关注城市总 
	public PageData queryByUIdzong(PageData pd) throws Exception {
	       return (PageData) this.dao.findForObject("AppClanMapper.queryByUIdzong",pd);
	} 
	
	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppClanMapper.queryById", pd);
	} 
	//取消关注
	public Object deleteguanzhu(PageData pd) throws Exception {
		return this.dao.delete("AppClanMapper.deleteguanzhu", pd);
	}
	//添加关注
	public Object saveguanzhu(PageData pd)throws Exception{
		return dao.save("AppClanMapper.saveguanzhu", pd);
	}
	//查询当前用户是否已经关注过
	public PageData queryByuserclan(PageData pd)  throws Exception {
	       return (PageData) this.dao.findForObject("AppClanMapper.queryByuserclan",pd);
	}
	//关注增加数量
	public Object updateaddClanCounts(PageData pd) throws Exception {
		return  this.dao.update("AppClanMapper.updateaddClanCounts", pd);
	}
	//取消关注数量减少
	public Object updatedelClanCounts(PageData pd) throws Exception {
		return  this.dao.update("AppClanMapper.updatedelClanCounts", pd);
	}
	
	//获取城市建联成员
	public List<PageData> queryclanusers(PageData pd) throws Exception {
	       return (List<PageData>)this.dao.findForList("AppClanMapper.queryclanusers",pd);
	} 
	public PageData queryclanuserscount(PageData pd)  throws Exception {
	       return (PageData) this.dao.findForObject("AppClanMapper.queryclanuserscount",pd);
	}
	
	public List<PageData> queryclanlist(PageData pd) throws Exception {
		return (List) this.dao.findForList("AppClanMapper.queryclanlist",pd);
	}
}

package com.fh.service.system.clan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("clanService")
public class ClanService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ClanMapper.listPageByParam", page);
	}
	//查询全部的城市建联
	public List<PageData> queryAll(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ClanMapper.queryAll", pd);
	}
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ClanMapper.findCount", page);
	}
	
	//查询
	public List<PageData> listPage(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ClanMapper.listPage", page);
	} 
	
	public PageData findMemberCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ClanMapper.findMemberCount", page);
	}
		
	// 添加
	public void addClan(PageData pd) throws Exception {
		this.dao.save("ClanMapper.addClan", pd);
	}

	// 更新
	public void upClan(PageData pd) throws Exception {
		this.dao.update("ClanMapper.upClan", pd);
	}
	
	// 更新
	public void updateaddClanCounts(PageData pd) throws Exception {
		this.dao.update("ClanMapper.updateaddClanCounts", pd);
	}
	
	// 更新
	public void updatedelClanCounts(PageData pd) throws Exception {
		this.dao.update("ClanMapper.updatedelClanCounts", pd);
	}

	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ClanMapper.queryById", pd);
	}

	// 批量删除
	public void delClan(String[] auserId) throws Exception {
		this.dao.delete("ClanMapper.delClan", auserId);
	}

	// 删除
	public void delClanbyid(PageData pd) throws Exception {
		this.dao.delete("ClanMapper.delClanbyid", pd);
	}

	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("ClanMapper.updatedelhd", pd);
	}
}

package com.fh.service.system.club;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("clubService")
public class ClubService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 查询所有活动信息
	public List<PageData> queryClub(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("ClubMapper.queryClub", pd);
	}
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ClubMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ClubMapper.findCount", page);
	}
	
	//查询需审核的记录数，以作提醒
	public PageData queryMsgByStatus() throws Exception {
	       return (PageData)this.dao.findForObject("ClubMapper.queryMsgByStatus",null);
	} 
	// 添加
	public void addClub(PageData pd) throws Exception {
		this.dao.save("ClubMapper.addClub", pd);
	}

	// 更新
	public void upClub(PageData pd) throws Exception {
		this.dao.update("ClubMapper.upClub", pd);
	}

	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ClubMapper.queryById", pd);
	}

	// 批量删除
	public void delClub(String[] auserId) throws Exception {
		this.dao.delete("ClubMapper.delClub", auserId);
	}

	// 删除
	public void delClubbyid(PageData pd) throws Exception {
		this.dao.delete("ClubMapper.delClubbyid", pd);
	}

	// 根据证件名称查找所有信息
	public List<PageData> querybyname(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("ClubMapper.querybyname", pd);
	}

	// 审核/不审核
//	public void upstatus(PageData pd) throws Exception {
//		this.dao.update("ClanMapper.upstatus", pd);
//	}
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("ClubMapper.updatedelhd", pd);
	}
}

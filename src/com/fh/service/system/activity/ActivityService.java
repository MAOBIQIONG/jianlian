package com.fh.service.system.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("activityService")
public class ActivityService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 查询所有活动信息
	public List<PageData> queryactivity(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("ActivityMapper.queryactivity", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ActivityMapper.listPageByParam", page);
	}
	
	//查询
	public List<PageData> queryLevelbyAid(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("ActivityMapper.queryLevelbyAid", pd);
	} 
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("ActivityMapper.findCount", page);
	}
	public PageData queryMsgByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("ActivityMapper.queryMsgByStatus", null);
	}
	
	// 添加
	public void save(PageData pd) throws Exception {
		this.dao.save("ActivityMapper.save", pd);
	}
	
	//添加活动等级限制
	public void addactivityLevel(PageData pd) throws Exception {
		this.dao.save("ActivityMapper.addactivityLevel", pd);
	} 

	// 更新
	public void edit(PageData pd) throws Exception {
		this.dao.update("ActivityMapper.edit", pd);
	}

	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ActivityMapper.queryById", pd);
	}

	// 批量删除
	public void delactivity(String[] auserId) throws Exception {
		this.dao.delete("ActivityMapper.delactivity", auserId);
	}

	// 删除
	public void delete(PageData pd) throws Exception {
		this.dao.delete("ActivityMapper.delete", pd);
	}
	
	//删除某个活动的等级
	public void deleteLevel(PageData pd) throws Exception {
		this.dao.delete("ActivityMapper.deleteLevel", pd);
	} 
	
	// 批量删除
	public void delActivityHonor(String[] auserId) throws Exception {
		this.dao.delete("ActivityMapper.delActivityHonor", auserId);
	}

	// 删除
	public void delActHonorById(PageData pd) throws Exception {
		this.dao.delete("ActivityMapper.delActHonorById", pd);
	}

	// 根据证件名称查找所有信息
	public List<PageData> querybyname(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("ActivityMapper.querybyname", pd);
	}

	// 审核/不审核
	public void upstatus(PageData pd) throws Exception {
		this.dao.update("ActivityMapper.upstatus", pd);
	}
	//根据userid获取
	public PageData querybyuserName(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ActivityMapper.querybyuserName", pd);
	}
	//创建人
	public PageData querybycreateName(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ActivityMapper.querybycreateName", pd);
	} 
	 
	public void updateImgPath(PageData pd) throws Exception {
		this.dao.update("ActivityMapper.updateImgPath", pd);
	} 
	
	//查询参与人列表
	public List<PageData> listByParam4(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ActivityMapper.listPage4",page);
	} 
	 
	
	public PageData findCount4(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ActivityMapper.findCount4", page);
	} 
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("ActivityMapper.doexlelist", pd);
	}
	
	//去掉小红点
		public Object updatedelhd(PageData pd) throws Exception{
			return this.dao.update("ActivityMapper.updatedelhd", pd);
		}
}

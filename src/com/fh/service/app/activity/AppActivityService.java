package com.fh.service.app.activity;

import java.util.List;

import javax.annotation.Resource;

import oracle.net.aso.p;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appActivityService")
public class AppActivityService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//<!--查询当前用户参与的项目列表 --> 
	public List<PageData> queryActByUId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.queryActByUId", pd);
	} 
	
	//查询当前用户参与的项目总
	public PageData queryActByUIdzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppActivityMapper.queryActByUIdzong", pd);
	}
	
	//<!--根据活动ID查询可以参加的会员等级 --> 
	public List<PageData> queryLevelByAId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.queryLevelByAId", pd);
	} 
	
	//<!--判断会员是否可以参与活动 --> 
	public List<PageData> checkCanApply(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.checkCanApply", pd);
	}  
	
	//查询热门活动
//	@Cacheable(value="qhaCache")
	public List<PageData> queryHotActivity(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.queryHotActivity", pd);
	}
	
	//<!--根据参数查询活动信息 --> 
	public List<PageData> queryByParam(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppActivityMapper.queryByParam", pd);
	}
	
	//<!--根据Id查询活动详细信息 --> 
	public PageData queryById(PageData pd)  throws Exception {
	       return (PageData)this.dao.findForObject("AppActivityMapper.queryById", pd);
	}
	
	//<!--根据参数查询用户发布活动反馈信息 --> 
	public List<PageData> queryBackupByParam(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("AppActivityMapper.queryBackupByParam", pd);
	} 
	//user发布活动总
	public PageData queryBackupByParamfbhdzong(PageData pd)  throws Exception {
	       return (PageData) this.dao.findForObject("AppActivityMapper.queryBackupByParamfbhdzong", pd);
	}
	
	//<!--根据活动Id查询嘉宾信息 -->
	public List<PageData> queryHonorByAId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.queryHonorByAId", pd);
	}
	
	//<!--根据活动Id查询参与者信息 -->
	public List<PageData> queryUserByAId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.queryUserByAId", pd);
	} 
	// <!-- 添加用户关注活动信息 -->
	public Object save(PageData pd) throws Exception {
		return this.dao.save("AppActivityMapper.save", pd);
	}
	
	//  <!-- 添加活动信息 -->
	public Object addActUser(PageData pd) throws Exception {
		return this.dao.save("AppActivityMapper.addActUser", pd);
	} 

	// 更新
	public Object upactivity(PageData pd) throws Exception {
		return this.dao.update("AppActivityMapper.upactivity", pd);
	} 
	
	//修改收藏次数
	public Object updateCounts(PageData pd) throws Exception {
		return  this.dao.update("AppActivityMapper.updateCounts", pd);
	} 
	
	//修改取消收藏次数
	public Object updateminus(PageData pd) throws Exception {
		return  this.dao.update("AppActivityMapper.updateminus", pd);
	}
	
	//取消报名
	public Object deleteApplyer(PageData pd) throws Exception {
		return  this.dao.delete("AppActivityMapper.deleteApplyer", pd);
	} 
	
	//删除活动
	public Object delete(PageData pd) throws Exception {
		return  this.dao.delete("AppActivityMapper.delete", pd);
	} 
	
	//根据标题模糊查询
	public List<PageData> findActityByName(PageData pd)throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.findActityByName", pd);
	}
	
	//检查某用户是否已参加该活动
	public List<PageData> checkIsApply(PageData pd)throws Exception {
		return (List<PageData>) this.dao.findForList("AppActivityMapper.checkIsApply", pd);
	} 
	
}

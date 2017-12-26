package com.fh.service.app.project;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appProjectService")
public class AppProjectService {


	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//根据项目Id搜索材料 
	public List<PageData> queryCateByPid(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryCateByPid",pd);
	}
	
	//热门项目列表搜索 
//	@Cacheable(value="qhpCache")
	public List<PageData> queryHotProject() throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryHotProject",null);
	}
	
    //最新成功交易的项目
//	@Cacheable(value="qnbCache")
	public PageData queryNewBidder(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryNewBidder", pd);
	} 
	 
	//根据项目名称搜索未中标项目列表
	public List<PageData> queryNoBidderByParam(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryNoBidderByParam", pd);
	} 
	
	//根据项目名称搜索未中标项目列表总
	public PageData queryNoBidderByParamzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryNoBidderByParamzong", pd);
	}
	
	//根据用户ID项目反馈信息
	public List<PageData> queryBackupByUId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryBackupByUId", pd);
	}  
	
	//根据用户查询总项目反馈 
	public PageData queryBackupByUIdCOUNT(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryBackupByUIdCOUNT", pd);
	} 
	
	//根据参数查询，分页
	public List<PageData> queryPageByParam(Page page) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryPageByParam", page);
	}
	
	//交易列表zong
	public PageData queryPageByParamprozong(Page page) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryPageByParamprozong", page);
	}
	//根据参数用户参与接单的项目列表，分页
	public List<PageData> queryBidByUId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryBidByUId", pd);
	} 
	
	//根据参数用户参与接单的项目zong
	public PageData queryBidByUIdjezong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryBidByUIdjezong", pd);
	} 
	
	//查询某用户是否参与参与某个项目的接单
	public PageData queryBiddedById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppProjectMapper.queryBiddedById", pd);
	} 
	
	
	//我要接单
	public Object addbidder(PageData pd)throws Exception{
		return dao.save("AppProjectMapper.addbidder", pd);
	}
 
	//项目中标确认
	public Object upsummaryok(PageData pd) throws Exception {
		return dao.update("AppProjectMapper.upsummaryok", pd);
	}
	
	//项目列表获取
	public List<PageData> queryprojectlist(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppProjectMapper.queryprojectlist", pd);
	} 
	
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("AppProjectMapper.save", pd);
	}
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("AppProjectMapper.edit", pd);
	} 
	
	//修改收藏次数
	public Object updateCounts(PageData pd) throws Exception {
		return  this.dao.update("AppProjectMapper.updateCounts", pd);
	} 
	
	//修改取消收藏次数
	public Object updateminus(PageData pd) throws Exception {
		return  this.dao.update("AppProjectMapper.updateminus", pd);
	} 
	
	//取消接单
	public Object deletebidder(PageData pd) throws Exception {
		return  this.dao.delete("AppProjectMapper.deletebidder", pd);
	} 
	
	//删除项目
	public Object delete(PageData pd) throws Exception {
		return  this.dao.delete("AppProjectMapper.delete", pd);
	} 
	
	//根据标题模糊查询
	public List<PageData> findProjectByTitle(PageData pd) throws Exception {
		return  (List<PageData>) this.dao.findForList("AppProjectMapper.findProjectByTitle", pd);
	} 
	

	//根据标题查出项目所有信息
	public PageData queryByProId(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("AppProjectMapper.queryByProId", pd);
	} 
	
	//根据项目Id查询项目详情
	public List<PageData> queryById(PageData pd) throws Exception {
		return  (List<PageData>) this.dao.findForList("AppProjectMapper.queryById", pd);
	} 
	
	//查询某个会员发布成功的项目总数
	public PageData countPubProject(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("AppProjectMapper.countPubProject", pd);
	}  
	//查找材料
	public List<PageData> querycategory(PageData pd) throws Exception {
		return  (List<PageData>) this.dao.findForList("AppProjectMapper.querycategory", pd);
	}
	
	//新增材料
	public Object saveproject_category(PageData pd)throws Exception{
		return dao.save("AppProjectMapper.saveproject_category", pd);
	}
	//删除
	public Object deletePROJECT_CATEGORY(PageData pd) throws Exception {
		return this.dao.delete("AppProjectMapper.deletePROJECT_CATEGORY", pd);
	}
}

package com.fh.service.system.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("businessService")
public class BusinessService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 查询所有活动信息
	public List<PageData> queryactivity(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("BusinessMapper.queryactivity", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("BusinessMapper.listPageByParam", page);
	}
	
	//查询
	public List<PageData> queryLevelbyAid(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("BusinessMapper.queryLevelbyAid", pd);
	} 
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("BusinessMapper.findCount", page);
	}
	public PageData queryMsgByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("BusinessMapper.queryMsgByStatus", null);
	}
	
//	// 添加
//	public void save(PageData pd) throws Exception {
//		this.dao.save("BusinessMapper.save", pd);
//	}
	
	//添加活动等级限制
	public void addactivityLevel(PageData pd) throws Exception {
		this.dao.save("BusinessMapper.addactivityLevel", pd);
	} 

//	// 更新
//	public void edit(PageData pd) throws Exception {
//		this.dao.update("BusinessMapper.edit", pd);
//	}
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		try {
			dao.save("BusinessMapper.save", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		try {
		    this.dao.update("BusinessMapper.edit", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("BusinessMapper.queryById", pd);
	}

	// 批量删除
	public void delactivity(String[] auserId) throws Exception {
		this.dao.delete("BusinessMapper.delactivity", auserId);
	}

	// 删除
	public void delete(PageData pd) throws Exception {
		this.dao.delete("BusinessMapper.delete", pd);
	}
	
	//删除某个活动的等级
	public void deleteLevel(PageData pd) throws Exception {
		this.dao.delete("BusinessMapper.deleteLevel", pd);
	} 
	
	// 批量删除
	public void delActivityHonor(String[] auserId) throws Exception {
		this.dao.delete("BusinessMapper.delActivityHonor", auserId);
	}

	// 删除
	public void delActHonorById(PageData pd) throws Exception {
		this.dao.delete("BusinessMapper.delActHonorById", pd);
	}

	// 根据证件名称查找所有信息
	public List<PageData> querybyname(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("BusinessMapper.querybyname", pd);
	}

	// 审核/不审核
	public void upstatus(PageData pd) throws Exception {
		this.dao.update("BusinessMapper.upstatus", pd);
	}
	//根据userid获取
	public PageData querybyuserName(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("BusinessMapper.querybyuserName", pd);
	}
	//创建人
	public PageData querybycreateName(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("BusinessMapper.querybycreateName", pd);
	} 
	 
	public void updateImgPath(PageData pd) throws Exception {
		this.dao.update("BusinessMapper.updateImgPath", pd);
	} 
	 
}

package com.fh.service.system.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("pointsservice")
public class PointsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PointsMapper.findCount", page);
	}
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PointsMapper.listPage",page);
	} 
	
	//查询所有公司信息
	public List<PageData> querypoint(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("PointsMapper.querypoint", pd);
	}
	//添加
	public void addpoint(PageData pd)throws Exception{
		this.dao.save("PointsMapper.addpoint", pd);
	}
	//更新
	public void uppoint(PageData pd) throws Exception {
		this.dao.update("PointsMapper.uppoint", pd);
	} 
	//根据id获取企业信息
	public PageData querypointid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PointsMapper.querypointid", pd);
	}
	//批量删除
	public void delpoint(String[] auserId)throws Exception{
		this.dao.update("PointsMapper.delpoint", auserId);
	}
	//删除
	public void delpointbyid(PageData pd)throws Exception{
		this.dao.update("PointsMapper.delpointbyid", pd);
	}
	//根据证件名称查找所有信息
	public List<PageData> querypointname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("PointsMapper.querypointname", pd);
	}
}

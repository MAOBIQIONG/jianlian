package com.fh.service.system.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("tbcompanyService")
public class TbcompanyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//查询企业个人信息
	public List<PageData> querypany(PageData pd) throws Exception{
		return (List<PageData>)this.dao.findForList("TbcompanyMapper.querycompany",pd);
	}
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbcompanyMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("TbcompanyMapper.findCount", page);
	}
	//更新
	public void upcom(PageData pd) throws Exception {
		this.dao.update("TbcompanyMapper.upcom", pd);
	} 
	//根据id获取企业信息
	public PageData querypanyid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("TbcompanyMapper.querypanyid", pd);
	}
	
	//行业
	public List<PageData> querybyhangye(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbcompanyMapper.querybyhangye", pd);
	}
	//删除
	public void delcompanybyid(PageData pd)throws Exception{
		this.dao.update("TbcompanyMapper.delcompanybyid", pd);
	}
	//批量删除
	public void delpany(String[] auserId)throws Exception{
		this.dao.update("TbcompanyMapper.delpany", auserId);
	}
	//添加
	public void addcompany(PageData pd)throws Exception{
		this.dao.save("TbcompanyMapper.addcompany", pd);
	}
	//根据企业名称，和用户查找所有信息
	public List<PageData> querycompanyname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("TbcompanyMapper.querycompanyname", pd);
	}
	//查询所有公司信息
	public List<PageData> querysuoyoupany(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("TbcompanyMapper.querysuoyoupany", pd);
	}
}

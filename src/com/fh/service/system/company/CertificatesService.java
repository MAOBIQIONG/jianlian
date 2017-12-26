package com.fh.service.system.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("certificatesservice")
public class CertificatesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("CertificatesMapper.findCount", page);
	}
	
	public PageData queryMsgByStatus() throws Exception{
	     return (PageData)this.dao.findForObject("CertificatesMapper.queryMsgByStatus", null);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CertificatesMapper.listPage",page);
	} 
	//----------------
	//查询所有公司信息
	public List<PageData> querycerti(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("CertificatesMapper.querycerti", pd);
	}
	//添加
	public void addcertifi(PageData pd)throws Exception{
		this.dao.save("CertificatesMapper.addcertifi", pd);
	}
	//更新
	public void upcertifi(PageData pd) throws Exception {
		this.dao.update("CertificatesMapper.upcertifi", pd);
	} 
	//根据id获取企业信息
	public PageData querycertifiid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("CertificatesMapper.querycertifiid", pd);
	}
	//批量删除
	public void delcert(String[] auserId)throws Exception{
		this.dao.update("CertificatesMapper.delcert", auserId);
	}
	//删除
	public void delcertbyid(PageData pd)throws Exception{
		this.dao.update("CertificatesMapper.delcertbyid", pd);
	}
	//根据证件名称查找所有信息
	public List<PageData> querycertname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("CertificatesMapper.querycertname", pd);
	}
	
	//审核/不审核
	public void upstatus(PageData pd)throws Exception{
		this.dao.update("CertificatesMapper.upstatus", pd);
	}
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("CertificatesMapper.doexlelist", pd);
	}
	
	//去掉小红点
	public Object updatedelhd(PageData pd) throws Exception{
		return this.dao.update("CertificatesMapper.updatedelhd", pd);
	}
}

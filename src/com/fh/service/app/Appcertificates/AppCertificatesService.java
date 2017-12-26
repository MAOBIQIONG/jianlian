package com.fh.service.app.Appcertificates;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appcertificatesService")
public class AppCertificatesService {


	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//根据用户ID获取证件信息
	public List<PageData> findByUserId(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppcertificatesMapper.findByUserId", pd);
	} 
	 
	//我的证件删除
	public Object delCertByid(PageData pd)throws Exception{
		return dao.delete("AppcertificatesMapper.delCertByid", pd);
	}
	
	//
	public Object addconcern(PageData pd)throws Exception{
		return dao.save("AppcertificatesMapper.addconcern", pd);
	}
	
	//修改证件信息
	public Object edit(PageData pd)throws Exception{
		return dao.save("AppcertificatesMapper.edit", pd);
	}
	
	//证件类型
	public List<PageData> queryByPBM(String p_bm) throws Exception
   { 
     return (List)this.dao.findForList("DictionariesMapper.queryByPBM", p_bm);
   }
	
	//三证
	public PageData queryBysanzhen(PageData pd) throws Exception {
		return (PageData)this.dao.findForObject("AppcertificatesMapper.queryBysanzhen", pd);
	} 
	
	//名片
	public PageData queryByminpian(PageData pd) throws Exception {
		return (PageData)this.dao.findForObject("AppcertificatesMapper.queryByminpian", pd);
	} 
	//logo
	public PageData queryBymlogo(PageData pd) throws Exception {
		return (PageData)this.dao.findForObject("AppcertificatesMapper.queryBymlogo", pd);
	} 
	
	//其它1
	public List<PageData> queryBymqita1(PageData pd) throws Exception {
		return (List<PageData>)this.dao.findForList("AppcertificatesMapper.queryBymqita1", pd);
	} 
	
}

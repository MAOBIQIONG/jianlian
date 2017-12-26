package com.fh.service.system.press;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("pressservice")
public class PressService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询所有栏目信息
	public List<PageData> querypress(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("PressMapper.querypress", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("PressMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("PressMapper.findCount", page);
	}
	//根据id获取栏目信息
	public PageData querypressid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PressMapper.querypressid", pd);
	}
	
	//添加
	public void addpress(PageData pd)throws Exception{
		this.dao.save("PressMapper.addpress", pd);
	}
	//修改
	public void uppress(PageData pd) throws Exception {
		this.dao.update("PressMapper.uppress", pd);
	} 
	
	//批量删除
	public void delpress(String[] auserId)throws Exception{
		this.dao.delete("PressMapper.delpress", auserId);
	}
	//根据栏目名称查找所有信息
	public List<PageData> querypressname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("PressMapper.querypressname", pd);
	}
	//显示/关闭
	public void upis_show(PageData pd)throws Exception{
		this.dao.update("PressMapper.upis_show", pd);
	}
	//删除
	public void delpressByid(PageData pd)throws Exception{
		this.dao.delete("PressMapper.delpressByid", pd);
	}
}

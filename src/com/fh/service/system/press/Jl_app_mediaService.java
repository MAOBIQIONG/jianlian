package com.fh.service.system.press;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("jl_app_mediaservice")
public class Jl_app_mediaService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询所有栏目信息
	public List<PageData> querymedia(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("Jl_app_mediaMapper.querymedia", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("Jl_app_mediaMapper.listPageByParam", page);
	}
	//查询栏目名称
	public List<PageData> querybybhname(PageData pd)  throws Exception {
	       return (List<PageData>) this.dao.findForList("Jl_app_mediaMapper.querybybhname", pd);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("Jl_app_mediaMapper.findCount", page);
	}
	//根据id获取新闻信息
	public PageData querymediaid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("Jl_app_mediaMapper.querymediaid", pd);
	}
	
	//添加
	public void addmedia(PageData pd)throws Exception{
		try {
			this.dao.save("Jl_app_mediaMapper.addmedia", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//修改
	public void upmedia(PageData pd) throws Exception {
		try {
			this.dao.update("Jl_app_mediaMapper.upmedia", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} 
	
	//批量删除
	public void delmedia(String[] auserId)throws Exception{
		this.dao.update("Jl_app_mediaMapper.delmedia", auserId);
	}
	//根据栏目名称查找所有信息
	public List<PageData> querymediatitle(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("Jl_app_mediaMapper.querymediatitle", pd);
	}
	//推荐/不推荐
	public void upISMMEND(PageData pd)throws Exception{
		this.dao.update("Jl_app_mediaMapper.upISMMEND", pd);
	}
	//推荐/不推荐
	public void upISTOP(PageData pd)throws Exception{
		this.dao.update("Jl_app_mediaMapper.upISTOP", pd);
	}
	//删除
	public void delmediaByid(PageData pd)throws Exception{
		this.dao.update("Jl_app_mediaMapper.delmediaByid", pd);
	}
	/**
	 * 新闻上下线
	 * @param pd
	 * @throws Exception
	 */
	public void updates(PageData pd)throws Exception{
		this.dao.update("Jl_app_mediaMapper.updates", pd);
	}
	
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("Jl_app_mediaMapper.doexlelist", pd);
	}
}

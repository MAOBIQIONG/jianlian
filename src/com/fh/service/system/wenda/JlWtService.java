package com.fh.service.system.wenda;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("questionService")
public class JlWtService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("JlWtMapper.listPageByParam", page);
	}
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("JlWtMapper.findCount", page);
	}
	 
	//通过id获取数据
	public PageData queryById(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("JlWtMapper.queryById", pd);
	} 
	
	//添加	
	public Object save(PageData pd) throws Exception {
		return this.dao.save("JlWtMapper.save", pd);
	}
	 
	//修改 
	public Object edit(PageData pd)throws Exception{
		return this.dao.update("JlWtMapper.edit", pd);
	} 
	 
	//删除 
	public Object delById(PageData pd)throws Exception{
		return this.dao.update("JlWtMapper.delById", pd);
	} 
}

package com.fh.service.system.wenda;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("answerService")
public class JlHdService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("JlHdMapper.listPageByParam", page);
	}
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("JlHdMapper.findCount", page);
	} 
	 
	//删除 
	public Object delByHDId(PageData pd)throws Exception{
		return this.dao.update("JlHdMapper.delByHDId", pd);
	} 
}

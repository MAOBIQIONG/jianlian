package com.fh.service.app.collection;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appCollectionService")
public class AppCollectionService {
     
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//收藏获取接口
	public List<PageData> findConllection(PageData pd)throws Exception {
		return  (List<PageData>) dao.findForList("AppCollectionMapper.findCollection", pd);
	}
	
	//收藏总数
	public PageData findCollectionsczong(PageData pd)throws Exception {
		return  (PageData) dao.findForObject("AppCollectionMapper.findCollectionsczong", pd);
	}
	
	//收藏新增接口
	public Object saveCollection(PageData pd)throws Exception {
		return dao.save("AppCollectionMapper.saveCollection", pd);
	} 
	
	//收藏删除接口
	public Object	delCollection(PageData pd)throws Exception {
		return dao.delete("AppCollectionMapper.delCollection", pd);
	}
	
	//查询当前用户是否 收藏过 
	public PageData checkCollection(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppCollectionMapper.checkCollection", pd);
	} 
	
	//收藏获取接口
	public List<PageData> queryByUidAndOid(PageData pd)throws Exception {
		return  (List<PageData>) dao.findForList("AppCollectionMapper.queryByUidAndOid", pd);
	} 
}

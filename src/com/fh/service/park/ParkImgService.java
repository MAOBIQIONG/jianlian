package com.fh.service.park;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport; 
import com.fh.util.PageData;



@Service("parkImgService")
public class ParkImgService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;   
	
	//查询列表信息
	public List<PageData> queryByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ZsParkPicturesMapper.queryByPid", pd);
	} 
	
	public PageData queryMaxOrderby(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZsParkPicturesMapper.queryMaxOrderby", pd);
	} 
	/*
	* 新增
	*/
	public boolean save(PageData pd)throws Exception{
		Object ob=dao.save("ZsParkPicturesMapper.save", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		} 
	}
	
	public boolean edit(PageData pd) throws Exception {
		Object ob=this.dao.update("ZsParkPicturesMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}  
	} 
	
	public boolean delete(String[] ids) throws Exception {
		Object ob=this.dao.update("ZsParkPicturesMapper.manyToDelete", ids);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}   
	}
	
}


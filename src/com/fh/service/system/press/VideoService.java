package com.fh.service.system.press;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("videoService")
public class VideoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询
	public List<PageData> listPageByParam(Page page)  throws Exception {
	      return (List<PageData>)this.dao.findForList("VideoMapper.listPageByParam", page);
	} 
	
	public PageData findCount(Page page)  throws Exception {
	      return (PageData)this.dao.findForObject("VideoMapper.findCount", page);
	}
	
	//根据id获取新闻信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("VideoMapper.queryById", pd);
	}
	
	//添加
	public boolean save(PageData pd)throws Exception{ 
		Object ob=this.dao.save("VideoMapper.save", pd); 
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		}
	}
	//修改
	public boolean edit(PageData pd) throws Exception { 
		Object ob=this.dao.update("VideoMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		} 
	}   
 
	//删除
	public boolean delete(PageData pd)throws Exception{
		Object ob=this.dao.update("VideoMapper.delete", pd);
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		} 
	}
	/**
	 * 新闻上下线
	 * @param pd
	 * @throws Exception
	 */
	public boolean editStatus(PageData pd)throws Exception{
		Object ob=this.dao.update("VideoMapper.editStatus", pd); 
		if(Integer.parseInt(ob.toString())>=1){
			return true;
		}else{
			return false;
		} 
	} 
}

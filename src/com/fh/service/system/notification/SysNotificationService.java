package com.fh.service.system.notification;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("sysNotificationService")
public class SysNotificationService {


	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询所有消息
	public List<PageData> queryAll() throws Exception {
		return (List<PageData>) this.dao.findForList("SysNotificationMapper.queryAll",null);
	}
	
	//根据条件查询消息
	public List<PageData> queryByParam(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("SysNotificationMapper.queryByParam",pd);
	} 
	/*
	* 新增
	*/
	public Object save(PageData pd)throws Exception{
		return dao.save("SysNotificationMapper.save", pd);
	} 
	 
	//删除
	public Object delete(PageData pd) throws Exception {
		return  this.dao.delete("SysNotificationMapper.delete", pd);
	} 
}

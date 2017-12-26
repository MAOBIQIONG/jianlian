package com.fh.service.app.notification;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appNotificationService")
public class AppNotificationService {
     
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//系统通知列表获取接口
	public List<PageData>	findNotification(PageData pa)throws Exception {
		return (List<PageData>) dao.findForList("AppNotificationMapper.findNotification", pa);
	}
	
	//系统通知列表获取接口
	public PageData	countNotification(PageData pa)throws Exception {
		return (PageData) dao.findForObject("AppNotificationMapper.countNotification", pa);
	}
	
	//查询所有消息
		public List<PageData> queryAll() throws Exception {
			return (List<PageData>) this.dao.findForList("AppNotificationMapper.queryAll",null);
		} 
		
		//根据条件查询消息
		public List<PageData> listByParam(PageData pd) throws Exception {
			return (List<PageData>) this.dao.findForList("AppNotificationMapper.listByParam",pd);
		} 
		//消息总
		public PageData listByParamzong(PageData pd) throws Exception {
			return (PageData) this.dao.findForObject("AppNotificationMapper.listByParamzong",pd);
		}
		//根据条件查询消息
		public List<PageData> queryByParam(PageData pd) throws Exception {
			return (List<PageData>) this.dao.findForList("AppNotificationMapper.queryByParam",pd);
		} 
		
		//根据条件查询消息
		public PageData queryCountsByParam(PageData pd) throws Exception {
			return (PageData) this.dao.findForObject("AppNotificationMapper.queryCountsByParam",pd);
		}  
		
		// 新增
		
		public Object save(PageData pd)throws Exception{
			return dao.save("AppNotificationMapper.save", pd);
		} 
		 
		//删除
		public Object delete(PageData pd) throws Exception {
			return  this.dao.delete("AppNotificationMapper.delete", pd);
		} 
		
		//修改状态
		public Object edit(PageData pd) throws Exception {
			return  this.dao.update("AppNotificationMapper.edit", pd);
		}
}

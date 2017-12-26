package com.fh.service.app.connections;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appconnectionService")
public class AppConnectionService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//我的人脉列表
	public List<PageData> querConnectionUser(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppConnectionMapper.querConnectionUser", pd);
	}
	//我的人脉-个人信息详情
	public List<PageData> queroneUser(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppConnectionMapper.queroneUser", pd);
	}
	//我的人脉-关注 
	public Object addconcern(PageData pd)throws Exception{
		return dao.save("AppConnectionMapper.addconcern", pd);
	}
	//删除
	public Object delconcern(PageData pd)throws Exception{
		return dao.delete("AppConnectionMapper.delconcern", pd);
	}
	//我的人脉列表
	public List<PageData> querUsername(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppConnectionMapper.querUsername", pd);
	}
	//我的人脉-个人信息评论
	public List<PageData> queronepinlun(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppConnectionMapper.queronepinlun", pd);
	}
	//拓展人脉推荐列表获取
	public List<PageData> quertuijian(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppConnectionMapper.quertuijian", pd);
	}
	//拓展人脉推荐列表总数
	public PageData quertuijianzong(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppConnectionMapper.quertuijianzong", pd);
	}
	//判断是否加了关注
	public List<PageData> checkConnected(PageData pd) throws Exception {
		return  (List<PageData>) this.dao.findForList("AppConnectionMapper.checkConnected", pd);
	} 

	//查询某个会员关注人脉的总数
	public PageData countUsersByUid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppConnectionMapper.countUsersByUid", pd);
	}  
}

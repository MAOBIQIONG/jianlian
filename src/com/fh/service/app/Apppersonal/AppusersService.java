package com.fh.service.app.Apppersonal;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appusersService")
public class AppusersService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//根据ID查询当前用户的所有信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUsersMapper.findById", pd);
	}
	
	//根据邮箱查找
	public List<PageData> queryByEmail(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.queryByEmail", pd);
	} 
		
	//根据ID查询当前用户的简单信息
	public List<PageData> querySimpleInfoById(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.querySimpleInfoById", pd);
	}
	
	//根据邮箱查找密码
	public List<PageData> findByEMAIL(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.findByEMAIL", pd);
	}
	//个人信息详细信息
	public List<PageData> querusers(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.querusers", pd);
	}
	//我的助理
	public List<PageData> querassname(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.querassname", pd);
	}
	//修改
	public Object edit(PageData pd) throws Exception {
		return dao.update("AppUsersMapper.edit", pd);
	}
	
	public Object edithonor(PageData pd) throws Exception {
		return dao.update("AppUsersMapper.edithonor", pd);
	}
	
	//我的当前公司介绍
	public List<PageData> quercompanyjianjie(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.quercompanyjianjie", pd);
	}

	//我的基本信息修改
	public Object upbasic(PageData pd) throws Exception {
		return this.dao.update("AppUsersMapper.upbasic", pd);
	}
	public Object upimg(PageData pd) throws Exception {
		return dao.update("AppUsersMapper.upimg", pd);
	}
	//个人信息评价获取
	public List<PageData> querrated(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.querrated", pd);
	}
	//账号和安全信息获取
	public List<PageData> queranquan(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.queranquan", pd);
	}
	//根据用户名查找用户
	public PageData findByUsername(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUsersMapper.findByUsername", pd);
	} 
	//根据手机号查找用户是否被注册
	public List<PageData> findByUserPHONE(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("AppUsersMapper.findByUserPHONE", pd);
	} 
	
	//保存
	public Object save(PageData pd) throws Exception {
		return this.dao.save("AppUsersMapper.adduser", pd);
	} 
	
	//修改密码
	public Object editPassWord(PageData pd) throws Exception {
		return  this.dao.update("AppUsersMapper.editPassWord", pd);
	}  
	/*
	* 通过用户名与密码获取数据
	*/
	public PageData checkLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppUsersMapper.checkLogin", pd);
	}
	
	/*
	* 通过用户名或者卡号获取数据
	*/
	public List<PageData> doCheckLogin(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppUsersMapper.doCheckLogin", pd);
	} 
	
	/*
	* 登录成功后修改登录时间和客户id
	*/
	public Object updateCidAndDate(PageData pd)throws Exception{
		return dao.update("AppUsersMapper.updateCidAndDate", pd);
	} 
	
	//根据会员等级、行业、公司名称搜索拓展人脉
	public List<PageData> queryExtendsByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppUsersMapper.queryExtendsByParam", pd);
	}
	
	//查询所有用户ID,PLATFORM（推送所有用户）
	public PageData queryAllForPush(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUsersMapper.findAllForPush", pd);
	}
	
	//添加用户
	public Object savezhuce(PageData pd) throws Exception {
		return this.dao.save("AppUsersMapper.savezhuce", pd);
	} 
	//查询行业
	public List<PageData> querycategory(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppUsersMapper.querycategory", pd);
	}
	
	//修改密码
	public Object bindEmail(PageData pd) throws Exception {
		return  this.dao.update("AppUsersMapper.bindEmail", pd);
	}   
	
	//设置融云token
	public Object editImToken(PageData pd) throws Exception {
		return  this.dao.update("AppUsersMapper.editImToken", pd);
	}
}

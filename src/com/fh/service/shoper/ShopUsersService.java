package com.fh.service.shoper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport; 
import com.fh.util.PageData;



@Service("shopUsersService")
public class ShopUsersService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	
	public PageData doLogin(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ShopUsersMapper.doLogin", pd);
	}   
	 
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("ShopUsersMapper.editPassWord", pd);
	}   
	 
	/*
	* 通过用户名或者卡号获取数据
	*/
	public PageData doCheckLogin(PageData pd)throws Exception{
		return (PageData) this.dao.findForObject("ShopUsersMapper.doCheckLogin", pd);
	} 
	
	
}


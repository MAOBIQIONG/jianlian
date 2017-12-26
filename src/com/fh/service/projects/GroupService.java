package com.fh.service.projects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service; 
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;



@Service("groupService")
public class GroupService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	* 创建群组
	*/
	public Object createGroup(PageData pd)throws Exception{
		return dao.save("GroupMapper.save", pd);
	}
	
	/*
	* 拉人入群
	*/
	public Object pull(PageData pd)throws Exception{
		return dao.save("GroupMapper.pull", pd);
	}
	
	/*
	* 踢人出群
	*/
	public Object kick(PageData pd)throws Exception{
		return dao.update("GroupMapper.kick", pd);
	}
	
	/*
	* 解散群组
	*/
	public Object removeGroup(PageData pd)throws Exception{
		return dao.update("GroupMapper.removeGroup", pd);
	}
	
	//根据当前登录人的编号查询app用户端的账号信息
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("GroupMapper.findById", pd);
	}
	
	
	//根据当前项目编号查询群信息
	public PageData findByGrouptId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("GroupMapper.findByGrouptId", pd);
	}
	
	//删除会话用户
	public Object removeCatGroupuser(PageData pd)throws Exception{
		return dao.update("GroupMapper.removeCatGroupuser", pd);
	}
		
}


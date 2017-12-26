package com.fh.service.system.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.DataSource;
import com.fh.util.PageData;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.findByUiId", pd);
	}

	
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.findByUId", pd);
	}

	
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.findByUE", pd);
	}

	
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.findByUN", pd);
	}

	
	public void saveU(PageData pd) throws Exception {
		this.dao.save("UserXMapper.saveU", pd);
	}

	
	public void editU(PageData pd) throws Exception {
		this.dao.update("UserXMapper.editU", pd);
	}

	
	public void setSKIN(PageData pd) throws Exception {
		this.dao.update("UserXMapper.setSKIN", pd);
	}

	
	public void deleteU(PageData pd) throws Exception {
		this.dao.delete("UserXMapper.deleteU", pd);
	}

	
	public void deleteAllU(String[] USER_IDS) throws Exception {
		this.dao.delete("UserXMapper.deleteAllU", USER_IDS);
	}

	
	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List) this.dao.findForList("UserXMapper.userlistPage", page);
	}

	public PageData findUserCount(Page page) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.findUserCount",page);
	} 
	
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List) this.dao.findForList("UserXMapper.listAllUser", pd);
	}

	
	public List<PageData> listGPdPageUser(Page page) throws Exception {
		return (List) this.dao.findForList("UserXMapper.userGlistPage", page);
	}

	public List<PageData> listManager() throws Exception {
		return (List) this.dao.findForList("UserXMapper.findAllManager", null);
	} 
	
	public List<PageData> findSecretary() throws Exception {
		return (List) this.dao.findForList("UserXMapper.findSecretary", null);
	} 
	
	public void saveIP(PageData pd) throws Exception {
		this.dao.update("UserXMapper.saveIP", pd);
	}

	
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserXMapper.getUserInfo", pd);
	}
	
	public Object updatePwd(PageData pd) throws Exception {
		return this.dao.update("UserXMapper.updatePwd", pd);
	}

	
	public void updateLastLogin(PageData pd) throws Exception {
		this.dao.update("UserXMapper.updateLastLogin", pd);
	}

	
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) this.dao.findForObject("UserMapper.getUserAndRoleById",
				USER_ID);
	}

	
	public PageData isBindSchool(User user) throws Exception {
		return (PageData) this.dao.findForObject("SchoolMapper.findByUserId",
				user);
	}

	
	public PageData findInfoByUId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserMapper.getUserInfoById",
				pd);
	}
	
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("UserMapper.findCount",pd);
	}
	
	public Set<String> findRoles(String username) {
		try {
			List<PageData> roles = (List<PageData>) this.dao.findForList(
					"UserMapper.findRolesByUsername", username);
			if (roles != null && !roles.isEmpty()) {
				Set<String> set = new HashSet<String>();
				for (PageData pd : roles) {
					set.add(pd.getString("ROLE_NAME"));
				}
				return set;
			} else
				return null;
		} catch (Exception e) {
			return null;
		}

	}

	//修改当前用户网易token
	public Object editWyToken(PageData pd) throws Exception {
		return this.dao.update("UserMapper.upWytoken", pd);
	}
	
	
	/************红点区域************/
	//接单管理人数增加
	public PageData queryjdByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryjdByStatus", null);
	}
	//项目代发管理
	public PageData queryxmdfByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryxmdfByStatus", null);
	}
	//证件上传
	public PageData queryzjscByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryzjscByStatus", null);
	}
	//支付入会
	public PageData queryrhByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryrhByStatus", null);
	}
	//活动提交审核
	public PageData queryhdtjByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryhdtjByStatus", null);
	}
	//加入城市建联
	public PageData querycsjlByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.querycsjlByStatus", null);
	}
	//城市合伙人申请
	public PageData querycshhrByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.querycshhrByStatus", null);
	}
	//场地预订
	public PageData querycdydByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.querycdydByStatus", null);
	}
	//反馈通知
	public PageData queryfkByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.queryfkByStatus", null);
	}
	//ppp人数增加  
	public PageData querypppcyByStatus()  throws Exception {
	       return (PageData)this.dao.findForObject("HdianMapper.querypppcyByStatus", null);
	}
	
}

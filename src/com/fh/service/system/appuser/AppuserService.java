package com.fh.service.system.appuser;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("appuserService")
public class AppuserService {

	protected Logger logger = Logger.getLogger(getClass());

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findByUiId", pd);
	}

	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findByUId", pd);
	}
	//验证PHONE是否存在
	public PageData findByPhone(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findByPhone", pd);
	}
	
	public PageData findCoByUId(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findCoByUId", pd);
	}

	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findByUE", pd);
	}

	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.findByUN", pd);
	}

	public void saveU(PageData pd) throws Exception {
		this.dao.save("AppUserMapper.saveU", pd);
	}

	public void editU(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.editU", pd);
	}
	
	public void updateCompanyId(PageData pd)throws Exception {
		this.dao.update("AppUserMapper.updateCompanyId", pd);
	}

	public void deleteU(PageData pd) throws Exception {
		this.dao.delete("AppUserMapper.deleteU", pd);
	}

	public void deleteAllU(String[] USER_IDS) throws Exception {
		this.dao.delete("AppUserMapper.deleteAllU", USER_IDS);
	}

	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List) this.dao.findForList("AppUserMapper.listAllUser", pd);
	}

	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List) this.dao.findForList("AppUserMapper.userlistPage", page);
	}


	public void updateImtoken(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.updateImToken", pd);
	}

	public void saveIP(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.saveIP", pd);
	}

	public PageData login(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.login",pd);
	}
	
	public PageData getStudentInfo(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.getStudentInfo",pd);
	}

	public PageData getTeacherInfo(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("AppUserMapper.getTeacherInfo",pd);
	}
	
	public void updateLastLogin(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.updateLastLogin", pd);
	}
	public void updateCID(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.updateCID", pd);
	}


	public PageData findInfoById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject(
				"AppUserMapper.findNameAndPortraitById", pd);
	}

	public PageData findByUsername(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) this.dao.findForObject("AppUserMapper.findByUsername", pd);
	}
	public PageData findByUsernameS(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) this.dao.findForObject("AppUserMapper.findByUsernameS", pd);
	}
	public PageData findByUsernameT(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) this.dao.findForObject("AppUserMapper.findByUsernameT", pd);
	}

	public void updateConfig(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.updateConfig", pd);
	}

	public void userDetails(PageData pd) throws Exception {
		this.dao.update("AppUserMapper.userDetails", pd);
	}

	public PageData getUserAndRoleById(String USER_ID) throws Exception {
		return (PageData) this.dao.findForObject(
				"AppUserMapper.getUserAndRoleById", USER_ID);
	}

	public PageData findUserDetails(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject(
				"AppUserMapper.findUserDetails", pd);
	}

	public void updateLastReadTime(PageData pd) throws Exception {
		pd.put("LAST_READ_TIME", DateUtil.getTime());
		this.dao.update("AppUserMapper.updateLastReadTime", pd);
	}

	/**
	 * apptoken 缓存, 在用户密码变更,过期后清空该缓存
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Cacheable(key = "#pd.getString(\"USER_ID\")", value = { "apptoken" })
	public String findAppToken(PageData pd) throws Exception {

		String apptoken = (String) this.dao.findForObject(
				"AppUserMapper.findAppToken", pd);
		return apptoken;
	}

	/**
	 * 更新密码
	 * 
	 * @param pd
	 * @throws Exception
	 */

	public void updatePassword(PageData pd) throws Exception {
		String password = new SimpleHash("SHA-1", pd.get("USERNAME"),pd.get("PASSWORD")).toString();
		pd.put("PASSWORD", password);
//		pd.put("APPTOKEN", UuidUtil.get32UUID());
		this.dao.update("AppUserMapper.updatePassword", pd);
	}

	@CacheEvict(key = "#pd.getString(\"USER_ID\")", value = { "apptoken" })
	public void clearApptoken() {

	}

}

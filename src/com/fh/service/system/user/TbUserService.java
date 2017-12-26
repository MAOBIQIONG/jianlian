package com.fh.service.system.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("tbuserService")
public class TbUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//添加用户	
	public void addU(PageData pd) throws Exception {
		this.dao.save("TbUserMapper.addU", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbUserMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("TbUserMapper.findCount", page);
	}
	
	//查询
	public List<PageData> listPage(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbUserMapper.listPage", page);
	}
	public PageData findInnerCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("TbUserMapper.findInnerCount", page);
	}
	
	//查询
	public List<PageData> listShopUserByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbUserMapper.listShopUserByParam", page);
	}
	public PageData listShopCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("TbUserMapper.listShopCount", page);
	} 
	
	//查询
	public List<PageData> listPageByEndtime(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("TbUserMapper.listPageByEndtime", page);
	}
	public PageData findCountByEndtime(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("TbUserMapper.findCountByEndtime", page);
	}
	//通过user_id获取数据
	public PageData queryById(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("TbUserMapper.queryById", pd);
	}
	
	//通过user_id获取数据
	public PageData queryuserandcom(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("TbUserMapper.queryuserandcom", pd);
	} 
	
	//获取用户荣誉
	public List<PageData> queryRy(PageData pd) throws Exception {
		return  (List<PageData>)this.dao.findForList("TbUserMapper.queryRy", pd);
	}
	//获取用户资源
	public List<PageData> queryZy(PageData pd) throws Exception {
		return  (List<PageData>)this.dao.findForList("TbUserMapper.queryZy", pd);
	}
	//获取用户需求
	public List<PageData> queryXq(PageData pd) throws Exception {
		return  (List<PageData>)this.dao.findForList("TbUserMapper.queryXq", pd);
	}
	//获取公司名称
	public PageData querybycomname(PageData pd) throws Exception {
		return  (PageData) this.dao.findForObject("TbUserMapper.querybycomname", pd);
	}
	
	//查询
	public List<PageData> querytbuser(PageData pd) throws Exception{
		return (List<PageData>)this.dao.findForList("TbUserMapper.querytbuser",pd);
	}
	//添加
	public Object adduser(PageData pd) throws Exception{
		return this.dao.save("TbUserMapper.adduser", pd);
	}
	
	//修改录入的会员信息
	public Object edituser(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.edituser", pd);
	}
	
	//删除
	public Object delmembyid(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.delmembyid", pd);
	}
	//批量删除
	public Object delTbuser(String[] auserId)throws Exception{
		return this.dao.update("TbUserMapper.delTbuser", auserId);
	}
	//重置密码
	public Object uppassword(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.uppassword", pd);
	}
	
	//重置密码
	public Object editPoint(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.editPoint", pd);
	} 
	
	//批量禁止
	public Object upstatus(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.upstatus", pd);
	}
	//修改
	public Object uptbuser(PageData pd)throws Exception{
		return this.dao.update("TbUserMapper.uptbuser", pd);
	}
	//根据用户姓名查找信息
	public List<PageData> querybyname(PageData pd) throws Exception{
		return (List<PageData>)this.dao.findForList("TbUserMapper.querybyname",pd);
	}
	/**
	 * 保存用户荣誉，资源，需求信息
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void editXx(PageData pd) throws Exception{
		String HONOR = pd.getString("HONOR");
		String NEED_NAME = pd.getString("NEED_NAME");
		String RESOURCE = pd.getString("RESOURCE");
		String GET_DATE = pd.getString("GET_DATE");
		/**
		 * 荣誉
		 */
		if(HONOR != null && !"".equals(HONOR)){
			String[] honor = HONOR.split(",");
			String[] getdate = GET_DATE.split(",");
			PageData pad = new PageData();
			pad.put("USER_ID", pd.get("ID"));
			//删除荣誉信息
			dao.delete("TbUserMapper.delRy",pad);
			for (int i = 0; i < honor.length; i++) {
				if(honor[i] != null && !"".equals(honor[i])){
					pad.put("ID", UuidUtil.get32UUID());
					pad.put("HONOR", honor[i]);
					pad.put("GET_DATE", getdate[i]);
					dao.save("TbUserMapper.addRy",pad);
				}
				
			}
		}
		
		/**
		 * 资源
		 */
		if(RESOURCE != null && !"".equals(RESOURCE)){
			String[] res = RESOURCE.split(",");
			PageData pcd = new PageData();
			pcd.put("USER_ID", pd.get("ID"));
			//删除需求信息
			dao.delete("TbUserMapper.delZy",pcd);
			for (int i = 0; i < res.length; i++) {
				if(res[i] != null && !"".equals(res[i])){
					pcd.put("ID", UuidUtil.get32UUID());
					pcd.put("RESOURCE", res[i]);
					dao.save("TbUserMapper.addZy",pcd);
				}
				
			}
		}
		
		/**
		 * 需求
		 */
		if(NEED_NAME != null && !"".equals(NEED_NAME)){
			String[] needname = NEED_NAME.split(",");
			PageData pbd = new PageData();
			pbd.put("USER_ID", pd.get("ID"));
			//删除资源信息
			dao.delete("TbUserMapper.delXq",pbd);
			for (int i = 0; i < needname.length; i++) {
				if(needname[i] != null && !"".equals(needname[i])){
					pbd.put("ID", UuidUtil.get32UUID());
					pbd.put("NEED_NAME", needname[i]);
					dao.save("TbUserMapper.addXq",pbd);
				}
				
			}
		}
	} 
	
	public PageData findU(PageData pd)  throws Exception {
	       return (PageData)this.dao.findForObject("TbUserMapper.findU", pd);
	}
	
	public PageData findBmCount(PageData pd) throws Exception {
	     return (PageData)this.dao.findForObject("TbUserMapper.findBmCount", pd);
	}
	
	//查询Excel导出数据
	public List<PageData> doexlelist(PageData pd) throws Exception{
	     return (List<PageData>)this.dao.findForList("TbUserMapper.doexlelist", pd);
	}
}

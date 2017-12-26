package com.fh.service.system.company;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("copyService")
public class CopyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//查询企业个人信息
	public List<PageData> querypany(PageData pd) throws Exception{
		return (List<PageData>)this.dao.findForList("CopyMapper.querycompany",pd);
	}
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("CopyMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("CopyMapper.findCount", page);
	}
	//更新
	public void upcom(PageData pd) throws Exception {
		this.dao.update("CopyMapper.upcom", pd);
	} 
	//根据id获取企业信息
	public PageData querypanyid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("CopyMapper.querypanyid", pd);
	}

	//行业
	public List<PageData> querybyhangye(PageData pd)  throws Exception {
	       return (List<PageData>)this.dao.findForList("CopyMapper.querybyhangye", pd);
	}
	//查询行业信息
	public List<PageData> queryByParam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CopyMapper.queryByParam",pd);
	}
	
	//查询行业信息
	public List<PageData> queryByPid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CopyMapper.queryByPid",pd);
	} 
	//删除
	public void delcompanybyid(PageData pd)throws Exception{
		this.dao.delete("CopyMapper.delcompanybyid", pd);
	}
	//获取公司荣誉信息
	public List<PageData> queryRy(PageData pd) throws Exception {
		return (List<PageData>)this.dao.findForList("CopyMapper.queryRy", pd);
	}
	
	/**
	 * 保存公司荣誉信息
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void editXx(PageData pd) throws Exception{
		String HONOR = pd.getString("HONOR");
		String GET_DATE = pd.getString("GET_DATE");
		/**
		 * 荣誉
		 */
		if(HONOR != null && !"".equals(HONOR)){
			String[] honor = HONOR.split(",");
			String[] getdate = GET_DATE.split(",");
			PageData pad = new PageData();
			pad.put("COMPANY_ID", pd.get("COMPANY_ID"));
			//删除荣誉信息
			dao.delete("CopyMapper.delRy",pad);
			for (int i = 0; i < honor.length; i++) {
				if(honor[i] != null && !"".equals(honor[i])){
					pad.put("ID", UuidUtil.get32UUID());
					pad.put("HONOR", honor[i]);
					pad.put("GET_DATE", getdate[i]);
					dao.save("CopyMapper.addRy",pad);
				}
				
			}
		}
		
		
	} 
	//根据id获取公司信息
	public PageData findGs(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("CopyMapper.findGs", pd);
	}
	
	//根据公司名称获取公司信息
	public List<PageData> findGsbuname(PageData pd) throws Exception {
		return (List<PageData>)this.dao.findForList("CopyMapper.findGsbuname", pd);
	}
	//修改公司信息
	public void editGs(PageData pd)throws Exception{
		this.dao.update("CopyMapper.editGs", pd);
	}
	//添加公司信息
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void addGs(PageData pd,String userid)throws Exception{
		//添加公司信息
		String COMPANY_ID = UuidUtil.get32UUID();
	    pd.put("COMPANY_ID", COMPANY_ID);
	  	pd.put("CREATE_BY", userid);
		this.dao.save("CopyMapper.addGs", pd);
		//新增产业链公司信息
		PageData pad = new PageData();
		pad.put("ID", UuidUtil.get32UUID());
		pad.put("COMPANY_ID", COMPANY_ID);
		pad.put("CREATE_BY", userid);
		this.dao.save("CopyMapper.addCylgs", pad);
	}
	//添加公司到产业链
		@Transactional(readOnly = false, rollbackFor = Exception.class)
		public void addGscy(PageData pd,String userid)throws Exception{
			//添加公司信息
			String[] COMPANY = pd.getString("COMPANY_ID").split(",");
		  	pd.put("CREATE_BY", userid);
			for (int i = 0; i < COMPANY.length; i++) {
			    pd.put("COMPANY_ID", COMPANY[i]);
			    pd.put("ID", UuidUtil.get32UUID());
			    this.dao.save("CopyMapper.addCylgs", pd);
			}
		}
	//批量删除
	public void delpany(String[] auserId)throws Exception{
		this.dao.update("CopyMapper.delpany", auserId);
	}
	//添加
	public void addcompany(PageData pd)throws Exception{
		this.dao.save("CopyMapper.addcompany", pd);
	}
	//根据企业名称，和用户查找所有信息
	public List<PageData> querycompanyname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("CopyMapper.querycompanyname", pd);
	}
	//查询所有公司信息
	public List<PageData> querysuoyoupany(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("CopyMapper.querysuoyoupany", pd);
	}
	
	//查询
	public List<PageData> listByParam1(Page page)  throws Exception {
	    return (List<PageData>)this.dao.findForList("CopyMapper.listPageByParam1", page);
	}
	public PageData findCount1(Page page)  throws Exception {
	    return (PageData)this.dao.findForObject("CopyMapper.findCount1", page);
	}
}

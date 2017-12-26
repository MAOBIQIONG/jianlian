package com.fh.service.system.PPPprojectService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;



@Service("PPPprojectBidderService")
public class PPPProjectBidderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectBidderMapper.listAll", pd);
	}
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("PPPProjectBidderMapper.findCount", page);
	} 
	
	public List<PageData> listByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PPPProjectBidderMapper.listPage",page);
	}  
	
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("PPPProjectBidderMapper.edit", pd);
	} 
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String editGroup(PageData pd) throws Exception { 
		pd.put("INGROUP_DATE",new Date());
		Object ob=this.dao.update("PPPProjectBidderMapper.edit", pd);
		if(Integer.parseInt(ob.toString())>=1){
			PageData isCy =(PageData) this.dao.findForObject("XzcyMapper.chenckIsIn",pd);//判断是否已经加入
			if(isCy==null){//没有加入 
				pd.put("ID",UuidUtil.get32UUID());
				pd.put("DATE", new Date()); 
				pd.put("IS_ADMIN", "0"); 
				pd.put("IS_APPUSER","1");  
				Object obj=dao.save("XzcyMapper.addXz",pd);
				if(Integer.valueOf(obj.toString()) >= 1){ 
					return "success";
				}else{
					return "fail";
				} 
			}else{//已经加入，退出讨论小组
				 Object obj =dao.delete("XzcyMapper.deleteXz",pd);
	 		     if(Integer.valueOf(obj.toString()) >= 1){ 
	 		    	return "success";
	 			}else{
	 				return "fail";
	 			} 
			}  
		} 
		return "fail";  
	} 
	
 
		
	
	public void delete(PageData pd) throws Exception {
		this.dao.delete("PPPProjectBidderMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PPPProjectBidderMapper.findById", pd);
	} 
}


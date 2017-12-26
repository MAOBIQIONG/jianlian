package com.fh.service.system.job;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("jobService")
public class JobService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 查询所有超24小时的订单信息
	public List<PageData> queryJob(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("JobMapper.queryJobAll", pd);
	}
	
	//更新订单状态为删除
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object editOr(PageData pd) throws Exception {
		
		Object obj = "";
		String content = "尊敬的用户，您有一条订单超24小时未付款已自动取消,如有疑问请致电客服.";
		/**
		 * 自动取消订单
		 */
		obj = this.dao.update("JobMapper.editOr", pd);
		 /**
         * 添加用户系统消息
         */
		PageData pdd = new PageData();
		pdd.put("OBJECT_ID",pd.get("ID")); 
		//判断是否已存在信息，存在修改，不存在，新增
		pdd = (PageData) dao.findForObject("UmbsMapper.queryXx",pd);
		if(pdd!= null &&!"".equals(pdd)){
			pdd.put("CONTENT", content);
			obj = dao.update("UmbsMapper.editXx",pd);
			
		}else{
			pdd = new PageData();
			pdd.put("OBJECT_ID",pd.get("ID")); 
			pdd.put("ID", UuidUtil.get32UUID());
			pdd.put("USER_ID", pd.get("USER_ID"));
			pdd.put("CONTENT", content);
			pdd.put("CREATE_DATE", DateUtil.getTime());
			pdd.put("TABLE_NAME", "orders");
			pdd.put("STATUS", "01");
			obj =  dao.update("UmbsMapper.saveUserMsg",pdd);
		}
		return obj;
	}
	
	//获取七天后将要过期的会员用户
	public List<PageData> finduserVip(PageData pd) throws Exception {
		return (List<PageData>) this.dao.findForList("JobMapper.finduserVip", pd);
	}
	
	
	 /**
     * 添加用户系统消息
     */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object saveMsg(String content,String USER_ID) throws Exception {
		Object obj = "";
		PageData pdd = new PageData();
		pdd.put("OBJECT_ID","201709849gqmsg"); 
		//判断是否已存在信息，存在修改，不存在，新增
		pdd = (PageData) dao.findForObject("UmbsMapper.queryXx",pdd);
		if(pdd!= null &&!"".equals(pdd)){
			pdd.put("CONTENT", content);
			obj = dao.update("UmbsMapper.editXx",pdd);
			
		}else{
			pdd = new PageData();
			pdd.put("OBJECT_ID","201709849gqmsg"); 
			pdd.put("ID", UuidUtil.get32UUID());
			pdd.put("USER_ID", USER_ID);
			pdd.put("CONTENT", content);
			pdd.put("CREATE_DATE", DateUtil.getTime());
			pdd.put("TABLE_NAME", "");
			pdd.put("STATUS", "01");
			obj =  dao.update("UmbsMapper.saveUserMsg",pdd);
		}
		return obj;
	}
		
	

}

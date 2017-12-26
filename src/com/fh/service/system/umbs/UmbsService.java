package com.fh.service.system.umbs;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page; 
import com.fh.util.CardNoUtil;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("umbsService")
public class UmbsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("UmbsMapper.listPage", page);
	}

	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("UmbsMapper.findCount", page);
	}
	public PageData findByid(PageData pd)  throws Exception {
	       return (PageData)this.dao.findForObject("UmbsMapper.queryById", pd);
	}
	
	public Object editLevel(PageData pd)  throws Exception {
	       return this.dao.update("UmbsMapper.editLevel", pd);
	} 
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object shjj(PageData pd)  throws Exception {
	    Object obj = "";
	    try {
	    	 /**
		        * 修改状态
		        */
	           obj = this.dao.update("UmbsMapper.shjj", pd);
	           /**
	            * 添加用户系统消息
	            */
	   		PageData pdd = new PageData();
	   		pdd.put("OBJECT_ID",pd.get("ID")); 
	   		//判断是否已存在信息，存在修改，不存在，新增
	   		pdd = (PageData) dao.findForObject("UmbsMapper.queryXx",pdd);
	   		if(pdd!= null &&!"".equals(pdd)){
	   			pdd.put("CONTENT", pd.get("CONTENT"));
	   			obj = dao.update("UmbsMapper.editXx",pdd);
	   			
	   		}else{
	   			pdd = new PageData(); 
	   			pdd.put("ID", UuidUtil.get32UUID());
	   			pdd.put("USER_ID", pd.get("USER_ID"));
	   			pdd.put("CONTENT", pd.get("CONTENT"));
	   			pdd.put("CREATE_DATE", DateUtil.getTime());
	   			pdd.put("TABLE_NAME", "jl_upgrade");
	   			pdd.put("STATUS", "01");
	   			obj =  dao.save("UmbsMapper.saveUserMsg",pdd);
	   		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
   		return obj;
	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object shtg(PageData pd)  throws Exception { 
		Object obj = "";
		try {
			PageData upgrade=findByid(pd);
			PageData info=(PageData)this.dao.findForObject("CardHistoryMapper.queryCardIsIn",upgrade.getString("USER_ID"));
			if(info!=null){
    			PageData his=new PageData();
    			his.put("ID",info.getString("HID"));
    			his.put("END_TIME",new Date());
    			his.put("IS_USED","01");
    			his.put("CREATE_DATE",new Date()); 
    			obj = dao.update("CardHistoryMapper.edit",his);
     		}else{
     			PageData his=new PageData();   
    			his.put("ID",UuidUtil.get32UUID());
    			his.put("USER_ID",upgrade.getString("USER_ID"));
    			his.put("CARD_NO",upgrade.getString("CARD_NO"));
    			his.put("START_TIME",upgrade.getString("UPGRADE_DATE"));
    			his.put("IS_USED","01");
    			his.put("END_TIME",new Date()); 
    			his.put("CREATE_DATE",new Date());  
    			obj = dao.save("CardHistoryMapper.save",his);
    		}
			PageData pa =new PageData();  
			String level=upgrade.getString("LEVEL");
			pa.put("VIP_LEVEL",upgrade.getString("UPGRADE_LEVEL"));
    		pa.put("IS_VIP","1"); 
    		if("00".equals(level)){//会员从一个等级升级到另一个等级，期限超过了三个月
    			pa.put("UPGRADE_DATE",new Date());
    			pa.put("DUE_DATE",DateUtil.getDate(DateUtil.getDay(new Date()),"12")); 
    		} 
    		pa.put("USER_ID",upgrade.getString("USER_ID"));
    		String vip_level=upgrade.getString("UPGRADE_LEVEL"); 
    		PageData clan=(PageData)this.dao.findForObject("UmbsMapper.queryClan",upgrade.getString("USER_ID"));
    		String no=CardNoUtil.getNo(vip_level,clan.getString("ZONE_CODE"));
    		PageData ta=(PageData)this.dao.findForObject("CardHistoryMapper.queryMaxCard",no);
    		String card_no=null;
    		if(ta.getString("code")!=null&&!"".equals(ta.getString("code"))){
    			card_no=CardNoUtil.getCardNo(ta.getString("code"));
    		}else{
    			card_no=CardNoUtil.getCardNo(no+"00000");
    		} 
    		String content=pd.getString("CONTENT")+"您的会员卡号为："+card_no;
    		pa.put("CARD_NO",card_no); 
    		obj=editLevel(pa); //修改会员信息 
    		System.out.println("rel="+Integer.valueOf(obj.toString()));
    		if(Integer.valueOf(obj.toString())>=1){
    			PageData his=new PageData();   
    			his.put("ID",UuidUtil.get32UUID());
    			his.put("USER_ID",upgrade.getString("USER_ID"));
    			his.put("IS_USED","00");
    			his.put("CARD_NO",card_no);
    			his.put("START_TIME",new Date()); 
    			his.put("CREATE_DATE",new Date());  
    			obj = dao.save("CardHistoryMapper.save",his);
    			 /**
 		        * 修改状态
 		        */ 
 		        obj = this.dao.update("UmbsMapper.shtg", pd); 
 		        /**
 		         * 添加用户系统消息
 		         */
 				PageData pdd = new PageData();
 				pdd.put("OBJECT_ID",pd.get("ID")); 
 				//判断是否已存在信息，存在修改，不存在，新增
 				pdd = (PageData) dao.findForObject("UmbsMapper.queryXx",pdd);
 				if(pdd!= null &&!"".equals(pdd)){
 					pdd.put("CONTENT", content);
 					obj = dao.update("UmbsMapper.editXx",pdd); 
 				}else{ 
 					pdd = new PageData(); 
 					pdd.put("ID",UuidUtil.get32UUID());
 					pdd.put("USER_ID", pd.get("USER_ID"));
 					pdd.put("CONTENT",content);
 					pdd.put("CREATE_DATE", DateUtil.getTime());
 					pdd.put("TABLE_NAME", "jl_upgrade");
 					pdd.put("STATUS", "01");
 					obj =  dao.save("UmbsMapper.saveUserMsg",pdd);
 				}
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return obj;
	}
	
	/*@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object shtg(PageData pd)  throws Exception {

		Object obj = "";
		try {
		       *//**
		        * 修改状态
		        *//*
			    String id=UuidUtil.get32UUID();
			    pd.put("ORDER_ID",id);
		        obj = this.dao.update("UmbsMapper.shtg", pd);
		        *//**
		         * 生成订单信息
		         *//*
		        PageData pad = new PageData();
				pad.put("ID",id);
				pad.put("USER_ID", pd.get("USER_ID"));
				pad.put("PRICE", pd.get("PRICE_TOPAY"));
				pad.put("EVENT", "升级入会");
				pad.put("OBJECT_ID", pd.get("ID"));
				pad.put("STATUS", "01");
				pad.put("TYPE", "01");
				pad.put("DATE", DateUtil.getTime());
				obj =  dao.save("UmbsMapper.saveOrder",pad);
		        *//**
		         * 添加用户系统消息
		         *//*
				PageData pdd = new PageData();
				pdd.put("OBJECT_ID",pd.get("ID")); 
				//判断是否已存在信息，存在修改，不存在，新增
				pdd = (PageData) dao.findForObject("UmbsMapper.queryXx",pd);
				if(pdd!= null &&!"".equals(pdd)){
					pdd.put("CONTENT", pd.get("CONTENT"));
					obj = dao.update("UmbsMapper.editXx",pd);
					
				}else{
					pdd.put("ID", UuidUtil.get32UUID());
					pdd.put("USER_ID", pd.get("USER_ID"));
					pdd.put("CONTENT", pd.get("CONTENT"));
					pdd.put("CREATE_DATE", DateUtil.getTime());
					pdd.put("TABLE_NAME", "jl_upgrade");
					pdd.put("STATUS", "01");
					obj =  dao.save("UmbsMapper.saveUserMsg",pdd);
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return obj;
	}*/
	 
	
	//去掉小红点
		public Object updatedelhd(PageData pd) throws Exception{
			return this.dao.update("UmbsMapper.updatedelhd", pd);
		}
}

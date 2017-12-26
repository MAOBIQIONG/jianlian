package com.fh.service.app.reservation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("appReservationService")
public class AppReservationService {
     
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//咨询提交接口
	public Object saveReservation(PageData pd)throws Exception {
		return  dao.save("AppReservationMapper.saveReservation", pd);
	}
	
	//咨询提交接口
	public List<PageData> findByYearAndMonth(PageData pd)throws Exception {
		return  (List<PageData>) dao.findForList("AppReservationMapper.findByYearAndMonth", pd);
	} 
}

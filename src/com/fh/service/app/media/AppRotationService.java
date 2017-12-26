package com.fh.service.app.media;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appRotationservice")
public class AppRotationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//根据位置编号查找要显示的轮换大图
	@Cacheable(value="imgCache",key="#pd.getString(\"LOCATION_NO\")")
	public List<PageData> queryByColno(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("AppRotationMapper.queryByColno", pd);
	} 
}

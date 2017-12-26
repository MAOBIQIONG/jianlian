package com.fh.service.system.press;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("rotationservice")
public class RotationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询所有图片轮换信息
	public List<PageData> queryrotation(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("RotationMapper.queryrotation", pd);
	}
	//查询所有的栏目编号
	public List<PageData> queryBybianhao(PageData pd)throws Exception{
		return (List<PageData>) this.dao.findForList("RotationMapper.queryBybianhao", pd);
	}
	//查询
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("RotationMapper.listPageByParam", page);
	}
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("RotationMapper.findCount", page);
	}
	//根据id获取栏目信息
	public PageData queryrotationid(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("RotationMapper.queryrotationid", pd);
	}
	
	//添加
	@CacheEvict(value="imgCache",key="#pd.getString(\"LOCATION_NO\")")
	public void addrotation(PageData pd)throws Exception{
		this.dao.save("RotationMapper.addrotation", pd);
	}
	//修改
	@CacheEvict(value="imgCache",key="#pd.getString(\"LOCATION_NO\")")
	public void uprotation(PageData pd) throws Exception {
		this.dao.update("RotationMapper.uprotation", pd);
	} 
	
	//批量删除
	public void delrotation(String[] auserId)throws Exception{
		this.dao.delete("RotationMapper.delrotation", auserId);
	}
	//根据图片ID查找所有信息
	public List<PageData> queryrotationname(PageData pd)throws Exception{
		return (List<PageData>)this.dao.findForList("RotationMapper.queryrotationname", pd);
	}
	//显示/关闭
	public void upis_show(PageData pd)throws Exception{
		this.dao.update("RotationMapper.upis_show", pd);
	}
	//删除
	@CacheEvict(value="imgCache",key="#pd.getString(\"LOCATION_NO\")")
	public void delrotationByid(PageData pd)throws Exception{
		this.dao.delete("RotationMapper.delrotationByid", pd);
	}
}

package com.fh.service.school.teacher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;


@Service("teacherService")
public class TeacherService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		pd.put("ID", UuidUtil.get32UUID());
		dao.save("TeacherMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("TeacherMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("TeacherMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TeacherMapper.listPage", page);
	}
	/*
	* 通过手机号码phone获取数据
	*/
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TeacherMapper.findByPhone", pd);
	}
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TeacherMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TeacherMapper.findById", pd);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("TeacherMapper.findCount",
				pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TeacherMapper.deleteAll", ArrayDATA_IDS);
	}
	
}


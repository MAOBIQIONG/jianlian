package com.fh.service.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("appClazzService")
public class AppClazzService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppClazzMapper.listData", page);
	}
	/*
	*findByStudentId
	*/
	public List<PageData> findByStudentId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppClazzMapper.findByStudentId", pd);
	}
	/*
	*班级同学全部
	*/
	public List<PageData> clazzStudentAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppClazzMapper.clazzStudentAll", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppClazzMapper.listAll", pd);
	}
	/*
	*根据课程ID获取相对应的学生信息
	*/
	public List<PageData> getStudentById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppClazzMapper.getStudentById", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppClazzMapper.findById", pd);
	}
	
}

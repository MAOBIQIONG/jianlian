package com.fh.service.school.student;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;


@Service("studentService")
public class StudentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StudentMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StudentMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StudentMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StudentMapper.listPage", page);
	}
	
	/*
	* 新增学生课程
	*/
	public void saveStudentSubjects(PageData pd)throws Exception{
		dao.save("StudentMapper.saveClazzStudent", pd);
	}
	
	/*
	* 删除学生课程信息
	*/
	public void deleteStudentSubjects(PageData pd)throws Exception{
		dao.delete("StudentMapper.deleteStudentSubjects", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StudentMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StudentMapper.findById", pd);
	}
	/*
	 * 通过学号Studentno获取数据
	 */
	public PageData findByStudentno(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StudentMapper.findByStudentno", pd);
	}
	/*
	* 通过手机号码phone获取数据
	*/
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StudentMapper.findByPhone", pd);
	}
	/*
	*学生的课程信息
	*/
	public List<PageData> findByStuSubInfo(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StudentMapper.findByStuSubInfo", pd);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("StudentMapper.findCount",
				pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StudentMapper.deleteAll", ArrayDATA_IDS);
	}
	
}


package com.fh.service.school.clazz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;


@Service("clazzService")
public class ClazzService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ClazzMapper.save", pd);
	}
	
	/*
	* 新增同班同学
	*/
	public void saveClazzStudent(PageData pd)throws Exception{
		pd.put("ID", UuidUtil.get32UUID());
		dao.save("ClazzMapper.saveClazzStudent", pd);
	}
	
	/*
	* 删除同班同学
	*/
	public void deleteClazzStudent(PageData pd)throws Exception{
		dao.delete("ClazzMapper.deleteClazzStudent", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ClazzMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ClazzMapper.edit", pd);
	}
	
	/*
	* 注册聊天室ID
	*/
	public void updateGroupId(PageData pd)throws Exception{
		dao.update("ClazzMapper.updateGroupId", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ClazzMapper.listPage", page);
	}
	
	/*
	*班级同学列表
	*/
	public List<PageData> clazzStudentList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ClazzMapper.clazzStudentlistPage", page);
	}
	
	/*
	*班级同学全部
	*/
	public List<PageData> clazzStudentAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ClazzMapper.clazzStudentAll", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ClazzMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ClazzMapper.findById", pd);
	}
	
	/*
	* 查询列表总数
	*/
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ClazzMapper.findCount",
				pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ClazzMapper.deleteAll", ArrayDATA_IDS);
	}
	
}


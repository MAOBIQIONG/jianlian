package com.fh.service.system.magazine;

import java.util.List;  
import javax.annotation.Resource; 
import org.springframework.stereotype.Service; 
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("jlqkService")
public class JlqkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao; 
	 
	//查询列表信息
	public List<PageData> listByParam(Page page)  throws Exception {
	       return (List<PageData>)this.dao.findForList("JlqkMapper.listPageByParam", page);
	}  
	
	public PageData findCount(Page page)  throws Exception {
	       return (PageData)this.dao.findForObject("JlqkMapper.findCount", page);
	}
	
	// 根据id获取企业信息
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("JlqkMapper.queryById", pd);
	} 
 
	// 添加
	public Object save(PageData pd) throws Exception {
		return this.dao.save("JlqkMapper.save", pd);
	} 
 
	// 更新
	public Object edit(PageData pd) throws Exception {
		return this.dao.update("JlqkMapper.edit", pd);
	}
	
	// 删除
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("JlqkMapper.delete", pd);
	} 
}

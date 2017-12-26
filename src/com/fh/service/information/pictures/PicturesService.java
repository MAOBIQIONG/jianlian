package com.fh.service.information.pictures;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("picturesService")
@Transactional(rollbackFor=Exception.class)
public class PicturesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		this.dao.save("PicturesMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		this.dao.delete("PicturesMapper.delete", pd);
	}

	public void edit(PageData pd) throws Exception {
		this.dao.update("PicturesMapper.edit", pd);
	}

	public List<PageData> list(Page page) throws Exception {
		return (List) this.dao.findForList("PicturesMapper.datalistPage", page);
	}

	public List<PageData> listAll(PageData pd) throws Exception {
		return (List) this.dao.findForList("PicturesMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("PicturesMapper.findById", pd);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		this.dao.delete("PicturesMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<PageData> getAllById(String[] ArrayDATA_IDS) throws Exception {
		return (List) this.dao.findForList("PicturesMapper.getAllById", ArrayDATA_IDS);
	}

	public void delTp(PageData pd) throws Exception {
		this.dao.update("PicturesMapper.delTp", pd);
	}

	public void editTitle(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		this.dao.update("PicturesMapper.editTitle", pd);
	}

	/**
	 * 赞
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void favor(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		pd.put("PICTUREFAVOR_ID", UuidUtil.get32UUID());
		pd.put("CREATE_TIME", DateUtil.getTime());
		this.dao.save("PictureFavorMapper.save", pd);
		this.dao.update("PicturesMapper.upFavor", pd);
	}

	/**
	 * 取消赞
	 */
	@Transactional(rollbackFor=Exception.class)
	public void unfavor(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		this.dao.save("PictureFavorMapper.deleteByPictureIdAndUserId", pd);
		this.dao.update("PicturesMapper.downFavor", pd);
	}
	/**
	 * 评论
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void comment(PageData pd) throws Exception{
		this.dao.save("PictureCommentMapper.save", pd);
		this.dao.update("PicturesMapper.upComment", pd);
	}
	
	/**
	 * 删除评论
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void uncomment(PageData pd) throws Exception{
		this.dao.delete("PictureCommentMapper.delete", pd);
		this.dao.update("PicturesMapper.downComment", pd);
	}
}

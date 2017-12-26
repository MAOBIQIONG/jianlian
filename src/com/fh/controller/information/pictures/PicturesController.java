package com.fh.controller.information.pictures;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.information.pictures.PicturesService;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.FileUpload;
import com.fh.util.ImageUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;
import com.sun.xml.internal.fastinfoset.stax.events.Util;

@Controller
@RequestMapping({ "/pictures" })
public class PicturesController extends BaseController {
	String menuUrl = "pictures/list.do";

	@Resource(name = "picturesService")
	private PicturesService picturesService;


	/**
	 * 批量上传图片
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploads")
	@ResponseBody
	public PageData handleFormUpload(MultipartHttpServletRequest request) {
		try {
			List<MultipartFile> file = request.getFiles("file");
			String ALBUM_ID = get32UUID();
			for (MultipartFile multipartFile : file) {
				save(multipartFile, ALBUM_ID);
			}
			PageData pd = this.getPageData();
			pd.put("ALBUM_ID", ALBUM_ID);
		} catch (Exception e) {
			e.printStackTrace();
			return AppUtil.paramError();
		}
		return AppUtil.success();
	}

	@RequestMapping({ "/save" })
	@ResponseBody
	public Object save(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String ALBUM_ID) throws Exception {
		logBefore(this.logger, "新增Pictures");
		Map<String,String> map = new HashMap<String,String>();
		String ffile = DateUtil.getDays();
		String fileName = "";
		PageData pd = new PageData();
		String filePath = null;
		if ((file != null) && (!file.isEmpty())) {
			 filePath = PathUtil.getClasspath() + "../" + "/image/" + ffile;
			fileName = FileUpload.fileUp(file, filePath, get32UUID());
			
		} else {
			//System.out.println("上传失败");
		}
		pd.put("ALBUM_ID", ALBUM_ID);
		int[] imgInfo = ImageUtil.getImageInfo(filePath+"/"+fileName);
		pd.put("PICTURES_ID", get32UUID());
		pd.put("W", imgInfo[0]);
		pd.put("H", imgInfo[1]);
		pd.put("USER_ID", getUser().getID());
		pd.put("NAME", fileName);
		pd.put("PATH", ffile + "/" + fileName);
		pd.put("CREATETIME", Tools.date2Str(new Date()));
		pd.put("MASTER_ID", "1");
		pd.put("BZ", "");

		this.picturesService.save(pd);

		map.put("result", "ok");
		map.put("id", pd.getString("PICTURES_ID"));
		map.put("path", pd.getString("PATH"));
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping({ "/delete" })
	public void delete(PrintWriter out) {
		logBefore(this.logger, "删除Pictures");
		PageData pd = new PageData();
		try {
			if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del")) {
				pd = getPageData();
				DelAllFile.delFolder(PathUtil.getClasspath() + "/image/" + pd.getString("PATH"));
				this.picturesService.delete(pd);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
	}

	@RequestMapping({ "/edit" })
	public ModelAndView edit(HttpServletRequest request,
			@RequestParam(value = "tp", required = false) MultipartFile file,
			@RequestParam(value = "tpz", required = false) String tpz,
			@RequestParam(value = "PICTURES_ID", required = false) String PICTURES_ID,
			@RequestParam(value = "TITLE", required = false) String TITLE,
			@RequestParam(value = "MASTER_ID", required = false) String MASTER_ID,
			@RequestParam(value = "BZ", required = false) String BZ) throws Exception {
		logBefore(this.logger, "修改Pictures");
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		if (Jurisdiction.buttonJurisdiction(this.menuUrl, "edit")) {
			pd.put("PICTURES_ID", PICTURES_ID);
			pd.put("TITLE", TITLE);
			pd.put("MASTER_ID", MASTER_ID);
			pd.put("BZ", BZ);

			if (tpz == null)
				tpz = "";
			String ffile = DateUtil.getDays();
			String fileName = "";
			if ((file != null) && (!file.isEmpty())) {
				String filePath = PathUtil.getClasspath() + "../" + "/image/" + ffile;
				fileName = FileUpload.fileUp(file, filePath, get32UUID());
				pd.put("PATH", ffile + "/" + fileName);
				pd.put("NAME", fileName);
			} else {
				pd.put("PATH", tpz);
			}
			Watermark.setWatemark(PathUtil.getClasspath() + "/image/" + ffile + "/" + fileName);
			this.picturesService.edit(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping({ "/list" })
	public ModelAndView list(Page page) {
		logBefore(this.logger, "列表Pictures");
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		try {
			pd = getPageData();

			String KEYW = pd.getString("keyword");

			if ((KEYW != null) && (!"".equals(KEYW))) {
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}

			page.setPd(pd);
			List varList = this.picturesService.list(page);
			mv.setViewName("information/pictures/pictures_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("QX", getHC());
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/goAdd" })
	public ModelAndView goAdd() {
		logBefore(this.logger, "去新增Pictures页面");
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			mv.setViewName("information/pictures/pictures_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/goEdit" })
	public ModelAndView goEdit() {
		logBefore(this.logger, "去修改Pictures页面");
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			pd = this.picturesService.findById(pd);
			mv.setViewName("information/pictures/pictures_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/deleteAll" })
	@ResponseBody
	public Object deleteAll() {
		logBefore(this.logger, "批量删除Pictures");
		PageData pd = new PageData();
		Map map = new HashMap();
		try {
			pd = getPageData();
			if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del")) {
				List pdList = new ArrayList();
				List pathList = new ArrayList();
				String DATA_IDS = pd.getString("DATA_IDS");
				if ((DATA_IDS != null) && (!"".equals(DATA_IDS))) {
					String[] ArrayDATA_IDS = DATA_IDS.split(",");
					pathList = this.picturesService.getAllById(ArrayDATA_IDS);

					for (int i = 0; i < pathList.size(); i++) {
						DelAllFile.delFolder(
								PathUtil.getClasspath() + "/image/" + ((PageData) pathList.get(i)).getString("PATH"));
					}
					this.picturesService.deleteAll(ArrayDATA_IDS);
					pd.put("msg", "ok");
				} else {
					pd.put("msg", "no");
				}
				pdList.add(pd);
				map.put("list", pdList);
			}
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		} finally {
			logAfter(this.logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping({ "/excel" })
	public ModelAndView exportExcel() {
		logBefore(this.logger, "导出Pictures到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		try {
			Map dataMap = new HashMap();
			List titles = new ArrayList();
			titles.add("标题");
			titles.add("文件名");
			titles.add("路径");
			titles.add("创建时间");
			titles.add("属于");
			titles.add("备注");
			dataMap.put("titles", titles);
			List varOList = this.picturesService.listAll(pd);
			List varList = new ArrayList();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", ((PageData) varOList.get(i)).getString("TITLE"));
				vpd.put("var2", ((PageData) varOList.get(i)).getString("NAME"));
				vpd.put("var3", ((PageData) varOList.get(i)).getString("PATH"));
				vpd.put("var4", ((PageData) varOList.get(i)).getString("CREATETIME"));
				vpd.put("var5", ((PageData) varOList.get(i)).getString("MASTER_ID"));
				vpd.put("var6", ((PageData) varOList.get(i)).getString("BZ"));
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping({ "/deltp" })
	public void deltp(PrintWriter out) {
		logBefore(this.logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = getPageData();
			String PATH = pd.getString("PATH");
			DelAllFile.delFolder(PathUtil.getClasspath() + "/image/" + pd.getString("PATH"));
			if (PATH != null) {
				this.picturesService.delTp(pd);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
	}

	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return (Map) session.getAttribute("QX");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
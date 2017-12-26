package com.fh.controller.system.PPPprojects;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.PPPprojectService.PPPProjectService;
import com.fh.service.system.PPPprojectService.PPPXmtpService;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.fileConfig;

@Controller
@RequestMapping({ "/pppxmtp" })
public class pppXmtpController extends BaseController {  
	
	@Resource(name = "pppXmtpService")
	private PPPXmtpService pppXmtpService; 
	
	@Resource(name = "PPPprojectService")
	private PPPProjectService PPPprojectService;
	
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	public static String serverImgPath = fileConfig.getString("serverImgPath");
		 
	@RequestMapping({ "/toLookImgs" })
	public ModelAndView toLookImgs() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData();  
			PageData pro=PPPprojectService.findById(pd); 
			pd.put("PRO_ID",pd.getString("ID"));
			List<PageData> xmtp =pppXmtpService.queryByPid(pd);  
			mv.addObject("pro", pro); 
			mv.addObject("xmtp", xmtp); 
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("system/PPPprojects/PPP_img_list");  
		return mv;
	}
	
	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData(); 
			mv.addObject("PRO_ID", pd.getString("PRO_ID")); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
	    mv.setViewName("system/PPPprojects/PPP_img_add");  
		return mv;
	}
	
	@RequestMapping({ "/uploadImgs" }) 
	@ResponseBody
	public JSONObject uploadImgs(MultipartHttpServletRequest request)throws Exception{
		JSONObject obj = new JSONObject(); 
		PageData pd = new PageData();  
		String PRO_ID=request.getParameter("PRO_ID");
		pd.put("PRO_ID",PRO_ID); 
		PageData data=pppXmtpService.queryMaxOrderby(pd);
		if(data.get("ORDER_BY")!=null){
			pd.put("ORDER_BY", data.get("ORDER_BY").toString());
		}else{
			pd.put("ORDER_BY","1");
		} 
		MultipartFile img=request.getFile("IMG_PATH");  
		if ((img != null) && (!img.isEmpty())) {
			String filePath =serverBasePath; 
			String fileName = FileUpload.fileUp(img,filePath,get32UUID());  
			if(fileName!=null&&!"".equals(fileName)){
				pd.put("IMG_PATH",serverImgPath+fileName.trim()); 
			} 
		}  
		pd.put("ID", get32UUID()); 
		pd.put("DATE", new Date()); 
		Object ob=pppXmtpService.save(pd);
		if(Integer.parseInt(ob.toString())>0){
			obj.put("statusCode", 200);   
		}else{
			obj.put("statusCode", 400);   
		} 
		return obj;
	} 
	
	@RequestMapping({ "/toAddCover" })
	public ModelAndView toAddCover() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try {
			pd = getPageData(); 
			mv.addObject("PRO_ID", pd.getString("PRO_ID")); 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
	    mv.setViewName("system/PPPprojects/PPP_cover_add");  
		return mv;
	}
	
	@RequestMapping({ "/uploadCover" }) 
	@ResponseBody
	public JSONObject uploadCover(MultipartHttpServletRequest request)throws Exception{
		JSONObject obj = new JSONObject(); 
		PageData pd = new PageData();  
		String PRO_ID=request.getParameter("PRO_ID");
		pd.put("PRO_ID",PRO_ID);  
		MultipartFile img=request.getFile("IMG_PATH");  
		if ((img != null) && (!img.isEmpty())) {
			String filePath =serverBasePath; 
			String fileName = FileUpload.fileUp(img,filePath,get32UUID());  
			if(fileName!=null&&!"".equals(fileName)){
				pd.put("COVER_PATH",serverImgPath+fileName); 
			} 
		}   
		Object ob=PPPprojectService.uploadCover(pd);
		if(Integer.parseInt(ob.toString())>0){
			obj.put("statusCode", 200);   
		}else{
			obj.put("statusCode", 400);   
		} 
		return obj;
	}  
	
	//批量删除
	@RequestMapping({ "/delImg" })
	@ResponseBody
	public String delImg() throws Exception{
		PageData pd = new PageData();
		JSONObject obj = new JSONObject();  
		pd = getPageData();
		String id = pd.getString("ID");
		String[] ids= id.split(",");
		try {
			this.pppXmtpService.delete(ids);
		} catch (Exception e) { 
			e.printStackTrace();
		}  
		obj.put("statusCode", "200");
		obj.put("message", "操作成功"); 
		return obj.toString();
	}
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 
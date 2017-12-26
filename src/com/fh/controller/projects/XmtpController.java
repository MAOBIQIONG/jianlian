package com.fh.controller.projects;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.projects.ProjectBackupService;
import com.fh.service.projects.ProjectScheduleService;
import com.fh.service.projects.ProjectService;
import com.fh.service.projects.XmtpService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.operLog.OperLogService;
import com.fh.service.system.user.TbUserService;
import com.fh.service.system.user.UserService;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/xmtp" })
public class XmtpController extends BaseController {  
	
	@Resource(name = "xmtpService")
	private XmtpService xmtpService; 
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
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
			PageData pro=projectService.findById(pd); 
			pd.put("PRO_ID",pd.getString("ID"));
			List<PageData> xmtp =xmtpService.queryByPid(pd);  
			mv.addObject("pro", pro); 
			mv.addObject("xmtp", xmtp); 
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("projects/pro_img_list");  
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
	    mv.setViewName("projects/pro_img_add");  
		return mv;
	}
	
	@RequestMapping({ "/uploadImgs" }) 
	@ResponseBody
	public JSONObject uploadImgs(MultipartHttpServletRequest request)throws Exception{
		JSONObject obj = new JSONObject(); 
		PageData pd = new PageData();  
		String PRO_ID=request.getParameter("PRO_ID");
		pd.put("PRO_ID",PRO_ID); 
		PageData data=xmtpService.queryMaxOrderby(pd);
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
		Object ob=xmtpService.save(pd);
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
	    mv.setViewName("projects/pro_cover_add");  
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
		Object ob=projectService.uploadCover(pd);
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
			this.xmtpService.delete(ids);
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
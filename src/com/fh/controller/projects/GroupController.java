package com.fh.controller.projects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.projects.GroupService;
import com.fh.service.projects.ProjectService;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.fh.util.wangyi.WyUtil;



@Controller
@RequestMapping({ "/group" })
public class GroupController extends BaseController { 
	
	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	public static String serverImgPath = fileConfig.getString("serverImgPath");
	
	@Resource(name = "groupService")
	private GroupService groupService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	
	@RequestMapping({ "/openGroup" })
	public ModelAndView openGroup() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();  
		try { 
			pd =getPageData();
			mv.addObject("pd",pd);  
		} catch (Exception e) { 
			e.printStackTrace();
		}    
		mv.setViewName("projects/project_group");  
		return mv;
	}
	 
	@RequestMapping({"/createGroup"}) 
	@ResponseBody
	public String createGroup(MultipartHttpServletRequest request) throws Exception {
		JSONObject obj = new JSONObject(); 
		PageData pd = new PageData();   
		String PRO_ID=request.getParameter("PRO_ID");
		pd.put("ID", PRO_ID);
		pd.put("SID", getUser().getID());
		MultipartFile img=request.getFile("icon");   
		
		if ((img != null) && (!img.isEmpty())) {
			String filePath =serverBasePath; 
			String fileName = FileUpload.fileUp(img,filePath,get32UUID());  
			if(fileName!=null&&!"".equals(fileName)){
				pd.put("icon",serverImgPath+fileName.trim()); 
			} 
		} 
		//项目信息
		PageData prodata=this.projectService.findById(pd);
		
		//获取当前用户app的账号信息
		PageData udata=this.groupService.findById(pd);
		
		PageData jqdata=new PageData();
		//建群
    	List<String> ids = new ArrayList<String>(); 
    	ids.add(udata.getString("AID"));
    	JSONArray jsonArr = JSONArray.fromObject(ids); 
    	
		//群名称
    	jqdata.put("tname",request.getParameter("tname"));
		//群主
    	jqdata.put("owner",udata.getString("AID"));
    	//群人员
    	jqdata.put("members",jsonArr.toString());
    	//群公告
    	jqdata.put("announcement","");
    	//群描述
    	jqdata.put("intro","");
    	//群内容
    	jqdata.put("msg","邀请加入项目讨论组");
    	//0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
    	jqdata.put("magree","0");
    	//0不用验证，1需要验证,2不允许任何人加入
    	jqdata.put("joinmode","0");
    	//群头像
    	jqdata.put("icon",pd.getString("icon"));
    	//调用网易创建群组
    	String wydata= WyUtil.createGroup(jqdata);
    	JSONObject JOBJ=new JSONObject(wydata);
    	
    	String code=JOBJ.getString("code");
    	
    	System.out.println("创建群组"+code);
    	//Object ob=this.groupService.createGroup(jqdata);
    	
    	
    	return obj.toString();
	}
	
	
	
	
	@RequestMapping({"/pull"}) 
	@SystemLog(methods="拉人入群",type="拉人入群")
	public String pull(PageData pd) throws Exception {
		return null;
	}
	
	/*@RequestMapping({"/kick"}) 
	@SystemLog(methods="踢人出群",type="踢人出群")
	public String kick(PageData pd) throws Exception {
		
	}
	
	@RequestMapping({"/removeGroup"}) 
	@SystemLog(methods="解散群组",type="解散群组")
	public String removeGroup(PageData pd) throws Exception {
		
	}*/
	
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
} 
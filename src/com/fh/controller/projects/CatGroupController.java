package com.fh.controller.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.projects.GroupService;
import com.fh.service.projects.ProjectBidderService;
import com.fh.service.projects.ProjectService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.fh.util.fileConfig;
import com.fh.util.wangyi.WyUtil;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/Catgroup" })
public class CatGroupController extends BaseController {

	//上传文件存放路径
	public static String videosBasePath = fileConfig.getString("videosBasePath");
	public static String serverBasePath = fileConfig.getString("serverBasePath");
	public static String serverImgPath = fileConfig.getString("serverImgPath");
	
	@Resource(name = "groupService")
	private GroupService groupService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name = "projectBidderService")
	private ProjectBidderService proBidderService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
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
		if(udata.getString("AID")==null || "".equals(udata.getString("AID"))){
			//没有查询到当前登录人的app账号
			obj.put("status",300); 
			return obj.toString();
		}
		
		PageData jqdata=new PageData();
		//建群
    	List<String> ids = new ArrayList<String>(); 
    	ids.add(udata.getString("AID"));
    	JSONArray jsonArr = JSONArray.fromObject(ids); 
    	String msg = request.getParameter("tname");
    	String str=new String(msg.getBytes("ISO-8859-1"),"UTF-8");
    	
    	//处理中文乱码
		//群名称
    	jqdata.put("tname",str);
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
    	if("200".equals(code)){
    		//创建本地群
    		PageData savedata=new PageData();
    		savedata.put("ID", get32UUID());
    		savedata.put("TID", JOBJ.getString("tid"));
    		savedata.put("TNAME", jqdata.getString("tname"));
    		savedata.put("OWNER", jqdata.getString("owner"));
    		savedata.put("NUMBER", 1);
    		savedata.put("ANNOUNCEMENT", jqdata.getString("announcement"));
    		savedata.put("INTRO", jqdata.getString("intro"));
    		savedata.put("MSG", jqdata.getString("msg"));
    		savedata.put("MAGREE", jqdata.getString("magree"));
    		savedata.put("JOINMODE", jqdata.getString("joinmode"));  
    		savedata.put("ICON", jqdata.getString("icon")); 
    		savedata.put("PRO_ID", prodata.getString("ID"));
    		savedata.put("CREATE_BY", pd.getString("SID")); 
    		Object ob=this.groupService.createGroup(savedata);
    		if(Integer.valueOf(ob.toString()) >= 1){
				System.out.println("创建本地群成功");
				PageData data=new PageData();
				//会话不存在
				data.put("ID",get32UUID());
				data.put("CREATE_DATE",new Date()); 
				data.put("PRO_ID",prodata.getString("ID")); 
				data.put("SCENE",1);
				data.put("USER_ID",udata.getString("AID"));
				data.put("JSID",savedata.getString("TID"));
				Object res=this.proBidderService.save(data);
				if(Integer.valueOf(res.toString()) >= 1){
					System.out.println("创建会话成功");
					obj.put("statusCode", 200);  
				}else{
					System.out.println("创建会话失败"); 
					obj.put("statusCode", 400);  
				} 
			}else{
				System.out.println("创建本地群失败"); 
			}
    	} 
    	return obj.toString();
	}
	
	
	@RequestMapping({"/pull"}) 
	@SystemLog(methods="拉人入群",type="拉人入群")
	@ResponseBody
	public String pull() throws Exception {
		JSONObject obj = new JSONObject(); 
		PageData pd=new PageData();
		pd =getPageData();
		if(pd!=null){ 
			//根据项目编号查询群组信息
			PageData prodata=this.groupService.findByGrouptId(pd);
			
			if(prodata!=null){  
				//拉人进群组
		    	List<String> ids = new ArrayList<String>();
		    	ids.add(pd.getString("USER_ID")); 
		    	JSONArray jsonArr = JSONArray.fromObject(ids);
		    	
		    	PageData pddata = new PageData();
		    	pddata.put("tid",prodata.getString("TID"));
		    	pddata.put("owner",prodata.getString("OWNER"));
		    	pddata.put("members",jsonArr.toString());
		    	pddata.put("msg","邀请进入群组");
		    	//调用网易拉人入群
		    	String wydata= WyUtil.addToGroup(pddata);
		    	JSONObject JOBJ=new JSONObject(wydata); 
		    	String code=JOBJ.getString("code");
		    	System.out.println("拉人进群组"+code);
		    	if("200".equals(code)){  
		    		//拉入本地群组
		    		PageData lrdata=new PageData();
		    		lrdata.put("ID", get32UUID());
					lrdata.put("GROUPID", prodata.getString("ID"));
					lrdata.put("USER_ID", pd.getString("USER_ID")); 
		    		Object ob=this.groupService.pull(lrdata);
		    		if(Integer.valueOf(ob.toString()) >= 1){
		    			System.out.println("拉人入本地群成功");
		    			PageData data=new PageData();
						//会话不存在
						data.put("ID",get32UUID());
						data.put("CREATE_DATE",new Date()); 
						data.put("PRO_ID",prodata.getString("ID")); 
						data.put("SCENE",1);
						data.put("USER_ID",pd.getString("USER_ID"));
						data.put("JSID",prodata.getString("TID"));
						Object res=this.proBidderService.save(data);
						if(Integer.valueOf(res.toString()) >= 1){
							System.out.println("创建会话成功");
							//推送消息给某个人
							 String content = "您已被拉入'"+prodata.getString("TNAME")+"'群组中";
							 String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
						     TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
						     PushUtil pushApp=new PushUtil();
							 String alias = pd.getString("USER_ID");
							 PageData notif=new PageData();
							 notif.put("ID", get32UUID()); 
							 notif.put("CREATE_DATE", new Date());
				   	 		 try {
				   	 			sysNotifService.save(notif); 
				   	 			//推送
								 pushApp.pushToSingle(tmTemplate,alias); 
				   	 			obj.put("status",200); 
				   	 			
							} catch (Exception e) {
								obj.put("status",400); 
							} 
							
						}else{
							System.out.println("创建会话失败"); 
							obj.put("status",400); 
						}  
		    		}else{
		    			System.out.println("拉人入本地群失败");
		    			obj.put("status",400);
		    		}
		    		
		    	}else{
		    		System.out.println("拉人进群失败");
		    		obj.put("status",300); 
		    	}
		    	
			}else{
				System.out.println("未获取到群组信息");
			}
			
		} 
		return obj.toString();
	}
	
	@RequestMapping({"/kick"}) 
	@SystemLog(methods="踢人出群",type="踢人出群")
	@ResponseBody
	public String kick() throws Exception {
		JSONObject obj = new JSONObject(); 
		PageData pd=new PageData();
		pd =getPageData();
		if(pd!=null){
			//根据项目编号查询群组信息
			PageData prodata=this.groupService.findByGrouptId(pd);
			if(prodata!=null){
				PageData Tpd = new PageData();
				Tpd.put("tid",prodata.getString("TID"));
				Tpd.put("owner",prodata.getString("OWNER"));  
				Tpd.put("member",pd.getString("USER_ID")); 
				String wydata=WyUtil.kickFromGroup(Tpd);
				JSONObject JOBJ=new JSONObject(wydata); 
		    	String code=JOBJ.getString("code");
		    	System.out.println("踢人出群"+code);
		    	if("200".equals(code)){  
		    		//踢人出本地群组
		    		PageData lrdata=new PageData(); 
					lrdata.put("GROUPID", prodata.getString("ID"));
					lrdata.put("USER_ID", pd.getString("USER_ID")); 
		    		Object ob=this.groupService.kick(lrdata);
		    		if(Integer.valueOf(ob.toString()) >= 1){
		    			System.out.println("踢人出本地群成功");
		    			PageData data=new PageData(); 
		    			//查询本地会话
						data.put("USER_ID",pd.getString("USER_ID"));
						data.put("JSID",prodata.getString("TID"));
						PageData hhres=this.proBidderService.findBycon(data);
						if(hhres!=null){
							//删除某人的会话
							try {
								PageData dedata=new PageData();
								dedata.put("ID", hhres.getString("ID"));
								this.proBidderService.catdelete(dedata); 
								//删除某个会话用户
								this.groupService.removeCatGroupuser(dedata);
								
								//推送消息给某个人
								 String content = "您已被移除'"+prodata.getString("TNAME")+"'群组";
								 String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
							     TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
							     PushUtil pushApp=new PushUtil();
								 String alias = pd.getString("USER_ID");
								 PageData notif=new PageData();
								 notif.put("ID", get32UUID()); 
								 notif.put("CREATE_DATE", new Date());
					   	 		 try {
					   	 			sysNotifService.save(notif); 
					   	 			//推送
									 pushApp.pushToSingle(tmTemplate,alias); 
					   	 			obj.put("status",200); 
					   	 			
								} catch (Exception e) {
									obj.put("status",400); 
								} 
								
							} catch (Exception e) {
								System.out.println("删除某人本地会话失败"); 
								obj.put("status",400); 
							}
							 
						}else{
							System.out.println("查询本地会话失败"); 
							obj.put("status",400); 
						}  
		    		}else{
		    			System.out.println("踢人出本地群失败");
		    			obj.put("status",400);
		    		}
		    		
		    	}else{
		    		System.out.println("踢人出群失败");
		    		obj.put("status",300); 
		    	}
			}
		}
		return obj.toString();
	}
	
	@RequestMapping({"/removeGroup"}) 
	@SystemLog(methods="解散群组",type="解散群组")
	@ResponseBody
	public String removeGroup() throws Exception {
		JSONObject obj = new JSONObject(); 
		PageData pd=new PageData();
		pd =getPageData();
		if(pd!=null){
			//tid、owner 
			//根据项目编号查询群组信息
			PageData prodata=this.groupService.findByGrouptId(pd);
			if(prodata!=null){
				PageData Tpd = new PageData();
				Tpd.put("tid",prodata.getString("TID"));
				Tpd.put("owner",prodata.getString("OWNER"));  
				String wydata=WyUtil.removeGroup(Tpd);
				JSONObject JOBJ=new JSONObject(wydata); 
		    	String code=JOBJ.getString("code");
		    	System.out.println("解散群组"+code);
		    	if("200".equals(code)){  
		    		//解散本地群组
		    		PageData lrdata=new PageData(); 
					lrdata.put("ID", prodata.getString("ID")); 
		    		Object ob=this.groupService.removeGroup(lrdata);
		    		if(Integer.valueOf(ob.toString()) >= 1){
		    			System.out.println("解散本地群成功");
		    			
		    			PageData data=new PageData(); 
		    			//查询本地会话
						data.put("USER_ID",prodata.getString("OWNER"));
						data.put("JSID",prodata.getString("TID"));
						PageData hhres=this.proBidderService.findBycon(data);
						if(hhres!=null){
							//删除某人的会话
							try {
								PageData dedata=new PageData();
								dedata.put("ID", hhres.getString("ID"));
								//删除用户跟群的会话
								this.proBidderService.catdelete(dedata); 
								//删除某个会话的用户
								this.groupService.removeCatGroupuser(dedata);
								obj.put("status",200);
								obj.put("message", "操作成功！"); 
							} catch (Exception e) {
								System.out.println("删除某人本地会话失败"); 
								obj.put("status",400); 
								obj.put("message", "操作失败！"); 
							}
							 
						}else{
							System.out.println("查询本地会话失败"); 
							obj.put("status",400); 
							obj.put("message", "操作失败！"); 
						}  
		    		}else{
		    			System.out.println("解散本地群失败");
		    			obj.put("status",400);
		    			obj.put("message", "操作失败！"); 
		    		}
		    		
		    	}else{
		    		System.out.println("解散群失败");
		    		obj.put("status",300); 
		    		obj.put("message", "操作失败！"); 
		    	}
			}
		}
		return obj.toString();
	}
}

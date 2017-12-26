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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;  

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.projects.GroupService;
import com.fh.service.projects.ProjectBidderService;
import com.fh.service.projects.ProjectService;
import com.fh.service.system.dictionaries.DictionariesService; 
import com.fh.service.system.notification.SysNotificationService;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/proBidder" })
public class ProjectBidderController extends BaseController { 
	
	@Resource(name = "projectBidderService")
	private ProjectBidderService proBidderService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService;
	
	@Resource(name = "projectService")
	private ProjectService projectService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "groupService")
	private GroupService groupService;
	
	@RequestMapping(value={"/toBidder"})
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();  
		pd.put("ID",pd.get("PROJECT_ID"));
		pd.put("PRO_ID",pd.get("PROJECT_ID"));
		PageData data = this.projectService.findById(pd);
		//查询是否建立过群
		PageData isGroup=this.groupService.findByGrouptId(pd);
		if(isGroup!=null){
			mv.addObject("isGro", 1); 
		}else{
			mv.addObject("isGro", 2); 
		}  
		mv.addObject("PROJECT_ID", pd.get("PROJECT_ID")); 
		mv.addObject("BIDDER_ID", pd.get("BIDDER_ID"));
		mv.addObject("IS_SIGNED", data.get("IS_SIGNED"));
		mv.setViewName("projects/project_bidder_list"); 
		return mv;
	} 
	
	@RequestMapping(value={"/queryPageList"}) 
	@ResponseBody
	public String queryPageList(Page page) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();

		JSONObject getObj = new JSONObject();
		String sEcho = "0";// 记录操作的次数  每次加1  
		String iDisplayStart = "0";// 起始  
		String iDisplayLength = "10";// size  
		String mDataProp = "mDataProp_";
		String sortName = "";//排序字段
		String iSortCol_0 = "";//排序索引
		String sSortDir_0 = "";//排序方式
		//获取jquery datatable当前配置参数  
		JSONArray jsonArray = JSONArray.fromObject(pd.getString("aoData"));  
		for (int i = 0; i < jsonArray.size(); i++)  
		{  
		    try  
		    {  
		        JSONObject jsonObject = (JSONObject)jsonArray.get(i);  
		        if (jsonObject.get("name").equals("sEcho")){ 
		            sEcho = jsonObject.get("value").toString();  
		        }
		        else if (jsonObject.get("name").equals("iDisplayStart")){
		            iDisplayStart = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iDisplayLength")){ 
		            iDisplayLength = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("sSortDir_0")){
		        	sSortDir_0 = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iSortCol_0")){
		        	iSortCol_0 = jsonObject.get("value").toString();
		        	mDataProp = mDataProp+""+iSortCol_0;
		        	for (int j = 0; j < jsonArray.size(); j++)  {
		        		JSONObject jsonObject1 = (JSONObject)jsonArray.get(j);  
		        		if(jsonObject1.get("name").equals(mDataProp)){
		        			sortName = jsonObject1.get("value").toString(); 
		        		}
		        	}
		        }
		        
		    }  
		    catch (Exception e) { 
		    	e.printStackTrace();
		        break;  
		    }  
		}   
		 
		String NAME = pd.getString("NAME"); 
		if ((NAME != null) && (!"".equals(NAME))) { 
			pd.put("REAL_NAME",NAME);
		}  
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;
		
		List<PageData> data = this.proBidderService.listByParam(page);
		Object	total = this.proBidderService.findCount(page).get("counts");//查询记录的总行数 
		
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(PageData bidder:data){ 
			bidder.put("BID_DATE",bidder.get("BID_DATE")==null?"":bidder.get("BID_DATE").toString()); 
			Object user=bidder.get("USER_ID");
			if(user!=null&&!"".equals(user)){ 
			}else{
				bidder.put("REAL_NAME","匿名用户");	
			} 
		} 
		//ISJRQ判断有没有加入群
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}  
	
	@RequestMapping({ "/delete" })
	@ResponseBody
	public String delete() {
		PageData pd = new PageData(); 
		try {
			pd = getPageData();
			//if (Jurisdiction.buttonJurisdiction(this.menuUrl, "del"))
			this.proBidderService.delete(pd); 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString(); 
	}
	
	@RequestMapping({ "/update" })
	@SystemLog(methods="中标管理",type="确定签署合同")
	@ResponseBody
	public String updateStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData(); 
			pd.put("IS_SIGNED","1");
			pd.put("SIGNE_DATE",new Date());
			Object ob=this.projectService.updateSigned(pd);
			if(Integer.parseInt(ob.toString())>=1){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	@RequestMapping({ "/editStatus" })
	@SystemLog(methods="中标管理",type="编辑中标信息")
	@ResponseBody
	public String editStatus() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData();  
	        pd.put("PHONE_DATE",new Date());
			Object ob=this.proBidderService.edit(pd);
			if(Integer.parseInt(ob.toString())>=1){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	@RequestMapping({ "/editGroup" })
	@SystemLog(methods="中标管理",type="编辑讨论组成员信息")
	@ResponseBody
	public String editGroup() {
		PageData pd = new PageData();
		JSONObject obj = new JSONObject(); 
		try {
			pd = getPageData();  
			String res=this.proBidderService.editGroup(pd); 
			if("success".equals(res)){
				obj.put("status",200);
				obj.put("message", "操作成功！"); 
			}else{
				obj.put("status",400);
				obj.put("message", "操作失败！"); 
			} 
		} catch (Exception e) {
			this.logger.error(e.toString(), e);
		}
		return obj.toString();
	} 
	
	//获取当前登录用户
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(); 
		User user = (User) session.getAttribute("sessionUser"); 
		return user;
	} 
	
	//中标模块推送
	@RequestMapping({ "/pushToApp" })
	@SystemLog(methods="中标模块推送",type="中标模块推送")
	@ResponseBody
	public String pushToApp() throws Exception {
		PageData pa=new PageData();
		pa = getPageData();
		JSONObject obj = new JSONObject();
		
		String bt = pa.getString("Title");
		String nr = pa.getString("Text");
		
		String flag=pa.getString("IS_FREE");
		String result="";
		
		//存储消息内容
		PageData notif=new PageData();
 		notif.put("CREATE_DATE", new Date());
 		notif.put("TABLE_NAME","");
 		notif.put("OBJECT_ID","");
 		notif.put("STATUS","01"); 
 		notif.put("CONTENT",pa.getString("Text"));
		
		PushUtil pushApp=new PushUtil();
		Integer val = 0;
		
		PageData data=new PageData();
		PageData pronam=this.proBidderService.findByProXmName(pa);
		String arrStr = "";
	    //adroid、ios推送
		String id =pa.getString("ID");//用户ID（即 别名） 
		String ios =pa.getString("IOSID");//用户ID（即 别名）
		if( id != null && !"".equals(id) ){
			arrStr = id;
		}
		if( ios != null && !"".equals(ios) ){
			arrStr = ","+ios;
		}
		if( arrStr.length() > 0 ){
			String[] ids = ios.split(",");
			for (String USER_ID : ids) {
				notif.put("ID", get32UUID());
				notif.put("USER_ID", USER_ID);
				val += (Integer)sysNotifService.save(notif);
				data.put("USER_ID", pronam.get("PROJECT_MANAGER_ID"));
				data.put("JSID", USER_ID);
				String CONID="";
				/*查询当前会话*/
				PageData pros =this.proBidderService.findBycon(data);  
				if(pros != null){
					//会话存在   
					 PageData updata=new PageData();
					 updata.put("ID", pros.getString("ID"));
					 updata.put("USER_DEL", "01");
					 updata.put("JS_DEL", "01"); 
					 updata.put("time",new Date());
					 updata.put("CONTENT", pa.getString("Text"));
					 data.put("ID", pros.getString("ID"));
					 CONID=pros.getString("ID");
					 //判断项目编号是否一样
					 if((pros.getString("PRO_ID")).equals(pa.getString("PROJECT_ID"))){ 
					 	}else {
					 	//项目编号不一样，获取最新的项目编号，修改会话项目编号
						 updata.put("PRO_ID", pa.getString("PROJECT_ID"));
						} 
					 
					 if((pros.getString("USER_ID")).equals(pronam.get("PROJECT_MANAGER_ID"))){
						 updata.put("JS_COUNT","JS_COUNT"); 
					 }else if ((pros.getString("JSID")).equals(pronam.get("PROJECT_MANAGER_ID"))) {
						 updata.put("USER_COUNT", "USER_COUNT"); 
					 }
					 Object uphh=this.proBidderService.upcon(updata);
					 if(Integer.valueOf(uphh.toString()) >= 1){
							System.out.println("会话状态修改成功");  
						}else{
							System.out.println("会话状态修改失败"); 
						} 
				}else{
					//会话不存在
					data.put("ID",get32UUID());
					data.put("CREATE_DATE",new Date()); 
					data.put("PRO_ID", pa.getString("PROJECT_ID"));
					CONID=data.getString("ID");
					Object res=this.proBidderService.save(data);
					if(Integer.valueOf(res.toString()) >= 1){
						System.out.println("创建会话成功");  
					}else{
						System.out.println("创建会话失败"); 
					}  
				} 
				//保存消息
				data.put("ID",get32UUID());
				data.put("CON_ID",CONID);
				data.put("CREATE_DATE",new Date()); 
				data.put("CONTENT",pa.getString("Text")); 
				data.put("type","text"); 
				Object messres=this.proBidderService.savemess(data);
				System.out.println("保存消息："+messres.toString());
			}
			
			String jsonStr = "{'type':'chat','ID':'"+pronam.get("PROJECT_MANAGER_ID")+"','title':'"+bt+"','content':'"+nr+"'}";//透传内容
			TransmissionTemplate tmTemplates = PushUtil.transmissionTemplateDemo(bt,nr,jsonStr); 
			if(ids.length>9){//批量推送
				result=pushApp.pushToIosMore(tmTemplates,ids); 
			}else{//少量推送
				result=pushApp.pushToIosMany(tmTemplates,ids); 
			}
		}
		
		if( "ok".equals(result) || val > 0 ){//发送成功
			obj.put("status", 200); 
		}else{//发送失败
			obj.put("status", 400);
		}  
		return obj.toString();
	} 
} 
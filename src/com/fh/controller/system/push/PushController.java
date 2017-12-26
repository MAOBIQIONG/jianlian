package com.fh.controller.system.push;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.system.notification.SysNotificationService;
import com.fh.service.system.push.PushService;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.gexin.rp.sdk.template.LinkTemplate; 
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Controller
@RequestMapping({ "/push" })
public class PushController extends BaseController { 
	
	@Resource(name = "PushService")
	private PushService pushService;
	
	@Resource(name="sysNotificationService")
	private SysNotificationService sysNotifService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService;
	
	@RequestMapping
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("system/push/push");
		return mv;
	} 
	
	@RequestMapping({ "/querypushuser" })
	@ResponseBody
	public String querypushuser(Page page)throws Exception{
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
		 
		String real_name = pd.getString("REAL_NAME");
		String company_name = pd.getString("COMPANY_NAME");
		String vip_level = pd.getString("VIP_LEVEL");
		
		if ((real_name != null) && (!"".equals(real_name))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("REAL_NAME",real_name);
		} 
		if ((company_name != null) && (!"".equals(company_name))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("COMPANY_NAME",company_name);
		} 
		if ((vip_level != null) && (!"".equals(vip_level))) { 
			//String param=new String(contractName.getBytes("ISO-8859-1"),"UTF-8").trim();
			pd.put("VIP_LEVEL",vip_level);
		} 
		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> data = this.pushService.listByParam(page);
		Object  total = this.pushService.findCount(page).get("counts");//查询记录的总行数
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/pushToApp" })
	@ResponseBody
	public String pushToApp() throws Exception {
		PageData pa=new PageData();
		pa = getPageData();
		JSONObject obj = new JSONObject();
		
		String title = pa.getString("Title");
		String text = pa.getString("Text");
		String jsonStr = "{'type':'notice','title':'"+title+"','content':'"+text+"'}";//透传内容
		TransmissionTemplate tmTemplates = PushUtil.transmissionTemplateDemo(title,text,jsonStr);
		NotificationTemplate template = PushUtil.notificationTemplateDemo();
		template.setTitle(title);
		template.setText(text);
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
		
		//全部推送  （只存一条通知消息）
		if( "1".equals(flag) ){
			notif.put("ID", get32UUID());
		    notif.put("USER_ID", "jianlian");//默认为系统通知消息
		    val = (Integer)sysNotifService.save(notif);
		    result=pushApp.pushToAll(tmTemplates);
		    System.out.println("向全部用户推送："+result.toString());
		}else{
			//adroid推送
			String id =pa.getString("ID");//用户ID（即 别名）
			if( id !=null && !"".equals(id) ){
				String[] ids = id.split(",");
				for (String USER_ID : ids) {
					notif.put("ID", get32UUID());
					notif.put("USER_ID", USER_ID);
					val += (Integer)sysNotifService.save(notif);
				}
				
				if(ids.length>9){//批量推送
					result=pushApp.pushToMore(tmTemplates,ids); 
				}else{//少量推送
					result=pushApp.pushToMany(tmTemplates,ids); 
				}
			}
			
			//ios推送
			String ios =pa.getString("IOSID");//用户ID（即 别名）
			if( ios != null && !"".equals(ios) ){
				String[] ids = ios.split(",");
				for (String USER_ID : ids) {
					notif.put("ID", get32UUID());
					notif.put("USER_ID", USER_ID);
					val += (Integer)sysNotifService.save(notif);
				}
				
				if(ids.length>9){//批量推送
					result=pushApp.pushToIosMore(tmTemplates,ids); 
				}else{//少量推送
					result=pushApp.pushToIosMany(tmTemplates,ids); 
				}
			}
		}
		
		if( "ok".equals(result) || val > 0 ){//发送成功
			obj.put("status", 200); 
		}else{//发送失败
			obj.put("status", 400);
		}  
		return obj.toString();
	} 
	
	@RequestMapping({ "/appCleanPush" })
	@ResponseBody
	public String appCleanPush() throws Exception {
		PageData pa=new PageData();
		pa = getPageData();
		PageData p = pa.getObject("pa");
		String userid = p.getString("userid");
		String cid = p.getString("cid");
		
		PushUtil jk=new PushUtil();
	    boolean result = jk.cleanAlias(userid, cid);
	    
		return result+"";
	}
}


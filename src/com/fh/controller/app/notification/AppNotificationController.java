package com.fh.controller.app.notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppDicService; 
import com.fh.service.app.notification.AppNotificationService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appNotification" })
public class AppNotificationController extends AppController{

	@Resource(name = "appNotificationService")
	private AppNotificationService appNotifService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;  
	
	@Resource(name="appDicService")
	private AppDicService appDicService;  
	
	//系统通知列表获取接口
	@RequestMapping({ "/findNotification" })
	@ResponseBody
	public PageData findNotification() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		pa.put("totalSize",totalSize);
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize);
		
		List<PageData> list=this.appNotifService.listByParam(pa);
		PageData notzong=this.appNotifService.listByParamzong(pa);
		for (PageData pageData : list) {  
			if("project".equals(pageData.getString("TABLE_NAME"))&&pageData.getString("DESCRIPTION")!=null&&!"".equals(pageData.getString("DESCRIPTION"))){
				PageData page=new PageData();
				page.put("BIANMA",pageData.getString("DESCRIPTION"));
				PageData data=appDicService.findByBianma(page);
				pageData.put("DESCRIPTION", data.getString("NAME"));
			}
			pageData.put("CREATE_DATE",pageData.getString("CREATE_DATE")!=null&&pageData.getString("CREATE_DATE")!=""?pageData.getString("CREATE_DATE").substring(0,16):""); 
		} 
		//PageData counts=this.appNotificationService.countNotification(pa);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		map.put("counts", list.size());
		map.put("notzong", notzong);
		_result = AppUtil.ss(map, "01", "成功","true", null);
		return _result;
	}
	
	//系统通知列表获取接口
	@RequestMapping({ "/editNotification" })
	@ResponseBody
	public PageData editNotification() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		Object ob=this.appNotifService.edit(pa); 
		if(Integer.valueOf(ob.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "修改消息状态为已读成功！","true",null);
		}else{
			  _result = AppUtil.ss(null, "90", "修改消息状态为已读失败！","false",null);
		} 
		return _result;
	}
	
	//系统通知列表获取接口
	@RequestMapping({ "/findMessage" })
	@ResponseBody
	public PageData findMessage() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData msg=new PageData(); 
		msg.put("USER_ID",pa.getString("userid"));  
		msg.put("IS_READ","0");
		PageData TotalMsg=this.appNotifService.queryCountsByParam(msg);//查询某会员的消息数  
	    _result = AppUtil.ss(TotalMsg, "01", "成功！","true",null); 
		return _result;
	}
	
	
}

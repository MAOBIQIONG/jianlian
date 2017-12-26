package com.fh.controller.system.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.system.job.JobService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
/**
 * /获取七天后将要过期的会员用户
 * 并向用户推送消息
 * @author Administrator
 *
 */

public class CommJob2 {
	@Resource(name="jobService")
	private JobService jobService;
	@Resource(name="appusersService")
	private AppusersService appusersService;
	// job执行时间在applicationContext.xml 文件中进行设置
	private static int i=1;
	@SuppressWarnings({ })
	public void execute() throws Exception{
		//调用方法
		pushMsg();
	}
	
	/**
	 * 定时任务
	 * @throws Exception
	 */
	@RequestMapping({ "/pushMsg" })
	@ResponseBody
	public void pushMsg() throws Exception {
		System.out.println("---进入push1方法---");
		/*PageData pd = new PageData();
		List<PageData> jobl = jobService.finduserVip(pd);
		
		for (int i = 0; i < jobl.size(); i++) {
			String content="";
			if(jobl.get(i).get("DAYS").toString().equals("0")){
				content="尊敬的用户，您的权益："+jobl.get(i).get("LEVELNAME")+"即将于今日到期，请及时升级或续费。";
			}else{
				content="尊敬的用户，您的权益："+jobl.get(i).get("LEVELNAME")+"即将于"+jobl.get(i).get("DAYS")+"天后到期，请及时升级或续费。";
			}
			//新增系统消息状态
			Integer count=(Integer)this.jobService.saveMsg(content,jobl.get(i).getString("ID"));
			if(count > 0){
				//推送消息
		 		PageData pagedata = new PageData();
		 		pagedata.put("ID",jobl.get(i).getString("ID"));
				PageData ur = this.appusersService.queryById(pagedata);
		 		if(ur != null ){
		 			//推送审核消息
					NotificationTemplate template = null;
					if( ur.getString("PLATFORM") == "1" ){
						template = PushUtil.notificationTemplateDemo(); 
						template.setTitle("上海建联");
						template.setText(content);
					}
					TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content);
					PushUtil pushApp=new PushUtil();
					String alias = jobl.get(i).getString("ID");
					pushApp.pushToSingle(tmTemplate,template,alias); 
		 		}
			}
		}*/
		
	}
	
}
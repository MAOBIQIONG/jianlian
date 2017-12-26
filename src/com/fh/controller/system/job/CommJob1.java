package com.fh.controller.system.job;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.system.job.JobService;
import com.fh.util.PageData;
/*
 * 定时取消订单
 */
import com.fh.util.PushUtil;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
/**
 * 订单信息
 * @author Administrator
 *
 */
public class CommJob1 {
	@Resource(name="jobService")
	private JobService jobService;
	@Resource(name="appusersService")
	private AppusersService appusersService;
	
	// job执行时间在applicationContext.xml 文件中进行设置
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
		PageData pd = new PageData();
		//获取超24小时的订单
		/*List<PageData> jobl = jobService.queryJob(pd);
		for (int i = 0; i < jobl.size(); i++) {
			//更新订单状态
			String content="尊敬的用户，您有一条订单超24小时未付款已自动取消,如有疑问请致电客服.";
			pd.put("CONTENT", content);
			Integer count=(Integer)this.jobService.editOr(jobl.get(i));
			if(count > 0){
				//推送消息
		 		PageData pagedata = new PageData();
		 		pagedata.put("ID",jobl.get(i).getString("USER_ID"));
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
					String alias = jobl.get(i).getString("USER_ID");
					pushApp.pushToSingle(tmTemplate,template,alias); 
		 		}
			}
		}*/
		
	}
	
}
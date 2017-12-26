package com.fh.controller.app.company;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Apppersonal.AppFeedBackService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appFeedBack" })
public class AppFeedBackController extends BaseController {  
	
	@Resource(name = "appFeedBackService")
	private AppFeedBackService feedBackService;  
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	 
	//新增问题反馈信息
	 @RequestMapping(value={"/save"}) 
	 @ResponseBody
	 public PageData save()throws Exception{  
	     PageData _restult = new PageData(); 
		 PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		 String content=pa.getString("FEEDBACK_CONTENT");
		 if ((content != null) && (!"".equals(content))) { 
			content=new String(content.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("FEEDBACK_CONTENT",content);
		 } 
		 pa.put("ID",get32UUID());
		 pa.put("FEEDBACK_DATE",new Date());
		 pa.put("STATUS","01");//未审核
		 Object re = this.feedBackService.save(pa); 
		if (Integer.valueOf(re.toString()) == 1) { 
			_restult = AppUtil.ss(null,"01","成功", "true", "");
		} else {
			_restult = AppUtil.ss(null,"92","失败", "false", null);
		}
		return _restult;
	} 
} 
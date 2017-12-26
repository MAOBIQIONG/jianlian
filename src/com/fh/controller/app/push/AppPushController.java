package com.fh.controller.app.push;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.util.PageData;
import com.fh.util.PushUtil;

@Controller
@RequestMapping({ "/appPush" })
public class AppPushController extends AppController { 
	
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


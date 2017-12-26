package com.fh.controller.app;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppUsertokenService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.util.AppUtil;
import com.fh.util.ChineseToEnglish;
import com.fh.util.CommonUtil;
import com.fh.util.Emoji;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appAcctBind" })
public class AppUsertokenController extends AppController {

	@Resource(name = "appUsertokenService")
	private AppUsertokenService appUsertokenService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService;
		
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name = "appusersService")
	private AppusersService appUsersService;
	
	ChineseToEnglish  cte=new ChineseToEnglish();
	

	/**
	 * 绑定第三方账号
	 */
	@RequestMapping({ "/acctBind" })
	@ResponseBody
	public PageData updateAcountById() throws Exception {

		PageData _result = new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());

		Object obj = this.appUsertokenService.updateUsertoken(pa);
		if (Integer.valueOf(obj.toString()) >= 1) {
			_result = AppUtil.ss(null,"01","成功","true", null);
		} else {
			_result = AppUtil.ss(null,"91","失败", "false", null);
		}
		return _result;
	}

	/**
	 * 根据第三方账号返回token
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/ashfordToken" })
	@ResponseBody
	public PageData login() throws Exception {

		PageData _restult = new PageData();
		PageData pa =getPageData().getObject("pa"); //this.appCommonService.findtokenDetail(getPageData());
		String TYPE=pa.getString("TYPE"); 
		String openid=pa.getString("openid");  
		String CLIENT_ID=pa.getString("CLIENT_ID");
		Emoji ee=new Emoji();
		String NICK_NAME=ee.filterEmoji(pa.getString("NICK_NAME"),""); 
		String img=pa.getString("img"); 
		if ("qq".equals(TYPE)||"QQ".equals(TYPE)) {  
			pa.put("QQ",openid); 
		}else if ("WEIXIN".equals(TYPE)||"weixin".equals(TYPE)){
			pa.put("WEBCHAT",openid); 
		}else if("WEBO".equals(TYPE)||"webo".equals(TYPE)){
			pa.put("WEBO",openid); 
		} 
		PageData usertoken = this.appUsertokenService.checkThirdparty(pa); 
		if (usertoken != null) {//判断用户是否存在
			/**
			 * 判断用户是否绑定第三方账号 是新增token信息，否则更新token信息
			 */
			PageData utk = this.appUsertokenService.checkToken(usertoken);

			// 用户token信息
			PageData topd = CommonUtil.getToken(usertoken.get("userid"));
			if (utk != null) {
				this.appUsertokenService.editToken(topd);
				this.appUsertokenService.checkThirdparty(pa);
			} else {
				this.appUsertokenService.saveToken(topd);
				this.appUsertokenService.checkThirdparty(pa);
			} 
			PageData client=new PageData();
			client.put("ID",usertoken.get("userid"));
			client.put("CLIENT_ID",pa.getString("CLIENT_ID"));
			client.put("LAST_LOGIN_DATE",new Date());
			client.put("IMG",img);
			if ((NICK_NAME != null) && (!"".equals(NICK_NAME))) { 
				//NICK_NAME=new String(NICK_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
				client.put("NICK_NAME",NICK_NAME);
				client.put("REAL_NAME",NICK_NAME);
			    String chr=cte.getPingYinToHeaderChar(NICK_NAME);
			    client.put("VALERIE",chr);
			} 
			this.appUsersService.updateCidAndDate(client);
			PageData data=appUsersService.queryById(client);
			_restult = AppUtil.ss(data,"01","成功", "true",topd.get("token"));
		} else { 
			// 用户信息
			PageData pad = new PageData(); 
			//String NICK_NAME=pa.getString("NICK_NAME");   
			if ("qq".equals(TYPE)||"QQ".equals(TYPE)) {  
				pad.put("QQ",openid); 
			}else if ("WEIXIN".equals(TYPE)||"weixin".equals(TYPE)){
				pad.put("WEBCHAT",openid); 
			}else if("WEBO".equals(TYPE)||"webo".equals(TYPE)){
				pad.put("WEBO",openid); 
			} 
			if ((NICK_NAME != null) && (!"".equals(NICK_NAME))) { 
				//NICK_NAME=new String(NICK_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
				pad.put("NICK_NAME",NICK_NAME);
				pad.put("REAL_NAME",NICK_NAME);
			    String chr=cte.getPingYinToHeaderChar(NICK_NAME);
				pad.put("VALERIE",chr);
			} 
			pad.put("ID",this.get32UUID());
			pad.put("REGISTER_DATE",new Date());
			pad.put("LAST_LOGIN_DATE",new Date()); 
			pad.put("TOTAL_POINTS",0);
			pad.put("STATUS","01"); 
			pad.put("IS_VIP",0);  
			pad.put("VIP_LEVEL","02");
			pad.put("CLIENT_ID",CLIENT_ID);
			pad.put("VIP_LEVEL","02"); 
			pad.put("IMG",img);
			
			Object re = this.appUsersService.save(pad); 
			if(Integer.valueOf(re.toString()) >= 1){
				// 用户token信息
				PageData topd = CommonUtil.getToken(pad.getString("ID"));
				this.appUsertokenService.saveToken(topd);
				this.appUsertokenService.checkThirdparty(pad);
				PageData data=appUsersService.queryById(pad);
				_restult = AppUtil.ss(data, "01", "成功", "true", topd.get("token"));
		    }else{
		    	_restult = AppUtil.ss(null, "92", "非法请求","false",null);
		    }  
		}
		return _restult;
	}
}

package com.fh.controller.app;

import io.rong.RongCloud;
import io.rong.models.TokenResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;












import org.apache.shiro.crypto.hash.SimpleHash; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.AppUsertokenService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.clan.AppClanService;
import com.fh.service.app.company.AppcompanyService;
import com.fh.util.AppUtil;
import com.fh.util.ChineseToEnglish;
import com.fh.util.CommonUtil;
import com.fh.util.ChineseToEnglish;
import com.fh.util.PageData;
import com.fh.util.fileConfig;
import com.fh.util.mail.SimpleMailSender;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.http.IGtPush;

@Controller
@RequestMapping({ "/appLogin" })
public class AppLoginController extends AppController{
	public static String serverImgPath = fileConfig.getString("serverImgPath");
	
	String imAppKey = "vnroth0kvfo6o";//替换成您的appkey
	String appSecret = "fEPFfFSclDYo";//替换成匹配上面key的secret
	RongCloud rongCloud = RongCloud.getInstance(imAppKey, appSecret);

	@Resource(name = "appusersService")
	private AppusersService appUsersService;  
	
	@Resource(name = "appUsertokenService")
	private AppUsertokenService appUsertokenService;
	
	@Resource(name = "appcompanyService")
	private  AppcompanyService appComService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name = "appClanService")
	private AppClanService appClanService;
	
	ChineseToEnglish  cte=new ChineseToEnglish();
	
	//推送
	private static String appId = "f3VtMt3APs6DhHWZhk2if9";
	private static String appKey = "MV6xcaqkMX8HhGioGjZyS9";
	private static String masterSecret = "V7EkFMB3Ij5IucuZeDwMz8";
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
	   
	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/register" })
	@ResponseBody
	public PageData register() throws Exception { 
		PageData pa = new PageData();
		PageData _result = new PageData();
		pa = getPageData().getObject("pa");
		// 验证用户是否已注册
		PageData uc = this.appUsersService.findByUsername(pa);

		if (uc != null) {
			_result = AppUtil.ss(null, "97", "用户已存在", "false", null);
		} else { 
				// 用户信息
				PageData pad = new PageData();   
				String REAL_NAME=pa.getString("REAL_NAME");
				if ((REAL_NAME != null) && (!"".equals(REAL_NAME))) { 
					REAL_NAME=new String(REAL_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
					pad.put("REAL_NAME",REAL_NAME);
					pad.put("VALERIE", cte.getPingYinToHeaderChar(REAL_NAME));
				} 
				pad.put("ID",this.get32UUID());
				pad.put("REGISTER_DATE",new Date());
				pad.put("LAST_LOGIN_DATE",new Date());
				pad.put("USER_NAME",pa.getString("USER_NAME"));  
				String password = new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD")).toString();
				pad.put("PASSWORD",password);
				pad.put("TOTAL_POINTS",0);
				pad.put("STATUS","01"); 
				pad.put("IS_VIP",0);  
				pad.put("VIP_LEVEL","02");
				pad.put("PHONE",pa.getString("PHONE")); 
				pad.put("EMAIL",pa.getString("EMAIL")); 
				pad.put("COMPANY_ID",this.get32UUID());  
				pad.put("CLAN_ID",pa.getString("CLAN_ID"));  
				Object re = this.appUsersService.save(pad); 
				
				PageData com = new PageData();  
				com.put("ID",pad.getString("COMPANY_ID"));
				String COMPANY_NAME=pa.getString("COMPANY_NAME");
				if ((COMPANY_NAME != null) && (!"".equals(COMPANY_NAME))) { 
					COMPANY_NAME=new String(COMPANY_NAME.getBytes("ISO-8859-1"),"UTF-8").trim();
					com.put("COMPANY_NAME",COMPANY_NAME);
				} 
				com.put("CATEGORY_ID",pa.getString("CATEGORY_ID"));
				com.put("CREATE_BY",pad.getString("ID"));
				com.put("CREATE_DATE",new Date());
				com.put("MODIFY_DATE",new Date());
				Object obj= this.appComService.save(com); 
				PageData page=new PageData();
				page.put("CLAN_ID",pa.getString("CLAN_ID"));
				page.put("MEMBER_COUNT",1); 
				Object o=this.appClanService.updateaddClanCounts(page);
				if(Integer.valueOf(re.toString()) >= 1&&Integer.valueOf(obj.toString()) >= 1&&Integer.valueOf(o.toString()) >= 1){
					_result = AppUtil.ss(null, "01", "成功","true",null);
			    }else{
				     _result = AppUtil.ss(null, "90", "失败","false",null);
			    } 
		}

		return _result;
	}
	/**
	 * 用户登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/login" })
	@ResponseBody
	public PageData login() throws Exception {

		PageData _restult = new PageData(); 
		PageData pa = getPageData().getObject("pa");  
		String PASSWORD=pa.getString("PASSWORD"); 
		List<PageData> user = this.appUsersService.doCheckLogin(pa);
		if(user!=null&&user.size()>0){
			if(user.size()==1){
				String pwd=new SimpleHash("SHA-1",user.get(0).getString("USER_NAME"),PASSWORD).toString();
				if(pwd.equals(user.get(0).getString("PASSWORD"))){ 
					/**
					 * 判断用户是否是第一次登录 是新增token信息，否则更新token信息
					 */
					PageData utk = this.appUsertokenService.checkToken(user.get(0));
		
					// 用户token信息
					PageData topd = CommonUtil.getToken(user.get(0).get("userid"));
					if (utk != null) {
						this.appUsertokenService.editToken(topd);
					} else {
						this.appUsertokenService.saveToken(topd);
					}
					
					//设置推送别名
					String cid = pa.getString("CLIENT_ID");
					String alias = user.get(0).getString("ID");
					IGtPush push = new IGtPush(url, appKey, masterSecret);
			        IAliasResult queryAlias = push.queryAlias(appId, cid);
			        if( !alias.equals(queryAlias.getAlias()) ){
			      	    IAliasResult bindSCid = push.bindAlias(appId, alias, cid);
			            System.out.println("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
			        }
					
					PageData client=new PageData();
					client.put("ID",user.get(0).getString("ID"));
					client.put("CLIENT_ID",pa.getString("CLIENT_ID"));
					client.put("PLATFORM",pa.getString("PLATFORM"));
					client.put("LAST_LOGIN_DATE",new Date());
					this.appUsersService.updateCidAndDate(client);
					_restult = AppUtil.ss(user.get(0), "01", "成功", "true", topd.get("token"));
					
					// 获取 Token 方法 
					System.out.println("******************获取Token******************");
					System.out.println(user.get(0).getString("ID")+":id<>name:"+user.get(0).getString("USER_NAME")+"<>img:"+user.get(0).getString("IMG"));
					String imToken = user.get(0).getString("IMTOKEN");
					if( "".equals(imToken) || imToken == null ){
						String userImg = user.get(0).getString("IMG");
						if( "".equals(userImg) || userImg == null ){
							userImg = serverImgPath+"moren.jpg";
						}else{
							if( userImg.indexOf("http://") < 0 ){
								userImg = serverImgPath + userImg;
							}
						}
						TokenResult userGetTokenResult = rongCloud.user.getToken(user.get(0).getString("ID"), user.get(0).getString("USER_NAME"), userImg);
						System.out.println("getToken:  " + userGetTokenResult.toString());
						//{"code":200,"token":"epAucr0ij83nOHsvv/arq/JyA5t7cZ8ugGxcyZ8wTzF1xtrBw2UowY4WkN4BYgqgksBr2sll8jaVqtXK97HGXnf82pSwgsZ8NDYlorTgEHMnt+Itjvb8qPVRnD4+MlX5etDI9FWWXz8=","userId":"cd13b594858d4d38a973efb1150ff2c9"}
						if( "200".equals(userGetTokenResult.getCode()) || userGetTokenResult.getCode() == 200 ){
							PageData im = new PageData();
							im.put("ID",user.get(0).getString("ID"));
							im.put("IMTOKEN",userGetTokenResult.getToken());
							this.appUsersService.editImToken(im);
						}
					}
					
				}else{
					_restult = AppUtil.ss(null, "90", "密码错误!", "true", null);
				}
			}else{//多条记录
				_restult = AppUtil.ss(null, "90", "用户名或卡号或手机号重复，请联系管理员！", "true", null);
			} 
		} else {
			_restult = AppUtil.ss(null, "90", "用户不存在，请注册!", "true", null);
		}
		return _restult;
	}
	
	/**
	 * 忘记密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/forgetPW" })
	@ResponseBody
	public PageData forgetPW() throws Exception {

		PageData _restult = new PageData();
		PageData pa =getPageData().getObject("pa"); //this.appCommonService.findtokenDetail(getPageData()); 
		try{
			String email=pa.getString("EMAIL");
			List<PageData> lists=appUsersService.queryByEmail(pa);
			if(lists!=null&&lists.size()>0){
				SimpleMailSender sms = new SimpleMailSender();
				long time=(new Date()).getTime();
				/*String link="<a href='http://139.196.169.139:8082/jianlian/appLogin/findPassword'>重置密码</a>"; 
				String content="如果你想继续重置密码，请点击如下链接："+link;*/
				String link="<a href='http://jianlian.shanghai-cu.com/jianlian/appLogin/findPassword？times="+time+"'>重置密码</a>"; 
				String content="如果你想继续重置密码，请点击如下链接："+link+",此链接有效期为一天，过期无效！";
				sms.sendMail("上海建联",content,"2",email);
				_restult = AppUtil.ss(null, "01", "邮件发送成功，请注意查看！", "true",null);
				//sms.sendMail("测试","密码","1",email);
			} else{//该邮箱没有绑定
				_restult = AppUtil.ss(null, "90", "此邮箱没有绑定，请绑定邮箱或者使用手机号修改密码！", "true",null);
			} 
		}catch(Exception e){
			e.printStackTrace();
			_restult = AppUtil.ss(null, "90", "邮件发送失败！", "false", null);
		}
		return _restult; 
	}

	
	/**
	 * 重设密码（邮件）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/updatePW" })
	@ResponseBody
	public PageData updatePW() throws Exception { 
		PageData _result=new PageData();
			// 用户基本信息
			PageData pa = new PageData(); 
			pa=getPageData();
			String password=new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD")).toString();
			pa.put("PASSWORD", password);
			pa.put("USER_NAME",pa.getString("USER_NAME")); 
			Object re = this.appUsersService.editPassWord(pa);
		 
			if (Integer.valueOf(re.toString()) == 1) {
				_result.put("msg","密码修改完成！"); 
				_result.put("statusCode",200); 
			} else {
				_result.put("msg","密码修改失败！"); 
				_result.put("statusCode",""); 
			} 
		return _result;
	}
	
	/**
	 * 重设密码（手机）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/updatePassword" })
	@ResponseBody
	public PageData updatePassword() throws Exception { 
		PageData _result=new PageData();  
		PageData pa = getPageData().getObject("pa");
		// 验证用户手机
		List<PageData> uc = this.appUsersService.findByUserPHONE(pa);
		if (uc != null && uc.size()>0) { 
			String password=new SimpleHash("SHA-1",uc.get(0).getString("USER_NAME"),pa.getString("PASSWORD")).toString();
			pa.put("PASSWORD", password);
			pa.put("USER_NAME",uc.get(0).getString("USER_NAME")); 
			Object re = this.appUsersService.editPassWord(pa);
		 
			if (Integer.valueOf(re.toString()) == 1) { 
				_result = AppUtil.ss(null, "01", "密码修改成功！", "true",null); 
			} else {
				_result = AppUtil.ss(null, "90", "密码修改失败!", "true",null); 
			}  
		} else { 
			_result = AppUtil.ss(null, "90", "此用户不存在，请注册！", "true",null); 
		} 
		return _result;
	}
	
	  
	@RequestMapping({ "/findPassword" }) 
	public ModelAndView findPassword(Page page) throws Exception {
		ModelAndView mv = getModelAndView(); 
		PageData pa = new PageData(); 
		pa=getPageData();
		String time=pa.getString("times");
		if(time!=null&&!"".equals(time)){
			long times=Long.parseLong(time);
			 long length = (new Date()).getTime()- times;
			 long hours = (length % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
			  if(hours<=24){
				  mv.setViewName("app/findPassword");
			  }else{
				  mv.setViewName("app/missPage");
			  }
		}else{
			mv.setViewName("app/missPage");
		} 
		return mv;
	} 
	//手机号注册
	@RequestMapping({ "/savephonezhuce" })
	@ResponseBody
	public PageData savephonezhuce() throws Exception { 
		PageData pa = new PageData();
		PageData _result = new PageData();
		pa = getPageData().getObject("pa");
		// 验证用户手机是否已注册
		List<PageData> uc = this.appUsersService.findByUserPHONE(pa);

		if (uc != null && uc.size()>0) {
			_result = AppUtil.ss(null, "97", "用户已存在", "false", null);
		} else { 
				// 用户信息
				PageData pad = new PageData();   
//				String REAL_NAME=pa.getString(,"jianlian"+str);
//				String randomStr="";
//				for (int i = 0; i < 9; i++) {
//				int random=(int)(Math.random()*9);
//				if(randomStr.indexOf(random+"")!=-1){
//						i=i-1;
//					}else{
//						randomStr+=random;
//					}	
//				}
				ArrayList list = new ArrayList();
		        for (char c = 'a'; c <= 'z'; c++) {
		            list.add(c);
		        }
		        String str = "";
		        for (int i = 0; i < 5; i++) {
		            int num = (int) (Math.random() * 26);
		            str = str + list.get(num);
		        }
		        String REAL_NAME="上海建联"+str;
				pad.put("REAL_NAME",REAL_NAME);
//				pad.put("VALERIE", cte.getPingYinToHeaderChar(REAL_NAME));
				pad.put("VALERIE", cte.getPingYinToHeaderChar(REAL_NAME));
				pad.put("ID",this.get32UUID());
				pad.put("REGISTER_DATE",new Date());
				pad.put("LAST_LOGIN_DATE",new Date());
				pad.put("PHONE",pa.getString("PHONE"));   
				pad.put("USER_NAME",pa.getString("PHONE")); 
				String password = new SimpleHash("SHA-1",pa.getString("PHONE"),pa.getString("PASSWORD")).toString();
				pad.put("PASSWORD",password);
				pad.put("TOTAL_POINTS",0);
				pad.put("STATUS","01"); 
				pad.put("IS_VIP",0);  
				pad.put("VIP_LEVEL","02");
//				pad.put("CLAN_ID",pa.getString("CLAN_ID"));  
				Object re = this.appUsersService.savezhuce(pad); 
				
//				PageData page=new PageData();
//				page.put("CLAN_ID",pa.getString("CLAN_ID"));
//				page.put("MEMBER_COUNT",1); 
//				Object o=this.appClanService.updateaddClanCounts(page);
				if(Integer.valueOf(re.toString()) >= 1){
					_result = AppUtil.ss(null, "01", "成功","true",null);
			    }else{
				     _result = AppUtil.ss(null, "90", "失败","false",null);
			    } 
		}

		return _result;
	}
	
	/**
	 * 发送邮箱验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/sendMailCode" })
	@ResponseBody
	public PageData sendMailCode() throws Exception { 
		PageData _restult = new PageData();
		PageData pa =getPageData().getObject("pa"); //this.appCommonService.findtokenDetail(getPageData()); 
		try{
			String email=pa.getString("EMAIL");
			if(email!=null&&!"".equals(email)){
				SimpleMailSender sms = new SimpleMailSender();
				int code=(int)((Math.random()*9+1)*1000);
				String content=code+"";  
				sms.sendMail("上海建联",content,"1",email);
				System.out.println("邮箱发送成功！验证码："+code);
				_restult = AppUtil.ss(code, "01", "成功", "true",null); 
			}else{
				_restult = AppUtil.ss(null, "90", "失败！", "true",null);
			}  
		}catch(Exception e){
			e.printStackTrace();
			_restult = AppUtil.ss(null, "90", "失败！", "false", null);
		}
		return _restult; 
	}

	/**
	 * 根据手机号绑定邮箱
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/bindEmail" })
	@ResponseBody
	public PageData bindEmail() throws Exception { 
		PageData _restult = new PageData();
		PageData pa =getPageData().getObject("pa"); //this.appCommonService.findtokenDetail(getPageData()); 
		try{ 
			Object ob=this.appUsersService.bindEmail(pa);   
			if(Integer.valueOf(ob.toString()) >= 1){
				_restult = AppUtil.ss(null, "01", "成功","true",null);
		    }else{
		    	_restult = AppUtil.ss(null, "90", "失败","false",null);
		    } 
		}catch(Exception e){
			e.printStackTrace();
			_restult = AppUtil.ss(null, "90", "失败！", "false", null);
		}
		return _restult; 
	} 
	
	/*
	 * 登陆验证是否存在密码
	 * 
	 */ 
	@RequestMapping({ "/phonelogin" })
	@ResponseBody
	public PageData phonelogin() throws Exception {  
		PageData _result = new PageData();
		PageData data=new PageData();
		PageData pa = getPageData().getObject("pa");
		// 验证用户手机号，账号，卡号查找信息
		List<PageData> uc = this.appUsersService.doCheckLogin(pa);
		if(uc.size()==1){
			if (uc.get(0).getString("PASSWORD")!=null&&!"".equals(uc.get(0).getString("PASSWORD"))) {
				_result = AppUtil.ss("10", "01", "密码已存在！", "true", null);
			} else {  
				_result = AppUtil.ss("20", "01", "暂无密码！", "true", null);
			}
		}else{
			if(uc.size()==0){//说明手机号不存在 
				_result = AppUtil.ss("30", "01", "账号不存在，请注册！", "true", null);
			}else{//存在多条记录 
				_result = AppUtil.ss("40", "01", "账号重复，请联系客服！", "true", null);
			}
		} 
		return _result;
	}
	
	/*
	 * 第一次登陆修改密码
	 * 
	 */ 
	@RequestMapping({ "/setPass" })
	@ResponseBody
	public PageData setPass() throws Exception {  
		PageData _result = new PageData();
		PageData data=new PageData();
		PageData pa = getPageData().getObject("pa");
		List<PageData> user = this.appUsersService.doCheckLogin(pa);
		String password = new SimpleHash("SHA-1",pa.getString("USER_NAME"),pa.getString("PASSWORD")).toString();
		pa.put("PASSWORD",password);
		
		/**
		 * 判断用户是否是第一次登录 是新增token信息，否则更新token信息
		 */
		PageData utk = this.appUsertokenService.checkToken(user.get(0));

		// 用户token信息
		PageData topd = CommonUtil.getToken(user.get(0).get("userid"));
		if (utk != null) {
			this.appUsertokenService.editToken(topd);
		} else {
			this.appUsertokenService.saveToken(topd);
		}
		
		//设置推送别名
		String cid = pa.getString("CLIENT_ID");
		String alias = user.get(0).getString("ID");
		IGtPush push = new IGtPush(url, appKey, masterSecret);
        IAliasResult queryAlias = push.queryAlias(appId, cid);
        if( !alias.equals(queryAlias.getAlias()) ){
      	    IAliasResult bindSCid = push.bindAlias(appId, alias, cid);
            System.out.println("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
        }
        PageData client=new PageData();
		client.put("ID",user.get(0).getString("ID"));
		client.put("CLIENT_ID",pa.getString("CLIENT_ID"));
		client.put("PLATFORM",pa.getString("PLATFORM"));
		client.put("LAST_LOGIN_DATE",new Date());
		this.appUsersService.updateCidAndDate(client);
		// 修改密码
		 Object ob = this.appUsersService.editPassWord(pa);
		 if(Integer.valueOf(ob.toString()) >= 1){
			 _result = AppUtil.ss(user.get(0), "01", "成功","true",topd.get("token"));
	     }else{
	    	 _result = AppUtil.ss(user.get(0), "90", "失败","false",null);
	     }  
		return _result;
	}
	public static void main(String[] args) {
		String name="30160800023";
		String pwd="888888";
		String password = new SimpleHash("SHA-1",name,pwd).toString();
		System.out.println(password);
	}
}

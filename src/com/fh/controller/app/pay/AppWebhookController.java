package com.fh.controller.app.pay;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal; 
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.beecloud.BCCache;
import cn.beecloud.BeeCloud; 

import com.fh.controller.base.BaseController;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.activity.AppActivityService;
import com.fh.service.app.pay.AppOrdersService;
import com.fh.service.app.pay.AppPayService;
import com.fh.service.app.pay.AppUpgradeService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.fileConfig;
import com.fh.util.noticePushutil;

@Controller
@RequestMapping(value="/appWebhook")
public class AppWebhookController extends BaseController {  

	@Resource(name="appPayService")
	private AppPayService appPayService;
	
	@Resource(name="appOrdersService")
	private AppOrdersService appOrdersService;  
	
	@Resource(name = "appusersService")
	private AppusersService appusersService; 
	
	@Resource(name = "appActivityService")
	private AppActivityService appActService;  
	
	@Resource(name = "appUpgradeService")
	private AppUpgradeService appUpService; 
	
	private static String appID="34b9869a-3ba6-41c1-a6d3-aae1fb3c0049";
	private static String testSecret="eec0b8ba-ce5a-46a5-82e7-b192b0f396c4";
	private static String appSecret="a5982ad9-400f-46d1-981f-b27acb7fd6f5";
	private static String masterSecret="ad707adb-aa8a-44f4-8f16-4c9500bd3f9e"; 
	
	public static String noticeText5 = fileConfig.getString("noticeText5");
	public static String noticeText9 = fileConfig.getString("noticeText9");
	public static String noticeText10 = fileConfig.getString("noticeText10");
	    Logger log = Logger.getLogger(this.getClass());

	    boolean verify(String sign, String text, String key, String input_charset) {
	        text = text + key;
	        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
	        log.info("mysign:" + mysign);

	        long timeDifference = System.currentTimeMillis() - Long.valueOf(key);
	        log.info("timeDifference:" + timeDifference);
	        if (mysign.equals(sign) && timeDifference <= 300000) {
	            return true;
	        } else {
	            return false;
	        }
	    }

	    boolean verifySign(String sign, String timestamp) {
	        log.info("sign:" + sign);
	        log.info("timestamp:" + timestamp);

	        return verify(sign, BCCache.getAppID() + BCCache.getAppSecret(),
	                timestamp, "UTF-8");

	    }

	    byte[] getContentBytes(String content, String charset) {
	        if (charset == null || "".equals(charset)) {
	            return content.getBytes();
	        }
	        try {
	            return content.getBytes(charset);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
	        }
	    }

	    /**
		 * 支付成功后回调地址
		 * @throws Exception 
		 */
		@RequestMapping({ "/success" })
		@ResponseBody
		public String success(HttpServletRequest request,HttpServletResponse response) throws Exception{
			BeeCloud.registerApp(appID,testSecret,appSecret,masterSecret); 
		    StringBuffer json = new StringBuffer();
		    String line = null;  
		   try {
		        request.setCharacterEncoding("utf-8"); 
		        BufferedReader reader = request.getReader();
		        while ((line = reader.readLine()) != null) {
		            json.append(line);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }  
		    if(json.toString()!=null&&!"".equals(json.toString())){
		    	JSONObject jsonObj = JSONObject.fromObject(json.toString());
		    	System.out.println(jsonObj);
			    String sign =jsonObj.getString("sign");
			    String timestamp =jsonObj.getString("timestamp");
			    boolean status = verifySign(sign, timestamp); 
			    if (status) { //验证成功  
			    	String transaction_id =jsonObj.getString("transaction_id");  
			    	PageData pa =new PageData(); 
			    	pa.put("ID",transaction_id);
			    	System.out.println(pa.getString("ID"));
			    	PageData  orders=appOrdersService.findById(pa);  
			    	Object rel=null;
			    	if(orders!=null){ 
			    		BigDecimal  price=(BigDecimal) orders.get("PRICE");//数据库中订单的金额   
			    		String transaction_fee =jsonObj.getString("transaction_fee"); //beecloud平台订单的金额
			    		BigDecimal result=price.multiply(new BigDecimal(100));//将price乘以100，变为分 
			   		    int amount=result.intValue(); 
				    	if(String.valueOf(amount).equals(transaction_fee)){//订单金额匹配
				    		orders.put("STATUS","03");
				    		orders.put("DATE",new Date());
				    		orders.put("PAY_TYPE",jsonObj.getString("sub_channel_type"));
				    		Object re=appOrdersService.edit(orders);
				    		System.out.println("re="+Integer.valueOf(re.toString()));
				    		if(Integer.valueOf(re.toString()) >= 1){//修改订单状态成功！
				    			if("01".equals(orders.getString("TYPE"))){//支付会费 
				    				PageData up=new PageData();
				    				up.put("ORDER_ID",transaction_id);
				    				up.put("STATUS","01");
				    				rel=appUpService.edit(up); 
			 						if(Integer.valueOf(rel.toString()) < 1){
			 							//获取notice.properties noticeText5内容。
			 					       String vv3=new String(noticeText5.getBytes("ISO-8859-1"),"UTF-8");
				 					    String notice1=vv3;
				 				        noticePushutil notutil=new noticePushutil();
				 				        notutil.toNotice(notice1); 
			 							System.out.println("fail8");
					    				return "fail"; 
			 						}  
				    			}else if("02".equals(orders.getString("TYPE"))){//支付活动费用
				    				//修改活动参与人数--新增活动参与人数 
				    				PageData actUser=new PageData(); 
				    				actUser.put("ID",get32UUID());
				    				actUser.put("STATUS","01");
				    				actUser.put("APPLY_DATE", new Date()); 
				    				actUser.put("USER_ID",orders.getString("USER_ID"));
				    				actUser.put("ACT_ID",orders.getString("OBJECT_ID")); 
			 						Object obj=appActService.addActUser(actUser);//保存活动参与人信息
			 						if(Integer.valueOf(obj.toString()) >= 1){
			 							PageData act=new PageData();
			 							act.put("ACTIVITY_ID",orders.getString("OBJECT_ID")); 
			 			 				rel=appActService.updateCounts(act); //修改参与活动的人数
			 			 				//获取notice.properties noticeText9内容。
			 					       String vv3=new String(noticeText9.getBytes("ISO-8859-1"),"UTF-8");
				 					    String notice1=vv3;
				 				        noticePushutil notutil=new noticePushutil();
				 				        notutil.toNotice(notice1); 
							    		System.out.println("rel="+Integer.valueOf(rel.toString()));
			 						}else{
			 							System.out.println("fail7");
					    				return "fail"; 
			 						}
				    			}else if ("03".equals(orders.getString("TYPE"))) {//会员续费
				    				PageData up=new PageData();
				    				up.put("ORDER_ID",transaction_id);
				    				up.put("STATUS","01");
				    				rel=appUpService.edit(up); 
			 						if(Integer.valueOf(rel.toString()) < 1){
			 							//获取notice.properties noticeText10内容。
				 					       String vv3=new String(noticeText10.getBytes("ISO-8859-1"),"UTF-8");
					 					    String notice1=vv3;
					 				        noticePushutil notutil=new noticePushutil();
					 				        notutil.toNotice(notice1); 
			 							System.out.println("fail8");
					    				return "fail"; 
			 						}
								} 
				    			if(Integer.valueOf(rel.toString())>=1){//修改会员等级或者修改活动参与信息成功
					     			PageData data=new PageData(); 
					    			data.put("ID",get32UUID());
					    			data.put("ORDER_ID",orders.getString("ID")); 
					    			data.put("PRICE",orders.get("PRICE")); 
					    			data.put("DESCRIPTION",orders.getString("EVENT"));
					    			data.put("PAY_TYPE",jsonObj.getString("sub_channel_type"));
					    			data.put("DATE",new Date()); 
					    			data.put("STATUS","03");//支付成功
					    			data.put("USER_ID",orders.getString("USER_ID")); 
					    			Object ob=appPayService.save(data); 
					    			System.out.println("ob="+Integer.valueOf(ob.toString()));
					    			if(Integer.valueOf(ob.toString()) >= 1){  
					    				return "success"; 
					    			}else{
					    				System.out.println("fail");
					    				return "fail"; 
					    			}
				    			}else{ 
				    				System.out.println("fail1");
					    			return "fail";
					    		}
				    		}else{
				    			System.out.println("fail2");
				    			return "fail";
				    		} 
				    	}else{
				    		System.out.println("fail3");
				    		return "fail";
				    	}
			    	}else{
			    		System.out.println("fail4");
			    		return "fail";
			    	} 
			    } else { //验证失败
			    	System.out.println("fail5");
			    	return "fail";
			    }
		    }else{
		    	System.out.println("fail6");
		    	return "fail";
		    } 
		} 
	} 
package com.fh.controller.app;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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
import com.fh.service.app.pay.AppOrdersService;
import com.fh.service.app.pay.AppPayService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/appWebhook1")
public class AppPayssssController extends BaseController {  

	@Resource(name="appPayService")
	private AppPayService appPayService;
	
	@Resource(name="appOrdersService")
	private AppOrdersService appOrdersService;  
	
	@Resource(name = "appusersService")
	private AppusersService appusersService; 
	
	private static String appID="34b9869a-3ba6-41c1-a6d3-aae1fb3c0049";
	private static String testSecret="eec0b8ba-ce5a-46a5-82e7-b192b0f396c4";
	private static String appSecret="a5982ad9-400f-46d1-981f-b27acb7fd6f5";
	private static String masterSecret="ad707adb-aa8a-44f4-8f16-4c9500bd3f9e"; 
	
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
	    //BeeCloud.registerApp("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719",  "4bfdd244-574d-4bf3-b034-0c751ed34fee", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c");
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
		        //out.println("success"); //请不要修改或删除
		    	String transaction_id =jsonObj.getString("transaction_id");
		    	String transaction_fee =jsonObj.getString("transaction_fee"); 
		    	PageData pa =new PageData(); 
		    	pa.put("ID",transaction_id);
		    	PageData  orders=appOrdersService.findById(pa);
		    	if(orders!=null){
		    		String price=orders.get("PRICE")+"";
			    	if(transaction_fee.equals(price)){//订单金额匹配
			    		orders.put("STATUS","03");
			    		orders.put("DATE",new Date());
			    		Object re=appOrdersService.edit(orders);
			    		pa.put("VIP_LEVEL","01");
			    		pa.put("IS_VIP","1");
			    		Object rel=appusersService.edit(pa); 
			     	   if(Integer.valueOf(re.toString()) >= 1&&Integer.valueOf(rel.toString())>=1){
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
			    			if(Integer.valueOf(ob.toString()) >= 1){
			    				response.setStatus(200);
			    				//response.getWriter().println("success"); 
			    			}else{
			    				response.setStatus(500);
			    				//response.getWriter().println("fail"); 
			    			} 
			    		}else{
			    			response.setStatus(500);
			    		}
			    	}else{
			    		response.setStatus(500); 
			    	}
		    	}else{
		    		response.setStatus(500);
		    	} 
		    } else { //验证失败
		    	response.setStatus(500);
		    }
	    }else{
	    	response.setStatus(500); 
	    }
	    return "success";
	} 
}

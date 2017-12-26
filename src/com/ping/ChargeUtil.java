package com.ping;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.util.PageData; 
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;

public class ChargeUtil {

	/**
	 * pingpp 管理平台对应的 API key
	 */
	public static String apiKey = PingConfig.getString("ping.apikey");
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static String appId = PingConfig.getString("ping.appid");
	
	 // 你生成的私钥路径
    private final static String privateKeyPath =PingConfig.getString("ping.privateKeyPath");
	
	private static ChargeUtil chargeUtil; 
	
	public static ChargeUtil getInstance(){
		if(chargeUtil == null){ 
			 // 设置 API Key
	        Pingpp.apiKey = apiKey;
	         // 设置私钥路径，用于请求签名
	        Pingpp.privateKeyPath = privateKeyPath; 
			chargeUtil = new ChargeUtil();
		} 
		return chargeUtil;
	}  
    
	 /**
     * 创建 Charge
     * 
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return Charge
     */
    public Charge createCharge(PageData pd) {
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", pd.get("PRICE"));
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "支付测试");
        chargeMap.put("body", "支付测试");
        chargeMap.put("order_no", pd.get("ID"));
        chargeMap.put("channel",  pd.get("CHANNEL"));
        chargeMap.put("client_ip", "127.0.0.1"); 
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app); 
        
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("success_url", pd.get("success_url"));
        chargeMap.put("extra", extra);
        try {
          //发起交易请求
          charge = Charge.create(chargeMap);
          // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
          //String chargeString = charge.toString(); 
        } catch (PingppException e) {
          e.printStackTrace();
        }
        return charge;
    } 
    
    /**
     * 创建 Charge (微信公众号)
     *
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return Charge
     */
    public Charge createChargeWithOpenid(PageData pd) {
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>(); 
        chargeMap.put("amount", pd.get("PRICE"));
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "支付测试");
        chargeMap.put("body", "支付测试");
        chargeMap.put("order_no", pd.get("ID")); // 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeMap.put("channel",  pd.get("CHANNEL"));// 支付使用的第三方支付渠道取值，请参考：https://www.pingxx.com/api#api-c-new
        chargeMap.put("client_ip", "127.0.0.1");  // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);

        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("open_id",pd.getString("openId"));// 用户在商户微信公众号下的唯一标识，获取方式可参考 WxPubOAuthExample.java
        chargeMap.put("extra", extra);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            String chargeString = charge.toString();
            System.out.println(chargeString);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }


    /**
     * 查询 Charge
     * 
     * 该接口根据 charge Id 查询对应的 charge 。
     * 参考文档：https://pingxx.com/document/api#api-c-inquiry
     * 
     * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
     * 参考文档： https://pingxx.com/document/api#api-expanding
     * @param id
     */ 
    public Charge retrieve(String id) {
        Charge charge = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            List<String> expand = new ArrayList<String>();
//            expand.add("app");
//            params.put("expand", expand);
            charge = Charge.retrieve(id, params);
            if (charge.getApp() instanceof App) {
                //App app = (App) charge.getApp();
                // System.out.println("App Object ,appId = " + app.getId());
            } else {
                // System.out.println("String ,appId = " + charge.getApp());
            }
            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }

        return charge;
    }

    /**
     * 分页查询Charge
     * 
     * 该接口为批量查询接口，默认一次查询10条。
     * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
     * 
     * 该接口同样可以使用 expand 参数。
     * @return
     */
    public ChargeCollection all() {
        ChargeCollection chargeCollection = null;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("limit", 3);

//增加此处设施，刻意获取app expande 
//        List<String> expande = new ArrayList<String>();
//        expande.add("app");
//        chargeParams.put("expand", expande);

        try {
            chargeCollection = Charge.all(chargeParams);
            System.out.println(chargeCollection);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return chargeCollection;
    }
	
}

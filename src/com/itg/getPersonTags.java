package com.itg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class getPersonTags {
	List<String> cidList = new ArrayList<String>();
		
	static String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
	static String appkey = "0hCg34H6j77XQEZ29VX0n3";
	static String masterSecret = "Kx7lRbyk2P7kTaa9zBCInA";

	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	//使用https的域名
  //static String host = "https://api.getui.com/apiex.htm";

	public static void main(String[] args) throws Exception {
		IGtPush push = new IGtPush(host, appkey, masterSecret);
		push.connect();
		getUserStatus();
	}

	public static void getUserStatus() {
		IGtPush push = new IGtPush(host, appkey, masterSecret);
		IQueryResult abc = push.getPersonaTags(appId);
		System.out.println(abc.getResponse());
	}
}

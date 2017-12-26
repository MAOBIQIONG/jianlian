package com.itg;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;

public class getUserTag {

	static String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
	static String appkey = "0hCg34H6j77XQEZ29VX0n3";
	static String master = "Kx7lRbyk2P7kTaa9zBCInA";
	static String CID = "";
	static String Alias = "";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//	static String host = "https://api.getui.com/apiex.htm";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getUserTags();
	}

	public static void getUserTags() {
		IGtPush push = new IGtPush(host, appkey, master);
		IPushResult result = push.getUserTags(appId, CID);
		System.out.println(result.getResponse().toString());
	}
}

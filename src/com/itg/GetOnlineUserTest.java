package com.itg;


import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetOnlineUserTest {
	public static String ip = new String("http://sdk.open.api.igexin.com/apiex.htm");
//	public static String ip = new String("https://api.getui.com/apiex.htm");
	public static void testGetOnlineUser() {
		String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
		String appkey = "0hCg34H6j77XQEZ29VX0n3";
		final String masterSecret = "Kx7lRbyk2P7kTaa9zBCInA";
		String groupName = "个推abc";
		
		IGtPush push = new IGtPush(ip, appkey, masterSecret);
		
		IQueryResult queryResult = push.getLast24HoursOnlineUserStatistics(appId);
		System.out.println(String.valueOf(queryResult.getResponse()));
	}
	
	public static void main(String[] args) {
		testGetOnlineUser();
	}
}

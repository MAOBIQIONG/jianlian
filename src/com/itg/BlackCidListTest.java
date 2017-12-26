package com.itg;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;

public class BlackCidListTest {
	public static String ip = new String("http://sdk.open.api.igexin.com/apiex.htm");
//	public static String ip = new String("https://api.getui.com/apiex.htm");
	public static void testBlackCidListTest() {
		//int maxLen = 1001;
		List<String> cidList = new ArrayList<String>();
		String cid = "19880102";
		final String masterSecret = "Kx7lRbyk2P7kTaa9zBCInA";
		String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
		String appkey = "0hCg34H6j77XQEZ29VX0n3";
		
//		for(int i = 0;i < maxLen;i++) {
			cidList.add(cid);
//		}
		IGtPush push = new IGtPush(ip, appkey, masterSecret);
		
		IPushResult pushResult1 = push.addCidListToBlk(appId, cidList);
		System.out.println("绑定结果：" + pushResult1.getResultCode() + "错误码:"
				+ pushResult1.getResponse());
	}
	public static void testBlackCidListTest2() {
		List<String> cidList = new ArrayList<String>();
		String cid = "19880102";
		final String masterSecret = "Kx7lRbyk2P7kTaa9zBCInA";
		String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
		String appkey = "0hCg34H6j77XQEZ29VX0n3";
		
		cidList.add(cid);
		IGtPush push = new IGtPush(ip, appkey, masterSecret);
		
		IPushResult pushResult2 = push.restoreCidListFromBlk(appId, cidList);
		System.out.println("绑定结果：" + pushResult2.getResultCode() + "错误码:"
				+ pushResult2.getResponse());
	}
	
	public static void main(String[] args) {
		testBlackCidListTest();
		testBlackCidListTest2();
	}
}

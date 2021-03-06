package com.itg;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IIGtQuery;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class testSetTagList {
	static String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
	static String appkey = "0hCg34H6j77XQEZ29VX0n3";
	static String master = "Kx7lRbyk2P7kTaa9zBCInA";
	static String CID = "";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//	static String host = "https://api.getui.com/apiex.htm";

	public static void testSetTag(IIGtQuery push, String appId, String cid,
			List<String> tagList) {
		push.setClientTag(appId, cid, tagList);
	}

	public static void main(String[] args) throws Exception {
		setTag();
		System.out.println(CID);
		// System.out.println(Integer.MAX_VALUE);
	}

	public static void testSetTagList() throws Exception {

		IIGtQuery push = new IGtPush(host, appkey, master);

		List<String> tagList = new ArrayList<String>();
		tagList.add("set888");
		// tagList.add("set3");
		testSetTag(push, appId, CID, tagList);
	}

	public static void setTag() {
		IGtPush push = new IGtPush(host, appkey, master);
		List<String> tagList = new ArrayList<String>();
				tagList.add(String.valueOf("卡卡"));
				tagList.add(host);
				tagList.add("CCCCCCCCCCCCCC");
		IQueryResult ret = push.setClientTag(appId, CID, tagList);
		System.out.println(ret.getResponse().toString());
	}

	public static void getUserTag() {
		IGtPush push = new IGtPush(host, appkey, master);
		IPushResult ret = push.getUserTags(appId, CID);
		System.out.println(ret);
	}
}

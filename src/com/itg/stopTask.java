package com.itg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class stopTask {
	static String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
	static String appkey = "0hCg34H6j77XQEZ29VX0n3";
	static String master = "Kx7lRbyk2P7kTaa9zBCInA";
	static String TaskId="";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//	static String host = "https://api.getui.com/apiex.htm";

	public static void main(String[] args) throws IOException,
			InterruptedException {
		stopTask();
	}

	public static void stopTask() throws IOException, InterruptedException {
			IGtPush push = new IGtPush(host, appkey, master);
			boolean result = push.stop(TaskId);
			System.out.println(result);
	}
}
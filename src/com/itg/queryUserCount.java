package com.itg;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;

public class queryUserCount {
	
	public static void main(String[] args) {
		String host = "http://sdk.open.api.igexin.com/apiex.htm";
	  //使用https的域名
    // String host = "https://api.getui.com/apiex.htm";
		
		String appId = "JVRHJnTO1Z8aFM4XM0W0a1";
		String appkey = "0hCg34H6j77XQEZ29VX0n3";
		String masterSecret = "Kx7lRbyk2P7kTaa9zBCInA";
		
		IGtPush gtpush = new IGtPush(host,appkey, masterSecret);
		AppConditions conditions = new AppConditions();
		//新增机型
		List<String> phoneTypes = new ArrayList<String>();
		phoneTypes.add("ANDROID");
		conditions.addCondition(AppConditions.PHONE_TYPE, phoneTypes);
		
		//新增地区
		List<String> regions = new ArrayList<String>();
		regions.add("浙江省");
		conditions.addCondition(AppConditions.REGION, regions);
		
		//新增tag
		List<String> tags = new ArrayList<String>();
		tags.add("haha");
		conditions.addCondition(AppConditions.TAG, tags);
				
		//查询可推送的用户画像
		IQueryResult personaTagResult = gtpush.getPersonaTags(appId);
		System.out.println(personaTagResult.getResponse());
		
		//新增用户画像
		//工作
		List<String> jobs = new ArrayList<String>();
		jobs.add("0102");
		jobs.add("0110");
		conditions.addCondition("job", jobs);	
				
		//年龄
		List<String> age = new ArrayList<String>();
		age.add("0000");
		conditions.addCondition("age", age);
				
		//查询
		IQueryResult  result = gtpush.queryUserCount(appId, conditions);
		System.out.println(result.getResponse());
	}

}

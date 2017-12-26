package com.fh.util;

import org.springframework.context.ApplicationContext;

public class JpushMessage {
	
	public static String NOTICE_PUBLISH = "通知：%s";
	
	public static String HOMEWORK_PUBLISH = "%s发布了作业，马上去看看！";
	
	public static String LEAVE_PUBLISH = "%s刚刚提交了请假申请，马上去看看！";
	
	public static String LEAVE_CONFIRM = "%s已经确认了您%s的请假申请！";
	
	public static String LEAVE_SEND_BACK = "%s已经退回"
			+ "了您%s的请假申请！";
	public static String YMTX = "不知不觉%s 已经%s啦, 提醒您不要忘记给宝宝接种%s哦！";

	public static String getMessage(String message, String... params) {
		if(message != null){
			return String.format(message, params);
		}
		return null;
	}
	public static void main(String[] args){
		String result = getMessage(NOTICE_PUBLISH,"hahah");
		//System.out.println(result);
	}
}

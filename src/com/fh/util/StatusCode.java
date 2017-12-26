package com.fh.util;

public class StatusCode {
	public class ORDER_STATUS {
		// 待确认
		public static final String CODE_00 = "00";
		
		//待支付
		public static final String CODE_01 = "01";
		
		//待入住
		public static final String CODE_02 = "02";
		
		//入住中
		public static final String CODE_03 = "03";
		
		//待评论
		public static final String CODE_20 = "20";
		
		// 已完成
		public static final String CODE_21 = "21";
		
		//取消
		public static final String CODE_90 = "90";
		
		//已拒绝
		public static final String CODE_91 = "91";
	}
	
	public class ORDER_ROOM_STATUS{
		//待确认
		public static final String CODE_00 = "00";
		
		//同意
		public static final String CODE_10 = "10";
		
		//不同意
		public static final String CODE_90 = "90";
		
		//取消
		public static final String CODE_91 = "91";
	}
	

	public class ORDER_CHANGE_STATUS {
		// 待确认
		public static final String CODE_00 = "00";
		
		// 待支付
		public static final String CODE_01 = "01";
		
		// 已完成
		public static final String CODE_20 = "20";
		
		//取消修改
		public static final String CODE_90 = "90";
		
	}


	public class ORDER_CHANGE_ROOM_STATUS {
		// 待确认
		public static final String CODE_00 = "00";
		
		// 申请取消
		public static final String CODE_01 = "01";
		
		// 同意
		public static final String CODE_10 = "10";
		
		//房客确认
		public static final String CODE_11 = "11";
		
		//不同意
		public static final String CODE_90 = "90";
		
	}

	public class ORDER_CANCEL_STATUS {
		// 代房东确认
		public static final String CODE_00 = "00";
		
		// 待房客确认价格
		public static final String CODE_01 = "01";
		
		// 已确认（待退款）
		public static final String CODE_11 = "11";
		
		// 已完成
		public static final String CODE_20 = "20";
		
		// 已撤销
		public static final String CODE_90 = "90";
	}
}

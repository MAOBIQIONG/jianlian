package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isEmail(String mobiles) {
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}

/*
 * Location: F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\ Qualified Name:
 * com.fh.util.ValidateUtil JD-Core Version: 0.6.2
 */
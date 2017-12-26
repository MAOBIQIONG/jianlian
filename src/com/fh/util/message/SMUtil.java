package com.fh.util.message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.config.ConfConfig;
import com.fh.util.Logger;

public class SMUtil {
	private static String url = ConfConfig.getString("sms.url");

	 private static String account = ConfConfig.getString("sms.account");
	 private static String pswd = ConfConfig.getString("sms.pswd");
	// private static String account = "hzpnkj";
	// private static String pswd = "HZpnkj07";
	// private static String account = "shchaold";
	// private static String pswd = "SHchaold12";

	public static boolean sendSM(String mobile, String msg) {
		try {
			msg = URLEncoder.encode(msg, "utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			String result = getHttp(url + "?account=" + account + "&pswd=" + pswd + "&mobile=" + mobile + "&msg=" + msg
					+ "&needstatus=true");
			
		} catch (Exception e) {
			Logger.getLogger(SMUtil.class).error(e);
			return false;
		}
		return true;
	}

	public static String getHttp(String _url) throws Exception {
		URL localURL = new URL(_url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}
}

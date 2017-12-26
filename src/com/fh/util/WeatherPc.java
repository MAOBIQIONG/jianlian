package com.fh.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherPc {
	static String url_shanghai = "http://www.weather.com.cn/weather1d/101020100.shtml";

	public static String getHtml(String urlString) {
		StringBuffer res = new StringBuffer("");
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url
					.openConnection();
			conn.setRequestMethod("GET");
			// urlConnection.setReadTimeout(1000 * 60 * 10); //设置读取超时
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			conn.setRequestProperty("Accept-Language", "zh-cn");
			// urlConnection.setRequestProperty("Connection", "close");
			// //不进行持久化连接
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	public static String getData(String city) {
		if ("上海".equals(city)) {
			org.jsoup.nodes.Document document = Jsoup
					.parse(getHtml(url_shanghai));
			String value = document.getElementById("hidden_title").attr("value");
			//System.out.println(value);
			value =  value.replace("08时", "");
			return value;
		}
		return null;
	}

	public static void main(String[] args) {
		// //System.out.println(" 内存信息 :" + toMemoryInfo());
		getData("上海");
	}

	public static String toMemoryInfo() {

		Runtime currRuntime = Runtime.getRuntime();

		int nFreeMemory = (int) (currRuntime.freeMemory() / 1024 / 1024);

		int nTotalMemory = (int) (currRuntime.totalMemory() / 1024 / 1024);

		return nFreeMemory + "M/" + nTotalMemory + "M(free/total)";

	}
}

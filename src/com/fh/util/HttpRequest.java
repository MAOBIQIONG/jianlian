package com.fh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 	                                                                                                 
 * @description		HttpClient 请求远程地址类 
 * @ClassName:      HttpRequest 			
 * @author			陈鹏                                     
 * @createDate		2014-4-9 下午04:51:04 
 * @version         v1.0
 *
 */
@SuppressWarnings("deprecation")
public class HttpRequest {
	private static String result = "";

	private static Logger log = Logger.getLogger(HttpRequest.class);
	
	/**
	 * 
	 * @Title           postRequestData 
	 * @description     post方法请求远程地址                                
	 * @author			陈鹏
	 * @createDate		2014-4-9 下午04:51:48
	 * @lastModified    2014-4-9 下午04:51:48
	 * @param           @param paramMap  post提交的参数  
	 * @param           @param url  请求的url地址
	 * @param				@param charSet  字符集编码
	 * @param           @return
	 * @param           @throws Exception 
	 * @return          String 返回json 类型字符串
	 */
	public static String postRequestData(final Map<String, String> paramMap, final String url, final String charSet) throws Exception {
		NameValuePair param = null;
		HttpEntity requestHttpEntity = null;
		StringBuffer jsonStr = new StringBuffer();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (paramMap != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>(paramMap.keySet().size());
				Iterator<String> it = paramMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					param = new BasicNameValuePair(key, paramMap.get(key));
					params.add(param);
				}
				requestHttpEntity = new UrlEncodedFormEntity(params, charSet);
				httpPost.setEntity(requestHttpEntity);
			}
			HttpClient httpClient = new DefaultHttpClient();
			InputStream is = null;
			HttpResponse response = httpClient.execute(httpPost);
			requestHttpEntity = response.getEntity();
			is = requestHttpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, charSet));
			String line = null;
			while ((line = br.readLine()) != null) {
				jsonStr.append(line);
			}
			result = jsonStr.toString();
		} catch (Exception e) {
			result = "{retFlag:false,retMsg:'系统出现了问题,请重试！'}";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @Title           getRequestData 
	 * @description     get方法请求远程地址                               
	 * @author			陈鹏
	 * @createDate		2014-4-9 下午04:52:46
	 * @lastModified    2014-4-9 下午04:52:46
	 * @param           @param paramMap  提交参数
	 * @param           @param url  远程地址
	 * @param           @return
	 * @param           @throws Exception 
	 * @return          String  返回json字符串
	 */
	public static String getRequestData(final Map<String, String> paramMap, final String url) throws Exception {
		HttpEntity requestHttpEntity = null;
		StringBuffer jsonStr = new StringBuffer();
		String paramUrl = "";
		try {
			StringBuffer params = new StringBuffer();
			if (paramMap != null) {
				Iterator<String> it = paramMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next().trim();
					params.append(key + "=" + paramMap.get(key).trim() + "&");
				}
				paramUrl = url + "?" + params.toString();
			} else {
				paramUrl = url;
			}
			HttpGet httpGet = new HttpGet(paramUrl);
			HttpClient httpClient = new DefaultHttpClient();
			InputStream is = null;
			HttpResponse response = httpClient.execute(httpGet);
			requestHttpEntity = response.getEntity();
			is = requestHttpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				jsonStr.append(line);
			}
			result = jsonStr.toString();
		} catch (Exception e) {
			result = "{retFlag:false,retMsg:'系统出现了问题,请重试！'}";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * <b>方法名：</b>：httpPost<br>
	 * <b>功能说明：</b>：httpPost请求<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2015-11-4 下午6:11:34
	 * @param url
	 * @param jsonParam
	 * @return
	 * @throws IOException
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		url = URLDecoder.decode(url, "UTF-8");
		HttpPost httpPost = new HttpPost(url);
		try {
			if (null != jsonParam) {
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = "";
				result = EntityUtils.toString(response.getEntity());
				jsonResult = JSONObject.parseObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return jsonResult;
	}

	/**
	 * post请求
	 */
	@SuppressWarnings("deprecation")
	public static String reqPost(String url, String xml) {
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		try {
			PostMethod method = new PostMethod(url);
			method.setRequestHeader("Content-type", "text/xml; charset=utf-8");
			method.setRequestBody(xml);
			int httpStatus = client.executeMethod(method);
			InputStream stream = method.getResponseBodyAsStream();
			byte[] cache = new byte[1024];
			int length = -1;
			StringBuffer sb = new StringBuffer("");
			while ((length = stream.read(cache)) != -1) {
				sb.append(new String(cache, 0, length, "utf-8"));
			}
			stream.close();
			if (httpStatus == HttpStatus.SC_OK) {
				return sb.toString();
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//获取ip地址
    public static String getIpAddr(HttpServletRequest request) {      
           String ip = request.getHeader("x-forwarded-for"); 
          // System.out.println(ip+"123");
          if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
              ip = request.getHeader("Proxy-Client-IP"); 
              //System.out.println(ip+"33333");
          }      
          if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
              ip = request.getHeader("WL-Proxy-Client-IP");
             // System.out.println(ip+"44444");
           }      
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
               ip = request.getRemoteAddr(); 
               //System.out.println(ip+"55555");
          }      
         return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;    
    }
	
}

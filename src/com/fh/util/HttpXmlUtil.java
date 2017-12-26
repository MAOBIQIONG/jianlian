package com.fh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class HttpXmlUtil {

	/**
	 * 封装请求参数
	 * @param data
	 * @return
	 */
	public static String formatXmlRequest(Map<String,Object> data){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Request>");
		for(String key : data.keySet()){
			if(data.get(key) instanceof List){
				sb.append("<" + key + ">");
				List list = (List) data.get(key);
				for(Object l : list){
					String ckey = key.substring(0,key.length() - 1);
					sb.append("<" + ckey + ">").append(l).append("</" + ckey + ">");
				}
				sb.append("</" + key + ">");
			}else{
				sb.append("<" + key + ">").append(data.get(key)).append("</" + key + ">");
			}
		}
		sb.append("</Request>");
		return sb.toString();
	}
	
	/**
	 * 获取返回报文
	 * @param strXml
	 * @return
	 */
	public static PageData getMsgXmlResponse(String strXml){
		PageData pd = null;
		if(strXml.length() <= 0 || strXml == null){
			return null;
		}
		try {
			//将字符串转化为xml文档对象
			Document document = DocumentHelper.parseText(strXml);
			//获得文档的根节点
			Element root = document.getRootElement();
			//遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			//遍历所有节点
			pd = new PageData();
			
			while(iter.hasNext()){
				Element ele = (Element) iter.next();
				pd.put(ele.getName(), ele.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	public static void main(String[] args) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("name", "JACK");
		data.put("age", "20");
		List cmap = new ArrayList();
		cmap.add("1");
		cmap.add("2");
		data.put("members", cmap);
		String formatRequest = HttpXmlUtil.formatXmlRequest(data);
		System.out.println(formatRequest);
		
//		String xmlmsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><age>20</age><name>JACK</name></Request>";
//		PageData pd = HttpXmlUtil.getMsgXmlResponse(xmlmsg);
//		System.out.println(pd.get("name"));
	}
	
}

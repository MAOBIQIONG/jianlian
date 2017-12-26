 package com.fh.util;
 
 import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
 public class CommonsWeatherUtils
 {
   InputStream inStream;
   Element root;
 
   public InputStream getInStream()
   {
     return this.inStream;
   }
 
   public void setInStream(InputStream inStream)
   {
     this.inStream = inStream;
   }
 
   public Element getRoot()
   {
     return this.root;
   }
 
   public void setRoot(Element root)
   {
     this.root = root;
   }
 
   public CommonsWeatherUtils()
   {
   }
 
   public CommonsWeatherUtils(InputStream inStream)
   {
     if (inStream != null)
     {
       this.inStream = inStream;
 
       DocumentBuilderFactory domfac = 
         DocumentBuilderFactory.newInstance();
       try
       {
         DocumentBuilder domBuilder = domfac.newDocumentBuilder();
 
         Document doc = domBuilder.parse(inStream);
 
         this.root = doc.getDocumentElement();
       }
       catch (ParserConfigurationException e)
       {
         e.printStackTrace();
       }
       catch (SAXException e)
       {
         e.printStackTrace();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
   }
 
   public CommonsWeatherUtils(String path)
   {
     InputStream inStream = null;
     try
     {
       inStream = new FileInputStream(path);
     }
     catch (FileNotFoundException e1)
     {
       e1.printStackTrace();
     }
 
     if (inStream != null)
     {
       this.inStream = inStream;
 
       DocumentBuilderFactory domfac = 
         DocumentBuilderFactory.newInstance();
       try
       {
         DocumentBuilder domBuilder = domfac.newDocumentBuilder();
 
         Document doc = domBuilder.parse(inStream);
 
         this.root = doc.getDocumentElement();
       }
       catch (ParserConfigurationException e)
       {
         e.printStackTrace();
       }
       catch (SAXException e)
       {
         e.printStackTrace();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
   }
 
   public CommonsWeatherUtils(URL url)
   {
     InputStream inStream = null;
     try
     {
       inStream = url.openStream();
     }
     catch (IOException e1)
     {
       e1.printStackTrace();
     }
 
     if (inStream != null)
     {
       this.inStream = inStream;
 
       DocumentBuilderFactory domfac = 
         DocumentBuilderFactory.newInstance();
       try
       {
         DocumentBuilder domBuilder = domfac.newDocumentBuilder();
 
         Document doc = domBuilder.parse(inStream);
 
         this.root = doc.getDocumentElement();
       }
       catch (ParserConfigurationException e)
       {
         e.printStackTrace();
       }
       catch (SAXException e)
       {
         e.printStackTrace();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
   }
 
   public Map<String, String> getValue(String[] nodes)
   {
     if ((this.inStream == null) || (this.root == null))
     {
       return null;
     }
 
     Map map = new HashMap();
 
     for (int i = 0; i < nodes.length; i++)
     {
       map.put(nodes[i], null);
     }
 
     NodeList topNodes = this.root.getChildNodes();
 
     if (topNodes != null)
     {
       for (int i = 0; i < topNodes.getLength(); i++)
       {
         Node book = topNodes.item(i);
 
         if (book.getNodeType() == 1)
         {
           for (int j = 0; j < nodes.length; j++)
           {
             for (Node node = book.getFirstChild(); node != null; node = node
               .getNextSibling())
             {
               if (node.getNodeType() == 1)
               {
                 if (node.getNodeName().equals(nodes[j]))
                 {
                   String val = null;//node.getTextContent();
 
                   String temp = (String)map.get(nodes[j]);
 
                   if ((temp != null) && (!temp.equals("")))
                   {
                     temp = temp + ";" + val;
                   }
                   else
                   {
                     temp = val;
                   }
 
                   map.put(nodes[j], temp);
                 }
               }
             }
           }
         }
       }
     }
 
     return map;
   }
 
   public static String getWeather(String city, String day) {
     Map map = null;
     try {
       city = URLEncoder.encode(city, "GBK");
     } catch (UnsupportedEncodingException e1) {
       e1.printStackTrace();
     }
     String link = "http://php.weather.sina.com.cn/xml.php?city=" + city + 
       "&password=DJOYnieT8234jlsK&day=" + day;
     //System.out.println(link);
 
     String[] nodes = { "city", "status1", "temperature1", "status2", 
       "temperature2" };
     try
     {
       URL url = new URL(link);
       CommonsWeatherUtils parser = new CommonsWeatherUtils(url);
 
       map = parser.getValue(nodes);
       //System.out.println(map);
     }
     catch (MalformedURLException e)
     {
       e.printStackTrace();
     }
     String result = null;
     result = (String)map.get(nodes[0]) + " 今天白天：" + (String)map.get(nodes[1]) + 
       " 最高温度：" + (String)map.get(nodes[2]) + "℃ 今天夜间：" + 
       (String)map.get(nodes[3]) + " 最低温度：" + (String)map.get(nodes[4]) + "℃ ";
     return result;
   }
 
   public static void main(String[] args) {
     Map weatherInfo = getWeatherInfo("上海", "0");
     System.err.println(weatherInfo.get("text"));
   }
 
   public static Map<String, String> getWeatherInfo(String city, String day) {
     Map<String, String> map = null;
     try {
       city = URLEncoder.encode(city, "GBK");
     } catch (UnsupportedEncodingException e1) {
       e1.printStackTrace();
     }
     String sina_url = "http://php.weather.sina.com.cn/xml.php?city=" + city + 
       "&password=DJOYnieT8234jlsK&day=";
     String link = sina_url + day;
 
     String[] nodes = { "city", "status1", "temperature1", "status2", 
       "temperature2" };
     try {
       URL url = new URL(link);
       CommonsWeatherUtils parser = new CommonsWeatherUtils(url);
       map = parser.getValue(nodes);
     } catch (MalformedURLException e) {
       e.printStackTrace();
     }
     map.put("statusdown", (String)WeatherType.map.get(map.get("status1")));
     map.put("statusup", (String)WeatherType.map.get(map.get("status2")));
     map.put("text", city+": "+map.get("status1")+"转"+map.get("status2")+" ,气温"+map.get("temperature2")+"~"+map.get("temperature1")+"°");
     return map;
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.CommonsWeatherUtils
 * JD-Core Version:    0.6.2
 */
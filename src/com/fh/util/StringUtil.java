 package com.fh.util;
 
 public class StringUtil
 {
   public static String[] StrList(String valStr)
   {
     int i = 0;
     String TempStr = valStr;
     String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
     valStr = valStr + ",";
     while (valStr.indexOf(',') > 0)
     {
       returnStr[i] = valStr.substring(0, valStr.indexOf(44));
       valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());
 
       i++;
     }
     return returnStr;
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.StringUtil
 * JD-Core Version:    0.6.2
 */
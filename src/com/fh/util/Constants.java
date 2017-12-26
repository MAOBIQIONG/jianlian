 package com.fh.util;
 
 public class Constants
 {
   public static String PICTURE_VISIT_FILE_PATH = "";
 
   public static String PICTURE_SAVE_FILE_PATH = "";
 
   public static String getPICTURE_VISIT_FILE_PATH()
   {
     return PICTURE_VISIT_FILE_PATH;
   }
 
   public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
     PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
   }
 
   public static String getPICTURE_SAVE_FILE_PATH() {
     return PICTURE_SAVE_FILE_PATH;
   }
 
   public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
     PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
   }
 
   public static void main(String[] args) {
     setPICTURE_SAVE_FILE_PATH("D:/Tomcat 6.0/webapps/FH/topic/");
     setPICTURE_VISIT_FILE_PATH("http://192.168.1.225:8888/FH/topic/");
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.Constants
 * JD-Core Version:    0.6.2
 */
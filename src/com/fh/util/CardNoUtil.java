 package com.fh.util;

import java.util.Date;
 
 public class CardNoUtil{
	 
   public static String getCardNo(String max){ 
	   long start = (new Date()).getTime(); 
	/*   if(max!=null){
		   
	   }*/
	   String ma=max.substring(6);
	   Integer no=Integer.parseInt(ma);
	   no=no+1; 
	   if(no.toString().contains("4")||no.toString().contains("7")){ 
		   for (int i = 0; i < 10000; i++) {
			   no++;
			   if(no.toString().contains("4")||no.toString().contains("7")){
				   
			   }else{ 
				   System.out.println(no);
				   break;
			   }  
		   } 
	   }
	   String len="0";
	   if(no.toString().length()==1){
		  len="0000"+no.toString(); 
	   }else if(no.toString().length()==2){
		   len="000"+no.toString(); 
	   }else if(no.toString().length()==3){
		   len="00"+no.toString(); 
	   }else if(no.toString().length()==4){
		   len="0"+no.toString(); 
	   }else{
		   len=no.toString(); 
	   }
	   long end = (new Date()).getTime();
	   long hours = ((end-start) % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60 * 60 * 60);  
       System.out.println(start);
       System.out.println(end);
       System.out.println(hours); 
       System.out.println(no);
       String code = max.substring(0, 6)+len;
       return code;
   }
   
   public static String getNo(String first,String second){
     String code = getFirst(first)+second.trim();
     code=code+"08";
     return code;
   }  
   
   public static String getFirst(String level){
	   String code="";
	   if("01".equals(level)){
		   code="2";
	   }else if("03".equals(level)){
		   code="3";
	   }else if("04".equals(level)){
		   code="5";
	   }else if("05".equals(level)){
		   code="6";
	   }
       return code;
   }  
 
   public static void main(String[] args) { 
		 
		String no=CardNoUtil.getNo("01","016"); 
		String gg=CardNoUtil.getCardNo("20160810003");
	  
	   /*Integer m=40000;
	 //字符串实现
	   long start = (new Date()).getTime(); 
	   if(m.toString().contains("4")){
		   for (int i = 0; i < 10000; i++) {
			   m++;
			   if(m.toString().contains("4")){
				   
			   }else{
				   System.out.println(m);
				   break;
			   } 
			   
		   }
	   }
	   System.out.println(m);
	   long end = (new Date()).getTime();
		 long hours = ((end-start) % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60 * 60 * 60);  
       System.out.println(start);*/
       //System.out.println(end);
	  // String ss=getCardNo(gg);
	   //String ss=getCardNo(gg);
       System.out.println(no);
       System.out.println(gg); 
   }
 } 
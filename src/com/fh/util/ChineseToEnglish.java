package com.fh.util;

import java.util.Date;
import java.util.Random;

import org.apache.shiro.crypto.hash.SimpleHash;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseToEnglish {

	public static String getPingYinToHeaderChar(String str){
		//String src="";
	    char[] t1 = null;  
	    t1 = str.toCharArray();  
	    String[] t2 = new String[t1.length];  
	    HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
	      
	    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
	    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
	    t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
	    String t4 = "";  
	    int t0 = t1.length;  
	    try {  
	        for (int i = 0; i < t0; i++) {  
	            // 判断是否为汉字字符  
	            if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {  
	                t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
	                t4 += t2[0];  
	            } else {
	                t4 += java.lang.Character.toString(t1[i]);  
	            }  
	        }
	        if(t4!=null&&!"".equals(t4)){
	        	t4=t4.toUpperCase().substring(0,1); 
	        }
	    } catch (BadHanyuPinyinOutputFormatCombination e1) {  
	        e1.printStackTrace();  
	    }  
	    return t4;
	}  
	public  static void main(String args[]){
		 Random rm = new Random();  
	      
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, 11);  
	  
	    // 将获得的获得随机数转化为字符串  
	     String fixLenthString = String.valueOf(pross);  
	     
	    // 返回固定的长度的随机数  
	     String i=fixLenthString.substring(2, 11 + 1);   
	 
	    System.out.println(i);
		
		long time=(new Date()).getTime();
		System.out.println(time);
		/*String password=new SimpleHash("SHA-1","15001703970","87654321").toString();
		System.out.println(password);*/
		 
	    
	   /* BigDecimal price=new BigDecimal("0.01");
	    //BigDecimal beishu=new BigDecimal(100);
	    BigDecimal result=price.multiply(new BigDecimal(100));
	    int d=result.intValue();
	    boolean isTrue=String.valueOf(d).equals("1"); 
	    System.out.println(isTrue);
	    System.out.println(result);
	    System.out.println(d);*/
	     
	}
}

package com.fh.util;

import java.util.Map;

public class ResultUtil {

	public static void resetRes(Map a ,String[] keys){ 
		for(int i=0;i<keys.length;i++){
			if(a.get(keys[i])==null||"".equals(a.get(keys[i]))){
				 a.put(keys[i],"");
			}else{
				a.put(keys[i],a.get(keys[i]).toString());
			} 
		} 
	}
	
	public static void resetResInt(Map a ,String[] keys){ 
		for(int i=0;i<keys.length;i++){
			if(a.get(keys[i])==null||"".equals(a.get(keys[i]))){
				 a.put(keys[i],0);
			}else{
				a.put(keys[i],a.get(keys[i]).toString());
			} 
		} 
	}
	
	public static void reset(Map a ,Map b,String[] keys){ 
		for(int i=0;i<keys.length;i++){
			if(a.get(keys[i])==null||"".equals(a.get(keys[i]))){
				 a.put(keys[i],b.get(keys[i]));
			 } 
		} 
	}
}

package com.fh.service.projects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProNoCacheService {
	
	public static Map<String,String> proNoCache=new ConcurrentHashMap<String,String>();
	
	static boolean isInit=false;
	
	public void init(Map<String,String> dataMap){
		if(isInit){
			return;
		}
		proNoCache=dataMap;
		isInit=true;
	}
	
	public void reload(Map<String,String> dataMap){
		proNoCache.clear();
		proNoCache=dataMap;
	}
	
	public String get(String key){
		return proNoCache.get(key);
	} 
	
	public boolean isInit(){
		return isInit;
	}
}

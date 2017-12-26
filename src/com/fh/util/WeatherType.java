package com.fh.util;

import java.util.HashMap;
import java.util.Map;

public class WeatherType {
	public static Map<String, String> map = new HashMap();

	static {
		map.put("晴", "01");
		map.put("多云", "02");
		map.put("阴", "03");
		map.put("小雨", "04");
		map.put("中雨", "05");
		map.put("大雨", "06");
		map.put("爆雨", "07");
		map.put("阵雨", "08");
		map.put("雷阵雨", "09");
		map.put("小雪", "10");
		map.put("中雪", "11");
		map.put("大雪", "12");
		map.put("雨夹雪", "13");
		map.put("雾", "14");
		map.put("冰雹", "15");
	}
}

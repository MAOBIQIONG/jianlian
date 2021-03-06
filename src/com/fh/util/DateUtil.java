package com.fh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DurationFormatUtils;

public class DateUtil {
	public static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	public static final SimpleDateFormat sdfMonth = new SimpleDateFormat("M");

	public static final SimpleDateFormat sdfYear_Month = new SimpleDateFormat(
			"yyyy-M");
	
	public static final SimpleDateFormat sdfYear_MMonth = new SimpleDateFormat(
			"yyyy-MM");

	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd");

	public static final SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final SimpleDateFormat sdfDays = new SimpleDateFormat(
			"yyyyMMdd");

	public static final SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	public static final SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getYear(Date date) {
		return sdfYear.format(date);
	}

	public static String getYear_Month(Date date) {
		return sdfYear_Month.format(date);
	}

	public static String getDate(Date date) {
		return sdfDate.format(date);
	}

	public static String getMonth(Date date) {
		return sdfMonth.format(date);
	}

	public static String getDay(Date date) {
		return sdfDay.format(date);
	}

	public static String getDays(Date date) {
		return sdfDays.format(date);
	}
	
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	public static String getTime(Date date) {
		return sdfTime.format(date);
	}
	
	public static String getTime() {
		return sdfTime.format(new Date());
	}
	
	public static String getTimeStamp(Date date){
		return timeStamp.format(date);
	}

	public static boolean compareDate(String s, String e) {
		if ((fomatDate(s) == null) || (fomatDate(e) == null)) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0L;
			return (int) ((fmt.parse(endTime).getTime() - fmt.parse(startTime)
					.getTime()) / 86400000L / 365L);
		} catch (Exception e) {
		}
		return 0;
	}

	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0L;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / 86400000L;

		return day;
	}

	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance();
		canlendar.add(5, daysInt);
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance();
		canlendar.add(5, daysInt);
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static String[] splitDate(String date) {
		return date.split("-");
	}

	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String fullAge(String birthday) {
		return fullAge(birthday,"y岁M个月零d天");
	}
	
	public static String fullAge(String birthday,String format) {
		String date = DurationFormatUtils.formatPeriod(fomatDate(birthday)
				.getTime(), Calendar.getInstance().getTime().getTime(),
				format);
		return date;
	}
	
	public static String fullAgeYM(String birthday) {
		String date = DurationFormatUtils.formatPeriod(fomatDate(birthday)
				.getTime(), Calendar.getInstance().getTime().getTime(),
				"y岁M个月");
		return date;
	}
	
	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };  
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };  
	  
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };
	 
	/**
	 * 根据日期获取生肖
	 * @return
	 */
	public static String getZodica(String birthday) {
	    
	    return zodiacArr[fomatDate(birthday).getYear() % 12];
	}
	
	//根据时间获取年月
	public static String getYearAndMonth(Date date) { 
	    return  sdfYear_Month.format(date);
	}
	 
	public static String getConstellation(String birthday) {
		Date date = fomatDate(birthday);
		int month = date.getMonth()+1;
		int day = date.getDate();
		return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];  
	}  

	public static void main(String[] args) {
		//System.out.println(getConstellation("2010-11-11"));;
		//String date=getTime(new Date());
		
		try {
			System.out.println(daysBetween("2017-05-19","2017-05-23"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//根据开始日期 增加 月份后 算出 日期(yyyy-mm)
	public static String getDate(String begin_date, String addMonth) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(DateUtil.fomatDate(begin_date).getTime());
		calendar.add(Calendar.MONTH, Integer.parseInt(addMonth));
		return sdfDay.format(calendar.getTime());
	}
	
	
}

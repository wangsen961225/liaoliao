package com.liaoliao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeKit {

	
	/**
	 * @param date
	 * @return
	 * 格式化Date时间
	 */
	public static String formatDate(Date date){  
		    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);  
		 }
	
	
	/**
	 * @return
	 * 日期字符串转Date时间
	 */
	public static Date StrToDate(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateStr);
		return date;
	}
	
	
	/**
	 * @return
	 * 获取今日0点时间
	 */
	public static Date todayStart(){
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date =cal.getTime();
        return date;
	}
	
	/**
	 * 字符串转换为日期
	 * 
	 * @param str
	 * @param tpl
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String tpl){
		try {
			return (new SimpleDateFormat(tpl).parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换为日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str){
		return strToDate(str, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param tpl
	 * @return
	 */
	public static String dateToStr(Date date, String tpl) {
		return (new SimpleDateFormat(tpl).format(date));
	}

	/**
	 * 日期转换为字符串yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 秒数转mm:ss格式
	 * @param second
	 * @return
	 */
	public static String secondFormat(String second) {
		String mm = null;
		String ss = null;
		int intTime = Double.valueOf(second).intValue();
		int m = intTime/60;
		if(m < 10){
			mm = "0" + m;
		}
		else{
			mm = String.valueOf(m);
		}
		int s = intTime%60;
		if(s < 10){
			ss = "0" + s; 
		}
		else{
			ss = String.valueOf(s);
		}
		second = mm+":"+ss;
		return second;
	}
	
	/** 
     * 得到几天前0点的时间 
     *  
     * @param date 
     * @param day 
     * @return 
     */  
    public static Date getDateBefore(int day) {  
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);  
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date =cal.getTime();
        return date;
    }

	/** 
     * 得到几天后0点的时间 
     *  
     * @param date 
     * @param day 
     * @return 
     */  
    public static Date getDateAfter(int day) {  
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);  
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date =cal.getTime();
        return date;
    }
    
    public static void main(String[] args) throws ParseException {
	//	System.out.println(todayStart());
	//	System.out.println(new Date().getTime()-dayStart().getTime());
	}

}

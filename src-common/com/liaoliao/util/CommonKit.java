package com.liaoliao.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 常用工具方法集合
 * 
 * <pre>
 * 工欲善其事，必先利其器
 * </pre>
 * 
 * @author vity
 * @date 2014-1-15
 * @mail vity.1215@gmail.com
 */
public class CommonKit implements Serializable {

	private static final long serialVersionUID = -2592888503219452508L;
	public static String datePattern = "yyyy-MM-dd";
	public static String timeFormat = "yyyy-MM-dd HH:mm:ss";
	private static final int CAPTCHA_LENGTH = 4;
	private static final String[] CAPTCHA_CHARS = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
			"J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 邀请码，此处只负责生成8位字符，不负责是否重复，应在业务系统中进行处理
	 *
	 * @return
	 * 
	 * @author Vity Created at 2015年10月15日 下午3:00:45
	 */
	public static synchronized String invitationCode() {
		// 生成随机类
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < CAPTCHA_LENGTH; i++) {
			String rand = String.valueOf(CAPTCHA_CHARS[random.nextInt(CAPTCHA_CHARS.length)]);
			sRand += rand;
		}
		return sRand;
		// return (Integer.toHexString((int) (System.currentTimeMillis() +
		// System.nanoTime()))).toUpperCase();
	}



	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (source == null || "".equals(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return source;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}

	}

	/**
	 * 获取文件前缀名称
	 * 
	 * @Title getPrefixFileName
	 * @Description
	 * @param filename
	 * @return
	 * @author 兮飒
	 * @date 2015年2月9日 上午10:37:21
	 */
	public static String getPrefixFileName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionFileName(String filename) {
		return getExtensionFileName(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @param defExt
	 *            默认扩展名
	 * @return
	 */
	public static String getExtensionFileName(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	/**
	 * 效验多个字符串是否有空值
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isBlank(String... args) {
		for (String arg : args) {
			if (StringUtils.isBlank(arg)) {
				return true;
			}
		}
		return false;
	}

	static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	/**
	 * 去除字符串换行、制表符
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/*
	 * 将数组转换为IN参数 <pre> strArr = {1,2,3,4} return '1','2','3','4' </pre>
	 */
	public static String convertInParams(String[] strArr) {
		String str = "";
		for (String _id : strArr) {
			str += "'" + _id + "',";
		}
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * 将数组转换为以，分割的字符串
	 * 
	 * <pre>
	 * strArr = {1,2,3,4}
	 * 
	 * return 1,2,3,4
	 * </pre>
	 * 
	 * @param strArr
	 * @return
	 */
	public static String convertString(String[] strArr) {
		String str = "";
		for (String _id : strArr) {
			str += _id + ",";
		}
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}


	/**
	 * 
	 * 把毫秒转化成日期
	 * 
	 * @param millSec
	 *            (毫秒数)
	 * 
	 * @return
	 */
	public static String transferLongToDate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * 转指定时区的时间
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 * 
	 * @author Vity Created at 2015年10月20日 上午11:25:25
	 */
	public static Date toUTC(Date date, TimeZone timeZone) {
		long v2 = date.getTime();
		Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		long offset = tz.getOffset(date.getTime());
		v2 = date.getTime() - offset; // v2 is local time
		long offset2 = timeZone.getOffset(v2);
		v2 += offset2;
		return new Date(v2);
	}

	/**
	 * 返回上周日时间
	 *
	 * @param date
	 * @return
	 * 
	 * @author Vity Created at 2016年11月1日 下午5:28:23
	 */
	public static Date getLastWeekSunday(Date date) {
		Date a = DateUtils.addDays(date, -1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(a);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		return cal.getTime();
	}

	/**
	 * 返回本周日时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}


	public static String getBaijiaResp(String url, String referer) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Referer", referer);
			httpget.setHeader("Cookie",
					"BAIDUID=F583EE74369B9B58C3FCF016C3503C31:FG=1; BIDUPSID=F583EE74369B9B58C3FCF016C3503C31; PSTM=1490967877; H_PS_PSSID=1438_21088_20927; Hm_lvt_7fe4ae9e1d0d01bcf748d066979cd2aa=1490971499,1491479817,1491746607; Hm_lpvt_7fe4ae9e1d0d01bcf748d066979cd2aa=1491746607");
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ParseException {

	}

}

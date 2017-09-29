package com.liaoliao.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SpiderHttpKit {
	
	
	/**
	 * 获取今日头条返回数据
	 * 
	 * @param url
	 * @param referer
	 * @return
	 */
	public static String getToutiaoResp(String url/*, String referer*/) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Referer", "https://www.toutiao.com/ch/funny/");
			httpget.setHeader("Cookie",
					"uuid=\"w:1c0b54be64854b4db00e3ecd6f6bc1da\"; UM_distinctid=15dab3e6f68427-0b4b5b83fad87e-333f5803-1fa400-15dab3e6f69825; csrftoken=d2712d20a58aa180f4a7531d9211a868; WEATHER_CITY=%E5%8C%97%E4%BA%AC; __tasessionId=q67uubc5v1501828304411; CNZZDATA1259612802=1192335892-1501807684-%7C1501823884; tt_webid=6450252654543046158");
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");
			httpget.setHeader("Host", "www.toutiao.com");
			httpget.setHeader("Accept","text/javascript, text/html, application/xml, text/xml, */*");
			httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8");
			httpget.setHeader("Accept-Encoding","gzip, deflate, br");
			httpget.setHeader("X-Requested-With","XMLHttpRequest");
			httpget.setHeader("Content-Type","application/x-www-form-urlencoded");
			httpget.setHeader("Connection","keep-alive");
			
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

	
	
	/**
	 * 获取内涵段子返回数据
	 * 
	 * @param url
	 * @param referer
	 * @return
	 */
	public static String getNeihanResp(String url/*, String referer*/) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Referer", "http://m.neihanshequ.com/video/");
			httpget.setHeader("Cookie",
					"uuid=\"w:f320c72aa02d498eb72623b6ee025dd0\"; tt_webid=61613050244; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1501831812; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1501831818; skip_guidence=1; csrftoken=2aa50bbf4676acd5366a308c9e676efb");
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Mobile Safari/537.36");
			httpget.setHeader("Host", "m.neihanshequ.com");
			httpget.setHeader("Accept","*/*");
			httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8");
			httpget.setHeader("Accept-Encoding","gzip, deflate");
			httpget.setHeader("X-Requested-With","XMLHttpRequest");
			httpget.setHeader("Content-Type","application/x-www-form-urlencoded");
			httpget.setHeader("Connection","keep-alive");
			
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
}

package com.liaoliao.util;

import java.util.HashMap;
import java.util.Map;


public class MeipaiUtils {
	public static Map<String,Object> getHex(String param1){
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("str", param1.substring(4));
		ret.put("hex", new StringBuilder(param1.substring(0,4)).reverse().toString());
		return ret;
	}
	public static Map<String,Object> getDec(String param1){
		Map<String,Object> ret = new HashMap<String, Object>();
		String loc2 = String.valueOf(Integer.parseInt(param1,16));
		ret.put("pre", loc2.substring(0, 2).split(""));
		ret.put("tail", loc2.substring(2).split(""));
		return ret;
	}
	public static String substr(String param1,String[] param2){
		String loc3 = param1.substring(0, Integer.valueOf(param2[0]));
		String loc4 = param1.substring(Integer.valueOf(param2[0]), Integer.valueOf(param2[0])+Integer.valueOf(param2[1]));
		return loc3 + param1.substring(Integer.valueOf(param2[0])).replace(loc4, "");
	}
	public static String[] getPos(String param1,String[] param2){
		param2[0] =String.valueOf(param1.length() - Integer.valueOf(param2[0]) - Integer.valueOf(param2[1]));
		return param2;
	}
	public static String decode(String encoded_string){
		Map<String,Object> dict2 =getHex(encoded_string);
		Map<String,Object> dict3 = getDec(dict2.get("hex").toString());
		String str4 = substr(dict2.get("str").toString(),(String[]) dict3.get("pre"));
		return new String(Base64Kit.getFromBase64(substr(str4,getPos(str4,(String[]) dict3.get("tail")))));
	}
	public static void main(String[] args) throws Exception {
		String test = decode("0031aHR04ePjvDX3cDovL212dmlkZW8xMC5tZWl0dWRhdGEuY29tLzU5NWJiYjdjNjQxZDQyMzM2LmuRxq1wNA==");	
//		http://mvvideo11.meitud]K���K�NMX��̎Y��NNM˛\
		System.out.println(test);
	}
}

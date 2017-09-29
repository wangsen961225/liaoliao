package com.liaoliao.util;

public class StringKit {

	public static boolean isEmpty(String str){
		if("".equals(str) || str==null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if(str!=null&&(!"".equals(str))&&(!"null".equals(str))){
			return false;
		}else{
			return true;
		}
		
	}
		
	
}

package com.liaoliao.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 短信发送接口
 * 
 */
public class MessageKit {

	public static boolean sendMessage(String msgCode,String mobile) {
		String  url = "http://www.282930.cn/SMSReceiver.aspx";// 应用地址
//		String  username = "2210009625@qq.com";//申请的用户名
//		String  password = "y7626272";// 任意字符
		String  username = "sjcmll";//申请的用户名
		String  password = "LintaoShijie880.8";// 任意字符
//		
//		String msgCode = "您好，你的验证码是：56953【赚赚】";
//		String mobile = "17090317208";
		
//		String  contentPhone = "您好，你的验证码是:";// 短信内容
//		String  contentReg = "您好，恭喜您注册成功，您的登录密码是:";// 短信内容
//		String  contentUp = "您好,";// 短信内容
		String  xmlmode = "xmlmode";// 回参数类型为XML格式
			
	    String backMessage = CommonUtil.sendPost(url,"username="+username+"&password="+password+"&xmlmode="+xmlmode+"&content="+msgCode+"&mobiles="+mobile+"&targetdate=");
	    backMessage = StringUtils.substringBetween(backMessage, "<ResultCode>", "</ResultCode>");
	    if("0".equals(backMessage)){
	    	return true;
	    }else{
	    	return false;
	    }
	}

	
}

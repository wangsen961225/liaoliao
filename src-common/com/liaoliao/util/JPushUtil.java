package com.liaoliao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @author Vio
 * @date 2017年7月15日
 */
public class JPushUtil {
	
	final static String masterSecret = "c605caf04834b41d486e76d6";
	final static String appKey = "244e699463b3f5a48f1c1b9a";
	
	/**
	 * 给所有平台的所有用户发包含额外内容的通知
	 */
	public static void sendAllsetNotification(String message,Map<String, String> extras) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey
		
/*		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外的通知");*/
		
		PushPayload payload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					// 设置平台
					.setAudience(Audience.all())
					// 按什么发送 tag alia
					.setNotification(
							Notification
							.newBuilder()
							.setAlert(message)
							.addPlatformNotification(
									AndroidNotification.newBuilder().addExtras(extras).build())
							.addPlatformNotification(
									IosNotification.newBuilder().addExtras(extras).build())
							.build())
					// 发送消息
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
					//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
		try {
			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result);
		}
		catch (APIConnectionException e) {
			System.out.println(e);
		}
		catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
	
	/**
	 * 给所有平台的所有用户发包含额外内容的自定义消息
	 * 
	 * @param message
	 * @author Vio
	 * @date 2017年7月15日
	 */
	public static void sendAllMessage(String message,Map<String, String> extras,int time) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey
		/*Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外透传的消息");*/
		PushPayload payload =  PushPayload.newBuilder()
				// 设置平台
				.setPlatform(Platform.all())
				// 按什么发送 tag alia
				.setAudience(Audience.all())
				// 发送通知
				.setMessage(Message.newBuilder().setMsgContent(message).addExtras(extras).build())
				//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
		
//		重设推送生存时间，默认是86400秒，即24小时
		payload.resetOptionsTimeToLive(time);
		try {
			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result);
		}
		catch (APIConnectionException e)
		{
			System.out.println(e);
		}
		catch (APIRequestException e)
		{
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
	
	
	
	/**
	 * 给所有平台的所有用户发通知
	 */
	public static void sendAllsetNotification(String message) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey

		PushPayload payload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					// 设置平台
					.setAudience(Audience.all())
					// 按什么发送 tag alia
					.setNotification(
							Notification
							.newBuilder()
							.setAlert(message)
							.build())
					// 发送消息
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
					//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
		try {
			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result);
		}
		catch (APIConnectionException e) {
			System.out.println(e);
		}
		catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
	
	
	
	/**
	 * 给所有平台的所有用户发自定义消息
	 * 
	 * @param message
	 * @author Vio
	 * @date 2017年7月15日
	 */
	public static void sendAllMessage(String message) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey

		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
					// 设置平台
					.setAudience(Audience.all())
					// 按什么发送 tag alia
					.setMessage(Message.newBuilder()
					.setMsgContent(message)
					.build())
					// 发送通知
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
					//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
		try {
			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result);
		}
		catch (APIConnectionException e) {
			System.out.println(e);
		}
		catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
	


	/**
	 * 客户端 给所有平台的一个或者一组用户发送信息
	 */
	public static void sendAlias(String message, Map<String, String> extras, List<String> aliasList) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey
		
/*		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外的消息--sendAlias");*/

		PushPayload payload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(aliasList))
					.setNotification(
							Notification
							.newBuilder()
							.setAlert(message)
							.addPlatformNotification(
									AndroidNotification.newBuilder().addExtras(extras).build())
							.addPlatformNotification(
									IosNotification.newBuilder().addExtras(extras).build())
							.build())
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		}
		catch (APIConnectionException e) {
			System.out.println(e);
		}
		catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}


	
	/**
	 * 客户端 给平台的一个或者一组标签发送消息。
	 */
	public static void sendTag(String message, String messageId, String type, List<String> tagsList) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey
		// 附加字段
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("messageId", messageId);
		extras.put("typeId", type);

		PushPayload payload = PushPayload
					.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.tag(tagsList))
					.setNotification(
							Notification
							.newBuilder()
							.setAlert(message)
							.addPlatformNotification(
									AndroidNotification.newBuilder().addExtras(extras).build())
							.addPlatformNotification(
									IosNotification.newBuilder().addExtras(extras).build())
							.build())
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		}
		catch (APIConnectionException e) {
			System.out.println(e);
		}
		catch (APIRequestException e) {
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}



	public static void main(String[] args)
	{
		new JPushUtil();
		JPushUtil.sendAllsetNotification("小钱钱，这是java后台发送的一个通知。。。。");
		
//		JPushUtil.sendAllMessage("小猪，这是后台发送的透传消息");
		
		
/*		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushVipLoginEffectType);
		JPushUtil.sendAllMessage("小猪",extras);*/
		
		/*List<String> sendAlias = new ArrayList<>();
		sendAlias.add("1001");
		JPushUtil.sendAlias("这是java后台发送的一个按照alia的通知", sendAlias);*/
		
/*		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushVipLoginEffectType);
		extras.put("time", "15");
		
		JPushUtil.sendAllMessage("有没有妹纸聊天啊~",extras);*/
	}
}

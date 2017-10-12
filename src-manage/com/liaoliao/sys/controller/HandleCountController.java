package com.liaoliao.sys.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liaoliao.sys.entity.HandleCount;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.TimeKit;

@Controller
@RequestMapping("/sys")
public class HandleCountController {
	
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private UserService userService;
	
	
	/*
	 * 统计列表by日期
	 * 
	 */
	@RequestMapping("/handleCountList")
	public String handleCountByDay(HttpServletRequest request,String datTimeStr){
		Date datTime = TimeKit.todayStart();
		if(datTimeStr!=null&&!("".equals(datTimeStr))){
			datTime = TimeKit.strToDate(datTimeStr, "yyyy-MM-dd");
		}
		
		List<HandleCount> handleCountList = handleCountService.handleCountList(datTime);
		for(HandleCount hc : handleCountList) {
//			文章列表
			if(hc.getClickName().equals("articleList")){
				request.setAttribute("articleList", hc.getClickCount());
			}
//			文章详情
			if(hc.getClickName().equals("articleContent")){
				request.setAttribute("articleContent", hc.getClickCount());
			}
//			文章下一篇
			if(hc.getClickName().equals("articleNext")){
				request.setAttribute("articleNext", hc.getClickCount());
			}
//			评论列表
			if(hc.getClickName().equals("commentList")){
				request.setAttribute("commentList", hc.getClickCount());
			}
//			视频列表
			if(hc.getClickName().equals("videoList")){
				request.setAttribute("videoList", hc.getClickCount());
			}
//			视频播放
			if(hc.getClickName().equals("videoContent")){
				request.setAttribute("videoContent", hc.getClickCount());
			}
//			料币记录
			if(hc.getClickName().equals("profitList")){
				request.setAttribute("profitList", hc.getClickCount());
			}
//			兑换料币界面
			if(hc.getClickName().equals("tobankShow")){
				request.setAttribute("tobankShow", hc.getClickCount());
			}
//			发送广播界面
			if(hc.getClickName().equals("broadcastShow")){
				request.setAttribute("broadcastShow", hc.getClickCount());
			}
//			邀请好友界面
			if(hc.getClickName().equals("inviteShow")){
				request.setAttribute("inviteShow", hc.getClickCount());
			}
//			开启App
			if(hc.getClickName().equals("startApp")){
				request.setAttribute("startApp", hc.getClickCount());
			}
//			成功签到
			if(hc.getClickName().equals("signSuccess")){
				request.setAttribute("signSuccess", hc.getClickCount());
			}
//			开通vip界面
			if(hc.getClickName().equals("vipShow")){
				request.setAttribute("vipShow", hc.getClickCount());
			}
//			提交手机验证码
			if(hc.getClickName().equals("msgCode")){
				request.setAttribute("msgCode", hc.getClickCount());
			}
//			提交用户注册
			if(hc.getClickName().equals("registerUser")){
				request.setAttribute("registerUser", hc.getClickCount());
			}
//			提交改密码
			if(hc.getClickName().equals("changePassword")){
				request.setAttribute("changePassword", hc.getClickCount());
			}
//			提交找回密码
			if(hc.getClickName().equals("findLosspass")){
				request.setAttribute("findLosspass", hc.getClickCount());
			}
//			提交修改昵称
			if(hc.getClickName().equals("changeNickname")){
				request.setAttribute("changeNickname", hc.getClickCount());
			}
//			协议展示界面
			if(hc.getClickName().equals("agreementShow")){
				request.setAttribute("agreementShow", hc.getClickCount());
			}
//			提交用户反馈
			if(hc.getClickName().equals("userFeedback")){
				request.setAttribute("userFeedback", hc.getClickCount());
			}
//			提交发送广播
			if(hc.getClickName().equals("sendBroadcast")){
				request.setAttribute("sendBroadcast", hc.getClickCount());
			}
//			霸屏上线
			if(hc.getClickName().equals("vipEffect")){
				request.setAttribute("vipEffect", hc.getClickCount());
			}
//			分润成功
			if(hc.getClickName().equals("profitSuccess")){
				request.setAttribute("profitSuccess", hc.getClickCount());
			}
//			抢红包成功
			if(hc.getClickName().equals("graspSuccess")){
				request.setAttribute("graspSuccess", hc.getClickCount());
			}
//			提交申请提现
			if(hc.getClickName().equals("drawMoney")){
				request.setAttribute("drawMoney", hc.getClickCount());
			}
//			文章分享成功
			if(hc.getClickName().equals("shareArticle")){
				request.setAttribute("shareArticle", hc.getClickCount());
			}
//			视频分享成功
			if(hc.getClickName().equals("shareVideo")){
				request.setAttribute("shareVideo", hc.getClickCount());
			}
//			邀请分享成功
			if(hc.getClickName().equals("shareInvite")){
				request.setAttribute("shareInvite", hc.getClickCount());
			}
			if(hc.getClickName().equals("jumpToTaskList")){
				request.setAttribute("jumpToTaskList", hc.getClickCount());
			}
//			料币分润总金额
			if(hc.getClickName().equals("totalProfitMoney")){
				request.setAttribute("totalProfitMoney", hc.getClickCount());
			}
//			统计在线人数
			if(hc.getClickName().equals("accountOnline")){
				request.setAttribute("accountOnline", hc.getClickCount());
			}

		}
//		request.setAttribute("list", handleCountList);
		datTimeStr = TimeKit.dateToStr(datTime, "yyyy-MM-dd");
		request.setAttribute("time", datTimeStr);
		List<Users> list = userService.findTen();
		request.setAttribute("list", list);
		return "handleCount/handleCountList";
	}
	
	

}

package com.liaoliao.sys.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.hnust.controller.MobilePushAction;
import com.cn.hnust.controller.MobilePushActionProxy;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Feedback;
import com.liaoliao.content.entity.KeyWords;
import com.liaoliao.content.entity.OriginalArticleInfo;
import com.liaoliao.content.entity.OriginalVideoInfo;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleCommentService;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.FeedbackService;
import com.liaoliao.content.service.KeyWordsService;
import com.liaoliao.content.service.OriginalArticleInfoService;
import com.liaoliao.content.service.OriginalVideoInfoService;
import com.liaoliao.content.service.VideoCommentService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.profit.entity.WeixinPayLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.profit.service.ToBankService;
import com.liaoliao.profit.service.WeixinPayLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.BroadcastLog;
import com.liaoliao.sys.service.AdvertClicksService;
import com.liaoliao.sys.service.BroadcastLogService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.user.entity.ReadHistory;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.ReadHistoryService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.JPushUtil;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping("/sys")
public class UserController {
	
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private WeixinPayLogService weixinPayLogService;
	
	@Autowired
	private ToBankService toBankService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private ArticleService articleService;

	@Autowired
	private VideoService videoService;
	
	@Autowired
	private OriginalArticleInfoService originalArticleInfoService;
	
	@Autowired
	private OriginalVideoInfoService originalVideoInfoService;
	
	@Autowired
	private FenrunLogService fenrunLogService;
	
	@Autowired
	private BroadcastLogService broadcastLogService;
	
	@Autowired
	AdvertClicksService advertClicksService;
	
	@Autowired
	ArticleCommentService articleCommentService;
	
	@Autowired
	VideoCommentService videoCommentService;
	
	@Autowired
	private KeyWordsService keyWordsService;
	@Autowired
	private ReadHistoryService readHistoryService;
	
	private Integer page=1;
	
	
	/*
	 * 前台用户列表
	 * 
	 */
	@RequestMapping("/userList")
	public String userList(HttpServletRequest request,Integer pageNo,Integer vipStatus,String mobile,String userName,Integer isActive){
		Map<String,Object> map = new HashMap<>();
		String condition="";
		if(vipStatus!=null&&!("".equals(vipStatus))){
			map.put("vipStatus", vipStatus);
			condition+="&vipStatus="+vipStatus;
		}
		if(mobile!=null&&!("".equals(mobile))){
			map.put("mobile", mobile);
			condition+="&mobile="+mobile;
		}
		if(userName!=null&&!("".equals(userName))){
			map.put("userName", userName);
			condition+="&userName="+userName;
		}
		
		if(isActive!=null&&!("".equals(isActive))){
			if(isActive==1){//活跃用户
				map.put("isActive", isActive);
				condition+="&isActive="+isActive;
			}
			
			if(isActive==0){//非活跃用户
				map.put("isActive", isActive);
				condition+="&isActive="+isActive;
			}
		}
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		
		Integer count = null;
		count=userService.findCountByValue(map);
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/userList");	
		request.setAttribute("pageNo", pageNo);	
		request.setAttribute("condition", condition);
		request.setAttribute("vipStatus", vipStatus);
		request.setAttribute("userName", userName);	
		request.setAttribute("mobile", mobile);	
		request.setAttribute("isActive", isActive);	
		
		//更新今日总额开始
		//当今日所有条件未选择时,执行 
		if(isActive==null&&vipStatus==null&&mobile==null&&userName==null&&(pageNo==0||pageNo==null)){
			//更新今日总额:从分润日志中查询当日总额,更新user
			List<Users> updateList=userService.findAll();
			for (Users user : updateList) {
				Double todayTotal = fenrunLogService.todayTotal(user.getId());
				//暂定: 每次访问列表时,从数据库中查询,当日用户所得料币数,保存至用户表中
				Users findById = userService.findById(user.getId());
				findById.setDayMoney(todayTotal);
				userService.updateUser(findById);
			}
		}
		//更新今日总额结束
		
		List<Users> uList=userService.findAll(pageNo,map);
		
		ArrayList<Map<String,Object>> list = new ArrayList<>();
		
		for (Users user : uList) {
			Map<String,Object> item = new HashMap<String,Object>();
			Integer dayCount = advertClicksService.findDayCountAdvertClicksById(user.getId());
			Integer totalCount = advertClicksService.findTotalCountAdvertClicksById(user.getId());
			item.put("user", user);
			item.put("dayCount", dayCount);
			item.put("totalCount", totalCount);
			
			Integer totalProfit = fenrunLogService.countSignProfit(user.getId());
			Integer totalSign = fenrunLogService.countSignNum(user.getId());
			item.put("totalProfit",totalProfit);
			item.put("totalSign",totalSign );
			
			list.add(item);
		}
		request.setAttribute("list", list);
		return "userPage/userList";
	}
	
	/**
	 * 用户升级vip记录
	 */
	@RequestMapping(value="/VIPList")
	public String VIPList(HttpServletRequest request,Integer pageNo){
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=weixinPayLogService.findCount();
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/VIPList");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "");	
		
		
		List<WeixinPayLog> list = weixinPayLogService.findAll(pageNo);
		request.setAttribute("list", list);
		return "userPage/VIPList";
	}

	
	@RequestMapping(value="/toBank")
	public String toBank(HttpServletRequest request,Integer pageNo){
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=toBankService.findCount();
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/toBank");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "");	
		
		
		List<ToBank> list = toBankService.findAll(pageNo); 
		request.setAttribute("list", list);
		return "userPage/toBank";
	}
	
	
	/**
	 * 根据id删除用户所有信息
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/deldeldeluser", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String deldeldeluser(HttpServletRequest request,Integer userId){
		String str = null;
		if(userId==null){
			str = "危险权限-暂时隐藏";
			return str;
		}
		try {
			redisService.del(String.valueOf(userId));
			userService.deleteUserAll(userId);
		} catch (Exception e) {
			e.printStackTrace();
			str = "error";
			return str;
		}
		str = "success";
		return str;
	}
	
	
	/**
	 * 自动创建大量金钱的VIP用户
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/vipvipvipuser", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String vipvipvipuser(HttpServletRequest request,Integer validate){
		String str = null;
		if(validate==null){
			str = "危险权限-暂时隐藏";
			return str;
		}
		if(validate==888){
			try {
				userService.createUserAll();
			} catch (Exception e) {
				str = "出错";
				return str;
			}
			str = "success";
			return str;
		}
		str = "危险权限-暂时隐藏";
		return str;
	}
	
	/**
	 * 假的霸屏上线-就是要你抢
	 * @return
	 */
	@RequestMapping(value="/unrealVipLogin")
	public void unrealVipLogin(HttpServletRequest request){
		int userId = ThreadLocalRandom.current().nextInt(1000,1099);
		Users user = userService.findById(userId);
		String nickName = user.getNickName();
		
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushVipLoginEffectType);
		extras.put("userId", String.valueOf(userId));
		JPushUtil.sendAllMessage(nickName,extras,1800);
		user.setLoginTime(new Date());
		userService.updateUser(user);
//		统计每日vipEffect霸屏上线次数
		handleCountService.handleCountPlusOne("vipEffect");
	}
	
	
	/**
	 * 用户反馈留言
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/userFeedBack")
	public String userFeedBack(HttpServletRequest request,Integer pageNo,Integer status){
		if(status==null||"".equals(status)){
			status=0;
		}
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=feedbackService.findCountByStatus(status);
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/userFeedBack");	
		request.setAttribute("pageNo", pageNo);	
		request.setAttribute("status", status);	
		request.setAttribute("condition", "&status="+status);	
		
		List<Feedback> list = feedbackService.findAllByStatus(pageNo,status);
		
		request.setAttribute("list", list);
		return "userPage/feedBack";
	}
	
	
	
	
	
	
	/**
	 * json获取留言详情
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feedBackInfo")
	public Map<String,Object> feedBackInfo(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id==null||"".equals(id)){
			map.put("code", 0);
			map.put("msg", "id错误!");
			return map;
		}
		Feedback fd = feedbackService.findById(id);
		if(fd==null){
			map.put("code", 0);
			map.put("msg", "该反馈不存在，请刷新页面!");
			return map;
		}
		map.put("content", fd.getContent());
		map.put("id", fd.getId());
		map.put("code", 1);
		map.put("msg", "success");
		map.put("userId", fd.getUser().getId());
		return map;
	}
	
	
	/**
	 * 处理反馈留言
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dealwith")
	public Map<String,Object> dealwith(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id==null||"".equals(id)){
			map.put("code", 0);
			map.put("msg", "id错误!useId错误");
			return map;
		}
		Feedback fd = feedbackService.findById(id);
		if(fd==null){
			map.put("code", 0);
			map.put("msg", "该反馈不存在，请刷新页面!");
			return map;
		}
		fd.setDealTime(new Date());
		fd.setStatus(2);
		feedbackService.updateFB(fd);
		
		Feedback feedback = feedbackService.findById(id);
		
		if(feedback!=null){
			/*Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendFeedback);
			extras.put("userId", String.valueOf(feedback.getUser().getId()));
			extras.put("status", "2");
			//发送通知
			JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,86400);*/
			String extras="{\"type\":\""+StaticKey.JPushSendFeedback+"\","
        			+ "\"userId\":\""+
        			String.valueOf(feedback.getUser().getId())+
			 "\",\"status\":\"2\"}";
		
			// 添加附加信息
			MobilePushAction mobilePushAction2=new MobilePushActionProxy();
			try {
				mobilePushAction2.send(StaticKey.AliPushMessage, "用户反馈留言结果通知", extras,86400);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		
		
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	

	/**
	 * 处理反馈留言
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/passFeedback")
	public Map<String,Object> passFeedback(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id==null||"".equals(id)){
			map.put("code", 0);
			map.put("msg", "id错误!");
			return map;
		}
		Feedback fd = feedbackService.findById(id);
		if(fd==null){
			map.put("code", 0);
			map.put("msg", "该反馈不存在，请刷新页面!");
			return map;
		}
		fd.setDealTime(new Date());
		fd.setStatus(3);
		feedbackService.updateFB(fd);
		
		Feedback feedback = feedbackService.findById(id);
		if(feedback!=null){
			/*Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendFeedback);
			extras.put("userId", String.valueOf(feedback.getUser().getId()));
			extras.put("status", "3");*/
			//发送通知
			//JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,86400 );

			String extras="{\"type\":\""+StaticKey.JPushSendFeedback+"\","
        			+ "\"userId\":\""+
        			String.valueOf(feedback.getUser().getId())+
			 "\",\"status\":\"3\"}";
			System.out.println(extras+"========");
			// 添加附加信息
			MobilePushAction mobilePushAction2=new MobilePushActionProxy();
			try {
				mobilePushAction2.send(StaticKey.AliPushMessage, "用户反馈留言结果通知", extras,86400);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 已发放奖励，
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feedBackGrant")
	public Map<String,Object> feedBackGrant(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id==null||"".equals(id)){
			map.put("code", 0);
			map.put("msg", "id错误!");
			return map;
		}
		Feedback fd = feedbackService.findById(id);
		if(fd==null){
			map.put("code", 0);
			map.put("msg", "该反馈不存在，请刷新页面!");
			return map;
		}
		fd.setDealTime(new Date());
		fd.setStatus(4);
		feedbackService.updateFB(fd);
		
		
		Feedback feedback = feedbackService.findById(id);
		
		if(feedback!=null){
			Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendFeedback);
			extras.put("userId", String.valueOf(feedback.getUser().getId()));
			extras.put("status", "4");
			//发送通知
			JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,86400 );
		}
		
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 用户原创文章列表
	 * 
	 */
	
	@RequestMapping(value="/userSelfArticle")
	public String userSelfArticle(HttpServletRequest request,Integer pageNo,Integer searchType){
		Map<String,Object> map = new HashMap<>();
		String condition="";
		if(searchType!=null&&!("".equals(searchType))){
			map.put("searchType", searchType);
			condition+="&searchType="+searchType;
		}
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		
		count=articleService.findUserSelfCount(map);
		
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/userSelfArticle");	
		request.setAttribute("pageNo", pageNo);	
		
		request.setAttribute("condition", condition);
		request.setAttribute("searchType", searchType);
		
//		request.setAttribute("condition", "");	
		
		List<Article> list = articleService.findUserSelfList(pageNo,map);
		request.setAttribute("list", list);
		return "userPage/userSelfArticle";
	}
	
	
	/**
	 * 通过id获取video信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getVideoInfo")
	public Map<String,Object>  getVideoInfo(Integer id){
		Map<String,Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if(video==null){
			map.put("code", 0);
			map.put("msg", "改视频不存在!");
			return map;
		}
		map.put("url", video.getVideoUrl());
		map.put("id", video.getId());
		map.put("code", 1);
		return map;
	}
	
	
	/**
	 * video通过审核
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/passVideo")
	public Map<String,Object>  passVideo(Integer id){
		Map<String,Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if(video==null){
			map.put("code", 0);
			map.put("msg", "改视频不存在!");
			return map;
		}
		video.setStatus(StaticKey.VideoStatusTrue);
		videoService.updateVideo(video);
		map.put("code", 1);
		return map;
	}
	
	
	/**
	 * 用户原创视频列表
	 * 
	 */
	
	@RequestMapping(value="/userSelfVideo")
	public String userSelfAVideo(HttpServletRequest request,Integer pageNo,Integer searchType){
		
		
		/*if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		
		count=videoService.findUserSelfCount();
		
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
	   		request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/userSelfVideo");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "");	
	   	
		List<Video> list = videoService.findUserSelfList(pageNo);
		request.setAttribute("list", list);
		return "userPage/userSelfVideo";
	}*/
	
	
	Map<String,Object> map = new HashMap<>();
	String condition="";
	if(searchType!=null&&!("".equals(searchType))){
		map.put("searchType", searchType);
		condition+="&searchType="+searchType;
	}
	
	if(pageNo==null){
		pageNo=page;
	}else{
		pageNo=pageNo>1?pageNo:page;
	}
	Integer count = null;
	
	count=videoService.findUserSelfCount(map);
	
	if(count==null){
		request.setAttribute("count",0);
		request.getSession().setAttribute("count", 0);
	}else{
		request.setAttribute("count", (int)(Math.ceil((double)count/10)));
		request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
	}
	request.setAttribute("url", "/sys/userSelfVideo");	
	request.setAttribute("pageNo", pageNo);	
	
	request.setAttribute("condition", condition);
	request.setAttribute("searchType", searchType);
	
//	request.setAttribute("condition", "");	
	
	List<Video> list = videoService.findUserSelfList(pageNo,map);
	request.setAttribute("list", list);
	return "userPage/userSelfVideo";
}
	

	/**
	 * 获取文章详情
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/articleInfo")
	public Map<String,Object> articleInfo(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		Article article = articleService.findById(id);
		if(article==null){
			map.put("code", 0);
			map.put("msg", "文章不存在!");
			return map;
		}
		map.put("title", article.getTitle());
		map.put("content", article.getContent());
		map.put("imgs", article.getImgList());
		map.put("id", article.getId());
		map.put("status", article.getStatus());
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 判定当前原创文章是否通过审核
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/passOriginalArticle")
	public Map<String,Object> passOriginalArticle(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		Article article = articleService.findById(id);
		if(article==null){
			map.put("code", 0);
			map.put("msg", "该文章不存在，请重新刷新列表!");
			return map;
		}
		if(article.getType()!=1){
			map.put("code", 0);
			map.put("msg", "该文章不是原创作品，请重新刷新列表!");
			return map;
		}
		
		if(article.getStatus()==StaticKey.VideoStatusTrue){
			map.put("code", 0);
			map.put("msg", "已经通过审核了，别点了！");
			return map;
		}
		
		article.setStatus(StaticKey.ArticleStatusTrue);
		Users user = userService.findById(article.getSourceId());
        OriginalArticleInfo oai = originalArticleInfoService.findByArticleId(id);
        if(oai ==null){
          oai=new OriginalArticleInfo();
          oai.setAddTime(new Date());
          oai.setArticle(article);
          oai.setReadCount(0);
          oai.setShareCount(0);
         if(user!=null&&user.getVipStatus()==StaticKey.UserVipStatusTrue){
        	 oai.setCountMoney(StaticKey.OriginalTotalMoney*2);
         }else{
        	 oai.setCountMoney(StaticKey.OriginalTotalMoney);
         }
          oai.setPassTime(new Date());
        }else{
        	oai.setPassTime(new Date());
        }
        articleService.updateArticle(article);
        originalArticleInfoService.updateOAI(oai);
        
       Article findById = articleService.findById(id);
        System.err.println("tongguo le ");
        System.out.println(findById);
		if(findById!=null){
			/*Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			extras.put("type", StaticKey.JPushSendOriginal);
			extras.put("userId", String.valueOf(findById.getSourceId()));
			extras.put("status", "1");
			//发送通知
			JPushUtil.sendAllMessage("用户原创审核结果通知", extras,86400 );*/
			String extras="{\"type\":\""+StaticKey.JPushSendOriginal+"\","
        			+ "\"userId\":\""+
        			String.valueOf(findById.getSourceId())+
			 "\",\"status\":\"1\"}";
			System.out.println(extras+"========");
			// 添加附加信息
			MobilePushAction mobilePushAction2=new MobilePushActionProxy();
			try {
				mobilePushAction2.send(StaticKey.AliPushMessage, "用户原创审核结果通知", extras,86400);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
        
//		统计每日分润料币总金额 (取消发表原创,统计料币)
//		handleCountService.handleCountTotalMoney("totalProfitMoney",StaticKey.passArticleMoney);
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 封禁原创文章
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/banOriginalArticle")
	public Map<String,Object> banOriginalArticle(HttpServletRequest request,Integer id,Integer type){
		Map<String,Object> map = new HashMap<>();
		Article article =articleService.findById(id);
		if(article==null){
			map.put("code", 0);
			map.put("msg", "文章不存在，请重新刷新!");
			return map;
		}
		if(type==1){
			article.setStatus(StaticKey.ArticleStatusFalse);
			map.put("msg", "封禁成功!");
		}else if(type==2){
			article.setStatus(StaticKey.ArticleStatusTrue);
			map.put("msg", "解封成功!");
		}
		articleService.updateArticle(article);
		map.put("code", 1);
		
		return map;
	}
	
	
	

	/**
	 * 获取视频详情
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/videoInfo")
	public Map<String,Object> videoInfo(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if(video==null){
			map.put("code", 0);
			map.put("msg", "视频不存在!");
			return map;
		}
		map.put("title", video.getTitle());
		map.put("id", video.getId());
		map.put("src", video.getVideoUrl());
		map.put("status", video.getStatus());
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 判定当前原创视频是否通过审核
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/passOriginalVideo")
	public Map<String,Object> passOriginalVideo(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		Video video = videoService.findById(id);
		if(video==null){
			map.put("code", 0);
			map.put("msg", "该视频不存在，请重新刷新列表!");
			return map;
		}
		if(video.getType()!=1){
			map.put("code", 0);
			map.put("msg", "该视频不是原创作品，请重新刷新列表!");
			return map;
		}
		if(video.getStatus()==StaticKey.VideoStatusTrue){
			map.put("code", 0);
			map.put("msg", "已经通过审核了，别点了！");
			return map;
		}
		video.setStatus(StaticKey.VideoStatusTrue);
		Users user = userService.findById(video.getSourceId());
        OriginalVideoInfo ovi = originalVideoInfoService.findByVideoId(id);
        if(ovi ==null){
        	ovi=new OriginalVideoInfo();
        	ovi.setVideo(video);
        	ovi.setPassTime(new Date());
        	ovi.setAddTime(new Date());
        	ovi.setReadCount(0);
        	ovi.setShareCount(0);
        	if(user!=null&&user.getVipStatus()==StaticKey.UserVipStatusTrue){
        		ovi.setCountMoney(StaticKey.OriginalTotalMoney*2);
	        }else{
	        	ovi.setCountMoney(StaticKey.OriginalTotalMoney);
	        }
        }else{
        	ovi.setPassTime(new Date());
        }
        videoService.updateVideo(video);
        originalVideoInfoService.updateOrSaveOVI(ovi);
        
        
        Video findById = videoService.findById(id);
		if(findById!=null){
			/*Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendOriginal);
			extras.put("userId", String.valueOf(findById.getSourceId()));
			extras.put("status", "1");
			//发送通知
			JPushUtil.sendAllMessage("用户原创审核结果通知", extras,86400 );*/
			String extras="{\"type\":\""+StaticKey.JPushSendOriginal+"\","
        			+ "\"userId\":\""+
        			String.valueOf(findById.getSourceId())+
			 "\",\"status\":\"1\"}";
			System.out.println(extras+"========");			
			MobilePushAction mobilePushAction2=new MobilePushActionProxy();
			try {
				mobilePushAction2.send(StaticKey.AliPushMessage, "用户原创审核结果通知", extras,86400);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
        
        
        
        
        
        
//		统计每日分润料币总金额(取消发表原创获得料币)
//		handleCountService.handleCountTotalMoney("totalProfitMoney",StaticKey.passVideoMoney);
		map.put("code", 1);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 封禁、解封原创视频
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/banOriginalVideo")
	public Map<String,Object> banOriginalVideo(HttpServletRequest request,Integer id,Integer type){
		Map<String,Object> map = new HashMap<>();
		Video video =videoService.findById(id);
		if(video==null){
			map.put("code", 0);
			map.put("msg", "文章不存在，请重新刷新!");
			return map;
		}
		if(type==1){
			video.setStatus(StaticKey.VideoStatusFalse);
			map.put("msg", "封禁成功!");
		}else{
			video.setStatus(StaticKey.VideoStatusTrue);
			map.put("msg", "解封成功!");
		}
		videoService.updateVideo(video);
		map.put("code", 1);
		map.put("msg", "封禁成功!");
		return map;
	}
	
	/**
	 * 用户评论展示
	 */
	@RequestMapping("/userBostcastLog")
	public String userBostcastLog(HttpServletRequest request,Integer pageNo){
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		request.setAttribute("url", "/sys/userBostcastLog");	
		request.setAttribute("pageNo", pageNo);	
		
		List<BroadcastLog> bList = broadcastLogService.userBostcastLog(pageNo);
		request.setAttribute("bList", bList);
		return "userPage/userBostcastLog";
	}
	
	/**
	 * 删除未通过审核,禁封的用户原创文章
	 */
	@ResponseBody
	@RequestMapping(value="/delOriginalArticle")
	public Map<String,Object> delOriginalArticle(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<>();
		
		Integer flag = articleService.delOriginalArticle(id);//flag:   0:删除成功.  1:删除失败.
		if(flag==0){
			map.put("flag",flag);
			map.put("msg", "删除成功!");
		}
		if(flag==1){
			map.put("flag", flag);
			map.put("msg", "删除失败!");
		}
		return map;
	}
	
	
	/**
	 * 删除 未通过审核,禁封  的用户原创视频
	 */
	@ResponseBody
	@RequestMapping(value="/delOriginalVideo")
	public Map<String,Object> delOriginalVideo(HttpServletRequest request,Integer id,Integer type){
		Map<String,Object> map = new HashMap<>();
		
		Integer flag = videoService.delOriginalVideo(id);//flag:   0:删除成功.  1:删除失败.
		if(flag==0){
			map.put("flag", 0);
			map.put("msg", "删除成功!");
		}
		if(flag==1){
			map.put("flag", 1);
			map.put("msg", "删除失败!");
		}
		return map;
	}
	
	
	/**
	 * 料币收支明细
	 */
	//@ResponseBody
	@RequestMapping(value="/expenditureDetails")
	public String expenditureDetails(HttpServletRequest request,Integer userId){
		Map<String,Object> data = new HashMap<>();
		List<FenrunLog> expenditureDetailsList = null;
		if(userId != null){
			expenditureDetailsList = fenrunLogService.expenditureDetails(userId);
		}
		
		if(expenditureDetailsList != null){
			data.put("expenditureDetailsList",expenditureDetailsList);
			data.put("code", 200);
			data.put("msg", "成功获得料币明细！");
		}else{
			data.put("code", -1);
			data.put("msg","该用户没有获得过料币！" );
		}
		request.setAttribute("expenditureDetailsList", expenditureDetailsList);
		return "userPage/expenditureDetailsList";
	}
	//用户阅读记录
	@RequestMapping(value="/readHistory")
	public String readHistory(HttpServletRequest request,Integer userId,Integer pageNo){
		List<ReadHistory> list=null;
		List<Map<String, String>> maps=new ArrayList<Map<String,String>>();
		Map<String, String> map=null;
		Integer count = null;
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		String condition="";
		if(userId!=null&&!("".equals(userId))){
			condition+="&userId="+userId;
		}
			list=readHistoryService.queryAll(userId,pageNo);
			count=readHistoryService.findCount(userId);
			request.setAttribute("url", "/sys/readHistory");	
			request.setAttribute("condition", condition);
			request.setAttribute("userId", userId);
			request.setAttribute("pageNo", pageNo);	
		
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/15)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/15)));
		}
		Users users2=null;
		String title=null;
		String dyte=null;
		for (ReadHistory readHistory : list) {
			map=new HashMap<String,String>();
			Video video=videoService.findById(readHistory.getArticleId());
			if(video==null){
				Article article=articleService.findById(readHistory.getArticleId());
				title=article.getTitle();
			}else{
				title=video.getTitle();
			}
			Integer type=readHistory.getType();
			if(type==0){
				dyte="文章";
			}else if (type==1) {
				dyte="视频";
			}
			users2=userService.queryOne(readHistory.getUserId());
			if(users2==null){
			map.put("read", "--");
			}else{
			map.put("read", users2.getNickName());
			}
			if(readHistory.getNum()==null){
				map.put("num", "1");
			}else{
				map.put("num", readHistory.getNum().toString());
			}
			map.put("title", title);
			map.put("type", dyte);
			int s=0;
			if(readHistory.getMoney()==null){
				readHistory.setMoney(0.0);
			}else{
				s=readHistory.getMoney().intValue();
			}
			map.put("money", s+"");
			map.put("addDate", readHistory.getAddDate().toString());
			maps.add(map);
		}
		request.setAttribute("list", maps);
		return "userPage/readHistory";
	}
	
	//关键字记录
			@RequestMapping(value="/keyWordsHistory")
			public String keyWordsHistory(HttpServletRequest request,Integer userId,Integer pageNo){
				Map<String,Object> map = new HashMap<>();
				String condition="";
				if(userId!=null&&!("".equals(userId))){
					map.put("userId", userId);
					condition+="&userId="+userId;
				}
				List<KeyWords> lists=null;
				Integer count = null;
				if(pageNo==null){
					pageNo=page;
				}else{
					pageNo=pageNo>1?pageNo:page;
				}
					lists=keyWordsService.queryAll(userId,pageNo);
					count=keyWordsService.findCount(userId);
					request.setAttribute("url", "/sys/keyWordsHistory");
					request.setAttribute("condition", condition);
					request.setAttribute("pageNo", pageNo);
				if(count==null){
					request.setAttribute("count",0);
					request.getSession().setAttribute("count", 0);
				}else{
					request.setAttribute("count", (int)(Math.ceil((double)count/15)));
					request.getSession().setAttribute("count", (int)(Math.ceil((double)count/15)));
				}
				request.setAttribute("lists", lists);
				return "userPage/keyWords";
			}
			
			//今日推荐
			@RequestMapping(value="/recommend")
			public String todayRecommend(HttpServletRequest request){
				List<Article> list=articleService.findByStatus(StaticKey.ArticleStatusTui);
				List<Video> vlist=videoService.findByStatus(StaticKey.VideoStatusTui);
				List<String> aList=new ArrayList<String>();
				if(vlist!=null){
					for (Video video : vlist) {
						aList.add(video.getTitle());
					}
				}
				
				if(list!=null){
				for (Article article : list) {
					aList.add(article.getTitle());
				}
				}
				request.setAttribute("list", aList);
				return "userPage/recommend";
			}
			//登录app设备记录
			@RequestMapping("/userDeviceTypeHistory")
			@ResponseBody
			public Map<String,Object> userDeviceTypeHistory(HttpServletRequest request ,Integer userId,Integer deviceType){
				Map<String, Object> map=new HashMap<String,Object>();
				Users user=userService.queryOne(userId);
				if(user==null||user.equals("")){
					map.put("msg", "没有该用户.");
					map.put("code",StaticKey.ReturnUserAccountNotExist);
					return map;
				}
				user.setDeviceType(deviceType);
				userService.updateUser(user);
				map.put("msg", "用户使用的设备是:"+deviceType);
				map.put("code","0");
				return map;
			}
			
			//购买vip
			@RequestMapping("/UserBuyVIP")
			@ResponseBody
			public Map<String,Object> UserBuyVIP(HttpServletRequest request ,Integer userId){
				Map<String, Object> map=new HashMap<String,Object>();
				Users user=userService.queryOne(userId);
				if(user==null||user.equals("")){
					map.put("msg", "没有该用户.");
					map.put("code",StaticKey.ReturnUserAccountNotExist);
					return map;
				}
				if(user.getVipStatus().equals(StaticKey.UserVipStatusTrue)){
					map.put("msg", "您已经是VIP会员");
					map.put("code","1");
				}else{
					user.setVipStatus(StaticKey.UserVipStatusTrue);
					userService.updateUser(user);
					map.put("msg", "恭喜您成为VIP会员.");
					map.put("code","0");
				}
				return map;
			}
			/**
			 * 通过用户得到用户的基本信息
			 * @param request
			 * @param userId
			 * @return
			 */
			@RequestMapping("/getUserById")
			@ResponseBody
		public Map<String, Object> getUser(HttpServletRequest request ,Integer userId){
			Users users=userService.queryOne(userId);
			Map<String, Object> map=new HashMap<String,Object>();
			if(users==null){
				map.put("code",1);
				map.put("msg","无该用户.");
			}else{
				List<WeixinPayLog> list=weixinPayLogService.findAllByUserAndType(users);
				if(list.size()==0){
					map.put("status", 1);
				}else{
					map.put("status", 0);
				}
				map.put("code",0);
				map.put("msg","信息如下:");
				map.put("nickName", users.getNickName());
				map.put("userImg",users.getAvatar());
				map.put("yuMoney", users.getTotalMoney());
			}
			return map;
			
		}
}

package com.liaoliao.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Feedback;
import com.liaoliao.content.entity.OriginalArticleInfo;
import com.liaoliao.content.entity.OriginalVideoInfo;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.FeedbackService;
import com.liaoliao.content.service.OriginalArticleInfoService;
import com.liaoliao.content.service.OriginalVideoInfoService;
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
import com.liaoliao.user.entity.Users;
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
			Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendFeedback);
			extras.put("userId", String.valueOf(feedback.getUser().getId()));
			extras.put("status", "2");
			//发送通知
			JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,1800 );
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
			Map<String, String> extras = new HashMap<String,String>();
			//状态,用户id
			
			extras.put("type", StaticKey.JPushSendFeedback);
			extras.put("userId", String.valueOf(feedback.getUser().getId()));
			extras.put("status", "3");
			//发送通知
			JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,1800 );
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
			JPushUtil.sendAllMessage("用户反馈留言结果通知", extras,1800 );
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
//		统计每日分润料币总金额
		handleCountService.handleCountTotalMoney("totalProfitMoney",StaticKey.passArticleMoney);
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
	 * 判定当前原创文章是否通过审核
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
//		统计每日分润料币总金额
		handleCountService.handleCountTotalMoney("totalProfitMoney",StaticKey.passVideoMoney);
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
//		request.getSession().setAttribute("expenditureDetailsList", expenditureDetailsList);
		request.setAttribute("expenditureDetailsList", expenditureDetailsList);
//		request.setAttribute("data", data);
		//return "userPage/userList";
		//return expenditureDetailsList;
		return "userPage/expenditureDetailsList";
		
	}
}

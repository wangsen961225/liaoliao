package com.liaoliao.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.liaoliao.common.service.CommonService;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.OriginalArticleInfo;
import com.liaoliao.content.entity.OriginalVideoInfo;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.ContentKindService;
import com.liaoliao.content.service.OriginalArticleInfoService;
import com.liaoliao.content.service.OriginalVideoInfoService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.AppVersion;
import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.sys.entity.TaskLog;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.AdvertService;
import com.liaoliao.sys.service.AppVersionService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.InvitationService;
import com.liaoliao.sys.service.TaskLogService;
import com.liaoliao.sys.service.UserTaskService;
import com.liaoliao.user.entity.Thesaurus;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.FocusLogService;
import com.liaoliao.user.service.ThesaurusService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.GetAliyunSTS;
import com.liaoliao.util.MessageKit;
import com.liaoliao.util.RC4Kit;
import com.liaoliao.util.RandomKit;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;

@Controller
@RequestMapping(value="/share")
public class ShareAction {
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ThesaurusService thesaurusService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ContentKindService contentKindService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private OriginalArticleInfoService originalArticleInfoService;
	
	@Autowired
	private OriginalVideoInfoService originalVideoInfoService;
	
	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private TaskLogService taskLogService;
	
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private FocusLogService focusLogService;
	
	
	/**
	 * 获取文章页：
	 * @return
	 */
	@RequestMapping(value="/article/{idStr}")
	public String getContent(HttpServletRequest request,@PathVariable String idStr,String ua) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(idStr)){
			return "share/404null";
		}
		if(ua==null){
			String userAgent = request.getHeader("user-agent");
			if(userAgent.contains("Mac OS X")){
//				map.put("ua", "ios");
				return "redirect:https://app.127120.com:8443/share/article/"+idStr+"?ua=ios";
			}
//			else{
//				map.put("ua", "other");
//			}
//			map.put("idStr", idStr);
		}
		
		
//		RC4解密
		try {
			idStr = RC4Kit.decry_RC4(idStr, "liao");
		} catch (Exception e) {
			return "share/404null";
		}
		int id = 0;
		if(StringUtils.isNumeric(idStr) && !StringUtils.isBlank(idStr)){
			id = Integer.valueOf(idStr);
		}
		else{
			return "share/404null";
		}
		
		Article article=articleService.findById(id);
		if(article==null){
			return "share/404null";
		}
		
		String content = article.getContent();
		String topAdvert = advertService.findArticleTopAdvert();
		String topFloatAdvert = advertService.findArticleTopFloatAdvert();
		String bottomAdvert = advertService.findArticleBottomAdvert();
		map.put("topAdvert", topAdvert);
		map.put("topFloatAdvert", topFloatAdvert);
		map.put("bottomAdvert", bottomAdvert);
		map.put("content", content);
		map.put("title", article.getTitle());
		Users luser = userService.findById(StaticKey.liaoliaoArticleId);
		if(article.getType()==1){
			Users user = userService.findById(article.getSourceId());
			if(user!=null){
				Long focusNum = focusLogService.countNum(user.getId());
				if(focusNum==null){
					focusNum=(long) 0;
				}
				map.put("focusNum", focusNum);
				map.put("userId", user.getId());
				map.put("name", user.getNickName());
				map.put("avatar", user.getAvatar());
			}else{
				Long focusNum = focusLogService.countNum(luser.getId());
				if(focusNum==null){
					focusNum=(long) 0;
				}
				Integer num = focusNum.intValue()<3000?3425:focusNum.intValue();
				
				map.put("focusNum", num);
				map.put("userId", luser.getId());
				map.put("name", luser.getNickName());
				map.put("avatar", luser.getAvatar());
			}
		}else{
			Long focusNum = focusLogService.countNum(luser.getId());
			if(focusNum==null){
				focusNum=(long) 0;
			}
			Integer num = focusNum.intValue()<3000?3425:focusNum.intValue();
			map.put("focusNum", num);
			map.put("userId", luser.getId());
			map.put("name", luser.getNickName());
			map.put("avatar", luser.getAvatar());
		}
		AppVersion av = appVersionService.findMaxVersion();
		map.put("downloadUrl", av.getDownloadUrl());
		
//		更多推荐开始
		Video videoMore = null;
		Article articleMore = null;
		List imgListObjList = null;
		// Map-->List-->Map 三层转换
		List<Map<String, Object>> datas = new ArrayList<>();
		Map<String, Object> item = null;
		for (int i = 0; i < 3; i++) {
			imgListObjList = new ArrayList();
			
			videoMore = videoService.findByRand(1).get(0);
			articleMore = articleService.findByRand(1,1).get(0);
			
			item = new LinkedHashMap<>();
//			video
//			item.put("videoId", videoMore.getId());
			item.put("videoTitle", videoMore.getTitle());
//			item.put("videoDescription", videoMore.getDescription());
			item.put("videoImgUrl", videoMore.getImgUrl());
//			item.put("videoUrl", videoMore.getVideoUrl());
//			item.put("videoDuration", videoMore.getDuration());
//			item.put("videoCommentCount", video.getCommentCount());
//			item.put("videoPlayingCount", video.getPlayingCount());
			item.put("videoCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
			item.put("videoLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoPlayingCount", ThreadLocalRandom.current().nextInt(9000, 25000));
//			RC4加密
			String idStrMoreVideo = RC4Kit.encry_RC4_string(String.valueOf(videoMore.getId()), "liao");
			item.put("videoShareUrl", "/share/video/"+idStrMoreVideo);
//			article
//			item.put("articleId", articleMore.getId());
			item.put("articleTitle", articleMore.getTitle());
//			item.put("articleDescription", articleMore.getDescription());
//			item.put("articleImgUrl", articleMore.getImgUrl());
//			RC4加密
			String idStrMoreArticle = RC4Kit.encry_RC4_string(String.valueOf(articleMore.getId()), "liao");
			item.put("articleShareUrl", "/share/article/"+idStrMoreArticle);
			String imglistStr = articleMore.getImgList();
			if(!StringUtils.isBlank(imglistStr)){
				Map imgMap = (Map) JSONObject.parse(imglistStr);
				List imgListObj = (List) imgMap.get("imgList");
				Map<String, Object> imgListItem = null;
				if(imgListObj.size()>2){
					for (int j = 0; j < 3; j++) {
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
					}
				}
				if(imgListObj.size()==2){
					for (int j = 0; j < 3; j++) {
						if(j>1){
							imgListItem = new LinkedHashMap<>();
							String imgListUrl = String.valueOf(imgListObj.get(1));
							Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
							imgListItem.put("url", imgMapItem.get("url"));
							imgListObjList.add(imgListItem);
						}else{
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
						}
					}
				}
				if(imgListObj.size()==1){
					for (int j = 0; j < 3; j++) {
						if(j>0){
							imgListItem = new LinkedHashMap<>();
							String imgListUrl = String.valueOf(imgListObj.get(0));
							Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
							imgListItem.put("url", imgMapItem.get("url"));
							imgListObjList.add(imgListItem);
						}
						else{
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
						}
					}
				}
				
			}
			item.put("articleImgList", imgListObjList);
//			item.put("articleReadingCount", article.getReadingCount());
//			item.put("articleCommentCount", article.getCommentCount());
			item.put("articleReadingCount", ThreadLocalRandom.current().nextInt(9000, 25000));
			item.put("articleLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
//			Ad
			int key = i+1;
			String addd = "?timestamp="+System.currentTimeMillis()/1000L+"&articleId="+articleMore.getId()+"&videoId="+videoMore.getId()+"&random="+(int) (Math.random()*10000);
			if(key==1){
				item.put("advertUrl", advertService.findArticleMore1Advert());
			}
			if(key==2){
				item.put("advertUrl", advertService.findArticleMore2Advert());
			}
			if(key==3){
				item.put("advertUrl", advertService.findArticleMore3Advert());
			}
			datas.add(item);
		}
//		更多推荐结束
		
		map.put("msg", "success");
		map.put("type", 1);
		map.put("code", 0);
		map.put("list", datas);
		request.setAttribute("map",map);
		return "share/share";
	}
	
	
	/**
	 * 获取视频页：
	 * @return
	 */
	@RequestMapping(value="/video/{idStr}")
	public String getVideo(HttpServletRequest request,@PathVariable String idStr,String ua) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(idStr)){
			return "share/404null";
		}
		
		if(ua==null){
			String userAgent = request.getHeader("user-agent");
			if(userAgent.contains("Mac OS X")){
//				map.put("ua", "ios");
				return "redirect:https://app.127120.com:8443/share/video/"+idStr+"?ua=ios";
			}
//			else{
//				map.put("ua", "other");
//			}
//			map.put("idStr", idStr);
		}
		
//		RC4解密
		try {
			idStr = RC4Kit.decry_RC4(idStr, "liao");
		} catch (Exception e) {
			return "share/404null";
		}
		int id = 0;
		if(StringUtils.isNumeric(idStr) && !StringUtils.isBlank(idStr)){
			id = Integer.valueOf(idStr);
		}
		else{
			return "share/404null";
		}
		
		Video video = videoService.findById(id);
		if(video==null){
			return "share/404null";
		}
		
//		String cssStyle = "<style type=\"text/css\">img{width:100%;text-align:center;}body{font-size:15px}p{margin:30px 30px;}</style>";
//		String content = article.getContent()+cssStyle;
		String videoUrl = video.getVideoUrl();
//		if(videoUrl.contains("neihan")){
//			Document doc;
//			try {
//				doc = Jsoup.connect(videoUrl).ignoreContentType(true).get();
//				videoUrl = doc.location();
////				videoUrl = doc.baseUri();
//			} catch (IOException e) {
//				System.out.println("分享视频解析异常(内涵段子?)");
//			}
//		}
		String videoAva = video.getImgUrl();
		String topAdvert = advertService.findArticleTopAdvert();
		String topFloatAdvert = advertService.findArticleTopFloatAdvert();
		String bottomAdvert = advertService.findArticleBottomAdvert();
		map.put("topAdvert", topAdvert);
		map.put("topFloatAdvert", topFloatAdvert);
		map.put("bottomAdvert", bottomAdvert);
		map.put("videoUrl", videoUrl);
		map.put("videoAva", videoAva);
		map.put("title", video.getTitle());
		
		Users luser = userService.findById(StaticKey.liaoliaoVideoId);
		if(video.getType()==1){
			Users user = userService.findById(video.getSourceId());
			if(user!=null){
				Long focusNum = focusLogService.countNum(user.getId());
				if(focusNum==null){
					focusNum=(long) 0;
				}
				map.put("focusNum", focusNum);
				map.put("userId", user.getId());
				map.put("name", user.getNickName());
				map.put("avatar", user.getAvatar());
			}else{
				Long focusNum = focusLogService.countNum(luser.getId());
				if(focusNum==null){
					focusNum=(long) 0;
				}
				Integer num = focusNum.intValue()<3000?3564:focusNum.intValue();
				map.put("focusNum", num);
				map.put("userId", luser.getId());
				map.put("name", luser.getNickName());
				map.put("avatar", luser.getAvatar());
			}
		}else{
			Long focusNum = focusLogService.countNum(luser.getId());
			if(focusNum==null){
				focusNum=(long) 0;
			}
			Integer num = focusNum.intValue()<3000?3564:focusNum.intValue();
			map.put("focusNum", num);
			map.put("userId", luser.getId());
			map.put("name", luser.getNickName());
			map.put("avatar", luser.getAvatar());
		}
		AppVersion av = appVersionService.findMaxVersion();
		map.put("downloadUrl", av.getDownloadUrl());
//		更多推荐开始
		Video videoMore = null;
		Article articleMore = null;
		List imgListObjList = null;
		// Map-->List-->Map 三层转换
		List<Map<String, Object>> datas = new ArrayList<>();
		Map<String, Object> item = null;
		for (int i = 0; i < 3; i++) {
			imgListObjList = new ArrayList();
			
			videoMore = videoService.findByRand(1).get(0);
			articleMore = articleService.findByRand(1,1).get(0);
			
			item = new LinkedHashMap<>();
//			video
//			item.put("videoId", videoMore.getId());
			item.put("videoTitle", videoMore.getTitle());
//			item.put("videoDescription", videoMore.getDescription());
			item.put("videoImgUrl", videoMore.getImgUrl());
//			item.put("videoUrl", videoMore.getVideoUrl());
//			item.put("videoDuration", videoMore.getDuration());
//			item.put("videoCommentCount", video.getCommentCount());
//			item.put("videoPlayingCount", video.getPlayingCount());
			item.put("videoCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
			item.put("videoLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoPlayingCount", ThreadLocalRandom.current().nextInt(9000, 25000));
//			RC4加密
			String idStrMoreVideo = RC4Kit.encry_RC4_string(String.valueOf(videoMore.getId()), "liao");
			item.put("videoShareUrl", "/share/video/"+idStrMoreVideo);
//			article
//			item.put("articleId", articleMore.getId());
			item.put("articleTitle", articleMore.getTitle());
//			item.put("articleDescription", articleMore.getDescription());
//			item.put("articleImgUrl", articleMore.getImgUrl());
//			RC4加密
			String idStrMoreArticle = RC4Kit.encry_RC4_string(String.valueOf(articleMore.getId()), "liao");
			item.put("articleShareUrl", "/share/article/"+idStrMoreArticle);
			String imglistStr = articleMore.getImgList();
			if(!StringUtils.isBlank(imglistStr)){
				Map imgMap = (Map) JSONObject.parse(imglistStr);
				List imgListObj = (List) imgMap.get("imgList");
				Map<String, Object> imgListItem = null;
				if(imgListObj.size()>2){
					for (int j = 0; j < imgListObj.size(); j++) {
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
					}
				}
				if(imgListObj.size()==2){
					for (int j = 0; j < 3; j++) {
						if(j>1){
							imgListItem = new LinkedHashMap<>();
							String imgListUrl = String.valueOf(imgListObj.get(1));
							Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
							imgListItem.put("url", imgMapItem.get("url"));
							imgListObjList.add(imgListItem);
						}else{
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
						}
					}
				}
				if(imgListObj.size()==1){
					for (int j = 0; j < 3; j++) {
						if(j>0){
							imgListItem = new LinkedHashMap<>();
							String imgListUrl = String.valueOf(imgListObj.get(0));
							Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
							imgListItem.put("url", imgMapItem.get("url"));
							imgListObjList.add(imgListItem);
						}
						else{
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(j));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
						}
					}
				}
				
			}
			item.put("articleImgList", imgListObjList);
//			item.put("articleReadingCount", article.getReadingCount());
//			item.put("articleCommentCount", article.getCommentCount());
			item.put("articleReadingCount", ThreadLocalRandom.current().nextInt(9000, 25000));
			item.put("articleLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
//			Ad
			int key = i+1;
			String addd = "?timestamp="+System.currentTimeMillis()/1000L+"&articleId="+articleMore.getId()+"&videoId="+videoMore.getId()+"&random="+(int) (Math.random()*10000);
			if(key==1){
				item.put("advertUrl", advertService.findArticleMore1Advert());
			}
			if(key==2){
				item.put("advertUrl", advertService.findArticleMore2Advert());
			}
			if(key==3){
				item.put("advertUrl", advertService.findArticleMore3Advert());
			}
			datas.add(item);
		}
		
		
		
//		更多推荐结束
		
		map.put("msg", "success");
		map.put("type", 2);
		map.put("code", 0);
		map.put("list", datas);
		request.setAttribute("map",map);
		return "share/share";
	}
	
	
	
	/**
	 * 判断手机号是否注册
	 */
	@RequestMapping(value="/existPhone",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> existPhone(HttpServletRequest request,String mobile){
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isBlank(mobile)){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数错误");
			return map;
		}
		Users user= userService.findByMobile(mobile);
		if(user!=null){
			map.put("code", StaticKey.ReturnUserAccountExist);
			map.put("msg", "该手机号码已被注册");
			return map;
		}
		String authCode = RandomKit.messageCode();
		request.getSession().setAttribute("authCode", authCode);
		request.getSession().setAttribute("mobile", mobile);
		String msg="亲爱的，您的验证码是:"+authCode+"。有效期为15分钟，料料君在等你哦~"+"【料料】";
		MessageKit.sendMessage(msg,mobile);
		map.put("code", StaticKey.ReturnServerTrue);
//		统计每日msgCode发送量
		handleCountService.handleCountPlusOne("msgCode");
		return map; 
	}
	
	
	/**
	 * 邀请提交，注册成功，获得分润
	 * @return
	 */
	@RequestMapping(value="/invitationPost",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> invitationPost(HttpServletRequest request,Integer userId,
			String mobile,String passWord,String authCode){
		
		Map<String,Object> map = new HashMap<>();
		HttpSession session = request.getSession();
		if(mobile==null||"".equals(mobile)||StringUtils.isBlank(passWord)||StringUtils.isBlank(authCode)){
			map.put("msg", "参数异常!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		Users userHas = userService.findByMobile(mobile);
		if(userHas!=null){
			map.put("msg", "该用户已注册,请直接登录");
			map.put("code", StaticKey.ReturnUserAccountExist);
			return map;
		}
		
		String lastMobile = (String) session.getAttribute("mobile");
		String lastAuthCode = (String) session.getAttribute("authCode");
		if(lastMobile==null){
			map.put("msg", "session失效");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!lastMobile.equals(mobile)){
			map.put("msg", "恶意注册");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!lastAuthCode.equals(authCode)){
			map.put("msg", "验证码输入错误");
			map.put("code", StaticKey.ReturnUserAuthCodeError);
			return map;
		}
		
		String passwordMD5 = CommonUtil.md5(mobile+passWord);
		String passwordSHA1 = CommonUtil.sha1(mobile+passwordMD5+"shijie");
		Users newUser = new Users();
		newUser.setMobile(mobile);
		newUser.setPassWord(passwordSHA1);
		newUser.setNickName(RandomKit.randomName());
		newUser.setDayMoney(StaticKey.UserDefaultMoney);
		newUser.setFreezeMoney(StaticKey.UserDefaultMoney);
		newUser.setTotalMoney(StaticKey.UserDefaultMoney);
		newUser.setPayMoney(StaticKey.UserDefaultMoney);
		newUser.setTobankMoney(StaticKey.UserDefaultMoney);
		newUser.setUnselfMoney(StaticKey.UserDefaultMoney);
		newUser.setStatus(StaticKey.UserStatusTrue);
		newUser.setVipStatus(StaticKey.UserVipStatusFalse);
		newUser.setSourceType(StaticKey.RegisterByMobile);
		newUser.setAddIp(CommonUtil.getIpAddr(request));
		newUser.setAddTime(new Date());
		
		if(userId!=null){
			Users user = userService.findById(userId);
			if(user != null){
//				设置上级
				newUser.setParent(user);
//				新建新用户
				userService.saveUser(newUser);
//				FenrunLog fenrunLog = new FenrunLog();
//				fenrunLog.setUser(user);
//				fenrunLog.setType(StaticKey.FenrunInvitation);
//				fenrunLog.setMoney(StaticKey.InvitationMoney);
//				fenrunLog.setAddTime(new Date());
				
//				邀请好友得料币数
				int invitationMoney = 0;
				String invitationMoneyStr = redisService.getConfigValue("invitationMoney");
				if(StringUtils.isBlank(invitationMoneyStr)){
					System.out.println("请检查SystemConfig表数据");
				}else{
					invitationMoney = Integer.valueOf(invitationMoneyStr);
				}
				
//				user.setTotalMoney(user.getTotalMoney()+invitationMoney);
//				commonService.Invitation(user,fenrunLog,newUser);
				
//				邀请表添加数据
				Invitation invitation = new Invitation();
				invitation.setChild(newUser);
				invitation.setParent(user);
				invitation.setStatus(StaticKey.InvitationStatusFalse);
				invitation.setMoney(invitationMoney);
				invitation.setAddTime(new Date());
				invitationService.saveInvitation(invitation);
				//邀请注册
				TaskLog taskLog = taskLogService.findDayExist(TimeKit.todayStart(),4,user.getId());
				if(taskLog==null){
					taskLog = new TaskLog();
					taskLog.setFinishTime(new Date());
					taskLog.setStatus(1);
					taskLog.setUser(user);
					UserTask ut = userTaskService.findById(4);
					taskLog.setUserTask(ut);//查询出用户邀请好友这条记录
					taskLogService.savaTaskLog(taskLog);
				}else if(taskLog.getStatus()==0){
					taskLog.setStatus(1);
					taskLog.setFinishTime(new Date());
					taskLogService.updateTaskLog(taskLog);
				}
//				统计每日registerUser用户注册量
				handleCountService.handleCountPlusOne("registerUser");
				map.put("code", StaticKey.ReturnServerTrue);
				map.put("msg", "注册成功！");
				return map;
			}else{
//				无上级
				userService.saveUser(newUser);
//				统计每日registerUser用户注册量
				handleCountService.handleCountPlusOne("registerUser");
				map.put("code", StaticKey.ReturnServerTrue);
				map.put("msg", "恭喜您，注册成功！");
				return map;
			}
		}else{
//			无上级
			userService.saveUser(newUser);
//			统计每日registerUser用户注册量
			handleCountService.handleCountPlusOne("registerUser");
			map.put("code", StaticKey.ReturnServerTrue);
			map.put("msg", "恭喜您，注册成功！");
			return map;
		}
	}
	
	/**
	 * 获取有参邀请页：
	 * @return
	 */
	@RequestMapping(value="/invitation/{userIdStr}")
	public String invitation(HttpServletRequest request,@PathVariable String userIdStr) {
		if(!StringUtils.isBlank(userIdStr)){
//			RC4解密
			try {
				userIdStr = RC4Kit.decry_RC4(userIdStr, "liao");
			} catch (Exception e) {
				return "share/invitation";
			}
			if(StringUtils.isNumeric(userIdStr) && !StringUtils.isBlank(userIdStr)){
				int userId = Integer.valueOf(userIdStr);
				Users user = userService.findById(userId);
				if(user!=null){
					request.setAttribute("userId",userId);
					request.setAttribute("userNickName",user.getNickName());
					return "share/invitation";
				}
				else{
					return "share/invitation";
				}
			}
			else{
				return "share/invitation";
			}
		}
		return "share/invitation";
	}
	
	/**
	 * 获取无参邀请页：
	 * @return
	 */
	@RequestMapping(value="/invitation")
	public String invitationNoParam(HttpServletRequest request) {
		return "share/invitation";
	}
	
	
	/**
	 * 获取阿里云sts
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUploadPermission")
	public Map<String,Object> getUploadPermission(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
	/*	if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		//获取SecurityToken、AccessKeyId、AccessKeySecret
		map=GetAliyunSTS.getSTS();
		
		if(map.get("SECURITY_TOKEN")==null||"".equals(map.get("SECURITY_TOKEN"))){
			map.put("code", StaticKey.ReturnServerNullError);
			map.put("msg", "SECURITY_TOKEN未获取");
			return map;
		}
	//	map.put("ACCESS_KEY_ID", "LTAI8W8OljECXmdW");
	//	map.put("ACCESS_KEY_SECRET", "dnpIcJVymrgfVSalQ1aSX4OF0y5F9H");
		map.put("ENDPOINT", "oss-cn-hangzhou.aliyuncs.com");
		map.put("BACKET_NAME", "appliaoliao");
	//	map.put("FOLDER", "images/aaa.png");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 用户上传原创作品
	 * 0：文章    1：视频
	 */
	@ResponseBody
	@RequestMapping(value="/uploadPrimary")
	public Map<String,Object> uploadPrimary(HttpServletRequest request,Integer userId,String duration,
			String description,String title,String content,String imgUrl,String videoUrl,Integer type){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||type==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}
		title=CommonUtil.emojiFilter(title);
		if(type == 0){//上传文章
			if(StringUtils.isBlank(title)||StringUtils.isBlank(content)){
				map.put("msg", "有参数为空");
				map.put("code", StaticKey.ReturnClientNullError);
				return map;
			}
			Article article = new Article();
			article.setAddTime(new Date());
			article.setContentKind(contentKindService.findByName("头条"));
			article.setDescription(content);
			if(imgUrl==null||"".equals(imgUrl)){
				map.put("msg", "有参数为空");
				map.put("code", StaticKey.ReturnClientNullError);
				return map;
			}
			String[] imgs =imgUrl.split(",");
			String spli="";
			String fengmianImgs="<div><div class=\"content\">";
			for(int i=0;i<imgs.length;i++){
				spli+="{\"url\":\"http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+imgs[i]+"?x-oss-process=style/liaoliao_icon\"},";
				fengmianImgs+="<p><img style=\"width:100%\" src=http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+imgs[i]+"?x-oss-process=style/liaoliao_icon></p>";
			}
			fengmianImgs+=content+"<p>欢迎各位吐槽 看谁更有内涵！<span></span></p></div> </div>";
			
			spli=spli.substring(0, spli.length()-1);
			spli="{\"imgList\":["+spli+"]}";
			article.setImgList(spli);
			article.setImgUrl("http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+imgs[0]+"?x-oss-process=style/liaoliao_icon");
			article.setContent(fengmianImgs);
			article.setCommentCount(0);
			article.setKeyId(RandomKit.keyId());
			article.setLikingCount(0);
			article.setReadingCount(0);
			article.setSendingCount(0);
			article.setType(1);
			article.setStatus(StaticKey.ArticleStatusLoding);
			article.setTitle(title);
			article.setSourceId(userId);
			articleService.saveArticle(article);
			String keywords="";
			if(title==null||title.length()<=4){
				keywords=title;
			}else{
				//String  aaa="搞笑奇葩对话，如果你能让我在三分钟内说我爱你，我就和你结婚";
				String getResult = CommonUtil.sendGet("http://zhannei.baidu.com/api/customsearch/keywords","title="+title);
				String resp = CommonUtil.ascii2native(getResult);
				Map parse = (Map) JSONObject.parse(resp);
				Map result = (Map)parse.get("result");
				Map  keyword_list=(Map)result.get("res");
				String subStr="";
				String suab = keyword_list.get("keyword_list").toString();
				String[] sub = suab.substring(1, suab.length()-1).split(",");
				for(int i=0;i<sub.length;i++){
					subStr+=sub[i].substring(1, sub[i].length()-1)+",";
				}
				keywords=subStr.substring(0, subStr.length()-1);
			}
			
			Thesaurus ths= new Thesaurus();
			ths.setAddTime(new Date());
			ths.setContentId(article.getId());
			ths.setKeywords(keywords);
			ths.setType(0);
			thesaurusService.saveThesaurus(ths);
			OriginalArticleInfo oai = new OriginalArticleInfo();
			oai.setArticle(article);
			oai.setAddTime(new Date());
			oai.setReadCount(0);
			oai.setShareCount(0);
			if(user.getVipStatus()==StaticKey.UserVipStatusTrue){
				oai.setCountMoney(StaticKey.OriginalTotalMoney*2);
			}else{
				oai.setCountMoney(StaticKey.OriginalTotalMoney);
			}
			originalArticleInfoService.saveOAI(oai);
			map.put("code", StaticKey.ReturnServerTrue);
			map.put("msg", "success");
			return map;
		}else if(type==1){
			
			if(StringUtils.isBlank(videoUrl) ){
				map.put("msg", "有参数为空");
				map.put("code", StaticKey.ReturnClientNullError);
				return map;
			}
			Video video = new Video();
			video.setAddTime(new Date());
			video.setCommentCount(0);
			video.setDescription(description);
			video.setDuration(duration);
			video.setImgUrl(imgUrl);
			video.setKeyId(RandomKit.keyId());
			video.setLikingCount(0);
			video.setPlayingCount(0);
			video.setType(1);
			video.setSendingCount(0);
			video.setStatus(StaticKey.VideoStatusLoding);
			video.setTitle(title);
			video.setVideoUrl("http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/videos/"+videoUrl);
			video.setSourceId(userId);
			videoService.saveVideo(video);
			String keywords="";
			if(title.length()<=4){
				keywords=title;
			}else{
				//String  aaa="搞笑奇葩对话，如果你能让我在三分钟内说我爱你，我就和你结婚";
				String getResult = CommonUtil.sendGet("http://zhannei.baidu.com/api/customsearch/keywords","title="+title);
				String resp = CommonUtil.ascii2native(getResult);
				System.out.println(resp);
				Map parse = (Map) JSONObject.parse(resp);
				Map result = (Map)parse.get("result");
				Map  keyword_list=(Map)result.get("res");
				String subStr="";
				String suab = keyword_list.get("keyword_list").toString();
				String[] sub = suab.substring(1, suab.length()-1).split(",");
				for(int i=0;i<sub.length;i++){
					subStr+=sub[i].substring(1, sub[i].length()-1)+",";
				}
				keywords=subStr.substring(0, subStr.length()-1);
			}
			Thesaurus ths= new Thesaurus();
			ths.setAddTime(new Date());
			ths.setContentId(video.getId());
			ths.setKeywords(keywords);
			ths.setType(1);
			thesaurusService.saveThesaurus(ths);
			OriginalVideoInfo ovi = new OriginalVideoInfo();
			ovi.setVideo(video);
			ovi.setAddTime(new Date());
			ovi.setReadCount(0);
			ovi.setShareCount(0);
			if(user.getVipStatus()==StaticKey.UserVipStatusTrue){
				ovi.setCountMoney(StaticKey.OriginalTotalMoney*2);
			}else{
				ovi.setCountMoney(StaticKey.OriginalTotalMoney);
			}
			originalVideoInfoService.saveOVI(ovi);
			map.put("msg", "success");
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}else{
			map.put("msg", "上传文件类型错误");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
	}
	
	
	
	
	
	
}
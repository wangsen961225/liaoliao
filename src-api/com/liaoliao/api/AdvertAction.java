package com.liaoliao.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.Advert;
import com.liaoliao.sys.service.AdvertService;
import com.liaoliao.user.entity.FocusLog;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.FocusLogService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.RC4Kit;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/share")
public class AdvertAction {
	
	
	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FocusLogService focusLogService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * list广告页：
	 * @return
	 */
	@RequestMapping(value="/listAD")
	public String listAD(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String listAdvert = advertService.findListAdvert();
		map.put("listAdvert", listAdvert);
		request.setAttribute("map", map);
		return "share/listAdvert";
	}
	
	/**
	 * 签到广告页：
	 * @return
	 */
	@RequestMapping(value="/sign")
	public String getSign(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String signAdvert = advertService.findSignAdvert();
		map.put("signAdvert", signAdvert);
		request.setAttribute("map", map);
		System.out.println(signAdvert);
		return "share/signAdvert";
	}
	
	/**
	 * 直投广告页(随机获取一个未被禁用的广告页)：
	 * @return
	 */
	@RequestMapping(value="/directInvest")
	public String getDirectInvest(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Advert> directInvest = advertService.findDirectInvest();
		int random = (int)Math.floor(Math.random()*directInvest.size());
		StringBuilder advertStr = new StringBuilder();
		advertStr.append(directInvest.get(random).getContent());
		String directInvestStr = advertStr.toString();
		map.put("directInvest", directInvestStr);
		request.setAttribute("map", map);
		return "share/directInvest";
	}
	
	
	
	/**
	 * top广告页：
	 * @return
	 */
	@RequestMapping(value="/top")
	public String getTop(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String topAdvert = advertService.findArticleTopAdvert();
		map.put("topAdvert", topAdvert);
		request.setAttribute("map", map);
		return "share/topAdvert";
	}
	
	/**
	 * topFloat广告页：
	 * @return
	 */
	@RequestMapping(value="/topFloat")
	public String getTopFloat(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String topFloatAdvert = advertService.findArticleTopFloatAdvert();
		map.put("topFloatAdvert", topFloatAdvert);
		request.setAttribute("map", map);
		return "share/topFloatAdvert";
	}
	
	
	/**
	 * bottom广告页：
	 * @return
	 */
	@RequestMapping(value="/bottom")
	public String getBottom(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String bottomAdvert = advertService.findArticleBottomAdvert();
		map.put("advert", bottomAdvert);
		System.out.println(bottomAdvert+"adf");
		request.setAttribute("map", map);
		return "share/bottomAdvert";
	}
	
	/**
	 * more1广告页：
	 * @return
	 */
	@RequestMapping(value="/more1")
	public String getMore1(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String more1Advert = advertService.findArticleMore1Advert();
		map.put("advert", more1Advert);
		request.setAttribute("map", map);
		return "share/bottomAdvert";
	}
	
	/**
	 * more2广告页：
	 * @return
	 */
	@RequestMapping(value="/more2")
	public String getMore2(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String more2Advert = advertService.findArticleMore2Advert();
		map.put("advert", more2Advert);
		request.setAttribute("map", map);
		return "share/bottomAdvert";
	}
	
	/**
	 * more3广告页：
	 * @return
	 */
	@RequestMapping(value="/more3")
	public String getMore3(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		String more3Advert = advertService.findArticleMore3Advert();
		map.put("advert", more3Advert);
		request.setAttribute("map", map);
		return "share/bottomAdvert";
	}
	
	
	/**
	 * AD+文章+视频
	 * @return
	 */
	@RequestMapping(value="/getAdArticleVideo")
	@ResponseBody 
	public Map<String,Object> getAdArticleVideo(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<String,Object>();
		
		Video video = null;
		Article article = null;
		
		List imgListObj = new ArrayList();
		// Map-->List-->Map 三层转换
		List<Map<String, Object>> datas = new ArrayList<>();
		Map<String, Object> item = null;
		for (int i = 0; i < 3; i++) {
			video = videoService.findByRand(1).get(0);
			article = articleService.findByRand(1,1).get(0);
			
			item = new LinkedHashMap<>();
//			video
			item.put("videoId", video.getId());
			item.put("videoTitle", video.getTitle());
			item.put("videoDescription", video.getDescription());
			item.put("videoImgUrl", video.getImgUrl());
			item.put("videoUrl", video.getVideoUrl());
			item.put("videoDuration", video.getDuration());
//			item.put("videoCommentCount", video.getCommentCount());
//			item.put("videoPlayCount", video.getPlayCount());
			item.put("videoCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
			item.put("videoLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("videoPlayCount", ThreadLocalRandom.current().nextInt(9000, 25000));
			
			Users luser = userService.findById(StaticKey.liaoliaoVideoId);
			Long luserFl = focusLogService.countNum(StaticKey.liaoliaoVideoId);
			int luserCount=0;
			if(luserFl!=null){
				luserCount= luserFl.intValue();
			}
			if(video.getType()==1){
				Users user = userService.findById(video.getSourceId());
				if(user==null){
					item.put("name_video", luser.getNickName());//料料头条
					item.put("userId_video",luser.getId());
					item.put("avatar_video",luser.getAvatar());
					item.put("focusCount_video", luserCount);
				}else{
					Long number = focusLogService.countNum(user.getId());
					item.put("focusCount_video", number!=null?number:0);
					item.put("name_video", user.getNickName());
					item.put("userId_video", user.getId());
					item.put("avatar_video", user.getAvatar());
				}
			}else{
				item.put("name_video", luser.getNickName());//料料头条
				item.put("userId_video",luser.getId());
				item.put("avatar_video",luser.getAvatar());
				item.put("focusCount_video", luserCount);
			}
			
			if(userId!=null&&redisService.getValidate(request,userId)){
				if(video.getType()==1){
					FocusLog fl = focusLogService.findByFocusId(userId, video.getSourceId());
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus_video", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus_video", StaticKey.FocusFlase);
					}
				}else{
					FocusLog fl = focusLogService.findByFocusId(userId, StaticKey.liaoliaoVideoId);
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus_video", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus_video", StaticKey.FocusFlase);
					}
				}
			}else{
						item.put("focusStatus_video", StaticKey.FocusFlase);
			}
			
//			RC4加密
			String idStr = RC4Kit.encry_RC4_string(String.valueOf(video.getId()), "liao");
			item.put("videoShareUrl", "/share/video/"+idStr);
//			article
			item.put("videoType", video.getType());
			item.put("articleId", article.getId());
			item.put("articleTitle", article.getTitle());
			item.put("articleDescription", article.getDescription());
			item.put("articleImgUrl", article.getImgUrl());
			item.put("articleType", article.getType());
			String imglistStr = article.getImgList();
			if(!StringUtils.isBlank(imglistStr)){
				Map imgMap = (Map) JSONObject.parse(imglistStr);
				imgListObj = (List) imgMap.get("imgList");
			}
			item.put("articleImgList", imgListObj);
//			item.put("articleReadingCount", article.getReadingCount());
//			item.put("articleCommentCount", article.getCommentCount());
			item.put("articleReadingCount", ThreadLocalRandom.current().nextInt(9000, 25000));
			item.put("articleLikingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleSendingCount", ThreadLocalRandom.current().nextInt(1000, 3000));
			item.put("articleCommentCount", ThreadLocalRandom.current().nextInt(100, 1000));
//			Ad
			int key = i+1;
			String addd = "?timestamp="+System.currentTimeMillis()/1000L+"&articleId="+article.getId()+"&videoId="+video.getId()+"&random="+(int) (Math.random()*10000);
			item.put("advertUrl", "/share/more"+key+addd);
			

			Users luser_article = userService.findById(StaticKey.liaoliaoArticleId);
			Long luserFl_article = focusLogService.countNum(StaticKey.liaoliaoArticleId);
			int luserCount_article=0;
			if(luserFl_article!=null){
				luserCount_article= luserFl_article.intValue();
			}
			if(article.getType()==1){
				Users user = userService.findById(article.getSourceId());
				if(user==null){
					item.put("name_article", luser_article.getNickName());//料料头条
					item.put("userId_article",luser_article.getId());
					item.put("avatar_article",luser_article.getAvatar());
					item.put("focusCount_article", luserCount_article);
				}else{
					Long number = focusLogService.countNum(user.getId());
					item.put("focusCount_article", number!=null?number:0);
					item.put("name_article", user.getNickName());
					item.put("userId_article", user.getId());
					item.put("avatar_article", user.getAvatar());
				}
			}else{
				item.put("name_article", luser_article.getNickName());//料料头条
				item.put("userId_article",luser_article.getId());
				item.put("avatar_article",luser_article.getAvatar());
				item.put("focusCount_article", luserCount_article);
			}
			if(userId!=null&&redisService.getValidate(request,userId)){
				if(video.getType()==1){
					FocusLog fl = focusLogService.findByFocusId(userId, video.getSourceId());
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus_article", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus_article", StaticKey.FocusFlase);
					}
				}else{
					FocusLog fl = focusLogService.findByFocusId(userId, StaticKey.liaoliaoArticleId);
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus_article", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus_article", StaticKey.FocusFlase);
					}
				 }
				}else{
					item.put("focusStatus_article", StaticKey.FocusFlase);
				}
			datas.add(item);
		}
		map.put("list", datas);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
}

















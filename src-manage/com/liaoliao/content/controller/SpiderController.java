package com.liaoliao.content.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.ContentKindService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.SpiderHttpKit;
import com.liaoliao.util.MeipaiUtils;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;


@Controller
@RequestMapping(value="/content")
public class SpiderController {
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ContentKindService contentKindService;
	
	@Autowired
	private RedisService redisService;
	

	/**
	 * 采集内涵段子的视频
	 */
	
	@RequestMapping(value="/neihanVideo")
	@ResponseBody
	public Map<String,Object> neihanVideo() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		Video video=null;
		String url = "http://neihanshequ.com/video/";
//		String min_time = "1501831821";
		String min_time = redisService.get("spiderNeihanduanziMinTime");
		if(StringUtils.isBlank(min_time)){
			redisService.set("spiderNeihanduanziMinTime", "0", -1);
			min_time = "0";
		}
		String resp = SpiderHttpKit.getNeihanResp(url+"?is_json=1&min_time="+min_time+"&app_name=neihanshequ_web&csrfmiddlewaretoken=2aa50bbf4676acd5366a308c9e676efb");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		Map parse = (Map) JSONObject.parse(resp);
		Map parseData = (Map) parse.get("data");
		
		min_time = String.valueOf(parseData.get("min_time"));
		redisService.set("spiderNeihanduanziMinTime", min_time, -1);
		
		JSONArray parseDatadata = (JSONArray) parseData.get("data");
		Map map = null;
		for (int i = 0; i < parseDatadata.size(); i++) {
			map = (Map) parseDatadata.get(i);
			Map parseGroup = (Map) map.get("group");
			
//			System.out.println("视频标题："+parseGroup.get("text"));
			String title=String.valueOf(parseGroup.get("text"));
			if(StringUtils.isBlank(title)){
				title=String.valueOf(parseGroup.get("content"));
			}
			if(StringUtils.isBlank(title)){
				title = "风澈搞笑视频第"+ThreadLocalRandom.current().nextInt(300, 6000)+"期";
			}
			
//			System.out.println("视频时长："+parseGroup.get("duration"));
			String duration=TimeKit.secondFormat(String.valueOf(parseGroup.get("duration")));
			
//			System.out.println("页面唯一id："+parseGroup.get("id"));
			String keyId=String.valueOf(parseGroup.get("id"));
			
//			System.out.println("视频喜欢数："+parseGroup.get("favorite_count"));
			Integer likingCount = Integer.valueOf(String.valueOf(parseGroup.get("favorite_count")));
			
//			System.out.println("视频播放数："+parseGroup.get("play_count"));
			Integer playingCount = Integer.valueOf(String.valueOf(parseGroup.get("play_count")));
			
			Integer sendingCount = (int) (Math.random()*2000);
			
//			System.out.println("视频评论数："+parseGroup.get("comment_count"));
			Integer commentCount=Integer.valueOf(String.valueOf(parseGroup.get("comment_count")));
			
//			System.out.println("原始电脑url："+"http://neihanshequ.com/p"+parseGroup.get("id"));
			String sourceUrl=String.valueOf("http://neihanshequ.com/p"+parseGroup.get("id"));
			
			Map mapMedium_cover = (Map) parseGroup.get("medium_cover");
			JSONArray listUrl_list = (JSONArray) mapMedium_cover.get("url_list");
			Map mapListurl = (Map) listUrl_list.get(0);
//			System.out.println("视频封面图url："+mapListurl.get("url"));
			String imgUrl = String.valueOf(mapListurl.get("url"));
			
			Map map_720p = (Map) parseGroup.get("720p_video");
			JSONArray list_720p = (JSONArray) map_720p.get("url_list");
			Map map720p = (Map) list_720p.get(0);
//			System.out.println("视频720p-url："+map720p.get("url"));
			String videoUrl = String.valueOf(map720p.get("url"));
			
			likingCount = 0;
			sendingCount = 0;
			commentCount = 0;
			playingCount = 0;
			
			Video videoCompare = videoService.findByKeyAndType(keyId,StaticKey.VideoNeiHanDuanZi);
			if(videoCompare==null&&videoUrl!=null){
			video = new Video(title, null, duration, videoUrl, imgUrl,
					StaticKey.VideoStatusTrue,0, keyId, playingCount, likingCount, sendingCount,commentCount,
					StaticKey.VideoNeiHanDuanZi, sourceUrl, new Date());
			videoService.saveVideo(video);
			count++;
			}
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	/**
	 * 采集小咖秀的视频
	 */
	@RequestMapping(value="/xiaokaxiuVideo")
	@ResponseBody
	public Map<String,Object> xiaokaxiuVideo() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url = "http://www.xiaokaxiu.com/video/web/get_splendid_video";
		String resp = CommonUtil.sendGet(url, "limit=10&page=1");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		Map parse = (Map) JSONObject.parse(resp);
		Map parseData = (Map) parse.get("data");
		JSONArray parseDatadata = (JSONArray) parseData.get("list");
		Map map = null;
		for (int i = 0; i < parseDatadata.size(); i++) {
			map = (Map) parseDatadata.get(i);
			String title = String.valueOf(map.get("title"));
//			System.out.println("视频标题："+title);
			
			String description = String.valueOf(map.get("desc"));
//			System.out.println("视频描述："+description);
			
			String videoUrl = String.valueOf(map.get("linkurl"));
//			System.out.println("视频真实url："+videoUrl);
			
			String imgUrl = String.valueOf(map.get("cover"));
//			System.out.println("视频封面："+imgUrl);
			
			String keyId = String.valueOf(map.get("videoid"));
//			System.out.println("视频唯一id："+keyId);
			
			Integer commentCount =(int) (Math.random() * 100);
			Integer sendingCount = (int) (Math.random()*2000);
			
			Integer likingCount = Integer.valueOf(String.valueOf(map.get("praisecount")));
//			System.out.println("视频喜欢数："+map.get("praisecount"));
			
			Integer playCount = Integer.valueOf(String.valueOf(map.get("hits")));
//			System.out.println("视频播放数："+playCount);
			
			String sourceUrl = String.valueOf("http://v.xiaokaxiu.com/v/"+map.get("scid")+".html");
//			System.out.println("原始url："+sourceUrl);
			
			likingCount = 0;
			sendingCount = 0;
			commentCount = 0;
			playCount = 0;
			
			Video videoCompare = videoService.findByKeyAndType(keyId,StaticKey.VideoXiaoKaXiu);
			if(videoCompare==null&&videoUrl!=null){
			Video video=null;
			video = new Video(title, description, null, videoUrl, imgUrl,
					StaticKey.VideoStatusTrue,0, keyId, playCount, likingCount, sendingCount,commentCount,
					StaticKey.VideoXiaoKaXiu, sourceUrl, new Date());
			videoService.saveVideo(video);
			count++;
			}
			
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	/**
	 * 采集360快视频的视频
	 */
	@RequestMapping(value="/kuaishipinVideo")
	@ResponseBody
	public Map<String,Object> kuaishipinVideo() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url = "http://v.sj.360.cn/pc/list";
		String uid = "42f9259bdb305d9ce51f9b32ed5da295";
		String resp = CommonUtil.sendGet(url, "n=10&p=1&f=json&ajax=1&uid=" + uid + "&channel_id=2");
//		String resp = CommonUtil.sendGet(url, "n=10&p=1&f=json&ajax=1&channel_id=2");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
//		System.out.println(resp);
		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		Map parseData = (Map) parse.get("data");
//		System.out.println("解析到的data==="+parseData);
		JSONArray parseRes = (JSONArray) parseData.get("res");
//		System.out.println("解析到的res==="+parseRes);
		Map map = null;
		for (int i = 0; i < parseRes.size(); i++) {
			map = (Map) parseRes.get(i);
			
			String title = String.valueOf(map.get("t"));
//			System.out.println("视频标题："+title);
			
			Integer playCount = Integer.valueOf(String.valueOf(map.get("playcnt")));
//			System.out.println("视频播放数："+playCount);
			
			Integer likingCount = Integer.valueOf(String.valueOf(map.get("zan_num")));
//			System.out.println("视频被赞数："+map.get("zan_num"));
			
			Integer commentCount =(int) (Math.random() * 100);
			Integer sendingCount = (int) (Math.random()*2000);
			
//			System.out.println("视频时长："+map.get("duration"));
			
			String imgUrl = String.valueOf(map.get("i"));
//			System.out.println("视频封面："+imgUrl);
			
			String keyId = String.valueOf(map.get("rptid"));
//			System.out.println("视频唯一id："+keyId);
			
			String sourceUrl = String.valueOf(map.get("u"));
//			System.out.println("原始url："+sourceUrl);
			
			Map parseExData = (Map) map.get("exData");
//			System.out.println("解析到的里层exData==="+parseExData);
//			System.out.println("视频scid："+parseExData.get("code"));
			
			String duration=String.valueOf(parseExData.get("totalTimeStr"));
//			System.out.println("视频格式化时长："+duration);
			
			String detailUrl = String.valueOf(parseExData.get("playLink"));
//			System.out.println("视频动态url："+detailUrl);
			String realDetailUrl = CommonUtil.sendGet(detailUrl,"");
//			System.out.println("get到的动态Url"+realDetailUrl);
			Map realUrlString = (Map) JSONObject.parse(realDetailUrl);
			Map realUrlData = (Map) realUrlString.get("data");
//			System.out.println("解析到的里层realUrlData==="+realUrlData);
			
			String videoUrl = String.valueOf(realUrlData.get("url"));
//			System.out.println("视频真实URL"+videoUrl);
			
			likingCount = 0;
			sendingCount = 0;
			commentCount = 0;
			playCount = 0;
			
			Video videoCompare = videoService.findByKeyAndType(keyId,StaticKey.Video360KuaiShiPin);
			if(videoCompare==null&&videoUrl!=null){
			Video video=null;
			video = new Video(title, null, duration, videoUrl, imgUrl,
					StaticKey.VideoStatusTrue,0, keyId, playCount, likingCount, sendingCount,commentCount,
					StaticKey.Video360KuaiShiPin, sourceUrl, new Date());
			videoService.saveVideo(video);
			count++;
			}
			
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	/**
	 * 采集美拍的视频
	 */
	@RequestMapping(value="/meipaiVideo")
	@ResponseBody
	public Map<String,Object> meipaiVideo() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url = "http://www.meipai.com/squares/new_timeline";
		
		int page = 1;
		String pageStr = redisService.get("spiderMeipaiPage");
		if(StringUtils.isBlank(pageStr)){
			redisService.set("spiderMeipaiPage", "1", -1);
			page = 1;
		}else{
			page = Integer.valueOf(pageStr);
		}
		redisService.set("spiderMeipaiPage", String.valueOf(page+1), -1);
		String resp = CommonUtil.sendGet(url, "page="+page+"&count=10&tid=13");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
//		System.out.println(resp);
		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
//		Map parseData = (Map) parse.get("medias");
//		System.out.println("解析到的medias==="+parseData);
		JSONArray parseRes = (JSONArray) parse.get("medias");
//		System.out.println("解析到的medias==="+parseRes);
		Map map = null;
		for (int i = 0; i < parseRes.size(); i++) {
			map = (Map) parseRes.get(i);
			
//			System.out.println("视频标题："+map.get("caption"));
			String title = String.valueOf(map.get("caption"));
			if(StringUtils.isBlank(title)){
				title = String.valueOf(map.get("caption_origin"));
			}
			if(StringUtils.isBlank(title)){
				title = String.valueOf(map.get("caption_all"));
			}
			if(StringUtils.isBlank(title)){
				title = String.valueOf(map.get("weixin_friendfeed_share_caption"));
				title = StringUtils.substring(title, 0, 8)+"...";
			}

			String commentCountString = String.valueOf(map.get("comments_count"));
			
			Integer commentCount = (int) (Math.random()*100);
			Integer playCount = (int) (Math.random()*5000);
			
			if(StringUtils.isNumeric(commentCountString)){
				commentCount = Integer.valueOf(commentCountString);
				playCount = Integer.valueOf(commentCountString);
			}
//			System.out.println("视频播放数："+playCount);
//			System.out.println("视频评论数："+map.get("comments_count"));
//			System.out.println("视频被赞数："+map.get("likes_count"));
			
			Integer likingCount = (int) (Math.random() * 8000);
			String likingCountStr = String.valueOf(map.get("likes_count"));
			if(StringUtils.isNumeric(likingCountStr)){
				likingCount = Integer.valueOf(likingCountStr);
			}
			
			Integer sendingCount = (int) (Math.random()*2000);
			
//			System.out.println("视频时长："+map.get("time"));
			String duration = TimeKit.secondFormat(String.valueOf(map.get("time")));
//			System.out.println("视频格式化时长："+duration);
			
			String imgUrl = String.valueOf(map.get("cover_pic"));
//			System.out.println("视频封面："+imgUrl);
			
			String keyId = String.valueOf(map.get("feed_id"));
//			System.out.println("视频唯一id："+keyId);
			
			String sourceUrl = String.valueOf(map.get("url"));
//			System.out.println("原始url："+sourceUrl);
			
//			System.out.println("视频加密URL=="+map.get("video"));
			String videoUrl = MeipaiUtils.decode(String.valueOf(map.get("video")));
//			System.out.println("视频真实URL=="+videoUrl);
			
			if(!videoUrl.contains(".mp4")){
				continue;
			}
			
			likingCount = 0;
			sendingCount = 0;
			commentCount = 0;
			playCount = 0;
			
			Video videoCompare = videoService.findByKeyAndType(keyId,StaticKey.VideoMeiPai);
			if(videoCompare==null&&videoUrl!=null){
			Video video=null;
			video = new Video(title, null, duration, videoUrl, imgUrl,
					StaticKey.VideoStatusTrue,0, keyId, playCount, likingCount, sendingCount,commentCount,
					StaticKey.VideoMeiPai, sourceUrl, new Date());
			videoService.saveVideo(video);
			count++;
			}
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	/**
	 * 今日头条Article采集
	 */
	@RequestMapping(value="/toutiaoArticle")
	@ResponseBody
	public Map<String,Object> toutiaoArticle() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url = "http://www.toutiao.com/api/pc/feed/";
//		String url = "http://www.toutiao.com/api/article/feed/";
		String tag = "funny";
//		JSONObject param = JinRiTouTiaoUtil.getUrlParam();
//		Object as = param.get("as");
//		Object cp = param.get("cp");
//		String as = "A165E968744150D";
//		String cp = "5984813550ED3E1";
		
//		String as = "A1356A70924C7EE";
//		String cp = "5A021C278EEE8E1";
		
		String as = "A1D5DA60A30B8EB";
		String cp = "5A03FBA8EEABCE1";
		
			
//		String max_behot_time_tmp = "1501816479";
		String max_behot_time_tmp = redisService.get("spiderJinritoutiaoNextTime");
		if(StringUtils.isBlank(max_behot_time_tmp)){
			redisService.set("spiderJinritoutiaoNextTime", "0", -1);
			max_behot_time_tmp = "0";
		}
		String resp = SpiderHttpKit.getToutiaoResp(url+"?category="+tag+"&as="+as+"&cp="+cp+"&max_behot_time_tmp="+max_behot_time_tmp+"&utm_source=toutiao&widen=1&max_behot_time=0&tadrequire=true");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		// 文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
		String urlDetails = "http://www.toutiao.com/group/";
		
		Map parse = (Map) JSONObject.parse(resp);
		
		Map max_behot_time_tmpMap = (Map) parse.get("next");
		max_behot_time_tmp = String.valueOf(max_behot_time_tmpMap.get("max_behot_time"));
		redisService.set("spiderJinritoutiaoNextTime", max_behot_time_tmp, -1);
		
		JSONArray parseArray = (JSONArray) parse.get("data");
		Map map = null;
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);
			
			/*if(!map.get("tag").equals(tag)){
				continue;
				//空指针
			}*/
			if(!tag.equals(map.get("tag"))){
				continue;
			}
			
//			System.out.println("文章标题："+map.get("title"));
			String title=String.valueOf(map.get("title"));
			
//			System.out.println("文章描述："+map.get("abstract"));
			String description = null;
			if(map.get("abstract")!=null){
				description = String.valueOf(map.get("abstract"));
			}else{
				description = title;
			}
			
			
//			System.out.println("文章唯一id："+map.get("group_id"));
			String keyId= String.valueOf(map.get("group_id"));
			
			Integer commentCount = (int) (Math.random()*100);
//			System.out.println("文章评论数："+map.get("comments_count"));
			if(map.get("comments_count")!=null){
				commentCount=Integer.valueOf(String.valueOf(map.get("comments_count")));
			}
			
//			System.out.println("原始url："+"http://toutiao.com/group/"+map.get("group_id"));
			String sourceUrl=String.valueOf("http://toutiao.com/group/"+map.get("group_id"));
			String content=null;
/*			Connection connection = Jsoup.connect(urlDetails + map.get("group_id"));
			try {
				Document document = connection.get();
				// 获取文章内容
				Elements contentEle = document.select("[class=article-content]");
//				System.out.println("文章内容："+contentEle.html());
				content = contentEle.html();
				if(StringUtils.isBlank(content)){
					continue;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			String respHTML = SpiderHttpKit.getToutiaoResp(urlDetails+map.get("group_id"));
//			System.out.println(respHTML);
			String itemId = StringUtils.substringBetween(respHTML, "item_id:'", "'");
			if(StringUtils.isBlank(itemId)){
				itemId = StringUtils.substringBetween(respHTML, "item_id: '", "'");
				if(StringUtils.isBlank(itemId)){
					continue;
				}
			}
//			System.out.println(respContent);
//			System.out.println("文章itemId："+itemId);
			String respContent = CommonUtil.sendGet("https://m.toutiao.com/i"+itemId+"/info/","");
			Map parseContent = (Map) JSONObject.parse(respContent);
//			System.out.println(parseContent);
			Map comtentMap = (Map) parseContent.get("data");
			if(comtentMap.get("content")==null){
				continue;
			}
			content = String.valueOf(comtentMap.get("content"));
//			System.out.println("文章内容："+content);
			
//			System.out.println("文章封面图："+map.get("image_url"));
			String imgUrl = String.valueOf(map.get("image_url"));
			if(map.get("image_url")!=null){
				imgUrl = String.valueOf(map.get("image_url"));
			}
			else{
				imgUrl = StringUtils.substringBetween(content,"<img src=\"","\"");
			}
			
//			System.out.println("文章image_list："+map.get("image_list"));
			String imgList = String.valueOf(map.get("image_list"));
			if(map.get("image_list")!=null){
				imgList = "{\"imgList\":"+imgList+"}";
			}
			if(imgUrl!=null && map.get("image_list")==null){
				imgList = "{\"imgList\":[{\"url\":\""+imgUrl+"\"}]}";
			}
			if(imgUrl==null && map.get("image_list")==null){
				imgList = null;
			}
			
			Integer likingCount = 0;
			Integer sendingCount = 0;
			commentCount = 0;
			Integer readingCount = 0;
			
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(description);
			article.setImgUrl(imgUrl);
			article.setImgList(imgList);
			article.setKeyId(keyId);
			article.setCommentCount(commentCount);
			article.setLikingCount(likingCount);
			article.setSendingCount(sendingCount);
			article.setReadingCount(readingCount);
			article.setSourceId(StaticKey.ArticleJinRiTouTiao);
			article.setSourceUrl(sourceUrl);
			article.setAddTime(new Date());
			article.setStatus(StaticKey.ArticleStatusTrue);
			article.setType(0);
			String name="头条";
			article.setContentKind(contentKindService.findByName(name));
			Article articleCompare = articleService.findByKeyAndType(keyId,StaticKey.ArticleJinRiTouTiao);
			if(articleCompare==null&&content!=null){
				article.setContent(content);
				articleService.saveArticle(article);
				count++;
			}
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	
	
	/**
	 * 趣头条Article采集
	 */
	@RequestMapping(value="/qutoutiaoArticle")
	@ResponseBody
	public Map<String,Object> qutoutiaoArticle() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url_1 = "http://api.1sapp.com/content/getList";
		String resp = CommonUtil.sendGet(url_1, "OSVersion=7.0&cid=2&content_type=1,2,4,3&deviceCode=868030024519827&dtu=002&lat=0.0&lon=0.0&max_time=0&network=wifi&op=2&page=1&show_time=0&time=1500541172413&uuid=ebf3528e7c374f8384f68c25f900a99c&version=20305&versionName=2.3.5.0621.1047&sign=c64e6fc3f95a88fcf231810d51d3c345");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);

		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		Map dataMap = (Map) parse.get("data");
//		System.out.println("get到的data==="+dataMap);
		JSONArray parseArray = (JSONArray) dataMap.get("data");
//		System.out.println("解析到的里层data==="+parseArray);
		Map map = null;
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);

//			TODO 采集视频
			if(map.get("id")==null||map.get("tips").equals("视频")||map.get("play_time")!=null){
				continue;
			}
//			System.out.println("文章标题："+map.get("title"));
			String title = String.valueOf(map.get("title"));
			if(StringUtils.isBlank(title)){
				title = "搞笑啊，哈哈哈23333333 --系列"+ThreadLocalRandom.current().nextInt(1, 99);
			}
			
//			System.out.println("文章阅读数："+map.get("read_count"));
			Integer readingCount = Integer.valueOf(String.valueOf(map.get("read_count")));
			
//			System.out.println("文章分享数："+map.get("share_count"));
			Integer sendingCount = Integer.valueOf(String.valueOf(map.get("share_count")));
			
//			System.out.println("文章评论数："+map.get("comment_count"));
			Integer commentCount = Integer.valueOf(String.valueOf(map.get("comment_count")));
			
//			System.out.println("原始url："+map.get("share_url"));
			String sourceUrl = String.valueOf(map.get("share_url"));
			sourceUrl = StringUtils.substringBefore(sourceUrl, ".html")+".html";
			
//			System.out.println("文章描述："+map.get("introduction"));
			String description = String.valueOf(map.get("introduction"));
			
//			System.out.println("文章key："+map.get("id"));
			String keyId = String.valueOf(String.valueOf(map.get("id")));
			
			List<Map<String, Object>> imgListStr = new ArrayList<>();
			Map<String, Object> urlStr = null;
			JSONArray coverArray = (JSONArray) map.get("cover");
			for (int j = 0; j < coverArray.size(); j++) {
				urlStr = new LinkedHashMap<>();
				urlStr.put("url", coverArray.get(j));
				imgListStr.add(urlStr);
			}
			Map<String, Object> imgListMap = new LinkedHashMap<>();
			imgListMap.put("imgList", imgListStr);
			String json = JSON.toJSONString(imgListMap);
//			System.out.println("文章image_list："+json);
			String imgList = json;
			
//			System.out.println("文章image_url："+coverArray.get(0));
			if(coverArray.size()==0){
				continue;
			}
			String imgUrl = String.valueOf(coverArray.get(0));
			
			String content=null;
			Connection connection = null;
			connection = Jsoup.connect(String.valueOf(map.get("share_url")));
			try {
				Document document = connection.get();
				String contentStr = document.html();
				// 获取文章内容
//				Elements wrapEle = document.select("[content]");
				String sectionStr = "<div><div class=\"content\">"+StringUtils.substringBetween(contentStr, "<div class=\"content\">", "</section>");
				sectionStr = StringUtils.replace(sectionStr, "data-src", "src");
				
//				System.out.println("文章内容："+sectionStr);
//				System.out.println("===========================================");
				content = sectionStr;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
			Integer likingCount = 0;
			sendingCount = 0;
			commentCount = 0;
			readingCount = 0;
			
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(null);
			article.setImgUrl(imgUrl);
			article.setImgList(imgList);
			article.setKeyId(keyId);
			article.setCommentCount(commentCount);
			article.setLikingCount(likingCount);
			article.setSendingCount(sendingCount);
			article.setReadingCount(readingCount);
			article.setSourceId(StaticKey.ArticleQuTouTiao);
			article.setSourceUrl(sourceUrl);
			article.setAddTime(new Date());
			article.setStatus(StaticKey.ArticleStatusTrue);
			article.setType(0);
			String name="头条";
			article.setContentKind(contentKindService.findByName(name));
			Article articleCompare = articleService.findByKeyAndType(keyId,StaticKey.ArticleQuTouTiao);
			if(articleCompare==null&&content!=null){
				article.setContent(content);
				articleService.saveArticle(article);
				count++;
			}
		}

		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	/**
	 * 新浪Article采集
	 */
	@RequestMapping(value="/mobileSinaArticle")
	@ResponseBody
	public Map<String,Object> mobileSinaArticle() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count = 0;
		int page = 1;
		String pageStr = redisService.get("spiderSinaPage");
		if(StringUtils.isBlank(pageStr)){
			redisService.set("spiderSinaPage", "1", -1);
			page = 1;
		}else{
			page = Integer.valueOf(pageStr);
		}
		redisService.set("spiderSinaPage", String.valueOf(page+1), -1);
		String url = "http://interface.sina.cn/ent/feed.d.json";
		String resp = CommonUtil.sendGet(url, "ch=joke&col=joke&show_num=20&page="+page);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);

		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
//		System.out.println("解析到的data==="+parseArray);
		Map map = null;
		String content = null;
		for (int i = 0; i < parseArray.size()-1; i++) {
			map = (Map) parseArray.get(i);

//			System.out.println("文章标题："+map.get("title"));
			String title = String.valueOf(map.get("title"));
			
//			System.out.println("文章评论数："+map.get("comment"));
//			System.out.println("文章image_url："+map.get("img"));
			String imgUrl = String.valueOf(map.get("img"));
			
			String sourceUrl = String.valueOf(map.get("link"));
			sourceUrl = StringUtils.substringBefore(sourceUrl, ".html")+".html";
//			System.out.println("原始url："+sourceUrl);
			
//			System.out.println("文章key："+map.get("docID"));
			String keyId = String.valueOf(map.get("docID"));
			
//			System.out.println("发布时间："+map.get("news_date"));
			
			String imgList =null;
			Connection connection = null;
			connection = Jsoup.connect(sourceUrl);
			try {
				Document document = connection.get();
				// 获取文章内容
				Elements contentEle = document.select("[class=art_pic_card art_content]");
//				System.out.println("文章内容："+contentEle.html());
				content = contentEle.html();
				content = CommonUtil.emojiFilter(content);
				if(StringUtils.isBlank(content)){
					continue;
				}
				
//				截取数组转list
				String[] imgSub = StringUtils.substringsBetween(contentEle.html(), "<img src=\"", "\"");
				List listOrgin = Arrays.asList(imgSub);
				
//				自行构造imgList的json形式
				List<Map<String, Object>> imgListStr = new ArrayList<>();
				Map<String, Object> urlStr = null;
				int listSize = listOrgin.size();
				if(listSize>4){
					listSize = 4;
				}
				for (int j = 1; j < listSize; j++) {
					urlStr = new LinkedHashMap<>();
					urlStr.put("url", listOrgin.get(j));
					imgListStr.add(urlStr);
				}
				if(imgListStr.size()==0){
					continue;
				}
				Map<String, Object> imgListMap = new LinkedHashMap<>();
				imgListMap.put("imgList", imgListStr);
				String json = JSON.toJSONString(imgListMap);
//				System.out.println("文章image_list："+json);
				imgList = json;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Integer likingCount = 0;
			Integer sendingCount = 0;
			Integer commentCount = 0;
			Integer readingCount = 0;
			
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(null);
			article.setImgUrl(imgUrl);
			article.setImgList(imgList);
			article.setKeyId(keyId);
			article.setCommentCount(commentCount);
			article.setLikingCount(likingCount);
			article.setSendingCount(sendingCount);
			article.setReadingCount(readingCount);
			article.setSourceId(StaticKey.ArticleMobileSina);
			article.setSourceUrl(sourceUrl);
			article.setAddTime(new Date());
			article.setStatus(StaticKey.ArticleStatusTrue);
			article.setType(0);
			String name="头条";
			article.setContentKind(contentKindService.findByName(name));
			Article articleCompare = articleService.findByKeyAndType(keyId,StaticKey.ArticleMobileSina);
			if(articleCompare==null&&content!=null){
				article.setContent(content);
				articleService.saveArticle(article);
				count++;
			}
		}
	
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
}
	
	
	
	/**
	 * 东方头条Article采集
	 */
	@RequestMapping(value="/dongfangArticle")
	@ResponseBody
	public Map<String,Object> dongfangArticle() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url_1 = "https://toutiao.eastday.com/toutiao_h5/pulldown";
		String resp = CommonUtil.sendGet(url_1, "type=xiaohua&startkey=8213310536440425993&pgnum=-1&jsonpcallback=data");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		
		resp = StringUtils.substringBetween(resp, "data(", "\"})");
		resp = resp + "\"}";

		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
//		System.out.println("解析到的data==="+parseArray);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size()-1; i++) {
			map = (Map) parseArray.get(i);
			
//			TODO 采集视频
			if(!String.valueOf(map.get("videolist")).equals("[]")){
				continue;
			}
//			System.out.println("文章标题："+map.get("topic"));
			String title = String.valueOf(map.get("topic"));
			
//			System.out.println("文章阅读数："+map.get("urlpv"));
			
//			System.out.println("文章评论数："+map.get("comment_count"));
			Integer commentCount = Integer.valueOf(String.valueOf(map.get("comment_count")));
			
//			System.out.println("原始url："+map.get("url"));
			String sourceUrl = String.valueOf(map.get("url"));
			
//			System.out.println("文章key："+map.get("rowkey"));
			String keyId = String.valueOf(map.get("rowkey"));
			
//			System.out.println("发布时间："+map.get("date"));
			
			JSONArray lbimgArray = (JSONArray) map.get("lbimg");
			Map lbimgMap = (Map) lbimgArray.get(0);
//			System.out.println("文章image_url："+lbimgMap.get("src"));

			
			List<Map<String, Object>> imgListStr = new ArrayList<>();
			Map<String, Object> urlStr = null;
//			System.out.println("文章image_list数："+map.get("miniimg_size"));
			JSONArray miniimgArray = (JSONArray) map.get("miniimg");
			for (int j = 0; j < Integer.parseInt((String)map.get("miniimg_size")); j++) {
				urlStr = new LinkedHashMap<>();
				Map miniimgMap = (Map) miniimgArray.get(j);
				urlStr.put("url", miniimgMap.get("src"));
				imgListStr.add(urlStr);
			}
			Map<String, Object> imgListMap = new LinkedHashMap<>();
			imgListMap.put("imgList", imgListStr);
			String json = JSON.toJSONString(imgListMap);
//			System.out.println("文章image_list："+json);
			
			String content=null;
			Connection connection = null;
			connection = Jsoup.connect(String.valueOf(map.get("url")));
			try {
				Document document = connection.get();

				// 获取文章内容
				Elements contentEle = document.select("[class=J-article-content article-content]");
//				System.out.println("文章内容："+contentEle.html());
				content = contentEle.html();
				if(StringUtils.isBlank(content)){
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String imgUrl = String.valueOf(lbimgMap.get("src"));
			if(map.get("lbimg")!=null){
				imgUrl = String.valueOf(lbimgMap.get("src"));
			}
			else{
				imgUrl = StringUtils.substringBetween(content,"<img src=\"","\"");
			}
			
			String imgList = null;
			if(map.get("miniimg_size")!=null){
				imgList = json;
			}
			if(imgUrl!=null && map.get("image_list")==null){
				imgList = "{\"imgList\":[{\"url\":\""+imgUrl+"\"}]}";
			}
			if(imgUrl==null && map.get("image_list")==null){
				imgList = null;
			}
			
			
			Integer likingCount = 0;
			Integer sendingCount = 0;
			commentCount = 0;
			Integer readingCount = 0;
			
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(null);
			article.setImgUrl(imgUrl);
			article.setImgList(imgList);
			article.setKeyId(keyId);
			article.setCommentCount(commentCount);
			article.setLikingCount(likingCount);
			article.setSendingCount(sendingCount);
			article.setReadingCount(readingCount);
			article.setSourceId(StaticKey.ArticleDongFangTouTiao);
			article.setSourceUrl(sourceUrl);
			article.setAddTime(new Date());
			article.setStatus(StaticKey.ArticleStatusTrue);
			article.setType(0);
			String name="头条";
			article.setContentKind(contentKindService.findByName(name));
			Article articleCompare = articleService.findByKeyAndType(keyId,StaticKey.ArticleDongFangTouTiao);
			if(articleCompare==null&&content!=null){
				article.setContent(content);
				articleService.saveArticle(article);
				count++;
			}
		}
		
	
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
		
	}
	
	
	/**
	 * 今日头条Article采集
	 */
	@RequestMapping(value="/toutiaoArticle4Society")
	@ResponseBody
	public Map<String,Object> toutiaoArticle4Society() {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		int count=0;
		String url = "http://www.toutiao.com/api/pc/feed/";
//		String url = "http://www.toutiao.com/api/article/feed/";
		String tag = "funny";
//		JSONObject param = JinRiTouTiaoUtil.getUrlParam();
//		Object as = param.get("as");
//		Object cp = param.get("cp");
//		String as = "A165E968744150D";
//		String cp = "5984813550ED3E1";
		
//		String as = "A1356A70924C7EE";
//		String cp = "5A021C278EEE8E1";
		
		String as = "A1D5DA60A30B8EB";
		String cp = "5A03FBA8EEABCE1";
		
			
//		String max_behot_time_tmp = "1501816479";
		String max_behot_time_tmp = redisService.get("spiderJinritoutiaoNextTime");
		if(StringUtils.isBlank(max_behot_time_tmp)){
			redisService.set("spiderJinritoutiaoNextTime", "0", -1);
			max_behot_time_tmp = "0";
		}
		String resp = SpiderHttpKit.getToutiaoResp(url+"?category="+tag+"&as="+as+"&cp="+cp+"&max_behot_time_tmp="+max_behot_time_tmp+"&utm_source=toutiao&widen=1&max_behot_time=0&tadrequire=true");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		// 文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
		String urlDetails = "http://www.toutiao.com/group/";
		
		Map parse = (Map) JSONObject.parse(resp);
		
		Map max_behot_time_tmpMap = (Map) parse.get("next");
		max_behot_time_tmp = String.valueOf(max_behot_time_tmpMap.get("max_behot_time"));
		redisService.set("spiderJinritoutiaoNextTime", max_behot_time_tmp, -1);
		
		JSONArray parseArray = (JSONArray) parse.get("data");
		Map map = null;
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);
			
			/*if(!map.get("tag").equals(tag)){
				continue;
				//空指针
			}*/
			if(!tag.equals(map.get("tag"))){
				continue;
			}
			
//			System.out.println("文章标题："+map.get("title"));
			String title=String.valueOf(map.get("title"));
			
//			System.out.println("文章描述："+map.get("abstract"));
			String description = null;
			if(map.get("abstract")!=null){
				description = String.valueOf(map.get("abstract"));
			}else{
				description = title;
			}
			
			
//			System.out.println("文章唯一id："+map.get("group_id"));
			String keyId= String.valueOf(map.get("group_id"));
			
			Integer commentCount = (int) (Math.random()*100);
//			System.out.println("文章评论数："+map.get("comments_count"));
			if(map.get("comments_count")!=null){
				commentCount=Integer.valueOf(String.valueOf(map.get("comments_count")));
			}
			
//			System.out.println("原始url："+"http://toutiao.com/group/"+map.get("group_id"));
			String sourceUrl=String.valueOf("http://toutiao.com/group/"+map.get("group_id"));
			String content=null;
/*			Connection connection = Jsoup.connect(urlDetails + map.get("group_id"));
			try {
				Document document = connection.get();
				// 获取文章内容
				Elements contentEle = document.select("[class=article-content]");
//				System.out.println("文章内容："+contentEle.html());
				content = contentEle.html();
				if(StringUtils.isBlank(content)){
					continue;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			String respHTML = SpiderHttpKit.getToutiaoResp(urlDetails+map.get("group_id"));
//			System.out.println(respHTML);
			String itemId = StringUtils.substringBetween(respHTML, "item_id:'", "'");
			if(StringUtils.isBlank(itemId)){
				itemId = StringUtils.substringBetween(respHTML, "item_id: '", "'");
				if(StringUtils.isBlank(itemId)){
					continue;
				}
			}
//			System.out.println(respContent);
//			System.out.println("文章itemId："+itemId);
			String respContent = CommonUtil.sendGet("https://m.toutiao.com/i"+itemId+"/info/","");
			Map parseContent = (Map) JSONObject.parse(respContent);
//			System.out.println(parseContent);
			Map comtentMap = (Map) parseContent.get("data");
			if(comtentMap.get("content")==null){
				continue;
			}
			content = String.valueOf(comtentMap.get("content"));
//			System.out.println("文章内容："+content);
			
//			System.out.println("文章封面图："+map.get("image_url"));
			String imgUrl = String.valueOf(map.get("image_url"));
			if(map.get("image_url")!=null){
				imgUrl = String.valueOf(map.get("image_url"));
			}
			else{
				imgUrl = StringUtils.substringBetween(content,"<img src=\"","\"");
			}
			
//			System.out.println("文章image_list："+map.get("image_list"));
			String imgList = String.valueOf(map.get("image_list"));
			if(map.get("image_list")!=null){
				imgList = "{\"imgList\":"+imgList+"}";
			}
			if(imgUrl!=null && map.get("image_list")==null){
				imgList = "{\"imgList\":[{\"url\":\""+imgUrl+"\"}]}";
			}
			if(imgUrl==null && map.get("image_list")==null){
				imgList = null;
			}
			
			Integer likingCount = 0;
			Integer sendingCount = 0;
			commentCount = 0;
			Integer readingCount = 0;
			
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(description);
			article.setImgUrl(imgUrl);
			article.setImgList(imgList);
			article.setKeyId(keyId);
			article.setCommentCount(commentCount);
			article.setLikingCount(likingCount);
			article.setSendingCount(sendingCount);
			article.setReadingCount(readingCount);
			article.setSourceId(StaticKey.ArticleJinRiTouTiao);
			article.setSourceUrl(sourceUrl);
			article.setAddTime(new Date());
			article.setStatus(StaticKey.ArticleStatusTrue);
			article.setType(0);
			String name="头条";
			article.setContentKind(contentKindService.findByName(name));
			Article articleCompare = articleService.findByKeyAndType(keyId,StaticKey.ArticleJinRiTouTiao);
			if(articleCompare==null&&content!=null){
				article.setContent(content);
				articleService.saveArticle(article);
				count++;
			}
		}
		returnMap.put("msg", "success");
		returnMap.put("count", count);
		return returnMap;
	}
	
	
	
	
		
}

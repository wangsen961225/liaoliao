package Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.MeipaiUtils;
import com.liaoliao.util.SpiderHttpKit;
import com.liaoliao.util.TimeKit;

public class SpiderUtilDemo {

	/**
	 * 内涵段子Video采集
	 */
	public void neihanVideo() {
		
		String url = "http://neihanshequ.com/video";
		String resp = CommonUtil.sendGet(url, "is_json=1");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);

		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		Map parseData = (Map) parse.get("data");
//		System.out.println("解析到的data==="+parseData);
		JSONArray parseDatadata = (JSONArray) parseData.get("data");
//		System.out.println("解析到的里层data==="+parseDatadata);
		Map map = null;
		for (int i = 0; i < parseDatadata.size(); i++) {
			map = (Map) parseDatadata.get(i);
			Map parseGroup = (Map) map.get("group");
//			System.out.println("解析到的里层group==="+parseGroup);
			
			System.out.println("视频标题："+parseGroup.get("text"));
			System.out.println("视频时长："+parseGroup.get("duration"));
			System.out.println("页面唯一id："+parseGroup.get("id"));
			System.out.println("视频播放数："+parseGroup.get("play_count"));
			System.out.println("视频评论数："+parseGroup.get("comment_count"));
			System.out.println("视频喜欢数："+parseGroup.get("favorite_count"));
			
//			System.out.println("视频分享数："+parseGroup.get("share_count"));
//			System.out.println("视频类别："+parseGroup.get("category_name"));
//			System.out.println("视频点击数："+parseGroup.get("go_detail_count"));
//			System.out.println("视频忽略数："+parseGroup.get("bury_count"));
//			System.out.println("视频唯一id："+parseGroup.get("uri"));
//			System.out.println("视频MP4-url："+parseGroup.get("mp4_url"));
			
//			System.out.println("原始手机url-Share："+parseGroup.get("share_url"));
//			System.out.println("原始手机url："+"http://m.neihanshequ.com/share/group/"+parseGroup.get("id"));
			System.out.println("原始电脑url："+"http://neihanshequ.com/p"+parseGroup.get("id"));
			
			Map mapMedium_cover = (Map) parseGroup.get("medium_cover");
//			System.out.println("视频mapMedium_cover："+mapMedium_cover);
			JSONArray listUrl_list = (JSONArray) mapMedium_cover.get("url_list");
			Map mapListurl = (Map) listUrl_list.get(0);
//			System.out.println("视频mapListurl："+mapListurl);
			System.out.println("视频封面图url："+mapListurl.get("url"));
			
			
			Map map_720p = (Map) parseGroup.get("720p_video");
//			System.out.println("视频720p-map："+map_720p);
			JSONArray list_720p = (JSONArray) map_720p.get("url_list");
			Map map720p = (Map) list_720p.get(0);
//			System.out.println("视频720p-url_list："+map720p);
			System.out.println("视频720p-url："+map720p.get("url"));
			System.out.println("===============================================");
		}
	}
	
	
	
	/**
	 * 小咖秀Video采集
	 */
	public void xiaokaxiuVideo() {
		
		String url = "http://www.xiaokaxiu.com/video/web/get_splendid_video";
		String resp = CommonUtil.sendGet(url, "limit=10&page=1");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
//		System.out.println(resp);
		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		Map parseData = (Map) parse.get("data");
//		System.out.println("解析到的data==="+parseData);
		JSONArray parseDatadata = (JSONArray) parseData.get("list");
//		System.out.println("解析到的list==="+parseDatadata);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseDatadata.size(); i++) {
			map = (Map) parseDatadata.get(i);
			
			System.out.println("视频标题："+map.get("title"));
			System.out.println("视频描述："+map.get("desc"));
			System.out.println("视频真实url："+map.get("linkurl"));
			System.out.println("视频封面："+map.get("cover"));
			System.out.println("视频唯一id："+map.get("videoid"));
			System.out.println("视频播放数："+map.get("hits"));
			System.out.println("视频点赞数："+map.get("praisecount"));
			System.out.println("原始url："+"http://v.xiaokaxiu.com/v/"+map.get("scid")+".html");
			System.out.println("===============================================");
			
//			System.out.println("视频scid："+map.get("scid"));
//			System.out.println("视频voiceid："+map.get("voiceid"));
			
		}
	}
	
	
	
	/**
	 * 360快视频Video采集
	 */
	@Test
	public void kuaishipinVideo(){

		String url_1 = "http://v.sj.360.cn/pc/list";
		String resp = CommonUtil.sendGet(url_1, "n=10&p=1&f=json&ajax=1&uid=1b6ca4ce12e62abd59dc597c12b038e3&channel_id=2");
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
			System.out.println("视频标题："+map.get("t"));
			System.out.println("视频播放数："+map.get("playcnt"));
			System.out.println("视频被赞数："+map.get("zan_num"));
//			System.out.println("视频时长："+map.get("duration"));
			System.out.println("视频封面："+map.get("i"));
			System.out.println("视频唯一id："+map.get("rptid"));
			System.out.println("原始url："+map.get("u"));
			Map parseExData = (Map) map.get("exData");
//			System.out.println("解析到的里层exData==="+parseExData);
//			System.out.println("视频scid："+parseExData.get("code"));
			System.out.println("视频格式化时长："+parseExData.get("totalTimeStr"));
			String detailUrl = String.valueOf(parseExData.get("playLink"));
//			System.out.println("视频动态url："+detailUrl);
			String realDetailUrl = CommonUtil.sendGet(detailUrl,"");
//			System.out.println("get到的动态Url"+realDetailUrl);
			Map realUrlString = (Map) JSONObject.parse(realDetailUrl);
			Map realUrlData = (Map) realUrlString.get("data");
//			System.out.println("解析到的里层realUrlData==="+realUrlData);
			System.out.println("视频真实URL"+realUrlData.get("url"));
			
			System.out.println("===============================================");
			
		}
	}
	
	
	public void meipaiVideo(){
		
		String url_1 = "http://www.meipai.com/squares/new_timeline";
		String resp = CommonUtil.sendGet(url_1, "page=1&count=10&tid=13");
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
			System.out.println("视频标题："+map.get("caption"));
			System.out.println("视频评论数："+map.get("comments_count"));
			System.out.println("视频被赞数："+map.get("likes_count"));
//			System.out.println("视频时长："+map.get("time"));
			System.out.println("视频格式化时长："+TimeKit.secondFormat(String.valueOf(map.get("time"))));
			System.out.println("视频封面："+map.get("cover_pic"));
			System.out.println("视频唯一id："+map.get("feed_id"));
			System.out.println("原始url："+map.get("url"));
//			System.out.println("视频加密URL=="+map.get("video"));
			System.out.println("视频真实URL=="+MeipaiUtils.decode(String.valueOf(map.get("video"))));
			System.out.println("====================================================");
		}
	}
	
	
	/**
	 * 今日头条Article采集
	 */
	public void toutiaoArticle() {

		String url = "http://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A1254964CB71DDE&cp=594B515DBD9ECE1/";
		String ref = "http://www.toutiao.com/funny/";
		String resp = SpiderHttpKit.getToutiaoResp(url);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		// 文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
		String urlDetails = "http://www.toutiao.com/group/";
		Connection connection = null;
		
		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
//		System.out.println("解析到的data==="+parseArray);
		Map map = null;
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);
			// 获取文章group_id
			System.out.println("文章标题："+map.get("title"));
			System.out.println("文章描述："+map.get("abstract"));
			System.out.println("文章封面图："+map.get("image_url"));
			System.out.println("文章image_list："+map.get("image_list"));
			System.out.println("文章唯一id："+map.get("group_id"));
			System.out.println("文章评论数："+map.get("comments_count"));
			System.out.println("原始url："+"http://toutiao.com/group/"+map.get("group_id"));
			
//			System.out.println("文章类别："+map.get("chinese_tag"));
//			System.out.println("文章类别码："+map.get("group_source"));
//			System.out.println("文章来源："+map.get("source"));
//			System.out.println("文章标签："+map.get("tag"));
//			System.out.println("文章类型："+map.get("article_genre"));
//			System.out.println("文章图片列表："+map.get("image_list"));
			
			connection = Jsoup.connect(urlDetails + map.get("group_id"));
			try {
				Document document = connection.get();
				// 获取文章标题
//				Elements title = document.select("[class=article-title]");
//				System.out.println("文章标题："+title.html());
				// 获取文章来源
				Elements articleInfo = document.select("[class=articleInfo]");
//				Elements src = articleInfo.select("[class=src]");
//				System.out.println("文章来源："+src.html());
				//获取文章发布时间
//				Elements time = articleInfo.select("[class=time]");
//				System.out.println("文章发布时间："+time.html());
				// 获取文章内容
				Elements contentEle = document.select("[class=article-content]");
				System.out.println("文章内容："+contentEle.html());
				System.out.println("====================================================");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 东方头条Article采集
	 */
	public void dongfangArticle() {

		String url_1 = "https://toutiao.eastday.com/toutiao_h5/pulldown";
		String resp = CommonUtil.sendGet(url_1, "type=xiaohua&startkey=8213310536440425993&pgnum=-1&jsonpcallback=data");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		
		resp = StringUtils.substringBetween(resp, "data(", "\"})");
		resp = resp + "\"}";

		Map parse = (Map) JSONObject.parse(resp);
		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
		System.out.println("解析到的data==="+parseArray);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size()-1; i++) {
			map = (Map) parseArray.get(i);
//			maps.add((Map) parseArray.get(i));
			// 获取文章group_id
			if(map.get("videolist")==null){
				continue;
			}
			System.out.println("文章标题："+map.get("topic"));
			System.out.println("文章阅读数："+map.get("urlpv"));
			System.out.println("文章评论数："+map.get("comment_count"));
			System.out.println("原始url："+map.get("url"));
			System.out.println("文章key："+map.get("rowkey"));
//			System.out.println("发布时间："+map.get("date"));
			
			JSONArray lbimgArray = (JSONArray) map.get("lbimg");
			Map lbimgMap = (Map) lbimgArray.get(0);
			System.out.println("文章image_url："+lbimgMap.get("src"));
			
			List<Map<String, Object>> imgListStr = new ArrayList<>();
			Map<String, Object> urlStr = null;
			System.out.println("文章image_list数："+map.get("miniimg_size"));
			JSONArray miniimgArray = (JSONArray) map.get("miniimg");
			for (int j = 0; j < Integer.parseInt((String)map.get("miniimg_size")); j++) {
				urlStr = new LinkedHashMap<>();
				Map miniimgMap = (Map) miniimgArray.get(j);
				urlStr.put("url", miniimgMap.get("src"));
				imgListStr.add(urlStr);
			}
			Map<String, Object> imgList = new LinkedHashMap<>();
			imgList.put("imgList", imgListStr);
			String json = JSON.toJSONString(imgList);
			System.out.println("文章image_list："+json);
			
			
			Connection connection = null;
			connection = Jsoup.connect(String.valueOf(map.get("url")));
			try {
				Document document = connection.get();

				// 获取文章内容
				Elements contentEle = document.select("[class=J-article-content article-content]");
				System.out.println("文章内容："+contentEle.html());
				System.out.println("====================================================");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
		
}

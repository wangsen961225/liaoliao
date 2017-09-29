package Spider;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.Base64Kit;
import com.liaoliao.util.CommonUtil;

public class xiaokaxiuVideo {

	public static void main(String[] args) {

		String url_1 = "http://www.xiaokaxiu.com/video/web/get_splendid_video";
//		String resp = CommonKit.getToutiaoResp(url_1,ref_1 );
		String resp = CommonUtil.sendGet(url_1, "limit=10&page=1");
//		ASCII码转中文
		resp = CommonUtil.ascii2native(resp);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
//		System.out.println(resp);
		// 文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
//		String url = "http://www.toutiao.com/group/";
//		Connection connection = null;
		// 链接到该网站
/*		Connection connection = Jsoup.connect(url);
		Document content = null;
		try {
			// 获取内容
			content = connection.get();
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 转换成字符串
		String htmlStr = content.html();
		System.out.println(htmlStr);
		// 因为今日头条的文章展示比较奇葩,都是通过js定义成变量,所以无法使用获取dom元素的方式获取值
		String jsonStr = StringUtils.substringBetween(htmlStr, "var _data = ", ";");
		System.out.println(jsonStr);*/
		Map parse = (Map) JSONObject.parse(resp);
//		System.out.println("get到的json==="+resp);
		Map parseData = (Map) parse.get("data");
		System.out.println("解析到的data==="+parseData);
		JSONArray parseDatadata = (JSONArray) parseData.get("list");
		System.out.println("解析到的list==="+parseDatadata);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseDatadata.size()-1; i++) {
			map = (Map) parseDatadata.get(i);
//			maps.add((Map) parseArray.get(i));
//			Map parseGroup = (Map) map.get("group");
//			System.out.println("解析到的里层group==="+parseGroup);
			
			System.out.println("视频唯一id："+map.get("videoid"));
			System.out.println("视频scid："+map.get("scid"));
			System.out.println("视频标题："+map.get("title"));
			System.out.println("视频封面："+map.get("cover"));
			System.out.println("视频真实url："+map.get("linkurl"));
			System.out.println("视频voiceid："+map.get("voiceid"));
			System.out.println("视频描述："+map.get("desc"));
			System.out.println("视频点击数："+map.get("hits"));
			System.out.println("视频点赞数："+map.get("praisecount"));
			System.out.println("原始url："+"http://v.xiaokaxiu.com/v/"+map.get("scid")+".html");
			
//			System.out.println("视频唯一id："+map.get("uri"));
//			System.out.println("视频点击数："+map.get("go_detail_count"));
//			System.out.println("视频MP4-url："+map.get("mp4_url"));
			
//			Map map_720p = (Map) map.get("720p_video");
//			System.out.println("视频720p-map："+map_720p);
			
//			JSONArray list_720p = (JSONArray) map_720p.get("url_list");
//			Map map720p = (Map) list_720p.get(0);
//			System.out.println("视频720p-url_list："+map720p);
			
//			System.out.println("视频720p-url："+map720p.get("url"));
			
			
			
			
			System.out.println("===============================================");
			
			
			/*System.out.println("视频标签："+parseGroup.get("tag"));
			System.out.println("视频类型："+parseGroup.get("article_genre"));
			System.out.println("视频唯一id："+parseGroup.get("video_id"));*/
			
			/*String urlBase = "http://i.snssdk.com/video/urls/1/toutiao/mp4/" + map.get("video_id");
			String videoResp = CommonKit.getToutiaoResp(urlBase,url_1);
			Map parseVideo = (Map) JSONObject.parse(videoResp);
			System.out.println("get到的json==="+videoResp);
			Map videoData = (Map) parseVideo.get("data");
			System.out.println("get到的data==="+videoData);
			Map videoList = (Map) videoData.get("video_list");
			System.out.println("get到的videoList==="+videoList);*/
			
//			if(parseGroup.get("video_3")!=null){
//				Map video3 = (Map) parseGroup.get("video_3");
//				System.out.println("get到的video3==="+video3);
//				String mainUrl = (String) video3.get("main_url");
//				System.out.println("get到的videoUrl==="+mainUrl);
//				String realUrl = Base64Kit.getFromBase64(mainUrl);
//				System.out.println("真实URL==="+realUrl);
//			}
//			else if(parseGroup.get("video_2")!=null){
//				Map video2 = (Map) parseGroup.get("video_2");
//				System.out.println("get到的video2==="+video2);
//				String mainUrl = (String) video2.get("main_url");
//				System.out.println("get到的videoUrl==="+mainUrl);
//				String realUrl = Base64Kit.getFromBase64(mainUrl);
//				System.out.println("真实URL==="+realUrl);
//			}
//			else if(parseGroup.get("video_1")!=null){
//				Map video1 = (Map) parseGroup.get("video_1");
//				System.out.println("get到的video1==="+video1);
//				String mainUrl = (String) video1.get("main_url");
//				System.out.println("get到的videoUrl==="+mainUrl);
//				String realUrl = Base64Kit.getFromBase64(mainUrl);
//				System.out.println("真实URL==="+realUrl);
//			}

			
			
//			Map mapVideo = null;
			
			
/*			connection = Jsoup.connect(url + map.get("group_id"));
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
				Elements time = articleInfo.select("[class=time]");
				System.out.println("文章发布时间："+time.html());
				// 获取文章内容
				Elements contentEle = document.select("[class=article-content]");
				System.out.println("文章内容："+contentEle.html());
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
		}
		// 遍历之前获取到的map集合,然后分别访问这些文章详情页
/*		for (Map map2 : maps) {
			connection = Jsoup.connect(url + map2.get("group_id"));
			try {
				Document document = connection.get();
				// 获取文章标题
				Elements title = document.select("[class=article-title]");
				System.out.println(title.html());
				// 获取文章来源和文章发布时间
				Elements articleInfo = document.select("[class=articleInfo]");
				Elements src = articleInfo.select("[class=src]");
				System.out.println(src.html());
				Elements time = articleInfo.select("[class=time]");
				System.out.println(time.html());
				// 获取文章内容
				Elements contentEle = document.select("[class=article-content]");
				System.out.println(contentEle.html());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
}

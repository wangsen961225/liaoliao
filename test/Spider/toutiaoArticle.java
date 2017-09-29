package Spider;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.SpiderHttpKit;

public class toutiaoArticle {

	public static void main(String[] args) {

//		String url_1 = "http://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A1254964CB71DDE&cp=594B515DBD9ECE1/";
//		String ref_1 = "http://www.toutiao.com/funny/";
//		String resp = CommonUtil.getToutiaoResp("http://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A125595883FDE63&cp=59831DCE16B30E1");
		String url_1 = "http://www.toutiao.com/api/pc/feed/";
		String tag = "funny";
//		JSONObject param = JinRiTouTiaoUtil.getUrlParam();
//		String as = param.get("as");
//		String cp = param.get("cp");
		String as = "A165E968744150D";
		String cp = "5984813550ED3E1";
		String max_behot_time_tmp = "1501816479";
		String resp = SpiderHttpKit.getToutiaoResp(url_1+"?category="+tag+"&as="+as+"&cp="+cp+"&max_behot_time_tmp="+max_behot_time_tmp+"&utm_source=toutiao&widen=1&max_behot_time=0&tadrequire=true");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);
		// 文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
		String url = "http://www.toutiao.com/group/";
		Connection connection = null;
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
		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
		System.out.println("解析到的data==="+parseArray);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);
//			maps.add((Map) parseArray.get(i));
			// 获取文章group_id
			System.out.println("文章group_id："+map.get("group_id"));
			System.out.println("文章类别："+map.get("chinese_tag"));
			System.out.println("文章类别码："+map.get("group_source"));
			System.out.println("文章image_url："+map.get("image_url"));
			System.out.println("文章image_list："+map.get("image_list"));
			System.out.println("文章来源："+map.get("source"));
			System.out.println("文章标题："+map.get("title"));
			System.out.println("文章描述："+map.get("abstract"));
			System.out.println("文章评论数："+map.get("comments_count"));
			System.out.println("文章标签："+map.get("tag"));
			if(!map.get("tag").equals(tag)){
				continue;
			}
			System.out.println("文章类型："+map.get("article_genre"));
			System.out.println("原始url："+"http://toutiao.com/group/"+map.get("group_id"));
			
			String respHTML = CommonUtil.sendGet(url+map.get("group_id")+"/","");
			System.out.println(respHTML);
			String itemId = StringUtils.substringBetween(respHTML, "item_id:'", "'");
			if(StringUtils.isBlank(itemId)){
				itemId = StringUtils.substringBetween(respHTML, "item_id: '", "'");
				if(StringUtils.isBlank(itemId)){
					continue;
				}
			}
//			System.out.println(respContent);
			System.out.println("文章itemId："+itemId);
			String respContent = CommonUtil.sendGet("https://m.toutiao.com/i"+itemId+"/info/","");
			Map parseContent = (Map) JSONObject.parse(respContent);
			System.out.println(parseContent);
			Map comtentMap = (Map) parseContent.get("data");
			System.out.println("文章内容："+comtentMap.get("content"));
			
			
//			connection = Jsoup.connect(url + map.get("group_id"));
//			try {
//				Document document = connection.get();
//				System.out.println(document.html());
//				String itemId = StringUtils.substringBetween(document.html(), "item_id:'", "',");
//				System.out.println("文章itemId："+itemId);
/*				// 获取文章标题
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
				System.out.println("文章内容："+contentEle.html());*/
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			
			
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

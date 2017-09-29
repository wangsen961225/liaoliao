package Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.CommonUtil;

/**
 *	来源http://joke.sina.cn/?vt=4
 */
public class sinaArticle {

	public static void main(String[] args) {

		int page = 1;
		String url = "http://interface.sina.cn/ent/feed.d.json";
		String resp = CommonUtil.sendGet(url, "ch=joke&col=joke&show_num=20&page="+page);
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);

		Map parse = (Map) JSONObject.parse(resp);
		System.out.println("get到的json==="+resp);
		JSONArray parseArray = (JSONArray) parse.get("data");
		System.out.println("解析到的data==="+parseArray);
		Map map = null;
		for (int i = 0; i < parseArray.size()-1; i++) {
			map = (Map) parseArray.get(i);

			System.out.println("文章标题："+map.get("title"));
			
//			System.out.println("文章评论数："+map.get("comment"));
			System.out.println("文章image_url："+map.get("img"));
			
			String sourceUrl = String.valueOf(map.get("link"));
			sourceUrl = StringUtils.substringBefore(sourceUrl, ".html")+".html";
			System.out.println("原始url："+sourceUrl);
			
			System.out.println("文章key："+map.get("docID"));
//			System.out.println("发布时间："+map.get("news_date"));
			
			Connection connection = null;
			connection = Jsoup.connect(sourceUrl);
			try {
				Document document = connection.get();
				// 获取文章内容
				Elements contentEle = document.select("[class=art_pic_card art_content]");
				System.out.println("文章内容："+contentEle.html());
				
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
				Map<String, Object> imgList = new LinkedHashMap<>();
				imgList.put("imgList", imgListStr);
				String json = JSON.toJSONString(imgList);
				System.out.println("文章image_list："+json);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}


	}
}

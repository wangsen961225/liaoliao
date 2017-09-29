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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.CommonUtil;

public class dongfangArticle {

	public static void main(String[] args) {

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

//			TODO 采集视频
			if(!String.valueOf(map.get("videolist")).equals("[]")){
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
			
//			自行构造imgList的json形式
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
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}


	}
}

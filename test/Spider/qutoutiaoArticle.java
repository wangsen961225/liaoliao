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

public class qutoutiaoArticle {

	public static void main(String[] args) {

		String url_1 = "http://api.1sapp.com/content/getList";
		String resp = CommonUtil.sendGet(url_1, "OSVersion=7.0&cid=2&content_type=1,2,4,3&deviceCode=868030024519827&dtu=002&lat=0.0&lon=0.0&max_time=0&network=wifi&op=2&page=1&show_time=0&time=1500541172413&uuid=ebf3528e7c374f8384f68c25f900a99c&version=20305&versionName=2.3.5.0621.1047&sign=c64e6fc3f95a88fcf231810d51d3c345");
//		过滤emoji表情
		resp = CommonUtil.emojiFilter(resp);


		Map parse = (Map) JSONObject.parse(resp);
		System.out.println("get到的json==="+resp);
		Map dataMap = (Map) parse.get("data");
		System.out.println("get到的data==="+dataMap);
		JSONArray parseArray = (JSONArray) dataMap.get("data");
		System.out.println("解析到的里层data==="+parseArray);
		Map map = null;
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象
		for (int i = 0; i < parseArray.size(); i++) {
			map = (Map) parseArray.get(i);

//			TODO 采集视频
			if(map.get("id")==null||map.get("tips").equals("视频")||map.get("play_time")!=null){
				continue;
			}
			System.out.println("文章标题："+map.get("title"));
			System.out.println("文章阅读数："+map.get("read_count"));
			System.out.println("文章分享数："+map.get("share_count"));
			System.out.println("文章评论数："+map.get("comment_count"));
			System.out.println("原始url："+map.get("share_url"));
			System.out.println("文章描述："+map.get("introduction"));
			System.out.println("文章key："+map.get("id"));
			
			List<Map<String, Object>> imgListStr = new ArrayList<>();
			Map<String, Object> urlStr = null;
			JSONArray coverArray = (JSONArray) map.get("cover");
			for (int j = 0; j < coverArray.size(); j++) {
				urlStr = new LinkedHashMap<>();
				urlStr.put("url", coverArray.get(j));
				imgListStr.add(urlStr);
			}
			Map<String, Object> imgList = new LinkedHashMap<>();
			imgList.put("imgList", imgListStr);
			String json = JSON.toJSONString(imgList);
			System.out.println("文章image_list："+json);
			
			System.out.println("文章image_url："+coverArray.get(0));
			
			
			
			Connection connection = null;
			connection = Jsoup.connect(String.valueOf(map.get("share_url")));
			try {
				Document document = connection.get();
				String contentStr = document.html();
				// 获取文章内容
//				Elements wrapEle = document.select("[content]");
				String sectionStr = "<div><div class=\"content\">"+StringUtils.substringBetween(contentStr, "<div class=\"content\">", "</section>");
				sectionStr = StringUtils.replace(sectionStr, "data-src", "src");
						
				System.out.println("文章内容："+sectionStr);
				System.out.println("===========================================");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}


	}
}

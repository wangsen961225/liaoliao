package Spider;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.Base64Kit;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.MeipaiUtils;
import com.liaoliao.util.TimeKit;

public class meipaiVideo {

								/*  搞笑       明星      女神       舞蹈       音乐       美食       美妆        吃秀        男神       宝宝       宠物       手工         游戏         运动        穿秀*/
//	final static String[] TYPES = {"13","16","19","63","62","59","27","423","31","18","6","450","480","487","460"};
//	final static Integer PAGE = 1;  //页数
//	final static Integer COUNT = 100;  //数量
//	final static String tid = "";  //数量
	
	
	public static void main(String[] args) {

		String url_1 = "http://www.meipai.com/squares/new_timeline";
//		String resp = CommonKit.getToutiaoResp(url_1,ref_1 );
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
		System.out.println("解析到的medias==="+parseRes);
		Map map = null;
		for (int i = 0; i < parseRes.size()-1; i++) {
			map = (Map) parseRes.get(i);

			
			System.out.println("视频标题："+map.get("caption"));
			System.out.println("视频评论数："+map.get("comments_count"));
			System.out.println("视频被赞数："+map.get("likes_count"));
			System.out.println("视频时长："+map.get("time"));
			System.out.println("视频格式化时长："+TimeKit.secondFormat(String.valueOf(map.get("time"))));
			System.out.println("视频封面："+map.get("cover_pic"));
			System.out.println("视频唯一id："+map.get("feed_id"));
			System.out.println("原始url："+map.get("url"));
			System.out.println("视频加密URL=="+map.get("video"));
			System.out.println("视频真实URL=="+MeipaiUtils.decode(String.valueOf(map.get("video"))));
			
			
			System.out.println("===============================================");
			
		}
	}
}

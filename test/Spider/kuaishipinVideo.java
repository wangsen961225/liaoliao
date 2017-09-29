package Spider;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.Base64Kit;
import com.liaoliao.util.CommonUtil;

public class kuaishipinVideo {

	public static void main(String[] args) {

		String url_1 = "http://v.sj.360.cn/pc/list";
//		String resp = CommonKit.getToutiaoResp(url_1,ref_1 );
		String resp = CommonUtil.sendGet(url_1, "n=10&p=1&f=json&ajax=1&uid=1b6ca4ce12e62abd59dc597c12b038e3&channel_id=2");
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
		JSONArray parseRes = (JSONArray) parseData.get("res");
		System.out.println("解析到的res==="+parseRes);
		Map map = null;
//		List<Map> maps = new ArrayList<>();
		// 遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
		for (int i = 0; i < parseRes.size()-1; i++) {
			map = (Map) parseRes.get(i);

			
			System.out.println("视频标题："+map.get("t"));
			System.out.println("视频播放数："+map.get("playcnt"));
			System.out.println("视频被赞数："+map.get("zan_num"));
			System.out.println("视频时长："+map.get("duration"));
			System.out.println("视频封面："+map.get("i"));
			System.out.println("视频唯一id："+map.get("rptid"));
			System.out.println("原始url："+map.get("u"));
			Map parseExData = (Map) map.get("exData");
			System.out.println("解析到的里层exData==="+parseExData);
			System.out.println("视频scid："+parseExData.get("code"));
			System.out.println("视频格式化时长："+parseExData.get("totalTimeStr"));
			String detailUrl = String.valueOf(parseExData.get("playLink"));
			System.out.println("视频动态url："+detailUrl);
			String realDetailUrl = CommonUtil.sendGet(detailUrl,"");
			System.out.println("get到的动态Url"+realDetailUrl);
			Map realUrlString = (Map) JSONObject.parse(realDetailUrl);
			Map realUrlData = (Map) realUrlString.get("data");
			System.out.println("解析到的里层realUrlData==="+realUrlData);
			System.out.println("视频真实URL"+realUrlData.get("url"));
			
			
			System.out.println("===============================================");
			
		}
	}
}

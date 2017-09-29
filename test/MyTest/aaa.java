package MyTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.JPushUtil;
import com.liaoliao.util.StaticKey;

public class aaa {
public static void main(String[] args) throws IOException {
	
//	int userId = 1000;
//	Map<String, String> extras = new HashMap<String, String>();
//	// 添加附加信息
//	extras.put("type", StaticKey.JPushVipLoginEffectType);
//	extras.put("userId", String.valueOf(userId));
//	
//	JPushUtil.sendAllMessage(user.getNickName(),extras);
//	user.setLoginTime(new Date());
//	userService.updateUser(user);
////	统计每日vipEffect霸屏上线次数
//	handleCountService.handleCountPlusOne("vipEffect");
//	map.put("msg", "success");
//	map.put("code", StaticKey.ReturnServerTrue);
//	System.out.println(map);
//	CommonUtil.sendGet("http://i.snssdk.com/neihan/video/playback/?video_id=6b160169687048ce8bc9938cebdb3a95&quality=720p&line=0&is_gif=0","");
//	String doc=Jsoup.connect("http://v3-nh.ixigua.com/b6fdd376b4e1bdeae99b8f9d4c53497e/59941c64/video/m/22063fc5067d3794c36bf4a90c019d47fbc114a1640000137e8a24d11e/").ignoreContentType(true).get().location();
//	System.out.println(doc.location()); 
//	System.out.println("");
	double a = 1.564;
	double b = Math.floor(a);
	int c = (int) a;
	System.out.println(c);
    }
}

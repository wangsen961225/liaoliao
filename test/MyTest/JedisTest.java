package MyTest;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("localhost");
		/*System.out.println("服务器正在运行:"+jedis.ping());
		jedis.set("11", "www.baidu.com");
		System.out.println(jedis.get("11"));
		jedis.lpush("set-liat", "baidu");
		jedis.lpush("set-list", "google");
		jedis.lpush("set-list", "zhanghao");
		List<String>list=jedis.lrange("set-list", 0, 2);
		for (String string : list) {
			System.out.println(string);
		}*/
		Set<String> jedis2=jedis.keys("*");
		Iterator<String> it=jedis2.iterator();
		int i=0;
		while (it.hasNext()) {
			i++;
			String string = (String) it.next();
			System.out.println("第"+i+"个key=====>"+string);
		}
	}
}

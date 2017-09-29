package MyTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liaoliao.redisclient.RedisService;

public class RedisTest {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:redis.xml");

		RedisService rs = (RedisService) factory.getBean("redisService");
		if (rs != null) {
			System.out.println("RedisService : " + rs);
			if (rs.check("mpz", "123456")) {
				// 已事先写进redis的数据
				System.out.println("redis 已存在  mpz 123456");
				System.out.println("添加一个新的 key-value : mpz 1234567 expire 30");
				rs.set("mpz", "123456", 30);
			} else {
				System.out.println("添加一个新的 key-value : mpz 123456 expire 30");
				rs.set("mpz", "1234567", 30);
			}

			String value = rs.get("mpz");
			System.out.println(value);
		}
	}
}

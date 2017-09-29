package MyTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDemo {

	public static void main(String[] args) {
		
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 60*60*1000); //10分钟前的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String nowTime_10 = dateFormat.format(now_10);
		System.out.println(nowTime_10);
		

	}

}

package MyTest;

import java.util.Map;

import org.junit.Test;
/**
 * 阿里云的移动推送
 * @author Administrator
 *
 */
public class AliyunMPushUtil {
	    private static BaseTest baseTest=new BaseTest();
	    private static PushTest pushTest=new PushTest();
	     /**
	      * 推送消息给android
	      * <p>
	      * 参见文档 https://help.aliyun.com/document_detail/48085.html
	      */
	     @Test
	     public static void testPushMessageToAndroid(String title,String body) throws Exception{
				
				
					baseTest.beforeClass();
					pushTest.testPushMessageToAndroid(title,body);			
				
			}
	     
	     /**
	      * 推送通知给android
	      * <p>
	      * 参见文档 https://help.aliyun.com/document_detail/48087.html
	      */
         @Test
         public static void testPushNoticeToAndroid(String title,String body) throws Exception{
				baseTest.beforeClass();
				pushTest.testPushNoticeToAndroid(title,body);
         }
        
         
         /**
          * 推送消息给iOS
          * <p>
          * 参见文档 https://help.aliyun.com/document_detail/48086.html
          */
         @Test
	     public static void testPushMessageToIOS() throws Exception{
				
				
					baseTest.beforeClass();
					pushTest.testPushMessageToIOS();			
				
			}
         
         
         /**
          * 推送通知给iOS
          * <p>
          * 参见文档 https://help.aliyun.com/document_detail/48088.html
          */
         @Test
	     public static void testPushNoticeToIOS_toAll() throws Exception{
				
				
					baseTest.beforeClass();
					pushTest.testPushNoticeToIOS_toAll();			
				
			}
         /**
          * 推送高级接口,推送给android和iOS
          * <p>
          * 参见文档 https://help.aliyun.com/document_detail/48089.html
          * //
          */
         @Test
	     public static void testAdvancedPush(String message,String title,Map<String, String> extras,int time) throws Exception{
				
				
					baseTest.beforeClass();
					pushTest.testAdvancedPush(message, title,extras, time);			
				
			}
         /**
          * 取消定时推送
          * <p>
          * //
          */
         @Test
	     public static void testCancelPush() throws Exception{
				
				
					baseTest.beforeClass();
					pushTest.testCancelPush();			
				
			}
}

package MyTest;

import java.io.File;
import java.net.URL;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;

public class WriterMarkTest {
	
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI8W8OljECXmdW";
    private static String accessKeySecret = "dnpIcJVymrgfVSalQ1aSX4OF0y5F9H";
    private static String bucketName = "sjcm";
    private static String key = "images/3.jpg";
    
    public static void main(String[] args) {
    	ImageSample();
	}
    
    /**
     * 生成自带签名的url
     */
    
    public static void url(){
    	 OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	    // 图片处理样式
    	    String style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";
    	    // 过期时间10分钟
    	    Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
    	    GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
    	    req.setExpiration(expiration);
    	    req.setProcess(style);
    	    URL signedUrl = ossClient.generatePresignedUrl(req);
    	    System.out.println(signedUrl);
    }
    
	 /**
     * 图片上水印
     */
    public static void ImageSample (){
    	
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";   
            GetObjectRequest request = new GetObjectRequest(bucketName, key);
            request = new GetObjectRequest(bucketName, key);
            request.setProcess(style);
          //  ossClient.putObject(bucketName, accessKeyId,new File("copy3.jpg"));
            
        } catch (OSSException oe) {
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
        } catch (ClientException ce) {
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
}

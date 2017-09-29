package MyTest;

public class Testsssss {
	public static void main(String[] args) {
		String aa="Screenshot_20170828134651_1503912980125.jpg,Screenshot_20170828134639_1503912980139.jpg,Screenshot_20170828134627_1503912980141.jpg,Screenshot_20170828134708_1503912980143.jpg,Screenshot_20170828151433_1503912980146.jpg,Screenshot_20170828151444_1503912980148.jpg";
		String[] imgs =aa.split(",");
		String spli="";
		for(int i=0;i<imgs.length;i++){
			spli+="{\"url\":\"http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+imgs[i]+"\"},";
		}
		spli=spli.substring(0, spli.length()-1);
		spli="{\"imgList\":["+spli+"]}";
	System.out.println(spli);
	}

}

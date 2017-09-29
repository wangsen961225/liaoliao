package MyTest;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.liaoliao.util.CommonUtil;

public class ThesaurusDemo {
	
	public static void main(String[] args) {
		String  aaa="你结婚";
		String getResult = CommonUtil.sendGet("http://zhannei.baidu.com/api/customsearch/keywords","title="+aaa);
		String resp = CommonUtil.ascii2native(getResult);
		Map parse = (Map) JSONObject.parse(resp);
		Map result = (Map)parse.get("result");
		Map  keyword_list=(Map)result.get("res");
		String subStr="";
		String suab = keyword_list.get("keyword_list").toString();
		System.out.println(suab.length());

		String[] sub = suab.substring(1, suab.length()-1).split(",");
		for(int i=0;i<sub.length;i++){
			subStr+=sub[i].substring(1, sub[i].length()-1)+",";
		}
		String keywords=subStr.substring(0, subStr.length()-1);
		System.out.println(keywords);
		
	
	}

}

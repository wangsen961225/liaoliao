package MyTest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class Demo11111 {
	public static void main(String[] args) {
		List imgListObjList = new ArrayList();
		
		String imglistStr = "{\"imgList\":[{\"url\":\"http://p1.pstatp.com/list/2bd7003638737dfdb34f\"},{\"url\":\"http://p1.pstatp.com/list/2a4300048bcc435e9fa7\"},{\"url\":\"http://p9.pstatp.com/list/2a450000d30578423470\"}]}";
		if(!StringUtils.isBlank(imglistStr)){
			Map imgMap = (Map) JSONObject.parse(imglistStr);
			List imgListObj = (List) imgMap.get("imgList");
			Map<String, Object> imgListItem = null;
			if(imgListObj.size()>2){
				for (int j = 0; j < imgListObj.size(); j++) {
					imgListItem = new LinkedHashMap<>();
					String imgListUrl = String.valueOf(imgListObj.get(j));
					Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
					imgListItem.put("url", imgMapItem.get("url"));
					imgListObjList.add(imgListItem);
				}
			}
			if(imgListObj.size()==2){
				for (int j = 0; j < 3; j++) {
					if(j>1){
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(1));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
					}else{
					imgListItem = new LinkedHashMap<>();
					String imgListUrl = String.valueOf(imgListObj.get(j));
					Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
					imgListItem.put("url", imgMapItem.get("url"));
					imgListObjList.add(imgListItem);
					}
				}
			}
			if(imgListObj.size()==1){
				for (int j = 0; j < 3; j++) {
					if(j>0){
						imgListItem = new LinkedHashMap<>();
						String imgListUrl = String.valueOf(imgListObj.get(0));
						Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
						imgListItem.put("url", imgMapItem.get("url"));
						imgListObjList.add(imgListItem);
					}
					else{
					imgListItem = new LinkedHashMap<>();
					String imgListUrl = String.valueOf(imgListObj.get(j));
					Map imgMapItem = (Map) JSONObject.parse(imgListUrl);
					imgListItem.put("url", imgMapItem.get("url"));
					imgListObjList.add(imgListItem);
					}
				}
			}
			System.out.println(imgListObjList);
		}	
		
	}

}

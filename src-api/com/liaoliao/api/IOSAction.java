package com.liaoliao.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/api")
public class IOSAction {
	/**
	 * IOS 上下架
	 * @return
	 */
	private String name="off";
	
	@RequestMapping(value="/getADStatus")
	@ResponseBody
	public Map<String, String>  getADStatus(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		map.put("status",name);
		System.out.println(name);
		return map;
	}
		
	@RequestMapping(value="/getADStatu")
	@ResponseBody
	public void  getADStatu(HttpServletRequest request){
		String name1=request.getParameter("name");
		if(!name1.equals(name)){
			name=name1;
		}
		System.out.println(name);
	}
	
}

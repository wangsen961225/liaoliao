package com.liaoliao.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.redisclient.RedisService;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/api")
public class UserUpLoadAction {
	
	@Autowired
	private RedisService redisService;
	
	@ResponseBody
	@RequestMapping(value="upLoadShare")
	public Map<String,Object> upLoadShare(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
		
		if(userId==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		
		
		
		
		return map;
	}

}

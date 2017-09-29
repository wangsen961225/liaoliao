package com.liaoliao.redisclient;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liaoliao.sys.service.SystemConfigService;
import com.liaoliao.user.entity.UserToken;
import com.liaoliao.user.service.UserTokenService;

@Service
public class RedisService extends AbstractRedisService<String, String> {

	@Autowired
	private UserTokenService userTokenService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	public boolean getValidate(HttpServletRequest request,Integer userId) {
		String tokenString = request.getHeader("token");
		String idString = String.valueOf(userId);
		if(StringUtils.isBlank(idString)||StringUtils.isBlank(tokenString)){
			return false;
		}
		boolean tokenCheck = this.check(idString,tokenString);
		if(tokenCheck){
			this.set(idString, tokenString, 60*60*24*15);
			return true;
		}
		UserToken userToken = userTokenService.findById(userId);
		if(userToken==null){
			return false;
		}
		if(tokenString.equals(userToken.getToken())){
			this.set(idString, tokenString, 60*60*24*15);
			return true;
		}
		return false;
	}
	
	public String getConfigValue(String configKey) {
		if(StringUtils.isBlank(configKey)){
			return null;
		}
		String configValue =  this.get(configKey);
		if(StringUtils.isBlank(configValue)){
			configValue = systemConfigService.findStringValue(configKey);
			if(StringUtils.isBlank(configValue)){
				return null;
			}
			this.set(configKey, configValue, -1);
			return configValue;
		}
		return configValue;
	}
	
}

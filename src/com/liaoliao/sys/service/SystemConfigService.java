package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.SystemConfig;

public interface SystemConfigService {
	
	SystemConfig findEntityByKey(String keyName);
	
	Integer findIntegerValue(String keyName);
	
	String findStringValue(String keyName);

	List<SystemConfig> findAll(Integer pageNo);

	void updateSystemConfig(SystemConfig systemConfig);

	Integer findCount();

}

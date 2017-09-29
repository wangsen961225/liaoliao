package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.SystemConfig;

public interface SystemConfigDao {

	SystemConfig findValueByKey(String keyName);

	List<SystemConfig> findAll(Integer pageNo);

	void updateSystemConfig(SystemConfig systemConfig);

	Integer findCount();

}

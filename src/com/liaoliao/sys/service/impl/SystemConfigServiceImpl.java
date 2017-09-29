package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.SystemConfigDao;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.sys.service.SystemConfigService;


@Service
@Transactional
public class SystemConfigServiceImpl implements SystemConfigService {
	
	@Autowired
	private SystemConfigDao systemConfigDao;

	@Override
	public SystemConfig findEntityByKey(String keyName) {
		return systemConfigDao.findValueByKey(keyName);
	}

	@Override
	public Integer findIntegerValue(String keyName) {
		return Integer.valueOf(systemConfigDao.findValueByKey(keyName).getContent());
	}

	@Override
	public String findStringValue(String keyName) {
		return systemConfigDao.findValueByKey(keyName).getContent();
	}

	@Override
	public List<SystemConfig> findAll(Integer pageNo) {
		return systemConfigDao.findAll( pageNo);
	}

	@Override
	public void updateSystemConfig(SystemConfig systemConfig) {
		systemConfigDao.updateSystemConfig(systemConfig);
	}

	@Override
	public Integer findCount() {
		return systemConfigDao.findCount();
	}




	

}

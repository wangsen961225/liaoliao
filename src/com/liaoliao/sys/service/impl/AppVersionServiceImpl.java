package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.AppVersionDao;
import com.liaoliao.sys.entity.AppVersion;
import com.liaoliao.sys.service.AppVersionService;


@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
	
	@Autowired
	private AppVersionDao appVersionDao;

	@Override
	public AppVersion findMaxVersion() {
		return appVersionDao.findMaxVersion();
	}

	@Override
	public List<AppVersion> findAll(Integer pageNo) {
		return appVersionDao.findAll(  pageNo);
	}

	@Override
	public void saveAppVersion(AppVersion appVersion) {
		appVersionDao.saveAppVersion(appVersion);
	}

	@Override
	public AppVersion findById(Integer id) {
		return appVersionDao.findById(id);
	}

	@Override
	public void updateAppVersion(AppVersion appVersion) {
		appVersionDao.updateAppVersion(appVersion);
	}

	@Override
	public void delAppVersion(AppVersion appVersion) {
		appVersionDao.delAppVersion(appVersion);
		
	}

	@Override
	public Integer findCount() {
		return appVersionDao.findCount();
	}


	

}

package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.AppVersion;

public interface AppVersionService {
	
	AppVersion findMaxVersion();

	List<AppVersion> findAll(Integer pageNo);

	void saveAppVersion(AppVersion appVersion);

	AppVersion findById(Integer id);

	void updateAppVersion(AppVersion appVersion);

	void delAppVersion(AppVersion appVersion);

	Integer findCount();


}

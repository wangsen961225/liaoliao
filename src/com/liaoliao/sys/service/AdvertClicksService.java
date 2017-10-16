package com.liaoliao.sys.service;

import com.liaoliao.sys.entity.AdvertClicks;

public interface AdvertClicksService {

	/**
	 * 保存用户点击广告信息
	 * @param ac
	 */
	void countAdvertClicks(AdvertClicks ac);

	/**
	 * 获取用户当日点击广告的次数
	 * @param userId
	 * @return
	 */
	Integer findDayCountAdvertClicksById(Integer userId);

	/**
	 * 获取用户总共点击广告的次数
	 * @param userId
	 * @return
	 */
	Integer findTotalCountAdvertClicksById(Integer userId);
		
	




}

package com.liaoliao.sys.dao;

import com.liaoliao.sys.entity.AdvertClicks;

public interface AdvertClicksDao {

	void countAdvertClicks(AdvertClicks ac);

	Integer findDayCountAdvertClicksById(Integer userId);

	Integer findTotalCountAdvertClicksById(Integer userId);
	
	



}

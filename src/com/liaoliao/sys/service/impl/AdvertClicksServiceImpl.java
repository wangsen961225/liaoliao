package com.liaoliao.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.AdvertClicksDao;
import com.liaoliao.sys.entity.AdvertClicks;
import com.liaoliao.sys.service.AdvertClicksService;

@Service
@Transactional
public class AdvertClicksServiceImpl implements AdvertClicksService {

	@Autowired
	AdvertClicksDao advertClicksDao;
	
	@Override
	public void countAdvertClicks(AdvertClicks ac) {
		advertClicksDao.countAdvertClicks(ac);
	}

	@Override
	public Integer findDayCountAdvertClicksById(Integer userId) {
		return advertClicksDao.findDayCountAdvertClicksById(userId);
	}

	@Override
	public Integer findTotalCountAdvertClicksById(Integer userId) {
		
		
		return advertClicksDao.findTotalCountAdvertClicksById(userId);
	}
	
	

}

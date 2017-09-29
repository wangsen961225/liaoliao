package com.liaoliao.profit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.profit.dao.ProfitTimeDao;
import com.liaoliao.profit.entity.ProfitTime;
import com.liaoliao.profit.service.ProfitTimeService;

@Service
@Transactional
public class ProfitTimeServiceImpl implements ProfitTimeService {

	@Autowired 
	private ProfitTimeDao profitTimeDao;

	@Override
	public void saveOrUpdateProfitTime(ProfitTime profitTime) {
		profitTimeDao.saveOrUpdateProfitTime(profitTime);
	}

	@Override
	public ProfitTime findByUserId(Integer userId) {
		return profitTimeDao.findByUserId(userId);
	}


	



	

}

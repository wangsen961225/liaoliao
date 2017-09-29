package com.liaoliao.profit.service;


import com.liaoliao.profit.entity.ProfitTime;


public interface ProfitTimeService {

	void saveOrUpdateProfitTime(ProfitTime profitTime);
	
	ProfitTime findByUserId(Integer userId);

}

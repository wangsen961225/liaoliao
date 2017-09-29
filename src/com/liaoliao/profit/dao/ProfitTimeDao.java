package com.liaoliao.profit.dao;

import com.liaoliao.profit.entity.ProfitTime;

public interface ProfitTimeDao {

	void saveOrUpdateProfitTime(ProfitTime profitTime);
	
	ProfitTime findByUserId(Integer userId);

}

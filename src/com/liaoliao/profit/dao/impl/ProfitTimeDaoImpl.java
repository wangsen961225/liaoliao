package com.liaoliao.profit.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.ProfitTimeDao;
import com.liaoliao.profit.entity.ProfitTime;

public class ProfitTimeDaoImpl extends BaseDaoImpl<ProfitTime,Integer> implements ProfitTimeDao {

	@Override
	public void saveOrUpdateProfitTime(ProfitTime profitTime) {
		this.saveOrUpdate(profitTime);
	}

	@Override
	public ProfitTime findByUserId(Integer userId) {
		String hqlString = "from ProfitTime where userId = ?0";
		return this.getByHQL(hqlString, userId);
	}

}

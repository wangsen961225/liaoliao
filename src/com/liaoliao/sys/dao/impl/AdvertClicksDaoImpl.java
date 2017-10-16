package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.AdvertClicksDao;
import com.liaoliao.sys.entity.AdvertClicks;
import com.liaoliao.util.TimeKit;

public class AdvertClicksDaoImpl extends BaseDaoImpl<AdvertClicks,Integer> implements AdvertClicksDao {

	@Override
	public void countAdvertClicks(AdvertClicks ac) {
		this.save(ac);
	}

	@Override
	public Integer findDayCountAdvertClicksById(Integer userId) {
		String hql = "from AdvertClicks where user.id=?0 and addTime between ?1 and ?2";
		List<AdvertClicks> list = this.getListByHQL(hql, userId,TimeKit.todayStart(),TimeKit.getDateAfter(1));
		return list.size();
	}

	@Override
	public Integer findTotalCountAdvertClicksById(Integer userId) {
		String hql = "from AdvertClicks where user.id=?0";
		List<AdvertClicks> list = this.getListByHQL(hql, userId);
		return list.size(); 
	}

	
	
}

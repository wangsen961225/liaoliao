package com.liaoliao.sys.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.CityDao;
import com.liaoliao.sys.entity.City;

public class CityDaoImpl extends BaseDaoImpl<City,Integer> implements CityDao {

	@Override
	public City findByName(String city) {
		String hql="from City where name = ?0";
		return this.getByHQL(hql, city);
	}

	@Override
	public void saveCity(City cit) {
		this.save(cit);
		
	}

}

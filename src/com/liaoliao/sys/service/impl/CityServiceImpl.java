package com.liaoliao.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.CityDao;
import com.liaoliao.sys.entity.City;
import com.liaoliao.sys.service.CityService;

@Service
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;
	
	@Override
	public City findByName(String city) {
		return cityDao.findByName(city);
	}

	@Override
	public void saveCity(City cit) {
		cityDao.saveCity(cit);
		
	}

}

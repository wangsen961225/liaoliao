package com.liaoliao.sys.service;

import com.liaoliao.sys.entity.City;

public interface CityService {

	City findByName(String city);

	void saveCity(City cit);

}

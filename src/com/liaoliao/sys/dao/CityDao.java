package com.liaoliao.sys.dao;

import com.liaoliao.sys.entity.City;

public interface CityDao {

	City findByName(String city);

	void saveCity(City cit);

}

package com.liaoliao.sys.dao;

import com.liaoliao.sys.entity.District;

public interface DistrictDao {

	District findById(Integer districtId);

	District findByName(String district);

	void saveDistrict(District dist);

}

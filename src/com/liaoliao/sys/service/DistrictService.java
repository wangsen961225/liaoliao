package com.liaoliao.sys.service;

import com.liaoliao.sys.entity.District;

public interface DistrictService {

	District findById(Integer districtId);

	District findByName(String district);

	void saveDistrict(District dist);

}

package com.liaoliao.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.DistrictDao;
import com.liaoliao.sys.entity.District;
import com.liaoliao.sys.service.DistrictService;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictDao districtDao;
	@Override
	public District findById(Integer districtId) {
		return districtDao.findById(districtId);
	}
	@Override
	public District findByName(String district) {
		return districtDao.findByName(district);
	}
	@Override
	public void saveDistrict(District dist) {
		districtDao.saveDistrict(dist);
		
	}

}

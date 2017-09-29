package com.liaoliao.sys.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.DistrictDao;
import com.liaoliao.sys.entity.District;

public class DistrictDaoImpl extends BaseDaoImpl<District,Integer> implements DistrictDao {

	@Override
	public District findById(Integer districtId) {
		return this.get(districtId);
	}

	@Override
	public District findByName(String district) {
		String hql="from District where name = ?0";
		return this.getByHQL(hql, district);
	}

	@Override
	public void saveDistrict(District dist) {
		this.save(dist);
		
	}

}

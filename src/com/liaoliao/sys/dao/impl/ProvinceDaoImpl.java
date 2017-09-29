package com.liaoliao.sys.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.ProvinceDao;
import com.liaoliao.sys.entity.Province;

public class ProvinceDaoImpl extends BaseDaoImpl<Province,Integer> implements ProvinceDao {

	@Override
	public Province findByName(String province) {
		String hql="from Province where name = ?0";
		return this.getByHQL(hql, province);
	}

	@Override
	public void saveProvince(Province pro) {
		this.save(pro);
	}

}

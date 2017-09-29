package com.liaoliao.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.ProvinceDao;
import com.liaoliao.sys.entity.Province;
import com.liaoliao.sys.service.ProvinceService;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private ProvinceDao provinceDao;
	@Override
	public Province findByName(String province) {
		return provinceDao.findByName(province);
	}
	@Override
	public void saveProvince(Province pro) {
		provinceDao.saveProvince(pro);
	}

}

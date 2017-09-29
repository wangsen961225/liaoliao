package com.liaoliao.sys.service;

import com.liaoliao.sys.entity.Province;

public interface ProvinceService {

	Province findByName(String province);

	void saveProvince(Province pro);

}

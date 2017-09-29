package com.liaoliao.sys.dao;

import com.liaoliao.sys.entity.Province;

public interface ProvinceDao {

	Province findByName(String province);

	void saveProvince(Province pro);

}

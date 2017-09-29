package com.liaoliao.user.dao;

import com.liaoliao.user.entity.UserSign;

public interface UserSignDao {

	void saveOrUpdateUserSign(UserSign userSign);

	UserSign findById(Integer userId);
	
}

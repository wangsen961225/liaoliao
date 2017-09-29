package com.liaoliao.user.service;

import com.liaoliao.user.entity.UserSign;

public interface UserSignService {
	void saveOrUpdateUserSign(UserSign userSign);

	UserSign findById(Integer userId);
}

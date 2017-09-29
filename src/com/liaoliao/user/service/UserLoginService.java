package com.liaoliao.user.service;

import com.liaoliao.user.entity.UserLogin;

public interface UserLoginService {

	void saveUserLogin(UserLogin userLogin);

	UserLogin findByOpenId(String openId, Integer sourceType);


}

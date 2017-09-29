package com.liaoliao.user.dao;

import com.liaoliao.user.entity.UserLogin;

public interface UserLoginDao {

	void saveUserLogin(UserLogin userLogin);

	UserLogin findByOpenId(String openId, Integer sourceType);




}

package com.liaoliao.user.dao;

import com.liaoliao.user.entity.UserToken;

public interface UserTokenDao {

	void saveOrUpdateUserToken(UserToken userToken);

	UserToken findById(Integer userId);
	
}

package com.liaoliao.user.service;

import com.liaoliao.user.entity.UserToken;

public interface UserTokenService {
	void saveOrUpdateUserToken(UserToken userToken);

	UserToken findById(Integer userId);
}

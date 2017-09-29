package com.liaoliao.user.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.UserTokenDao;
import com.liaoliao.user.entity.UserToken;

public class UserTokenDaoImpl extends BaseDaoImpl<UserToken,Integer> implements UserTokenDao {

	@Override
	public void saveOrUpdateUserToken(UserToken userToken) {
		this.saveOrUpdate(userToken);
	}

	@Override
	public UserToken findById(Integer userId) {
		return this.get(userId);
	}

}

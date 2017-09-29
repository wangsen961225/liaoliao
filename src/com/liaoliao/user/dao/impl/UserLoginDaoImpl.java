package com.liaoliao.user.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.UserLoginDao;
import com.liaoliao.user.entity.UserLogin;

public class UserLoginDaoImpl extends BaseDaoImpl<UserLogin,Integer> implements UserLoginDao {

	@Override
	public void saveUserLogin(UserLogin userLogin) {
		this.save(userLogin);
		
	}

	@Override
	public UserLogin findByOpenId(String openId, Integer sourceType) {
		String hqlString = "from UserLogin where openId = ?0 and sourceType = ?1";
		return this.getByHQL(hqlString, openId, sourceType);
	}


	
}

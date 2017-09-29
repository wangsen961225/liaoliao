package com.liaoliao.user.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.UserSignDao;
import com.liaoliao.user.entity.UserSign;

public class UserSignDaoImpl extends BaseDaoImpl<UserSign,Integer> implements UserSignDao {

	@Override
	public void saveOrUpdateUserSign(UserSign userSign) {
		this.saveOrUpdate(userSign);
	}

	@Override
	public UserSign findById(Integer userId) {
		String hql = "from UserSign where userId = ?0";
		return this.getByHQL(hql, userId);
	}

}

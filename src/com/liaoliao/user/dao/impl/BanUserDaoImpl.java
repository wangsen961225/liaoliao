package com.liaoliao.user.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.BanUserDao;
import com.liaoliao.user.entity.BanUser;
import com.liaoliao.user.entity.Users;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;

public class BanUserDaoImpl extends BaseDaoImpl<BanUser,Integer> implements BanUserDao {

	@Override
	public void saveOrUpdateBanUser(BanUser banUser) {
		this.saveOrUpdate(banUser);
	}

	@Override
	public BanUser findByUserId(Integer userId) {
		String hqlString = "from BanUser where user.id = ?0";
		return this.getByHQL(hqlString, userId);
	}

	@Override
	public List<BanUser> bannedUserListByTime() {
//		String hqlString = "from BanUser where userStatus = "+StaticKey.UserStatusException+" and addTime > ?0";
		//查询出所有未越狱的用户信息(包括被禁用的用户)
		String hqlString = "from BanUser where (userStatus =2 or userStatus =0) and dealTime is null and breakoutId is null";
		return this.getListByHQL(hqlString);
	}

	@Override
	public BanUser findNotBreakoutByUserId(Integer userId) {
		String hqlString = "from BanUser where (userStatus=2 or userStatus=0) and user.id = ?0 and dealTime is null and breakoutId is null";
		return this.getByHQL(hqlString, userId);
	}


}

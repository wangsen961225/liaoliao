package com.liaoliao.user.dao;

import java.util.List;

import com.liaoliao.user.entity.BanUser;

public interface BanUserDao {

	void saveOrUpdateBanUser(BanUser banUser);

	BanUser findByUserId(Integer userId);

	List<BanUser> bannedUserListByTime();



}

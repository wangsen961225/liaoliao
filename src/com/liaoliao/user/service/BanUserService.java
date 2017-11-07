package com.liaoliao.user.service;

import java.util.List;

import com.liaoliao.user.entity.BanUser;

public interface BanUserService {

	void saveOrUpdateBanUser(BanUser banUser);

	BanUser findByUserId(Integer userId);

	List<BanUser> bannedUserListByTime();

	/**
	 * 查询未越狱的封禁用户记录
	 * @param userId
	 * @return
	 */
	BanUser findNotBreakoutByUserId(Integer userId);

}

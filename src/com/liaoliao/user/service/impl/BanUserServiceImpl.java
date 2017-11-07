package com.liaoliao.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.BanUserDao;
import com.liaoliao.user.entity.BanUser;
import com.liaoliao.user.service.BanUserService;

@Service
@Transactional
public class BanUserServiceImpl implements BanUserService {

	@Autowired
	private BanUserDao banUserDao;

	@Override
	public void saveOrUpdateBanUser(BanUser banUser) {
		banUserDao.saveOrUpdateBanUser(banUser);
	}

	@Override
	public BanUser findByUserId(Integer userId) {
		return banUserDao.findByUserId(userId);
	}

	@Override
	public List<BanUser> bannedUserListByTime() {
		return banUserDao.bannedUserListByTime();
	}

	@Override
	public BanUser findNotBreakoutByUserId(Integer userId) {
		return banUserDao.findNotBreakoutByUserId(userId);
	}
	
	

}

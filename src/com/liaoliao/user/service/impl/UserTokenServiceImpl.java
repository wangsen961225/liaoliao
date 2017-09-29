package com.liaoliao.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.UserTokenDao;
import com.liaoliao.user.entity.UserToken;
import com.liaoliao.user.service.UserTokenService;

@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired 
	private UserTokenDao userTokenDao;
	

	@Override
	public void saveOrUpdateUserToken(UserToken userToken) {
		userTokenDao.saveOrUpdateUserToken(userToken);
	}
	
	@Override
	public UserToken findById(Integer userId) {
		return userTokenDao.findById(userId);
	}

}

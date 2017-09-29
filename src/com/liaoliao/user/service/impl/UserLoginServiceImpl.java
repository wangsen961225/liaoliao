package com.liaoliao.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.UserLoginDao;
import com.liaoliao.user.entity.UserLogin;
import com.liaoliao.user.service.UserLoginService;

@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginDao userLoginDao;
	
	@Override
	public void saveUserLogin(UserLogin userLogin) {
		userLoginDao.saveUserLogin(userLogin);
	}

	@Override
	public UserLogin findByOpenId(String openId, Integer sourceType) {
		return userLoginDao.findByOpenId(openId, sourceType);
	}



	

}

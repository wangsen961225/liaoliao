package com.liaoliao.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.UserSignDao;
import com.liaoliao.user.entity.UserSign;
import com.liaoliao.user.service.UserSignService;

@Service
@Transactional
public class UserSignServiceImpl implements UserSignService {

	@Autowired 
	private UserSignDao userSignDao;
	

	@Override
	public void saveOrUpdateUserSign(UserSign userSign) {
		userSignDao.saveOrUpdateUserSign(userSign);
	}
	
	@Override
	public UserSign findById(Integer userId) {
		return userSignDao.findById(userId);
	}

}

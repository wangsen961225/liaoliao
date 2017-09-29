package com.liaoliao.profit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.profit.dao.BindPayDao;
import com.liaoliao.profit.entity.BindPay;
import com.liaoliao.profit.service.BindPayService;
import com.liaoliao.user.entity.Users;

@Service
@Transactional
public class BindPayServiceImpl implements BindPayService {

	@Autowired 
	private BindPayDao bindPayDao;

	@Override
	public BindPay findByUser(Users user) {
		return bindPayDao.findByUser(user);
	}

	@Override
	public void saveBindPay(BindPay bindPay) {
		bindPayDao.saveBindPay(bindPay);
		
	}

	@Override
	public BindPay findByPayAccount(String payAccount) {
		return bindPayDao.findByPayAccount(payAccount);
	}
	



	

}

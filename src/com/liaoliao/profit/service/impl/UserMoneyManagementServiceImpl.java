package com.liaoliao.profit.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.UserMoneyManagementDao;
import com.liaoliao.profit.entity.UserMoneyManagement;
import com.liaoliao.profit.service.UserMoneyManagementService;
@Service
@Transactional
public class UserMoneyManagementServiceImpl extends BaseDaoImpl<UserMoneyManagement,Integer> implements UserMoneyManagementService {

	@Autowired
	private UserMoneyManagementDao userMoneyManagementDao;
	@Override
	public void save(UserMoneyManagement modle) {
		userMoneyManagementDao.add(modle);
		
	}

	@Override
	public void revise(UserMoneyManagement modle) {
		
		 userMoneyManagementDao.revise(modle);
	}

	@Override
	public UserMoneyManagement del(Integer id) {
		
		return userMoneyManagementDao.del(id);
	}

	

	@Override
	public List<UserMoneyManagement> queryAll(Integer id) {
		return userMoneyManagementDao.queryAll(id);
	}

	@Override
	public List<UserMoneyManagement> queryAll() {
		
		return userMoneyManagementDao.queryAll();
	}

	@Override
	public List<UserMoneyManagement> querySum() {
		return userMoneyManagementDao.querySum();
	}

	
}


package com.liaoliao.profit.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.MoneyManagementDao;
import com.liaoliao.profit.entity.MoneyManagement;
import com.liaoliao.profit.service.MoneyManagementService;
@Service
@Transactional
public class MoneyManagementServiceImpl extends BaseDaoImpl<MoneyManagement,Integer> implements MoneyManagementService {

	@Autowired
	private MoneyManagementDao moneyManagementDao;
	@Override
	public void save(MoneyManagement modle) {
		moneyManagementDao.save(modle);
		
	}

	@Override
	public MoneyManagement revise(MoneyManagement modle) {
		
		return moneyManagementDao.revise(modle);
	}

	@Override
	public MoneyManagement del(String id) {
		
		return moneyManagementDao.del(id);
	}

	@Override
	public MoneyManagement queryOne(String id) {
		
		return moneyManagementDao.queryOne(id);
	}

	@Override
	public List<MoneyManagement> queryAll() {
		return moneyManagementDao.queryAll();
	}

	@Override
	public List<MoneyManagement> queryAll(String type) {
		return moneyManagementDao.queryAll(type);
	}
	@Override
	public MoneyManagement queryByName(String name){
		return moneyManagementDao.queryByName(name);
		
	}
	
	
	
}

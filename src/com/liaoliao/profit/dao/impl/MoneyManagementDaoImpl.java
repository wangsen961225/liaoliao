package com.liaoliao.profit.dao.impl;


import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.MoneyManagementDao;
import com.liaoliao.profit.entity.MoneyManagement;

public class MoneyManagementDaoImpl extends BaseDaoImpl<MoneyManagement,Integer> implements MoneyManagementDao {

	@Override
	public void save(MoneyManagement modle) {
		
		
	}

	@Override
	public MoneyManagement revise(MoneyManagement modle) {
		
		return null;
	}

	@Override
	public MoneyManagement del(String id) {
		
		return null;
	}

	@Override
	public MoneyManagement queryOne(String id) {
		String hqlString = "from MoneyManagement where id=?0";
		return this.getByHQL(hqlString,id);
	}

	@Override
	public List<MoneyManagement> queryAll() {
		String hqlString = "from MoneyManagement where type=0";
		List<MoneyManagement> list= this.getListByHQL(hqlString);
		return list;
		
	}

	@Override
	public List<MoneyManagement> queryAll(String type) {
		return  null;
	}

	@Override
	public MoneyManagement queryByName(String name) {
		String hqlString = "from MoneyManagement where name=?0 and type=0";
		MoneyManagement moneyManagement=this.getByHQL(hqlString,name);
		return moneyManagement;
	}

	
	
	
}

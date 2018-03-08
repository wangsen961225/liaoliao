package com.liaoliao.profit.dao;


import java.util.List;

import com.liaoliao.profit.entity.MoneyManagement;

public interface MoneyManagementDao {

	
	void save(MoneyManagement modle);

	//更新
	MoneyManagement revise(MoneyManagement modle);

	MoneyManagement del(String id);

	MoneyManagement queryOne(String id);
	
	List<MoneyManagement> queryAll(String type);
	
	List<MoneyManagement> queryAll();
	
	MoneyManagement  queryByName(String name);
	
	
	
}

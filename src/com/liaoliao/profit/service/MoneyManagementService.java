package com.liaoliao.profit.service;


import java.util.List;

import com.liaoliao.profit.entity.MoneyManagement;

public interface MoneyManagementService {

	
	void save(MoneyManagement modle);

	//更新
	MoneyManagement revise(MoneyManagement modle);

	MoneyManagement del(String id);

	MoneyManagement queryOne(String id);
	
	MoneyManagement queryByName(String name);
	
	List<MoneyManagement> queryAll(String type); 
	
	List<MoneyManagement> queryAll();
	
	
}

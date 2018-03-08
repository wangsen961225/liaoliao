package com.liaoliao.profit.dao;


import java.util.List;

import com.liaoliao.profit.entity.UserMoneyManagement;

public interface UserMoneyManagementDao {

	
	
	void add(UserMoneyManagement modle);

	//更新
	void revise(UserMoneyManagement modle);

	UserMoneyManagement del(Integer id);

	
	List<UserMoneyManagement> queryAll(Integer id);
	
	List<UserMoneyManagement> querySum();
	
	List<UserMoneyManagement> queryAll();
	
	
}

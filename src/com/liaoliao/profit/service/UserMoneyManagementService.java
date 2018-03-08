package com.liaoliao.profit.service;


import java.util.List;

import com.liaoliao.profit.entity.UserMoneyManagement;

public interface UserMoneyManagementService {

	
	void save(UserMoneyManagement modle);

	//更新
	void revise(UserMoneyManagement modle);

	UserMoneyManagement del(Integer id);
	
	List<UserMoneyManagement> queryAll(Integer id);
	/**
	 *利润结算
	 * @return
	 */
	List<UserMoneyManagement> queryAll();
	
	List<UserMoneyManagement> querySum();
	
}

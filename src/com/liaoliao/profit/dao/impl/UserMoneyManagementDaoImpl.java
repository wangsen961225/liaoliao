package com.liaoliao.profit.dao.impl;


import java.util.List;


import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.UserMoneyManagementDao;
import com.liaoliao.profit.entity.UserMoneyManagement;

public class UserMoneyManagementDaoImpl extends BaseDaoImpl<UserMoneyManagement,Integer> implements UserMoneyManagementDao {

	@Override
	public void add(UserMoneyManagement modle) {
		
		this.save(modle);
	}

	@Override
	public void revise(UserMoneyManagement modle) {
		
		 this.update(modle);;
	}

	@Override
	public UserMoneyManagement del(Integer id) {
		
		return null;
	}


	@Override
	public List<UserMoneyManagement> queryAll(Integer id) {
		String hql="from UserMoneyManagement where userId=?0 order by buyDate desc";
		return this.getListByHQL(hql, id);
	}

	@Override
	public List<UserMoneyManagement> queryAll() {
		String hql="from UserMoneyManagement where status=0  order by buyDate desc";
		return this.getListByHQL(hql);
	}

	@Override
	public List<UserMoneyManagement> querySum() {
		String hql="SELECT SUM(money) as moneys FROM UserMoneyManagement GROUP BY moneyId";
		return this.getListByHQL(hql);
	}
	
}

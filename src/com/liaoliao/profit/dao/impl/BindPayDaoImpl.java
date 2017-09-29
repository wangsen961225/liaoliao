package com.liaoliao.profit.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.profit.dao.BindPayDao;
import com.liaoliao.profit.entity.BindPay;
import com.liaoliao.user.entity.Users;

public class BindPayDaoImpl extends BaseDaoImpl<BindPay,Integer> implements BindPayDao {

	@Override
	public void saveBindPay(BindPay bindPay) {
		this.save(bindPay);
	}

	@Override
	public BindPay findByUser(Users user) {
		String hqlString = "from BindPay where user = ?0";
		return this.getByHQL(hqlString, user);
	}

	@Override
	public BindPay findByPayAccount(String payAccount) {
		String hqlString = "from BindPay where payAccount = ?0";
		return this.getByHQL(hqlString, payAccount);
	}

}

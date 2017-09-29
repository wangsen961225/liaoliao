package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.BindPay;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.user.entity.Users;

public interface BindPayDao {

	void saveBindPay(BindPay bindPay);

	BindPay findByUser(Users user);

	BindPay findByPayAccount(String payAccount);

}

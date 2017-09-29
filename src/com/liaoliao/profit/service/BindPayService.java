package com.liaoliao.profit.service;


import com.liaoliao.profit.entity.BindPay;
import com.liaoliao.user.entity.Users;


public interface BindPayService {


	BindPay findByUser(Users user);
	
	BindPay findByPayAccount(String payAccount);

	void saveBindPay(BindPay bindPay);

}

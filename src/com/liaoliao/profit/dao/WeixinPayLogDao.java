package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.WeixinPayLog;
import com.liaoliao.user.entity.Users;

public interface WeixinPayLogDao {

	void saveWeixinPayLog(WeixinPayLog wpl);

	WeixinPayLog findByOutTradNo(String out_trade_no);

	void updateWeixinPayLog(WeixinPayLog wpl);

	List<WeixinPayLog> findAll(Integer pageNo);

	Integer findCount();

	List<WeixinPayLog> queryByBodyAndUser(String body,Users user);
	
	List<WeixinPayLog> findAllByUser(Users users);
	
	List<WeixinPayLog> findAllByUserAndType(Users users);
	
	WeixinPayLog queryOne(Integer id);
	
	WeixinPayLog queryOneByOrderNum(String id);
}

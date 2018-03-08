package com.liaoliao.profit.service;

import java.util.List;

import com.liaoliao.profit.entity.WeixinPayLog;
import com.liaoliao.user.entity.Users;

public interface WeixinPayLogService {

	List<WeixinPayLog> findAllByUserAndType(Users users);
	
	WeixinPayLog queryOneByOrderNum(String id);
	
	WeixinPayLog queryOne(Integer id);
	
	void saveWeixinPayLog(WeixinPayLog wpl);

	WeixinPayLog findByOutTradNo(String out_trade_no);

	void updateWeixinPayLog(WeixinPayLog wpl);
	
	List<WeixinPayLog> findAllByUser(Users users);
	
	List<WeixinPayLog> findAll(Integer pageNo);

	Integer findCount();
	
	List<WeixinPayLog> queryByBodyAndUser(String body,Users user);

}

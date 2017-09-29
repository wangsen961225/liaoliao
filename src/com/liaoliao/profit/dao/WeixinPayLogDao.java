package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.WeixinPayLog;

public interface WeixinPayLogDao {

	void saveWeixinPayLog(WeixinPayLog wpl);

	WeixinPayLog findByOutTradNo(String out_trade_no);

	void updateWeixinPayLog(WeixinPayLog wpl);

	List<WeixinPayLog> findAll(Integer pageNo);

	Integer findCount();

}

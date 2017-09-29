package com.liaoliao.sys.dao;

import java.util.Date;
import java.util.List;

import com.liaoliao.sys.entity.HandleCount;

public interface HandleCountDao {

	void handleCountPlusOne(String clickName);

	List<HandleCount> handleCountList(Date datTime);


	void handleCountList(String totalProfitMoney, int coin);

	void clearOnline();

}

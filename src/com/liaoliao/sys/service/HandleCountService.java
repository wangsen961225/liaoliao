package com.liaoliao.sys.service;

import java.util.Date;
import java.util.List;

import com.liaoliao.sys.entity.HandleCount;

public interface HandleCountService {

	void handleCountPlusOne(String clickName);
	
	List<HandleCount> handleCountList(Date datTime);

	void handleCountTotalMoney(String string, int coin);

	void clearOnline();
}

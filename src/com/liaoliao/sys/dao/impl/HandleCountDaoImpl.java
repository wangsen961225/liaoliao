package com.liaoliao.sys.dao.impl;

import java.util.Date;
import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.HandleCountDao;
import com.liaoliao.sys.entity.HandleCount;

public class HandleCountDaoImpl extends BaseDaoImpl<HandleCount,Integer> implements HandleCountDao {

	@Override
	public void handleCountPlusOne(String clickName) {
		String sqlString = "INSERT INTO ll_handle_count VALUES(UUID(), ?0, 1, CURDATE()) ON DUPLICATE KEY UPDATE click_count=click_count+1";
		this.querySql(sqlString, clickName);;
	}
	
	@Override
	public List<HandleCount> handleCountList(Date datTime) {
		String hqlString = "from HandleCount where clickDate = ?0";
		return this.getListByHQL(hqlString , datTime);
	}

	@Override
	public void handleCountList(String totalProfitMoney, int coin) {
		String sqlString = "INSERT INTO ll_handle_count VALUES(UUID(), ?0, 0, CURDATE()) ON DUPLICATE KEY UPDATE click_count=click_count + ?1";
		this.querySql(sqlString, totalProfitMoney,coin);
	}

	@Override
	public void clearOnline(){
		String hqlString = "update HandleCount set clickCount = 0 where clickName = ?0";
		this.queryHql(hqlString,"accountOnline");
	}


}
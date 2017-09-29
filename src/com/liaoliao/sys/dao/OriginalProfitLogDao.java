package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.OriginalProfitLog;

public interface OriginalProfitLogDao {

	OriginalProfitLog findLastData();

	void saveOriginalProfitLog(OriginalProfitLog opl);

	List<OriginalProfitLog> findData();

	List<OriginalProfitLog> findOriginalLogByUserId(Integer userId, Integer pageNo);

}

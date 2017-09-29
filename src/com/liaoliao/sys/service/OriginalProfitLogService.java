package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.OriginalProfitLog;

public interface OriginalProfitLogService {

	void saveOriginalProfitLog(OriginalProfitLog opl);

	List<OriginalProfitLog> findData();

	List<OriginalProfitLog> findOriginalLogByUserId(Integer userId, Integer pageNo);

}

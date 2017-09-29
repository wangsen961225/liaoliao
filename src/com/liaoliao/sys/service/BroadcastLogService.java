package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.BroadcastLog;

public interface BroadcastLogService {

	void saveBLog(BroadcastLog bl);

	List<BroadcastLog> findLog();
	
	/**
	 * 用户评论展示
	 */
	List<BroadcastLog> userBostcastLog(Integer pageNo);
}

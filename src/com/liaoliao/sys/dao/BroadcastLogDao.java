package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.BroadcastLog;

public interface BroadcastLogDao {

	void saveBLog(BroadcastLog bl);

	List<BroadcastLog> findLog();
	/**
	 * 用户评论展示
	 * @return
	 */
	List<BroadcastLog> userBostcastLog(Integer pageNo);
}

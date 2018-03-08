package com.liaoliao.user.service;

import java.util.List;

import com.liaoliao.user.entity.ReadHistory;

public interface ReadHistoryService {
	
	
	void updateMoble(ReadHistory moblie);

	ReadHistory findByUserId(Integer articleId, Integer userId,Integer type);
	
	void savemobile(ReadHistory moblie);
	
	List<ReadHistory> queryAll(Integer userid,Integer pageNo);
	
	Integer findCount(Integer userId);
	
}

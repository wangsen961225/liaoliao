package com.liaoliao.user.dao;

import java.util.List;

import com.liaoliao.user.entity.ReadHistory;

public interface ReadHistoryDao {
	
	
	 ReadHistory findByUserId(Integer articleId, Integer userId,Integer type) ;
	
	 void updateMoblie(ReadHistory moblie); 
	 
	void savemobile(ReadHistory moblie);
	
	List<ReadHistory> queryAll(Integer userid,Integer pageNo);
	
	Integer findCount(Integer userId);
}

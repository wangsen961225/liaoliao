package com.liaoliao.palyTour.dao;

import java.util.List;

import com.liaoliao.palyTour.entity.PlayHistory;

public interface PlayHistoryDao {
	
	void add(PlayHistory modle);
	
	PlayHistory queryOne(Integer articleId,Integer type,Integer reader);
	//更新
	PlayHistory revise(PlayHistory modle);

	PlayHistory del(PlayHistory modle);
	
	List<PlayHistory> queryAllRederId(Integer rederId);
	
	List<PlayHistory> queryAllAuthorId(Integer authorId);
	
	Integer findCount(Integer articleId);
	
	List<PlayHistory> findSums(Integer articleId);
}

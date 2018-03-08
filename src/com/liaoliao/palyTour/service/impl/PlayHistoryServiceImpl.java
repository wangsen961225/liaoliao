package com.liaoliao.palyTour.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.palyTour.dao.PlayHistoryDao;
import com.liaoliao.palyTour.entity.PlayHistory;
import com.liaoliao.palyTour.service.PlayHistoryService;
@Service
@Transactional
public class PlayHistoryServiceImpl extends BaseDaoImpl<PlayHistory,Integer> implements PlayHistoryService {

	@Autowired
	private PlayHistoryDao playHistoryDao;
	@Override
	public void add(PlayHistory modle) {
		
		this.save(modle);
	}

	@Override
	public PlayHistory revise(PlayHistory modle) {
		
		return playHistoryDao.revise(modle);
	}

	@Override
	public PlayHistory del(PlayHistory modle) {
		
		return playHistoryDao.del(modle);
	}




	@Override
	public List<PlayHistory> queryAllRederId(Integer rederId){

		return playHistoryDao.queryAllRederId(rederId);
	}

	@Override
	public List<PlayHistory> queryAllAuthorId(Integer authorId) {
	
		return playHistoryDao.queryAllAuthorId(authorId);
	}

	@Override
	public Integer findCount(Integer articleId) {
		
		return playHistoryDao.findCount(articleId);
	}

	@Override
	public List<PlayHistory> findSums(Integer articleId) {
		return playHistoryDao.findSums(articleId);
	}

	@Override
	public PlayHistory queryOne(Integer articleId, Integer type, Integer reader) {
		return playHistoryDao.queryOne(articleId, type, reader);
	}
	
	
}

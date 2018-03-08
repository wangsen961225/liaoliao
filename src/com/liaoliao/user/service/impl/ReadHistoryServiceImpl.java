package com.liaoliao.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.ReadHistoryDao;
import com.liaoliao.user.entity.ReadHistory;
import com.liaoliao.user.service.ReadHistoryService;

@Service
@Transactional
public class ReadHistoryServiceImpl implements ReadHistoryService {

	@Autowired
	private ReadHistoryDao readHistoryDao;

	@Override
	public void savemobile(ReadHistory moblie) {
		readHistoryDao.savemobile(moblie);
		
	}

	@Override
	public List<ReadHistory> queryAll(Integer userid,Integer pageNo) {
		return readHistoryDao.queryAll(userid,pageNo);
	}
	@Override
	public Integer findCount(Integer userId) {
		return readHistoryDao.findCount(userId);
	}

	@Override
	public ReadHistory findByUserId(Integer articleId, Integer userId,Integer type) {
		return readHistoryDao.findByUserId(articleId, userId,type);
	}

	@Override
	public void updateMoble(ReadHistory moblie) {
		
		readHistoryDao.updateMoblie(moblie);
	}

}

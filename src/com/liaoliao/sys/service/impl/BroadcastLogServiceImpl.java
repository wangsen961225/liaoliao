package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.BroadcastLogDao;
import com.liaoliao.sys.entity.BroadcastLog;
import com.liaoliao.sys.service.BroadcastLogService;


@Service
@Transactional
public class BroadcastLogServiceImpl implements BroadcastLogService {

	@Autowired
	private BroadcastLogDao broadcastLogDao;
	
	@Override
	public void saveBLog(BroadcastLog bl) {
		broadcastLogDao.saveBLog(bl);
	}
	
	@Override
	public List<BroadcastLog> findLog() {
		return broadcastLogDao.findLog();
	}
	
	@Override
	public List<BroadcastLog> userBostcastLog(Integer pageNo) {
		return broadcastLogDao.userBostcastLog(pageNo);
	}
}

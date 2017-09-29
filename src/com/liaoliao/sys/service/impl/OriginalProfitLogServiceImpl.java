package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.OriginalProfitLogDao;
import com.liaoliao.sys.entity.OriginalProfitLog;
import com.liaoliao.sys.service.OriginalProfitLogService;

@Service("originalProfitLogService")
@Transactional
public class OriginalProfitLogServiceImpl implements OriginalProfitLogService {

	
	@Autowired
	private OriginalProfitLogDao originalProfitLogDao; 
	
	@Override
	public void saveOriginalProfitLog(OriginalProfitLog opl) {
		originalProfitLogDao.saveOriginalProfitLog(opl);
	}
	@Override
	public List<OriginalProfitLog> findData() {
		return originalProfitLogDao.findData();
	}
	@Override
	public List<OriginalProfitLog> findOriginalLogByUserId(Integer userId, Integer pageNo) {
		return originalProfitLogDao.findOriginalLogByUserId(userId,pageNo);
	}

}

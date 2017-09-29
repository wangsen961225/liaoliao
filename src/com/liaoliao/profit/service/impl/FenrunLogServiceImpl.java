package com.liaoliao.profit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.profit.dao.FenrunLogDao;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;

@Service("fenrunLogService")
@Transactional
public class FenrunLogServiceImpl implements FenrunLogService {
	
	@Autowired
	private FenrunLogDao fenrunLogDao;

	@Override
	public void saveFenrunLog(FenrunLog fenrun) {
		fenrunLogDao.saveFenrunLog(fenrun);
	}

	@Override
	public List<FenrunLog> findByUserId(Integer id, Integer pageNo) {
		return fenrunLogDao.findByUserId(id,pageNo);
	}

	@Override
	public List<FenrunLog> findOriginalLogByUserId(Integer userId, Integer pageNo) {
		return fenrunLogDao.findOriginalLogByUserId(userId,pageNo);
	}

	@Override
	public FenrunLog findByRedPackageId(Integer userId, Integer id) {
		return fenrunLogDao.findByRedPackageId(userId,id);
	}

	@Override
	public List<FenrunLog> findRedPackageLog( Integer redPackageId) {
		return fenrunLogDao.findRedPackageLog( redPackageId);
	}
	
	

}

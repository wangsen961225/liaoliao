package com.liaoliao.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.FocusLogDao;
import com.liaoliao.user.entity.FocusLog;
import com.liaoliao.user.service.FocusLogService;

@Service
@Transactional
public class FocusLogServiceImpl implements FocusLogService {

	@Autowired
	private FocusLogDao focusLogDao;
	
	@Override
	public void saveFocusLog(FocusLog fl) {
		focusLogDao.saveFocusLog(fl);
	}

	@Override
	public List<FocusLog> findByUserId(Integer userId) {
		return focusLogDao.findByUserId(userId);
	}

	
	@Override
	public Long countNum(Integer id) {
		return focusLogDao.countNum(id);
	}
	@Override
	public List<FocusLog> findBeConcernedByUserId(Integer userId) {
		return focusLogDao.findBeConcernedByUserId(userId);
	}

	@Override
	public FocusLog findByFocusId(Integer userId, Integer focusId) {
		return focusLogDao.findByFocusId(userId,focusId);
	}

	@Override
	public void updateFocusLog(FocusLog fl) {
		focusLogDao.updateFocusLog(fl);
		
	}

	@Override
	public Long findFocusCountById(Integer id) {
		// TODO Auto-generated method stub
		return focusLogDao.getFocusCountById(id);
	}


}

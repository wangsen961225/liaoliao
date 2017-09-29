package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liaoliao.sys.dao.UserTaskDao;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.UserTaskService;

@Service
@Transactional
public class UserTaskServiceImpl implements UserTaskService {

	@Autowired
	private UserTaskDao userTaskDao;
	
	
	@Override
	public List<UserTask> findAll() {
		return userTaskDao.findAll();
	}


	@Override
	public List<UserTask> findByType(int i) {
		
		return userTaskDao.findByType(i);
	}


	@Override
	public UserTask findById(int i) {
		return userTaskDao.findById(i);
	}

}

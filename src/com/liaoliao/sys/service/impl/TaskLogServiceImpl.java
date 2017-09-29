package com.liaoliao.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.TaskLogDao;
import com.liaoliao.sys.entity.TaskLog;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.TaskLogService;


@Service
@Transactional
public class TaskLogServiceImpl implements TaskLogService {
	@Autowired
	private TaskLogDao taskLogDao;

	@Override
	public TaskLog findExist(Integer id, Integer userId) {
		return taskLogDao.findExist(id,userId);
	}

	@Override
	public void savaTaskLog(TaskLog userTaskLog) {
		 taskLogDao.savaTaskLog(userTaskLog);
	}

	@Override
	public TaskLog findDayExist(Date todayStart, Integer id, Integer userId) {

		return taskLogDao.savaTaskLog(todayStart,id,userId);
	}

	@Override
	public void updateTaskLog(TaskLog taskLog) {
		taskLogDao.updateTaskLog(taskLog);
	}

	@Override
	public TaskLog findTask(Integer userId, int id) {
	
		return taskLogDao.findTask(userId,id);
	}

	@Override
	public void changeTaskStatus(TaskLog taskLog) {
		taskLogDao.changeTaskStatus(taskLog);
	}



}

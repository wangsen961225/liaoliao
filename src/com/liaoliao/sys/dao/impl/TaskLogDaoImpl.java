package com.liaoliao.sys.dao.impl;

import java.util.Date;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.TaskLogDao;
import com.liaoliao.sys.entity.TaskLog;

public class TaskLogDaoImpl extends BaseDaoImpl<TaskLog,Integer> implements TaskLogDao {

	@Override
	public TaskLog findExist(Integer id, Integer userId) {
		String hql="from TaskLog where userTask.id = ?0 and user.id = ?1";
		return this.getByHQL(hql, id,userId);
		
	}

	@Override
	public void savaTaskLog(TaskLog userTaskLog) {
		 this.save(userTaskLog);
	}

	@Override
	public TaskLog savaTaskLog(Date todayStart, Integer id, Integer userId) {
		String hql="from TaskLog where userTask.id = ?0 and user.id = ?1 and ( finishTime is null or finishTime > ?2 )";
		return this.getByHQL(hql,id,userId,todayStart);
	}

	@Override
	public void updateTaskLog(TaskLog taskLog) {
		this.update(taskLog);
	}

	@Override
	public TaskLog findTask(Integer userId, int id) {
		String hql="from TaskLog where user.id = ?0 and userTask.id = ?1";
		return this.getByHQL(hql, userId,id);
	}

	@Override
	public void changeTaskStatus(TaskLog taskLog) {
		this.update(taskLog);
	}



}

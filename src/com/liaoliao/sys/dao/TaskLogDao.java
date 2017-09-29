package com.liaoliao.sys.dao;


import java.util.Date;

import com.liaoliao.sys.entity.TaskLog;

public interface TaskLogDao {

	TaskLog findExist(Integer id, Integer userId);

	void savaTaskLog(TaskLog userTaskLog);

	TaskLog savaTaskLog(Date todayStart, Integer id, Integer userId);

	void updateTaskLog(TaskLog taskLog);

	TaskLog findTask(Integer userId, int id);
	
	void changeTaskStatus(TaskLog taskLog);
	

}

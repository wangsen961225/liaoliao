package com.liaoliao.sys.service;


import java.util.Date;

import com.liaoliao.sys.entity.TaskLog;

public interface TaskLogService {

	TaskLog findExist(Integer id, Integer userId);

	void savaTaskLog(TaskLog userTaskLog);

	TaskLog findDayExist(Date todayStart, Integer id, Integer userId);

	void updateTaskLog(TaskLog taskLog);

	TaskLog findTask(Integer userId, int i);

	void changeTaskStatus(TaskLog taskLog);

}

package com.liaoliao.sys.service;

import java.util.List;
import com.liaoliao.sys.entity.UserTask;


public interface UserTaskService {

	List<UserTask> findAll();

	List<UserTask> findByType(int i);

	UserTask findById(int i);

}

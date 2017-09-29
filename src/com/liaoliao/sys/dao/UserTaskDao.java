package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.UserTask;

public interface UserTaskDao {

	List<UserTask> findAll();

	List<UserTask> findByType(int i);

	UserTask findById(int i);

}

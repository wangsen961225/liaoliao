package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.UserTaskDao;
import com.liaoliao.sys.entity.UserTask;

public class UserTaskDaoImpl extends BaseDaoImpl<UserTask,Integer> implements UserTaskDao {

	@Override
	public List<UserTask> findAll() {
		String hql="from UserTask ";
		return this.getListByHQL(hql);
	}

	@Override
	public List<UserTask> findByType(int i) {
		String hql = "from UserTask where type = ?0";
		return this.getListByHQL(hql,i);
	}

	@Override
	public UserTask findById(int i) {
		String hql="from UserTask where id = ?0";
		return this.getByHQL(hql, i);
	}

}

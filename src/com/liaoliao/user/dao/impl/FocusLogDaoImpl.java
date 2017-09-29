package com.liaoliao.user.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.FocusLogDao;
import com.liaoliao.user.entity.FocusLog;

public class FocusLogDaoImpl extends BaseDaoImpl<FocusLog,Integer> implements FocusLogDao {

	@Override
	public void saveFocusLog(FocusLog fl) {
		this.save(fl);
	}

	@Override
	public List<FocusLog> findByUserId(Integer userId) {
		String hql = "from FocusLog where user.id = ?0 and status = 1" ;
		return this.getListByHQL(hql, userId);
	}

	@Override
	public List<FocusLog> findBeConcernedByUserId(Integer userId) {
		String hql = "from FocusLog where focusUser.id = ?0 and status = 1";
		return this.getListByHQL(hql,userId);
		
	}
	@Override
	public Long countNum(Integer id) {
		String hql = "select count(a) from FocusLog a where focusUser.id = ?0 and status = 1";
		return this.countByHql(hql,id);
	}

	@Override
	public FocusLog findByFocusId(Integer userId, Integer focusId) {
		String hql = "from FocusLog where user.id = ?0 and focusUser.id = ?1 ";
		return this.getByHQL(hql,userId,focusId);
	}

	@Override
	public void updateFocusLog(FocusLog fl) {
		this.update(fl);
		
	}
}

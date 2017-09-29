package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.sys.dao.BroadcastLogDao;
import com.liaoliao.sys.entity.BroadcastLog;

public class BroadcastLogDaoImpl extends BaseDaoImpl<BroadcastLog,Integer> implements BroadcastLogDao {

	@Override
	public void saveBLog(BroadcastLog bl) {
		this.save(bl);
	}

	@Override
	public List<BroadcastLog> findLog() {
		String hql="from BroadcastLog order by addTime desc ";
		String countHql="select count(a) from BroadcastLog a";
		PageResults<BroadcastLog> results=this.findPageByFetchedHql(hql, countHql, 1, 100);
		return results.getResults();
	}
	
	@Override
	public List<BroadcastLog> userBostcastLog(Integer pageNo) {
		String hql="from BroadcastLog order by addTime desc";
		String countHql = "select count(a) from BroadcastLog a";
		PageResults<BroadcastLog> results = this.findPageByFetchedHql(hql, countHql, pageNo, 15);
		List<BroadcastLog> list = results.getResults();
		return list;
	}
}

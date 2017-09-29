package com.liaoliao.profit.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.profit.dao.ToBankDao;
import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.user.entity.Users;
import com.liaoliao.util.StaticKey;

public class ToBankDaoImpl extends BaseDaoImpl<ToBank,Integer> implements ToBankDao {

	@Override
	public List<ToBank> findByUser(Users user, Integer pageNo) {
		String hql="from ToBank where user = ?0 order by addTime desc";
		String countHql="select count(a) from ToBank a where user = ?0";
		PageResults<ToBank> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.PageNum, user);
		return results.getResults();
	}

	@Override
	public void updateToBank(ToBank toBank) {
		this.update(toBank);
	}

	@Override
	public void saveToBank(ToBank toBank) {
		this.save(toBank);
	}

	@Override
	public Long findToBankCountByUser(Users user) {
		String sqlString = "select sum(toBankMoney) from ToBank where user = ?0 and toBankStatus = ?1";
        return this.countByHql(sqlString,user,1);
	}

	@Override
	public List<ToBank> findAll(Integer pageNo) {
		String hql="from ToBank";
		String countHql="select count(a) from ToBank a";
		PageResults<ToBank> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public Integer findCount() {
		String hql="select count(s) from ToBank s";
		return Integer.valueOf(this.countByHql(hql).toString());
	}





	
}

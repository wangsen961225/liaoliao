package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.sys.dao.InvitationDao;
import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.util.StaticKey;

public class InvitationDaoImpl extends BaseDaoImpl<Invitation,Integer> implements InvitationDao {

	@Override
	public List<Invitation> findAllNoDeal(Integer inviteChildMinMoney) {
		String hql="from Invitation  a where a.status = ?0 and (a.child.totalMoney - a.child.unselfMoney) >= ?1 ";
		 List<Invitation> list= this.getListByHQL(hql,StaticKey.InvitationStatusFalse,0.0+inviteChildMinMoney);
		 return list;
	}

	@Override
	public void saveInvitation(Invitation invitation) {
		this.save(invitation);
	}

	@Override
	public List<Invitation> getInviteList(Integer userId, Integer pageNo) {
		String hql = "from Invitation where parent.id = ?0";
		String countHql = "select count(i) from Invitation i where parent.id = ?0";
		int pageSize = 10;
		PageResults<Invitation> results = this.findPageByFetchedHql(hql, countHql, pageNo, pageSize , userId);
		return results.getResults();
	}

	@Override
	public void updataInvitation(Invitation in) {
		this.update(in);
	}
	
	@Override
	public Long getInviteCount(Integer userId) {
		String hql = "select count(i) from Invitation i where parent.id = ?0";
		return this.countByHql(hql, userId);
	}

}



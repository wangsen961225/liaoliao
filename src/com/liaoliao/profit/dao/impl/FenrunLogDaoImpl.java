package com.liaoliao.profit.dao.impl;

import java.util.List;
import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.profit.dao.FenrunLogDao;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.util.StaticKey;

public class FenrunLogDaoImpl extends BaseDaoImpl<FenrunLog, Integer> implements FenrunLogDao {

	@Override
	public void saveFenrunLog(FenrunLog fenrun) {
		this.save(fenrun);
	}
	

	@Override
	public List<FenrunLog> findByUserId(Integer id, Integer pageNo) {
		String hql="from FenrunLog where user.id = ?0  order by addTime desc";
		String countHql="select count(a) from FenrunLog a where user.id = ?0 ";
		PageResults<FenrunLog> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.PageNum, id);
		return results.getResults();
	}


	@Override
	public List<FenrunLog> findOriginalLogByUserId(Integer userId, Integer pageNo) {
		String hql="from FenrunLog where user.id = ?0 and (type = ?1 or type = ?2) order by addTime desc";
		String countHql="select count(a) from FenrunLog a where user.id = ?0 and (type = ?1 or type = ?2)";
		PageResults<FenrunLog> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.PageNum, userId,StaticKey.FenrunOriginalArticle,StaticKey.FenrunOriginalVideo);
		return results.getResults();
	}


	@Override
	public FenrunLog findByRedPackageId(Integer userId, Integer id) {
		String hql="from FenrunLog where user.id = ?0  and contentId = ?1 and money > 0";
		return this.getByHQL(hql,userId,id);
	}


	@Override
	public List<FenrunLog> findRedPackageLog(Integer redPackageId) {
		String hql="from FenrunLog where type = 0? and contentId = ?1";
		return this.getListByHQL(hql, StaticKey.FenrunGrabUser,redPackageId);
	}
}

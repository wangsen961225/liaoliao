package com.liaoliao.profit.dao.impl;

import java.util.List;

import org.hibernate.Query;

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
//		String hql="from FenrunLog where type =?0 and contentId = ?1 ";
		String hql1 = "from FenrunLog where id=" + redPackageId;
		List<FenrunLog> listByHQL2 = this.getListByHQL(hql1);
		FenrunLog fenrunLog = listByHQL2.get(0);
		redPackageId = fenrunLog.getContentId();
		String hql="from FenrunLog where type =?0 and contentId = ?1 ";
		System.out.println(redPackageId);
		List<FenrunLog> listByHQL = this.getListByHQL(hql, StaticKey.FenrunGrabUser,redPackageId);
		//System.out.println(listByHQL);
		return listByHQL;
//		return this.getListByHQL(hql, StaticKey.FenrunGrabUser,redPackageId);
	}


	@Override
	public List<FenrunLog> expenditureDetails(Integer userId) {
		String hql="from FenrunLog where user.id =?0";
		List<FenrunLog> listByHQL = this.getListByHQL(hql,userId);
		return listByHQL;
	}


	@Override
	public Integer countSignProfit(Integer userId) {
		String hql = "from FenrunLog f where user.id=?0 and type=?1";
		List<FenrunLog> list = this.getListByHQL(hql, userId,5);
		Integer totalProfit = 0;
		if(list!=null)
		{
			for (FenrunLog fenrunLog : list) 
			{
				totalProfit += fenrunLog.getMoney();
			}
		}
		
		return totalProfit;
	}


	@Override
	public Integer countSignNum(Integer userId) {
		String hql = "from FenrunLog f where user.id=?0 and type=?1";
		List<FenrunLog> list = this.getListByHQL(hql, userId,5);
		return list.size();
	}
}

















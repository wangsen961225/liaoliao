
package com.liaoliao.profit.dao.impl;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.profit.dao.FenrunLogDao;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;

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


	@Override
	public Double todayTotal(Integer userId) {
		//String sql = "SELECT sum(money) from ll_fenrun_log where TO_DAYS(add_time) = TO_DAYS(now()) and user_id=?0";
		String countHql="select sum(money) from FenrunLog where user.id=?0 and addTime between ?1 and ?2";
		Long dayMoneyTotal = this.countByHql(countHql,userId,TimeKit.todayStart(),new Date());
		if(dayMoneyTotal==null){
			dayMoneyTotal=(long) 0;
		}
		return (double)dayMoneyTotal;
	}


	@Override
	public List<FenrunLog> findByContentId(Integer articleId) {
		String hql=" from FenrunLog where contentId =?0";
		return this.getListByHQL(hql, articleId);
	}

	@Override
	public List<FenrunLog> findByContentIdFive(Integer articleId) {
		String string="from FenrunLog where type=22 and contentId=? and money<0 order by money asc ";
		 Query query = this.getSession().createQuery(string);
		 query.setFirstResult(0); //开始记录 
		  query.setMaxResults(6); 
		  query.setParameter(0,articleId);//查询出来的记录数 
		return query.list();
	}


	@Override
	public List<FenrunLog> randFind(Integer pageNo) {
		String string="from FenrunLog where money<0 and type=22 ORDER BY RAND()";
		Query query=this.getSession().createQuery(string);
		query.setFirstResult(0);
		query.setMaxResults(pageNo);
		return query.list();
	}
}

















package com.liaoliao.user.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.user.dao.ReadHistoryDao;
import com.liaoliao.user.entity.ReadHistory;

public class ReadHistoryDaoImpl extends BaseDaoImpl<ReadHistory, Integer> implements ReadHistoryDao {

	
	@Override
	public void savemobile(ReadHistory mobile) {
		this.save(mobile);
	}
	@Override
	public List<ReadHistory> queryAll(Integer userId,Integer pageNo) {
			String hql="";
			String countHql="";
			PageResults<ReadHistory> results=null;
			SessionFactory sessionFactory=getSessionFactory();
			Session session=sessionFactory.getCurrentSession();
			SQLQuery sqlQuery;
			if(pageNo==0){
				pageNo=1;
			}
			if(userId==null){
				 //hql="select * from ll_read_history GROUP BY article_id,type ORDER BY num desc ";
				 hql="select id,user_id,article_id,type,add_date,sum(money) as money,sum(num) as num "
					 		+ "from ll_read_history GROUP BY article_id,type ORDER BY num desc  limit "+(pageNo-1)*15+",15";
				 sqlQuery = session.createSQLQuery(hql).addEntity(ReadHistory.class);
				 return sqlQuery.list();
				/* countHql="select count(a) from ReadHistory a";
				 results=this.findPageByFetchedHql(hql, countHql, pageNo, 15);*/
			}else{
				hql="from ReadHistory where userId=?0 order by num desc ";
				 countHql="select count(a) from ReadHistory a where userId=?0";
				results=this.findPageByFetchedHql(hql, countHql, pageNo, 15,userId);
			}
			 
			return results.getResults();
	}

	@Override
	public Integer findCount(Integer userId) {
		if(userId==null){
			SessionFactory sessionFactory=getSessionFactory();
			Session session=sessionFactory.getCurrentSession();
			String hql="select id,userId,articleId,type,addDate, money, num from ReadHistory s group by articleId,type";
			Query Query =  session.createQuery(hql);
			 return Query.list().size();
			// String hql="select count(s) from ReadHistory s ";
			//return Integer.valueOf(this.countByHql(hql).toString());
		}else{
			String hql="select count(s) from ReadHistory s where userId=?0";
			return Integer.valueOf(this.countByHql(hql,userId).toString());
		}
		
		
	}
	@Override
	public ReadHistory findByUserId(Integer articleId, Integer userId,Integer type) {
		String hql="";
		if(userId==null){
			hql=" from ReadHistory  where articleId=?0 and type=?1 and userId is null";
			return this.getByHQL(hql,articleId,type);
		}else{
		 hql="from ReadHistory where articleId=?0 and userId=?1 and type=?2";
		return this.getByHQL(hql, articleId,userId,type);
		}
	}
	@Override
	public void updateMoblie(ReadHistory moblie) {

		this.update(moblie);
	}
	
}

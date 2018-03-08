package com.liaoliao.content.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.KeyWordsDao;
import com.liaoliao.content.entity.KeyWords;

public class KeyWordsDaoImpl extends BaseDaoImpl<KeyWords,Integer> implements KeyWordsDao {

	@Override
	public void add(KeyWords keyWords) {
		this.save(keyWords);
		
	}

	@Override
	public KeyWords findById(Integer id) {
		
		return null;
	}

	@Override
	public List<KeyWords> findBySourceId(Integer userId) {
		
		return null;
	}

	@Override
	public void add(List<KeyWords> keyWords) {
		this.saveList(keyWords);
	}

	@Override
	public List<KeyWords> queryAll(Integer userid,Integer pageNo) {
		SessionFactory sessionFactory=getSessionFactory();
		Session session=sessionFactory.getCurrentSession();
		SQLQuery sqlQuery;
		if(pageNo==0){
			pageNo=1;
		}
		if(userid==null){
		     sqlQuery = session.createSQLQuery("SELECT id,sum(freq) as freq,name,add_date,idf,score,user_id FROM ll_keywords GROUP BY name order by freq desc limit "+(pageNo-1)*15+",15").addEntity(KeyWords.class);
		}else{
			sqlQuery = session.createSQLQuery("SELECT id,freq ,name,add_date,idf,score,user_id FROM ll_keywords where user_id ="+userid+"  limit "+(pageNo-1)*15+",15").addEntity(KeyWords.class);
		}
		return sqlQuery.list();
	}

	@Override
	public Integer findCount(Integer userId) {
		if(userId==null){
			SessionFactory sessionFactory=getSessionFactory();
			Session session=sessionFactory.getCurrentSession();
			Query Query =  session.createQuery("select id, freq,name,addDate,idf,score,userId from KeyWords group by name");
			 return Query.list().size();
		}else{
			SessionFactory sessionFactory=getSessionFactory();
			Session session=sessionFactory.getCurrentSession();
			Query Query =  session.createQuery("select id, freq,name,addDate,idf,score,userId from KeyWords where userId="+userId);
			 return Query.list().size();
			//String hql="select count(s) from KeyWords s where userId=?0 ";
			//return Integer.valueOf(this.countByHql(hql,userId).toString());
		}
		
	}

	@Override
	public KeyWords findById(Integer userId, String name) {
		String hql="";
		if(userId==null){
			hql=" from KeyWords s where name=?0 and userId is null";
			return this.getByHQL(hql,name);
		}else{
		 hql=" from KeyWords s where userId=?0 and name=?1";
		 return this.getByHQL(hql, userId,name);
		}
		 
	}

	@Override
	public void updateMobel(KeyWords keyWords) {
		
		this.update(keyWords);
	}

	@Override
	public List<KeyWords> sumCount(Integer userId) {
		String hql="";
		if(userId==null){
			hql="select name,addDate,sum(freq) from KeyWords s group by name ORDER BY add_date asc";
			return this.getListByHQL(hql);
		}else{
		 hql=" from KeyWords s where userId=?0 ";
		 return this.getListByHQL(hql, userId);
		}
	}

	
	

}

package com.liaoliao.content.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.content.dao.ArticleDao;
import com.liaoliao.content.entity.Article;
import com.liaoliao.util.StaticKey;

public class ArticleDaoImpl extends BaseDaoImpl<Article,Integer> implements ArticleDao {
	
	/**
	 * TODO 接口api
	 */
	
	@Override
	public List<Article> findByKind(Integer kindId,Integer pageNo) {
		if(kindId==null||kindId==0||"".equals(kindId)){
			String hql="from Article where status = 1 order by id desc";
			String countHql="select count(a) from Article a where status = 1";
			PageResults<Article> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.ArticlePageNum);
			return results.getResults();
		}
		String hql="from Article where contentKind.id = ?0 and status = 1 order by id desc";
		String countHql="select count(a) from Article a where contentKind.id = ?0  and status = 1";
		PageResults<Article> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.ArticlePageNum, kindId);
		return results.getResults();
	}

	/**
	 * 接口下拉刷新获取随机新数据
	 */
	@Override
	public Article findByRand(Integer kindId) {
		if(kindId==null||kindId==0||"".equals(kindId)){
//			String sql = "SELECT * FROM `ll_article` AS t1 "
//					+ " JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM `ll_article`)) AS idd) AS t2 "
//					+ " WHERE t1.id >= t2.idd "
//					+ " ORDER BY t1.id ASC LIMIT 1;";
			/*String sql = "SELECT * FROM `ll_article` AS t1 "
					+ " JOIN (SELECT ROUND(RAND() * "
					+ " ((SELECT MAX(id) FROM `ll_article` where `STATUS`=1)-(SELECT MIN(id) FROM `ll_article` where `STATUS`=1))+(SELECT MIN(id) FROM `ll_article` where `STATUS`=1)) AS idd) "
					+ " AS t2 "
					+ " WHERE t1.id >= t2.idd and t1.status=1"
					+ " ORDER BY t1.id LIMIT 1;";*/
			String sql ="SELECT * from ll_article where `status`=1 ORDER BY RAND() LIMIT 1";
			return this.getBySQL(sql);
		}
//		String sql = "SELECT * FROM `ll_article` AS t1 "
//				+ " JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM `ll_article`)) AS idd) AS t2 "
//				+ " WHERE t1.id >= t2.idd "
//				+ " ORDER BY t1.id ASC LIMIT 1;";
		/*String sql = "SELECT * FROM `ll_article` AS t1 "
				+ " JOIN (SELECT ROUND(RAND() * "
				+ " ((SELECT MAX(id) FROM `ll_article` where `STATUS`=1)-(SELECT MIN(id) FROM `ll_article` where `STATUS`=1))+(SELECT MIN(id) FROM `ll_article` where `STATUS`=1)) AS idd) "
				+ " AS t2 "
				+ " WHERE t1.id >= t2.idd and t1.status=1"
				+ " ORDER BY t1.id LIMIT 1;";*/
		String sql="SELECT * from ll_article where `status`=1 ORDER BY RAND() LIMIT 1";
		return this.getBySQL(sql);
	}
	
	public List<Article> findFive(){
		String hql="from Article  where id = '3431'";
		String countHql="select count(a) from Article a where id = '3431'";
		PageResults<Article> results=this.findPageByFetchedHql(hql, countHql, 5, StaticKey.ArticlePageNum);
		return results.getResults();
	}
	
	@Override
	public Article findById(Integer articleId) {
		String hql="from Article where id = ?0";
		return this.getByHQL(hql, articleId);
	}
	
	@Override
	public List<Article> findBySourceId(Integer userId) {
		String hql="from Article where sourceId = ?0 order by id desc";
		return this.getListByHQL(hql, userId);
	}
	/**
	 * TODO 公共
	 */
	
	
	/**
	 * TODO 后台manage
	 */
	
	@Override
	public List<Article> findAll(Integer pageNo,Map<String,Object> map) {
		String hql="from Article";
		String countHql="select count(a) from Article a";
		
		
		if(map.get("searchType")!=null&& !("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			hql+= " where status="+searchType;
			countHql+=" where status="+searchType;
		}
		
		PageResults<Article> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.ArticlePageNum);
		return results.getResults();
	}

	@Override
	public void saveArticle(Article a) {
		this.save(a);
	}

	@Override
	public Article findByKeyAndType(String keyId, Integer type) {
		String hql="from Article where sourceId = ?0 and keyId = ?1";
		return this.getByHQL(hql, type, keyId);
	}


	@Override
	public Integer findCount(Map<String,Object> map) {
		String countHql="select count(a) from Article a where 1=1";
		if(map.get("searchType")!=null&&!("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			countHql+="and status ="+searchType;
		}
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public void deleteArticle(Article article) {
		this.delete(article);
	}

	@Override
	public void updateArticle(Article article) {
		this.update(article);
	}

	@Override
	public Integer findUserSelfCount(Map<String,Object> map) {
		String countHql="select count(a) from Article a where type = 1";
		if(map.get("searchType")!=null&&!("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			countHql+="and status ="+searchType;
		}
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public List<Article> findUserSelfList(Integer pageNo,Map<String,Object> map) {
		String hql="from Article where type = 1";
		String countHql="select count(a) from Article a where type = 1";
		
		if(map.get("searchType")!=null&& !("".equals(map.get("searchType")))){
			Integer searchType = (Integer) map.get("searchType");
			hql+="and status="+searchType;
			countHql+="and status="+searchType;
		}
		
		PageResults<Article> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public void delOriginalArticle(Integer id) {
		
		String originalArticleInfoHql = "delete from OriginalArticleInfo where article.id =?";
		String articleHql = "delete from Article where id =?";
		
		Query originalArticleInfoQuery = this.getSession().createQuery(originalArticleInfoHql);
		Query articleQuery = this.getSession().createQuery(articleHql);
		
		if(id != null && !("".equals(id))){
			originalArticleInfoQuery.setInteger(0,id);
			originalArticleInfoQuery.executeUpdate();
			
			articleQuery.setInteger(0,id);
			articleQuery.executeUpdate();
		}
		
	}

	@Override
	public List<Article> findPassedBySourceId(Integer userId) {
		//String hql="from Article where sourceId = ?0 and status=1 and addTime>(new Date()-5*60*1000) order by id desc";
		String hql="from Article where sourceId = ?0 and status=1 and addTime between ?1 and ?2 order by id desc";
		Date now = new Date();
		Date old = new Date(now.getTime() - 5*60*1000);
		
        Timestamp nowT = new Timestamp(now.getTime());
        Timestamp oldT = new Timestamp(old.getTime());
        
		return this.getListByHQL(hql, userId,oldT,nowT);
	}

	@Override
	public List<Article> findAll() {
		String hql="from Article  order by addTime desc";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Article> findByStatus(Integer status) {
		String hql="from Article  where status=?0 order by addTime desc";
		return this.getListByHQL(hql,status);
	}

	@Override
	public Article findByUserId(Integer articleId, Integer userId) {
		String hql="from Article  where articleId=?0 and userId==?1";
		return  this.getByHQL(hql,articleId,userId);
	}

	

}

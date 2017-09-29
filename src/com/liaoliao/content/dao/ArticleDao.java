package com.liaoliao.content.dao;

import java.util.List;
import java.util.Map;

import com.liaoliao.content.entity.Article;

public interface ArticleDao {

	/**
	 * TODO 接口api
	 */
	
	List<Article> findByKind(Integer kindId, Integer pageNo);

	Article findByRand(Integer kindId);//接口下拉刷新获取随机新数据
	
	Article findById(Integer articleId);
	
	public List<Article> findFive();
	
	List<Article> findBySourceId(Integer userId);
	
	/**
	 * TODO 公共
	 */
	
	
	
	/**
	 * TODO 后台manage
	 */
	
	List<Article> findAll(Integer pageNo,Map<String,Object> map);

	void saveArticle(Article article);
	
	Article findByKeyAndType(String keyId, Integer type);
	
	Integer findCount(Map<String,Object> map);
	
	void deleteArticle(Article article);
	
	void updateArticle(Article article);

	Integer findUserSelfCount(Map<String,Object> map);

	List<Article> findUserSelfList(Integer pageNo,Map<String,Object> map);
	
	/**
	 * 删除未通过审核,禁封的用户原创文章
	 */
	void delOriginalArticle(Integer id);



}

package com.liaoliao.content.service;

import java.util.List;
import java.util.Map;

import com.liaoliao.content.entity.Article;

public interface ArticleService {

	
	List<Article> findByStatus(Integer status);
	
	List<Article> findByKind(Integer kindId, Integer pageNo);
	
	List<Article> findByRand(Integer kindId, Integer size);

	List<Article> findAll(Integer pageNo,Map<String,Object> map);

	void saveArticle(Article article);

	Article findByKeyAndType(String keyId, Integer Type);

	Article findById(Integer articleId);

	Integer findCount(Map<String,Object> map);

	void deleteArticle(Article article);

	void updateArticle(Article article);

	Integer findUserSelfCount(Map<String,Object> map);

	List<Article> findUserSelfList(Integer pageNo,Map<String,Object> map);
	
	public List<Article> findFive();
	
	List<Article> findAll();

	List<Article> findBySourceId(Integer userId);
	
	/**
	 * 删除未通过审核,禁封的用户原创文章
	 */
	Integer delOriginalArticle(Integer id);

	/**
	 * 查找最近5分钟内通过审核的文章
	 * @param userId
	 * @return
	 */
	List<Article> findPassedBySourceId(Integer userId);

}

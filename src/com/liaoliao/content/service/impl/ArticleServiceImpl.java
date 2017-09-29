package com.liaoliao.content.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.ArticleDao;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.service.ArticleService;


@Service("articleService")
@Transactional
public class ArticleServiceImpl  implements ArticleService{
	
	@Autowired
	private ArticleDao articleDao;

	@Override
	public List<Article> findByKind(Integer kindId,Integer pageNo) {
		return articleDao.findByKind(kindId,pageNo);
	}
	
	/**
	 * 接口下拉刷新获取随机新数据
	 */
	@Override
	public List<Article> findByRand(Integer kindId, Integer size) {
		List<Article> randList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			randList.add(articleDao.findByRand(kindId));
		}
		return randList;
	}
 	
	public List<Article> findFive(){
		return 	articleDao.findFive();
	}

	@Override
	public List<Article> findAll(Integer pageNo,Map<String,Object> map) {
		return articleDao.findAll(pageNo,map);
	}

	@Override
	public void saveArticle(Article article) {
		articleDao.saveArticle(article);
	}

	@Override
	public Article findByKeyAndType(String keyId, Integer type) {
		return articleDao.findByKeyAndType(keyId, type);
	}

	@Override
	public Article findById(Integer articleId) {
		return articleDao.findById(articleId);
	}

	@Override
	public Integer findCount(Map<String,Object> map) {
		return articleDao.findCount(map);
	}

	@Override
	public void deleteArticle(Article article) {
		articleDao.deleteArticle(article);
	}

	@Override
	public void updateArticle(Article article) {
		articleDao.updateArticle(article);
	}

	@Override
	public Integer findUserSelfCount(Map<String,Object> map) {
		
		return articleDao.findUserSelfCount(map);
	}

	@Override
	public List<Article> findUserSelfList(Integer pageNo,Map<String,Object> map) {
		return articleDao.findUserSelfList(pageNo,map);
	}

	@Override
	public List<Article> findBySourceId(Integer userId) {
		return articleDao.findBySourceId(userId);
	}

	@Override
	public Integer delOriginalArticle(Integer id) {
		Integer flag = 1;
		
		try {
			articleDao.delOriginalArticle(id);
			flag = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	
	

}

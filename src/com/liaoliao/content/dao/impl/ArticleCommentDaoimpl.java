package com.liaoliao.content.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.ArticleCommentDao;
import com.liaoliao.content.entity.ArticleComment;

public class ArticleCommentDaoimpl extends BaseDaoImpl<ArticleComment,Integer> implements ArticleCommentDao {

	@Override
	public List<ArticleComment> findByArticleId(Integer articleId) {
		String hql="from ArticleComment where article.id = ?0 order by id desc";
		return this.getListByHQL(hql, articleId);
	}

	@Override
	public void saveComment(ArticleComment ac) {
		this.save(ac);
	}
	

}

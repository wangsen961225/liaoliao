package com.liaoliao.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liaoliao.content.dao.ArticleCommentDao;
import com.liaoliao.content.entity.ArticleComment;
import com.liaoliao.content.service.ArticleCommentService;


@Service
@Transactional
public class ArticleCommentServiceImpl implements ArticleCommentService {
	
	@Autowired
	private ArticleCommentDao articleCommentDao;

	
	/**
	 * api
	 */
	@Override
	public List<ArticleComment> findByArticleId(Integer articleId) {
		return articleCommentDao.findByArticleId(articleId);
	}


	@Override
	public void saveComment(ArticleComment articleComment) {
		articleCommentDao.saveComment(  articleComment);
	}

}

package com.liaoliao.content.dao;

import java.util.List;

import com.liaoliao.content.entity.ArticleComment;

public interface ArticleCommentDao {


	List<ArticleComment> findByArticleId(Integer articleId);

	void saveComment(ArticleComment ac);

}

package com.liaoliao.content.service;

import java.util.List;

import com.liaoliao.content.entity.ArticleComment;

public interface ArticleCommentService {

	List<ArticleComment> findByArticleId(Integer articleId);

	void saveComment(ArticleComment ac);


}

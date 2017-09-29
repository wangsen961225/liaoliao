package com.liaoliao.content.dao;

import java.util.List;

import com.liaoliao.content.entity.ArticleComment;
import com.liaoliao.content.entity.VideoComment;

public interface VideoCommentDao {

	List<VideoComment> findByVideoId(Integer id);

	void saveComment(VideoComment video);


}

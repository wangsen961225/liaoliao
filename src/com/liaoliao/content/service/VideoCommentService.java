package com.liaoliao.content.service;

import java.util.List;

import com.liaoliao.content.entity.VideoComment;

public interface VideoCommentService {

	List<VideoComment> findByVideoId(Integer id);

	void saveComment(VideoComment ac);

}

package com.liaoliao.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liaoliao.content.dao.VideoCommentDao;
import com.liaoliao.content.entity.VideoComment;
import com.liaoliao.content.service.VideoCommentService;


@Service
@Transactional
public class VideoCommentServiceImpl implements VideoCommentService {
	
	@Autowired
	private VideoCommentDao videoCommentDao;

	@Override
	public List<VideoComment> findByVideoId(Integer id) {
		return videoCommentDao.findByVideoId(id);
	}

	@Override
	public void saveComment(VideoComment video) {
		videoCommentDao.saveComment(video);
	}

	
}

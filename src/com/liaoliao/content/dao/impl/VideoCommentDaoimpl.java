package com.liaoliao.content.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.VideoCommentDao;
import com.liaoliao.content.entity.VideoComment;

public class VideoCommentDaoimpl extends BaseDaoImpl<VideoComment,Integer> implements VideoCommentDao {

	@Override
	public List<VideoComment> findByVideoId(Integer id) {
		String hql="from VideoComment where video.id = ?0 order by id desc";
		return this.getListByHQL(hql, id);
	}

	@Override
	public void saveComment(VideoComment video) {
		this.save(video);
	}



}

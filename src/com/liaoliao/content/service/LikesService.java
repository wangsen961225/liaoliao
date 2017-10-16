package com.liaoliao.content.service;

import java.util.List;

import com.liaoliao.content.entity.Likes;

public interface LikesService {

	void saveLikes(Likes li);
	
	/**
	 * 根据用户id和type(0:文章 1:视频)查询
	 */
	List<Likes> findLikesById(Integer userId,Integer type);
	
}

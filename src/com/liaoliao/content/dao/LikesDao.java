package com.liaoliao.content.dao;

import java.util.List;

import com.liaoliao.content.entity.Likes;

public interface LikesDao {

	void saveLikes(Likes li);

	/**
	 * 根据用户id和type(0:文章,1:视频)查询
	 * @param userId
	 * @param type
	 */
	List<Likes> findLikesById(Integer userId, Integer type);

	


}

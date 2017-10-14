package com.liaoliao.content.dao;

import java.util.List;
import java.util.Map;

import com.liaoliao.content.entity.Video;

public interface VideoDao {

	List<Video> findAll(Integer pageNo,Map<String,Object> map);

	Video findByRand();
	
	void save(Video video);

	void saveVideo(Video video);

	Video findByKeyAndType(String keyId, Integer type);

	Integer findCount(Map<String,Object> map);

	Video videoDao(Integer id);

	void deleteVideo(Video video);

	void updateVideo(Video video);

	Integer findUserSelfCount(Map<String,Object> map);

	List<Video> findUserSelfList(Integer pageNo,Map<String,Object> map);

	List<Video> findBySourceId(Integer userId);

	/**
	 * 删除未通过审核,禁封的用户原创视频
	 */
	void delOriginalVideo(Integer id);

	/**
	 * 查询最近5分钟通过审核的原创视频
	 * @param userId
	 * @return
	 */
	List<Video> findPassedBySourceId(Integer userId);

	
}

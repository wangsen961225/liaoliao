package com.liaoliao.content.service;

import java.util.List;
import java.util.Map;

import com.liaoliao.content.entity.Video;

public interface VideoService {

	List<Video> findAll(Integer pageNo,Map<String,Object> map);

	List<Video> findByRand(Integer size);
	
	void saveVideo(Video v);
	
	Video findByKeyAndType(String keyId, Integer type);

	Integer findCount(Map<String,Object> map);

	Video findById(Integer id);

	void deleteVideo(Video video);

	void updateVideo(Video video);

	Integer findUserSelfCount(Map<String,Object> map);

	List<Video> findUserSelfList(Integer pageNo,Map<String,Object> map);

	List<Video> findBySourceId(Integer userId);
	
	/**
	 * 删除未通过审核,禁封的用户原创视频
	 */
	Integer delOriginalVideo(Integer id);

	/**
	 * 查找最近5分钟内通过审核的视频
	 * @param userId
	 * @return
	 */
	List<Video> findPassedBySourceId(Integer userId);

}

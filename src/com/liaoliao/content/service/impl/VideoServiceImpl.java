package com.liaoliao.content.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.VideoDao;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.VideoService;


@Service("videoService")
@Transactional
public class VideoServiceImpl  implements VideoService{
	
	@Autowired
	private VideoDao videoDao;

	@Override
	public List<Video> findAll(Integer pageNo,Map<String,Object> map) {
		return videoDao.findAll(pageNo,map);
	}

	/**
	 * 接口下拉刷新获取随机新数据
	 */
	@Override
	public List<Video> findByRand(Integer size) {
		List<Video> randList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			randList.add(videoDao.findByRand());
		}
		return randList;
	}
	
	@Override
	public void saveVideo(Video video) {
		videoDao.saveVideo(video);
	}

	@Override
	public Video findByKeyAndType(String keyId, Integer type) {
		return videoDao.findByKeyAndType(keyId, type);
	}

	@Override
	public Integer findCount(Map<String,Object> map) {
		return videoDao.findCount(map);
	}

	@Override
	public Video findById(Integer id) {
		return videoDao.videoDao(id);
	}

	@Override
	public void deleteVideo(Video video) {
		videoDao.deleteVideo(video);
	}

	@Override
	public void updateVideo(Video video) {
		videoDao.updateVideo(video);
	}

	@Override
	public Integer findUserSelfCount(Map<String,Object> map) {
		return videoDao.findUserSelfCount(map);
	}

	@Override
	public List<Video> findUserSelfList(Integer pageNo,Map<String,Object> map) {
		return videoDao.findUserSelfList(pageNo,map);
	}

	@Override
	public List<Video> findBySourceId(Integer userId) {
		return videoDao.findBySourceId(userId);
	}

	@Override
	public Integer delOriginalVideo(Integer id) {
		Integer flag = 1;
		
		try {
			videoDao.delOriginalVideo(id);
			flag = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Video> findPassedBySourceId(Integer userId) {
		return videoDao.findPassedBySourceId(userId);
	}


	
	
}

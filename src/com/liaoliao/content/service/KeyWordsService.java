package com.liaoliao.content.service;

import java.util.List;

import com.liaoliao.content.entity.KeyWords;

public interface KeyWordsService {

	void add(KeyWords keyWords);
	
	void updateMoble(KeyWords keyWords);
	
	void add(List<KeyWords> keyWords);
	
	KeyWords findById(Integer id);
	
	KeyWords findById(Integer userId,String name);
	
	List<KeyWords> findBySourceId(Integer userId);
	
	List<KeyWords> queryAll(Integer userid,Integer pageNo);
	
	 List<KeyWords> sumCount(Integer userId) ;
	
	Integer findCount(Integer userId);
}

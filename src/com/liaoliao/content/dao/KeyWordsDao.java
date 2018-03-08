package com.liaoliao.content.dao;

import java.util.List;

import com.liaoliao.content.entity.KeyWords;

public interface KeyWordsDao {

	void add(KeyWords keyWords);
	
	void updateMobel(KeyWords keyWords);
	
	void add(List<KeyWords> keyWords);
	
	KeyWords findById(Integer id);
	
	KeyWords findById(Integer userId,String name);
	
	List<KeyWords> findBySourceId(Integer userId);
	
	List<KeyWords> queryAll(Integer userid,Integer pageNo);
	
	List<KeyWords> sumCount(Integer userId);
	
	Integer findCount(Integer userId);
	
	
	


}

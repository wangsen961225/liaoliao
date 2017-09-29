package com.liaoliao.content.dao;

import java.util.List;

import com.liaoliao.content.entity.Feedback;

public interface FeedbackDao {

	void saveFeedback(Feedback feedback);

	Integer findCount();

	List<Feedback> findAll(Integer pageNo);

	Integer findCountByStatus(Integer status);

	List<Feedback> findAllByStatus(Integer pageNo, Integer status);

	Feedback findById(Integer id);

	void updateFB(Feedback fd);

//	UserToken findById(Integer id);
	
}

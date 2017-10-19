package com.liaoliao.content.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.FeedbackDao;
import com.liaoliao.content.entity.Feedback;
import com.liaoliao.content.service.FeedbackService;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired 
	private FeedbackDao feedbackDao;
	

	@Override
	public void saveFeedback(Feedback feedback) {
		feedbackDao.saveFeedback(feedback);
	}


	@Override
	public Integer findCount() {
		return feedbackDao.findCount();
	}


	@Override
	public List<Feedback> findAll(Integer pageNo) {
		return feedbackDao.findAll(pageNo);
	}


	@Override
	public Integer findCountByStatus(Integer status) {
		return feedbackDao.findCountByStatus(status);
	}


	@Override
	public List<Feedback> findAllByStatus(Integer pageNo, Integer status) {
		return feedbackDao.findAllByStatus(pageNo,status);
	}


	@Override
	public Feedback findById(Integer id) {
		return feedbackDao.findById(id);
	}


	@Override
	public void updateFB(Feedback fd) {
		feedbackDao.updateFB(fd);
		
	}


	@Override
	public List<Feedback> findByUserId(Integer userId) {
		return feedbackDao.findByUserId(userId);
	}
	

}

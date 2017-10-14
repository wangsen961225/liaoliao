package com.liaoliao.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.ArticleCommentDao;
import com.liaoliao.content.dao.LikesDao;
import com.liaoliao.content.entity.Likes;
import com.liaoliao.content.service.LikesService;


@Service
@Transactional
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	private LikesDao likesDao;

	@Override
	public void saveLikes(Likes li) {
		
	}

	
	

}

package com.liaoliao.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.ReptileArticleDao;
import com.liaoliao.content.entity.ReptileArticle;
import com.liaoliao.content.service.ReptileArticleService;


@Service("reptileArticleService")
@Transactional
public class ReptileArticleServiceImpl  implements ReptileArticleService{
	
	@Autowired
	private ReptileArticleDao reptileArticleDao;

	@Override
	public List<ReptileArticle> list() {
		return reptileArticleDao.list();
	}

	@Override
	public void updaeMoble(ReptileArticle moble) {
		reptileArticleDao.updaeMoble(moble);		
	}

	

}

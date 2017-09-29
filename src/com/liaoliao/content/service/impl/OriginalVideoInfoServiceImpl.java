package com.liaoliao.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.OriginalVideoInfoDao;
import com.liaoliao.content.entity.OriginalVideoInfo;
import com.liaoliao.content.service.OriginalVideoInfoService;

@Service
@Transactional
public class OriginalVideoInfoServiceImpl implements OriginalVideoInfoService {

	@Autowired
	private OriginalVideoInfoDao originalVideoInfoDao;
	
	
	@Override
	public void saveOVI(OriginalVideoInfo ovi) {
		originalVideoInfoDao.saveOVI(ovi);
	}


	@Override
	public OriginalVideoInfo findByVideoId(Integer id) {
		return originalVideoInfoDao.findByVideoId(id);
	}


	@Override
	public void updateOVI(OriginalVideoInfo ovi) {
		originalVideoInfoDao.updateOVI(ovi);
	}


	@Override
	public void updateOrSaveOVI(OriginalVideoInfo ovi) {
		originalVideoInfoDao.updateOrSaveOVI(ovi);
	}

}

package com.liaoliao.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.OriginalArticleInfoDao;
import com.liaoliao.content.entity.OriginalArticleInfo;
import com.liaoliao.content.service.OriginalArticleInfoService;


@Service
@Transactional
public class OriginalArticleInfoServiceImpl implements OriginalArticleInfoService {

	@Autowired
	private OriginalArticleInfoDao originalArticleInfoDao;
	@Override
	public void saveOAI(OriginalArticleInfo oai) {
		originalArticleInfoDao.saveOAI(oai);
	}
	@Override
	public OriginalArticleInfo findByArticleId(Integer id) {
		return originalArticleInfoDao.findByArticleId(id);
	}
	@Override
	public void updateOAI(OriginalArticleInfo oai) {
		originalArticleInfoDao.updateOAI(oai);
	}

}

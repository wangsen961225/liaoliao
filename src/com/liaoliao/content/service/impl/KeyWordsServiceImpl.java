package com.liaoliao.content.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.KeyWordsDao;
import com.liaoliao.content.entity.KeyWords;
import com.liaoliao.content.service.KeyWordsService;

@Service
@Transactional
public class KeyWordsServiceImpl extends BaseDaoImpl<KeyWords,Integer> implements KeyWordsService {

	@Autowired
	private KeyWordsDao KeyWordsDao;
	@Override
	public void add(KeyWords keyWords) {
		this.save(keyWords);
		
	}

	@Override
	public KeyWords findById(Integer id) {
		
		return null;
	}

	@Override
	public List<KeyWords> findBySourceId(Integer userId) {
		
		return null;
	}

	@Override
	public void add(List<KeyWords> keyWords) {
		KeyWordsDao.add(keyWords);
		
	}

	@Override
	public List<KeyWords> queryAll(Integer userid, Integer pageNo) {
		
		return KeyWordsDao.queryAll(userid, pageNo);
	}

	@Override
	public Integer findCount(Integer userId) {
		return KeyWordsDao.findCount(userId);
	}

	@Override
	public KeyWords findById(Integer userId, String name) {
		
		return KeyWordsDao.findById(userId, name);
	}

	@Override
	public void updateMoble(KeyWords keyWords) {
		KeyWordsDao.updateMobel(keyWords);
	}

	@Override
	public List<KeyWords> sumCount(Integer userId) {
		return KeyWordsDao.sumCount(userId);
	}

}

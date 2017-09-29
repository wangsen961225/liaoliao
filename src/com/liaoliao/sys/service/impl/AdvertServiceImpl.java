package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.AdvertDao;
import com.liaoliao.sys.entity.Advert;
import com.liaoliao.sys.service.AdvertService;

@Service
@Transactional
public class AdvertServiceImpl implements AdvertService {
	
	@Autowired
	private AdvertDao advertDao;

	@Override
	public String findListAdvert() {
		List<Advert> list = advertDao.findListAdvert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
	@Override
	public String findSignAdvert() {
		List<Advert> list = advertDao.findSignAdvert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
	@Override
	public String findArticleTopAdvert() {
		List<Advert> list = advertDao.findArticleTopAdvert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
	@Override
	public String findArticleTopFloatAdvert() {
		List<Advert> list = advertDao.findArticleTopFloatAdvert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
  	@Override
	public String findArticleBottomAdvert() {
		List<Advert> list = advertDao.findArticleBottomAdvert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
  	
	@Override
	public String findArticleMore1Advert() {
		List<Advert> list = advertDao.findArticleMore1Advert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
	@Override
	public String findArticleMore2Advert() {
		List<Advert> list = advertDao.findArticleMore2Advert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}
	
	@Override
	public String findArticleMore3Advert() {
		List<Advert> list = advertDao.findArticleMore3Advert();
		StringBuilder advertStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			advertStr.append(list.get(i).getContent());
		}
		return advertStr.toString();
	}

	
	@Override
	public List<Advert> findAll(Integer pageNo) {
		return advertDao.findAll( pageNo);
	}

	@Override
	public void saveAdvert(Advert ad) {
		advertDao.saveAdvert(ad);
		
	}

	@Override
	public Advert findById(Integer id) {
		return advertDao.findById(id);
	}

	@Override
	public void delAdvert(Advert ad) {
		advertDao.delAdvert(ad);
	}

	@Override
	public void updateAdvert(Advert ad) {
		advertDao.updateAdvert(ad);
	}

	@Override
	public Integer findCount() {
		return advertDao.findCount();
	}

}

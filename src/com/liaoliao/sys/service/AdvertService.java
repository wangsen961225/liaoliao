package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.Advert;

public interface AdvertService {

	String findListAdvert();
	
	String findSignAdvert();
	
	String findArticleTopAdvert();
	
	String findArticleTopFloatAdvert();
	
	String findArticleBottomAdvert();

	String findArticleMore1Advert();
	
	String findArticleMore2Advert();
	
	String findArticleMore3Advert();
	
	List<Advert> findAll(Integer pageNo);

	void saveAdvert(Advert ad);

	Advert findById(Integer id);

	void delAdvert(Advert ad);

	void updateAdvert(Advert ad);

	Integer findCount();



}

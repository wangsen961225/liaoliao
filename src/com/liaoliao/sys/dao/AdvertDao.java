package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.Advert;

public interface AdvertDao {
	
	List<Advert> findListAdvert();
	
	List<Advert> findSignAdvert();

	List<Advert> findArticleTopAdvert();
	
	List<Advert> findArticleTopFloatAdvert();

	List<Advert> findArticleBottomAdvert();

	List<Advert> findArticleMore1Advert();
	
	List<Advert> findArticleMore2Advert();
	
	List<Advert> findArticleMore3Advert();
	
	List<Advert> findAll(Integer pageNo);

	void saveAdvert(Advert ad);

	Advert findById(Integer id);

	void delAdvert(Advert ad);

	void updateAdvert(Advert ad);

	Integer findCount();

	List<Advert> findDirectInvest();




}

package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.Advert;
import com.liaoliao.sys.entity.AdvertClicks;

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

	String findDirectInvest();

	/**
	 * 随机获取一个待指定的广告(显示在料料头条内容页的内容中)
	 * @return
	 */
	String toOrder();




}

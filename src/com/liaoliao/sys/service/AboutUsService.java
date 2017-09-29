package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.AboutUs;

public interface AboutUsService {

	List<AboutUs> findQuestionList();

	List<AboutUs> findAll(Integer pageNo,Integer pageSize);

	AboutUs findContentById(Integer id);

	void updateAboutUs(AboutUs au);

	void saveAboutUs(AboutUs au);

	void deleteAboutUs(AboutUs au);

	Long findCount();

}

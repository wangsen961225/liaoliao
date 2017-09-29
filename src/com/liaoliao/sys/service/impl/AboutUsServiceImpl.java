package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.AboutUsDao;
import com.liaoliao.sys.entity.AboutUs;
import com.liaoliao.sys.service.AboutUsService;

@Service
@Transactional
public class AboutUsServiceImpl implements AboutUsService {
	
	@Autowired
	private AboutUsDao aboutUsDao;

	@Override
	public List<AboutUs> findQuestionList() {
		return aboutUsDao.findQuestionList();
	}

	@Override
	public List<AboutUs> findAll(Integer pageNo,Integer pageSize) {
		return aboutUsDao.findAll(pageNo,pageSize);
	}

	@Override
	public AboutUs findContentById(Integer id) {
		return aboutUsDao.findContentById(id);
	}

	@Override
	public void updateAboutUs(AboutUs au) {
		aboutUsDao.updateAboutUs(au);
	}

	@Override
	public void saveAboutUs(AboutUs au) {
		aboutUsDao.saveAboutUs(au);
	}

	@Override
	public void deleteAboutUs(AboutUs au) {
		aboutUsDao.deleteAboutUs(au);
	}

	@Override
	public Long findCount() {
		return aboutUsDao.findCount();
	}

}










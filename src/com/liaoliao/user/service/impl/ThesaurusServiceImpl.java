package com.liaoliao.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.ThesaurusDao;
import com.liaoliao.user.entity.Thesaurus;
import com.liaoliao.user.service.ThesaurusService;

@Service
@Transactional
public class ThesaurusServiceImpl implements ThesaurusService {
	
	@Autowired
	private ThesaurusDao thesaurusDao;

	@Override
	public void saveThesaurus(Thesaurus ths) {
		thesaurusDao.saveThesaurus(ths);
	}

}

package com.liaoliao.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.content.dao.ContentKindDao;
import com.liaoliao.content.entity.ContentKind;
import com.liaoliao.content.service.ContentKindService;


@Service("contentKindService")
@Transactional
public class ContentKindServiceImpl  implements ContentKindService{
	
	@Autowired
	private ContentKindDao contentKindDao;

	@Override
	public ContentKind findByName(String name) {
		return contentKindDao.findByName(name);
	}
	
	

}

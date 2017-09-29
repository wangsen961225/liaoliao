package com.liaoliao.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.SystemMessageDao;
import com.liaoliao.sys.entity.SystemMessage;
import com.liaoliao.sys.service.SystemMessageService;

@Service
@Transactional
public class SystemMessageServiceImpl implements SystemMessageService {
	
	@Autowired
	private SystemMessageDao systemMessageDao;

	@Override
	public void saveMsg(SystemMessage sm) {
		systemMessageDao.saveMsg(sm) ;
	}

}

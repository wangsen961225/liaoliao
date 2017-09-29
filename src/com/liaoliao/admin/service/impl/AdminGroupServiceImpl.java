package com.liaoliao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.admin.dao.AdminGroupDao;
import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.admin.service.AdminGroupService;


@Service("adminGroupService")
@Transactional
public class AdminGroupServiceImpl  implements AdminGroupService{
	
	@Autowired
	private AdminGroupDao adminGroupDao;

	@Override
	public void saveAdminGroup(AdminGroup ag) {
		adminGroupDao.saveAdminGroup(ag);
		
	}

	@Override
	public List<AdminGroup> findAll() {
		return adminGroupDao.findAll();
	}

	@Override
	public AdminGroup findById(Integer groupId) {
		return adminGroupDao.findById(groupId);
	}


}

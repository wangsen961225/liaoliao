package com.liaoliao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.admin.dao.AdminUserDao;
import com.liaoliao.admin.entity.AdminUser;
import com.liaoliao.admin.service.AdminUserService;


@Service("adminUserService")
@Transactional
public class AdminUserServiceImpl  implements AdminUserService{
	
	@Autowired
	private AdminUserDao adminUserDao;

	public AdminUser findByUserName(String userName) {
		return adminUserDao.findByUserName(userName);
	}

	@Override
	public List<AdminUser> findAll(Integer pageNo) {
		return adminUserDao.findAll(pageNo);
	}

	@Override
	public Integer findCount() {
		return adminUserDao.findCount();
	}

	@Override
	public void saveAdminUser(AdminUser au) {
		adminUserDao.saveAdminUser(au);
	}

	@Override
	public AdminUser findById(Integer id) {
		return adminUserDao.findById(id);
	}

	@Override
	public void updateAdminUser(AdminUser au) {
		adminUserDao.updateAdminUser(au);
	}
	

}

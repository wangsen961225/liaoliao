package com.liaoliao.admin.dao;

import java.util.List;

import com.liaoliao.admin.entity.AdminUser;


public interface AdminUserDao {

	AdminUser findByUserName(String userName);

	List<AdminUser> findAll(Integer pageNo);

	Integer findCount();

	void saveAdminUser(AdminUser au);

	AdminUser findById(Integer id);

	void updateAdminUser(AdminUser au);

}

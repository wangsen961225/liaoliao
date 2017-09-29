package com.liaoliao.admin.dao;

import java.util.List;

import com.liaoliao.admin.entity.AdminGroup;


public interface AdminGroupDao {

	void saveAdminGroup(AdminGroup ag);

	List<AdminGroup> findAll();

	AdminGroup findById(Integer groupId);

}

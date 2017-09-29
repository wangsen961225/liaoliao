package com.liaoliao.admin.service;

import java.util.List;

import com.liaoliao.admin.entity.AdminGroup;

public interface AdminGroupService {

	void saveAdminGroup(AdminGroup ag);

	List<AdminGroup> findAll();

	AdminGroup findById(Integer groupId);

}

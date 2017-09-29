package com.liaoliao.admin.service;

import java.util.List;

import com.liaoliao.admin.entity.Permission;

public interface PermissionService {

	List<Permission> findByGroupId(Integer groupId);

	List<Permission> findAll();

	void delPermission(Permission p);

	void savePermission(Permission per);

	List<Permission> findByNavigationId(Integer id);

}

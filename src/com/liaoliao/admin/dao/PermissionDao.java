package com.liaoliao.admin.dao;

import java.util.List;

import com.liaoliao.admin.entity.Permission;

public interface PermissionDao {

	List<Permission> findByGroupId(Integer groupId);

	List<Permission> findAll();

	void delPermission(Permission p);

	void savePermission(Permission per);

	List<Permission> findByNavigationId(Integer id);

}

package com.liaoliao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.admin.dao.PermissionDao;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.admin.service.PermissionService;
import com.liaoliao.shiro.FilterChainDefinitionsService;


@Service("permissionService")
@Transactional
public class PermissionServiceImpl  implements PermissionService{
	
	@Autowired
	private PermissionDao permissionDao;
	
	

	
	
	@Override
	public List<Permission> findByGroupId(Integer groupId) {
		return permissionDao.findByGroupId(groupId);
	}



	@Override
	public List<Permission> findAll() {
		return permissionDao.findAll();
	}



	@Override
	public void delPermission(Permission p) {
		permissionDao.delPermission(p);
	}



	@Override
	public void savePermission(Permission per) {
		permissionDao.savePermission(per);
	}



	@Override
	public List<Permission> findByNavigationId(Integer id) {
		return permissionDao.findByNavigationId(id);
	}



}

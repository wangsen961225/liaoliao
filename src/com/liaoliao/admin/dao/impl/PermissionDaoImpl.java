package com.liaoliao.admin.dao.impl;

import java.util.List;

import com.liaoliao.admin.dao.PermissionDao;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.basedao.BaseDaoImpl;

public class PermissionDaoImpl  extends BaseDaoImpl<Permission,Long> implements PermissionDao {

	@Override
	public List<Permission> findByGroupId(Integer groupId) {
		String hql="from Permission where adminGroup.id = ?0 order by navigation.sort";
		List<Permission> list=this.getListByHQL(hql, groupId);
		return list;
	}

	@Override
	public List<Permission> findAll() {
		String hql="from Permission  order by navigation.sort";
		List<Permission> list=this.getListByHQL(hql);
		return list;
	}

	@Override
	public void delPermission(Permission p) {
		this.delete(p);
	}

	@Override
	public void savePermission(Permission per) {
		this.save(per);
	}

	@Override
	public List<Permission> findByNavigationId(Integer id) {
		String hql="from Permission where navigation.id = ?0";
		return this.getListByHQL(hql, id);
	}

}

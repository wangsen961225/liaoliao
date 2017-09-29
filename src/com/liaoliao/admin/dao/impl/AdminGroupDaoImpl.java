package com.liaoliao.admin.dao.impl;

import java.util.List;

import com.liaoliao.admin.dao.AdminGroupDao;
import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.basedao.BaseDaoImpl;

public class AdminGroupDaoImpl extends BaseDaoImpl<AdminGroup,Integer>  implements AdminGroupDao{

	public void saveAdminGroup(AdminGroup ag) {
      this.save(ag);
	}

	public List<AdminGroup> findAll() {
		String hql="from AdminGroup";
		List<AdminGroup> list= this.getListByHQL(hql);
		return list;
	}

	@Override
	public AdminGroup findById(Integer groupId) {
		return this.load(groupId);
	}

}





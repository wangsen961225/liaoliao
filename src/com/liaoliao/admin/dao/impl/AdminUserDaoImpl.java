package com.liaoliao.admin.dao.impl;

import java.util.List;

import com.liaoliao.admin.dao.AdminUserDao;
import com.liaoliao.admin.entity.AdminUser;
import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;


public class AdminUserDaoImpl extends BaseDaoImpl<AdminUser,Integer> implements AdminUserDao{

	public AdminUser findByUserName(String userName) {
		String hql = "from AdminUser where userName = ?0";  
        AdminUser adminUser=this.getByHQL(hql, userName);
        return adminUser;
	}

	@Override
	public List<AdminUser> findAll(Integer pageNo) {
		String hql = "from AdminUser";  
		String countHql="select count(a) from AdminUser a";
        PageResults<AdminUser> results = (PageResults<AdminUser>) this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public Integer findCount() {
		String countHql="select count(a) from AdminUser a";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public void saveAdminUser(AdminUser au) {
		this.save(au);
	}

	@Override
	public AdminUser findById(Integer id) {
		return this.get(id);
	}

	@Override
	public void updateAdminUser(AdminUser au) {
		this.update(au);
	}

}

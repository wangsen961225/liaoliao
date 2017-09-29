package com.liaoliao.admin.dao;

import java.util.List;

import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.admin.entity.Navigation;

public interface NavigationDao {


	List<Navigation> findByGroup(AdminGroup ag);

	List<Navigation> findParent();

	Integer findCount();

	Navigation findById(Integer parentId);

	 void saveNa(Navigation na);

	List<Navigation> findByParentId(Integer id);

	List<Navigation> findAll();

	void deleteNavigation(Navigation navigation);

	Integer findParenCount();

	void updataNavigation(Navigation navigation);
	

}

package com.liaoliao.admin.dao.impl;

import java.util.List;

import com.liaoliao.admin.dao.NavigationDao;
import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.admin.entity.Navigation;
import com.liaoliao.basedao.BaseDaoImpl;

public class NavigationDaoImpl  extends BaseDaoImpl<Navigation,Integer> implements NavigationDao {

	@Override
	public List<Navigation> findByGroup(AdminGroup ag) {
		String hql="from Navigation where AdminGroup  = ?0";
		List<Navigation> list=this.getListByHQL(hql, ag);
		return list;
	}

	@Override
	public List<Navigation> findParent() {
		String hql="from Navigation where navigation.id = id or navigation.id is null order by sort";
		List<Navigation> list=this.getListByHQL(hql);
		return list;
	}

	@Override
	public Integer findCount() {
		String countHql="select count(a) from Navigation a";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public Navigation findById(Integer parentId) {
		return this.get(parentId);
	}

	@Override
	public void saveNa(Navigation na) {
		this.save(na);
	}

	@Override
	public List<Navigation> findByParentId(Integer id) {
		String hql="from Navigation where  navigation.id = ?0";
		return this.getListByHQL(hql, id);
	}

	@Override
	public List<Navigation> findAll() {
		String hql="from Navigation";
		return this.getListByHQL(hql);
	}

	@Override
	public void deleteNavigation(Navigation navigation) {
		this.delete(navigation);
	}

	@Override
	public Integer findParenCount() {
		String countHql="select count(a) from Navigation a where a.navigation.id = a.id or a.navigation.id is null ";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public void updataNavigation(Navigation navigation) {
		this.update(navigation);
		
	}



}

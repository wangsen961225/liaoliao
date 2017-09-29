package com.liaoliao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.admin.dao.NavigationDao;
import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.admin.entity.Navigation;
import com.liaoliao.admin.service.NavigationService;
import com.liaoliao.shiro.FilterChainDefinitionsService;


@Service("navigationService")
@Transactional
public class NavigationServiceImpl  implements NavigationService{
	
	@Autowired
	private NavigationDao navigationDao;
	
	
	@Override
	public List<Navigation> findByGroup(AdminGroup ag) {
		return navigationDao.findByGroup(ag);
	}
	@Override
	public List<Navigation> findParent() {
		return navigationDao.findParent();
	}
	@Override
	public Integer findCount() {
		return navigationDao.findCount();
	}

	@Override
	public Navigation findById(Integer parentId) {
		return navigationDao.findById(parentId);
	}
	@Override
	public void saveNa(Navigation na) {
		navigationDao.saveNa(na);
	}
	@Override
	public List<Navigation> findByParentId(Integer id) {
		return navigationDao.findByParentId(id);
	}
	@Override
	public List<Navigation> findAll() {
		return navigationDao.findAll();
	}
	@Override
	public void deleteNavigation(Navigation navigation) {
		navigationDao.deleteNavigation(navigation);
	}
	@Override
	public Integer findParenCount() {
		return navigationDao.findParenCount();
	}
	@Override
	public void updataNavigation(Navigation navigation) {
		navigationDao.updataNavigation(navigation);
	}


	

}

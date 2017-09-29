package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.sys.dao.AppVersionDao;
import com.liaoliao.sys.entity.AppVersion;

public class AppVersionDaoImpl extends BaseDaoImpl<AppVersion,Integer>implements AppVersionDao {

	@Override
	public AppVersion findMaxVersion() {
		String sql = "SELECT * FROM ll_app_version WHERE version_code = (SELECT MAX(version_code) FROM ll_app_version)";
		return this.getBySQL(sql);
	}

	@Override
	public List<AppVersion> findAll(Integer pageNo) {
		String hql="from AppVersion ";
		String countHql="select count(a) from AppVersion a";
		PageResults<AppVersion> results=this.findPageByFetchedHql(hql, countHql, pageNo, 5);
		return results.getResults();
	}

	@Override
	public void saveAppVersion(AppVersion appVersion) {
		this.save(appVersion);
	}

	@Override
	public AppVersion findById(Integer id) {
		return this.get(id);
	}

	@Override
	public void updateAppVersion(AppVersion appVersion) {
		this.update(appVersion);
	}

	@Override
	public void delAppVersion(AppVersion appVersion) {
		this.delete(appVersion);
	}

	@Override
	public Integer findCount() {
		String hql="select count(s) from AppVersion s";
		return Integer.valueOf(this.countByHql(hql).toString());
	}


}

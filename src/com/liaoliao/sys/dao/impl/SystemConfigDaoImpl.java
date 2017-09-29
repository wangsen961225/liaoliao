package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.content.entity.Article;
import com.liaoliao.sys.dao.SystemConfigDao;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.util.StaticKey;

public class SystemConfigDaoImpl extends BaseDaoImpl<SystemConfig,String>implements SystemConfigDao {

	@Override
	public SystemConfig findValueByKey(String keyName) {
		return this.get(keyName);
	}

	@Override
	public List<SystemConfig> findAll(Integer pageNo) {
		
		String hql="from SystemConfig order by sort";
		String countHql="select count(a) from SystemConfig a";
		PageResults<SystemConfig> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();


	}
	
	@Override
	public void updateSystemConfig(SystemConfig systemConfig) {
		this.update(systemConfig);
	}

	@Override
	public Integer findCount() {
		String hql="select count(s) from SystemConfig s";
		return Integer.valueOf(this.countByHql(hql).toString());
	}



}

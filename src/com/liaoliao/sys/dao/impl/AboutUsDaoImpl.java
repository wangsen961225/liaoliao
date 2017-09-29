package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.sys.dao.AboutUsDao;
import com.liaoliao.sys.entity.AboutUs;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.util.StaticKey;

public class AboutUsDaoImpl extends BaseDaoImpl<AboutUs,Integer> implements AboutUsDao {

	@Override
	public List<AboutUs> findQuestionList() {
		String hql="from AboutUs where type = ?0";
		return this.getListByHQL(hql, StaticKey.AboutUsTypeQuestion);
	}

	@Override
	public List<AboutUs> findAll(Integer pageNo,Integer pageSize) {
		String hql="from AboutUs ";
		String countHql="select count(a) from AboutUs a";
		PageResults<AboutUs> results=this.findPageByFetchedHql(hql, countHql, pageNo, pageSize);
		return results.getResults();
	}

	@Override
	public AboutUs findContentById(Integer id) {
		return this.get(id);
	}

	@Override
	public void updateAboutUs(AboutUs au) {
		this.update(au);
	}

	@Override
	public void saveAboutUs(AboutUs au) {
		this.save(au);
	}

	@Override
	public void deleteAboutUs(AboutUs au) {
		this.delete(au);
	}

	@Override
	public Long findCount() {
		String hql="select count(s) from AboutUs s";
		return this.countByHql(hql);
	}
}

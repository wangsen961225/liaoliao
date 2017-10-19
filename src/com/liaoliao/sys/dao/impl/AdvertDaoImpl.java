package com.liaoliao.sys.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.sys.dao.AdvertDao;
import com.liaoliao.sys.entity.Advert;

public class AdvertDaoImpl extends BaseDaoImpl<Advert,Integer> implements AdvertDao {

	@Override
	public List<Advert> findListAdvert() {
		String hql="from Advert where type = 1 and position = 'list' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findSignAdvert() {
		String hql="from Advert where type = 1 and position = 'sign' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findArticleTopAdvert() {
		String hql="from Advert where type = 1 and position = 'top' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findArticleTopFloatAdvert() {
		String hql="from Advert where type = 1 and position = 'topFloat' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findArticleBottomAdvert() {
		String hql="from Advert where type = 1 and position = 'bottom' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findArticleMore1Advert() {
		String hql="from Advert where type = 1 and position = 'more1' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}

	@Override
	public List<Advert> findArticleMore2Advert() {
		String hql="from Advert where type = 1 and position = 'more2' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findArticleMore3Advert() {
		String hql="from Advert where type = 1 and position = 'more3' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}
	
	@Override
	public List<Advert> findAll(Integer pageNo) {
		String hql="from Advert";
		String countHql="select count(a) from Advert a";
		PageResults<Advert> results=this.findPageByFetchedHql(hql, countHql, pageNo, 5);
		return results.getResults();
	}

	@Override
	public void saveAdvert(Advert ad) {
		this.save(ad);
	}

	@Override
	public Advert findById(Integer id) {
		return this.get(id);
	}

	@Override
	public void delAdvert(Advert ad) {
		this.delete(ad);
	}

	@Override
	public void updateAdvert(Advert ad) {
		this.update(ad);
	}

	@Override
	public Integer findCount() {
		String countHql="select count(a) from Advert a";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public List<Advert> findDirectInvest(){
		String hql="from Advert where type = 1 and position = 'directInvest' and status = 1 order by sort";
		return this.getListByHQL(hql);
	}

	
	
}

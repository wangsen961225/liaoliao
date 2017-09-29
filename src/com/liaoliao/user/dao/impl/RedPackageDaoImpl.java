package com.liaoliao.user.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.RedPackageDao;
import com.liaoliao.user.entity.RedPackage;

public class RedPackageDaoImpl extends BaseDaoImpl<RedPackage,Integer> implements RedPackageDao {

	@Override
	public void saveRedPackage(RedPackage rp) {
		this.save(rp);
		
	}

	@Override
	public RedPackage findByUserId(Integer userId) {
		String hql="from RedPackage where user.id = ?0 and status = 1 order by id desc";
		List<RedPackage> list= this.getListByHQL(hql, userId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
		
	}

	@Override
	public void updateRedPackage(RedPackage rp) {
		this.update(rp);
	}

	@Override
	public RedPackage findById(Integer rpId) {
		return this.get(rpId);
	}

	@Override
	public List<RedPackage> findUnDeal() {
		String hql="from RedPackage where status = 1 and balance > 0";
		return this.getListByHQL(hql);
	}

	@Override
	public List<RedPackage> findListByUserId(Integer userId) {
		String hql="from RedPackage where userId =?0";
		return this.getListByHQL(hql, userId);
	}

}

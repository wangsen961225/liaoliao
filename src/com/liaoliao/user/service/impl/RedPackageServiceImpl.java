package com.liaoliao.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.RedPackageDao;
import com.liaoliao.user.entity.RedPackage;
import com.liaoliao.user.service.RedPackageService;


@Service("redPackageService")
@Transactional
public class RedPackageServiceImpl implements RedPackageService {
	
	@Autowired
	private RedPackageDao redPackageDao;

	@Override
	public void saveRedPackage(RedPackage rp) {
		redPackageDao.saveRedPackage(rp);
		
	}

	@Override
	public RedPackage findByUserId(Integer userId) {
		return redPackageDao.findByUserId(userId);
	}

	@Override
	public void updateRedPackage(RedPackage rp) {
		redPackageDao.updateRedPackage(rp);
	}

	@Override
	public RedPackage findById(Integer rpId) {
		return redPackageDao.findById(rpId);
	}

	@Override
	public List<RedPackage> findUnDeal() {
		return redPackageDao.findUnDeal( );
	}

	@Override
	public List<RedPackage> findListByUserId(Integer userId) {
		return redPackageDao.findListByUserId( userId);
	}

}

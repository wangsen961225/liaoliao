package com.liaoliao.user.service;

import java.util.List;

import com.liaoliao.user.entity.RedPackage;

public interface RedPackageService {

	void saveRedPackage(RedPackage rp);

	RedPackage findByUserId(Integer userId);

	void updateRedPackage(RedPackage rp);

	RedPackage findById(Integer rpId);

	List<RedPackage> findUnDeal();

	List<RedPackage> findListByUserId(Integer userId);

}

package com.liaoliao.user.dao;

import java.util.List;

import com.liaoliao.user.entity.RedPackage;

public interface RedPackageDao {

	void saveRedPackage(RedPackage rp);

	RedPackage findByUserId(Integer userId);

	void updateRedPackage(RedPackage rp);

	RedPackage findById(Integer rpId);

	List<RedPackage> findUnDeal();

	List<RedPackage> findListByUserId(Integer userId);

}

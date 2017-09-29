package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.user.entity.Users;

public interface ToBankDao {

	List<ToBank> findByUser(Users user, Integer pageNo);

	void updateToBank(ToBank toBank);

	void saveToBank(ToBank toBank);

	Long findToBankCountByUser(Users user);

	List<ToBank> findAll(Integer pageNo);

	Integer findCount();


}

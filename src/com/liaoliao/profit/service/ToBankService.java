package com.liaoliao.profit.service;


import java.util.List;

import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.user.entity.Users;


public interface ToBankService {

	List<ToBank> findByUser(Users user, Integer pageNo);

	void updateToBank(ToBank toBank);

	void saveToBank(ToBank toBank);

	Long findToBankCountByUser(Users user);

	List<ToBank> findAll(Integer pageNo);

	Integer findCount();

}

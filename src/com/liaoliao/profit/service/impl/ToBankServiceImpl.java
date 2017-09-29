package com.liaoliao.profit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.profit.dao.ToBankDao;
import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.profit.service.ToBankService;
import com.liaoliao.user.entity.Users;

@Service
@Transactional
public class ToBankServiceImpl implements ToBankService {

	@Autowired 
	private ToBankDao toBankDao;

	@Override
	public List<ToBank> findByUser(Users user, Integer pageNo) {
		return toBankDao.findByUser(user, pageNo);
	}

	@Override
	public void updateToBank(ToBank toBank) {
		toBankDao.updateToBank(toBank);
	}

	@Override
	public void saveToBank(ToBank toBank) {
		toBankDao.saveToBank(toBank);
	}

	@Override
	public Long findToBankCountByUser(Users user) {
		return toBankDao.findToBankCountByUser(user);
	}

	@Override
	public List<ToBank> findAll(Integer pageNo) {
		return toBankDao.findAll(  pageNo);
	}

	@Override
	public Integer findCount() {
		return toBankDao.findCount() ;
	}

	

}

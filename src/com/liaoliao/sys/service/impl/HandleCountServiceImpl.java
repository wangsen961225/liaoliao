package com.liaoliao.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.HandleCountDao;
import com.liaoliao.sys.entity.HandleCount;
import com.liaoliao.sys.service.HandleCountService;

@Service("handleCountService")
@Transactional
public class HandleCountServiceImpl implements HandleCountService {
	
	@Autowired
	private HandleCountDao handleCountDao;

	@Override
	public void handleCountPlusOne(String clickName) {
		handleCountDao.handleCountPlusOne(clickName);
	}

	@Override
	public List<HandleCount> handleCountList(Date datTime) {
		return handleCountDao.handleCountList(datTime);
	}

	@Override
	public void handleCountTotalMoney(String totalProfitMoney, int coin) {
		handleCountDao.handleCountList(totalProfitMoney,coin);
	}

	@Override
	public void clearOnline() {
		handleCountDao.clearOnline();
	}



}

	








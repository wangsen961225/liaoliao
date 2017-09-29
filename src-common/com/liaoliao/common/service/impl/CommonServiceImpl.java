package com.liaoliao.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liaoliao.common.service.CommonService;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;


/**
 * 用于事务回滚
 */
@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FenrunLogService fenrunLogService;

	
	/**
	 * 更新用户、保存分润记录
	 * fenrunLogService.saveFenrunLog(fenrun);
	 * userService.updateUser(user);
	 */
	@Override
	public void updateUserAndSaveProfitLog(FenrunLog fenrun, Users user) {
		userService.updateUser(user);
		fenrunLogService.saveFenrunLog(fenrun);
	}

	

}

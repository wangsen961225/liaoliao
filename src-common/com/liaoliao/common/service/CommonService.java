package com.liaoliao.common.service;

import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.user.entity.Users;

public interface CommonService {

	
	/**
	 * 更新用户、保存分润记录
	 * fenrunLogService.saveFenrunLog(fenrun);
	 * userService.updateUser(user);
	 */
	void updateUserAndSaveProfitLog(FenrunLog fenrun, Users user);


}

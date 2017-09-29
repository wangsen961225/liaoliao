package com.liaoliao.user.service;

import java.util.List;

import com.liaoliao.user.entity.FocusLog;

public interface FocusLogService {

	void saveFocusLog(FocusLog fl);

	List<FocusLog> findByUserId(Integer userId);

	
	Long countNum(Integer id);
	
	/**
	 * 根据用户查询被关注
	 */
	List<FocusLog> findBeConcernedByUserId(Integer userId);

	FocusLog findByFocusId(Integer userId, Integer focusId);

	void updateFocusLog(FocusLog fl);

}

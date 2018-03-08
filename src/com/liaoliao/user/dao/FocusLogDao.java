package com.liaoliao.user.dao;

import java.util.List;

import com.liaoliao.user.entity.FocusLog;

public interface FocusLogDao {

	 void saveFocusLog(FocusLog fl);

	List<FocusLog> findByUserId(Integer userId);

	Long countNum(Integer id);
	
	/**
	 * 查询关注userId的用户
	 * @param userId
	 * @return
	 */
	List<FocusLog> findBeConcernedByUserId(Integer userId);

	FocusLog findByFocusId(Integer userId, Integer focusId);
	Long getFocusCountById(Integer id);
	

	void updateFocusLog(FocusLog fl);

}

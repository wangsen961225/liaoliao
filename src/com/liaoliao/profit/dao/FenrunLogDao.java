package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.FenrunLog;

public interface FenrunLogDao {

	void saveFenrunLog(FenrunLog fenrun);

	List<FenrunLog> findByUserId(Integer userId, Integer pageNo);

	List<FenrunLog> findOriginalLogByUserId(Integer userId, Integer pageNo);

	FenrunLog findByRedPackageId(Integer userId, Integer id);

	List<FenrunLog> findRedPackageLog( Integer redPackageId);

	List<FenrunLog> expenditureDetails(Integer userId);

	Integer countSignProfit(Integer userId);

	Integer countSignNum(Integer userId);

	Double todayTotal(Integer userId);

}

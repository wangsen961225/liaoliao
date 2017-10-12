package com.liaoliao.profit.service;

import java.util.List;

import com.liaoliao.profit.entity.FenrunLog;

public interface FenrunLogService {

	void saveFenrunLog(FenrunLog fenrun);

	List<FenrunLog> findByUserId(Integer id, Integer pageNo);

	List<FenrunLog> findOriginalLogByUserId(Integer userId, Integer pageNo);

	FenrunLog findByRedPackageId(Integer userId, Integer id);

	List<FenrunLog> findRedPackageLog( Integer redPackageId);

	List<FenrunLog> expenditureDetails(Integer userId);

}

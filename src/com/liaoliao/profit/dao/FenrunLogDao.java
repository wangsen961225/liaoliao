package com.liaoliao.profit.dao;

import java.util.List;

import com.liaoliao.profit.entity.FenrunLog;

public interface FenrunLogDao {
	/**
	 * 随机获取n条数据
	 * @param pageNo
	 * @return
	 */
	List<FenrunLog> randFind(Integer pageNo);

	void saveFenrunLog(FenrunLog fenrun);

	List<FenrunLog> findByUserId(Integer userId, Integer pageNo);

	List<FenrunLog> findOriginalLogByUserId(Integer userId, Integer pageNo);

	FenrunLog findByRedPackageId(Integer userId, Integer id);

	List<FenrunLog> findRedPackageLog( Integer redPackageId);

	List<FenrunLog> expenditureDetails(Integer userId);

	Integer countSignProfit(Integer userId);

	Integer countSignNum(Integer userId);

	Double todayTotal(Integer userId);
	
	/**
	 * 查出所有屬於這個用戶名下的文章或視頻
	 * @param articleId
	 * @return
	 */
	List<FenrunLog> findByContentId(Integer articleId);
	
	/**
	 * 查询文章或视频打赏金额最大的前五名用户信息
	 * @param articleId
	 * @return
	 */
	List<FenrunLog> findByContentIdFive(Integer articleId);

}

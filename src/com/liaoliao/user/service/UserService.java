package com.liaoliao.user.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liaoliao.user.entity.Users;


public interface UserService {

	Users findById(Integer id);

	Users findByMobile(String mobile);

	void updateUser(Users user);

	void saveUser(Users user);

	void clearUserDayMoney();

	List<Users> findAll(Integer pageNo, Map<String, Object> map);

	long getInviteCount(Integer userId);

	List<Users> getInviteList(Integer userId,Integer pageNo);

	/**
	 * 根据id删除与该用户关联的所有信息
	 */
	void deleteUserAll(Integer userId);
	
	/**
	 * 自动创建大量金钱的vip用户
	 */
	void createUserAll();

	Integer findCount();

	Integer findCountByParentId(Integer userId);

	List<Users> findByParentId(Integer userId, Integer pageNo, Integer standard);

	Integer findPassUser(Integer userId);

	Integer findCountByTime(Integer userId, Date start, Date end);

	List<Users> findByTime(Integer userId, Date start, Date end, Integer pageNo, Integer standard);

	Integer findCountByValue(Map<String, Object> map);

	Integer findCountTest(Map<String, Object> map);

	Integer findTotalPerson(Map<String, Object> map);

	List<Users> findByParentId(Integer userId);

	Integer findNoPassUser(Integer userId);

	List<Users> findByNoPass(Map<String, Object> map, Integer pageNo);

	List<Users> findAllPass(Map<String, Object> map);

	List<Users> findByException(Map<String, Object> map, Integer pageNo);

	Integer findCountException(Integer userId);

	List<Users> findHasParent(Double parentProfitRate);

	List<Users> findTen();

	Users findByNiceName(String nickName);

	List<Users> findBySex(Integer sex,Integer number,Integer start); //参数i:性别 0:待确认 1:男 2:女  	//参数j:查询条数
	
	Long accountBySex(Integer sex);

	Users findByRand(Integer sex);

	/**
	 * 随机从数据库中获取一名虚拟用户(id:2000-2246)
	 * @return
	 */
	Users findInventUserByRand();
	
	
	
}

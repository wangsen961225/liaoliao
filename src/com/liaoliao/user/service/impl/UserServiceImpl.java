package com.liaoliao.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.user.dao.UserDao;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired 
	private UserDao userDao;
	
	@Override
	public Users findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public Users findByMobile(String mobile) {
		return userDao.findByMobile(mobile);
	}

	@Override
	public void updateUser(Users user) {
		userDao.updateUser(user);
	}

	@Override
	public void saveUser(Users user) {
		userDao.saveUser(user);
		
	}

	@Override
	public void clearUserDayMoney() {
		userDao.clearUserDayMoney();
	}

	@Override
	public List<Users> findAll(Integer pageNo, Map<String, Object> map) {
		return userDao.findAll(pageNo,map);
	}

	@Override
	public long getInviteCount(Integer userId) {
		return userDao.getInviteCount(userId);
	}

	@Override
	public List<Users> getInviteList(Integer userId,Integer pageNo) {
		return userDao.getInviteList(userId,pageNo);
	}

	
	/**
	 * 根据id删除与该用户关联的所有信息
	 */
	@Override
	public void deleteUserAll(Integer userId) {
		userDao.deleteUserAll(userId);
	}

	/**
	 * 自动创建大量金钱的vip用户
	 */
	@Override
	public void createUserAll() {
		userDao.createUserAll();
	}
	
	@Override
	public Integer findCount() {
		return userDao.findCount();
	}

	@Override
	public Integer findCountByParentId(Integer userId) {
		return userDao.findCountByParentId(userId);
	}

	@Override
	public List<Users> findByParentId(Integer userId, Integer pageNo, Integer standard) {
		return userDao.findByParentId(userId,pageNo,   standard);
	}

	@Override
	public Integer findPassUser(Integer userId) {
		return userDao.findPassUser(userId);
	}
	
	@Override
	public Integer findNoPassUser(Integer userId) {
		return userDao.findNoPassUser(userId);
	}

	@Override
	public Integer findCountByTime(Integer userId, Date start, Date end) {
		return userDao.findCountByTime(userId,start,end);
	}

	@Override
	public List<Users> findByTime(Integer userId, Date start, Date end, Integer pageNo, Integer standard) {
		return userDao.findByTime(userId,start,end,pageNo,  standard);
	}

	@Override
	public Integer findCountByValue(Map<String, Object> map) {
		return userDao.findCountByValue(map);
	}

	@Override
	public Integer findCountTest(Map<String, Object> map) {
		return userDao.findCountTest(map);
	}

	@Override
	public Integer findTotalPerson(Map<String, Object> map) {
		return userDao.findTotalPerson(map);
	}

	@Override
	public List<Users> findByParentId(Integer userId) {
		return userDao.findTotalPerson(userId);
	}

	@Override
	public List<Users> findByNoPass(Map<String, Object> map, Integer pageNo) {
		return userDao.findByNoPass(map,pageNo);
	}

	@Override
	public List<Users> findAllPass(Map<String, Object> map) {
		return userDao.findAllPass(map);
	}

	@Override
	public List<Users> findByException(Map<String, Object> map, Integer pageNo) {
		return userDao.findByException(map,pageNo);
	}

	@Override
	public Integer findCountException(Integer userId) {
		return userDao.findCountException(userId);
	}

	@Override
	public List<Users> findHasParent(Double profit) {
		return userDao.findHasParent(profit);
	}

	@Override
	public List<Users> findTen() {
		return userDao.findTen();
	}

	@Override
	public Users findByNiceName(String nickName) {
		return userDao.findByNiceName(nickName);
	}

	/**
	 * 料友广场随机获取料友
	 */
	@Override
	public List<Users> findBySex(Integer sex,Integer number,Integer start) {
		return userDao.findBySex(sex, number,start);
		
	}

	@Override
	public Long accountBySex(Integer sex) {
		return userDao.accountBySex(sex);
	}

	@Override
	public Users findByRand(Integer sex) {
		return userDao.findByRand(sex);
	}

	@Override
	public Users findInventUserByRand() {
		return userDao.findInventUserByRand();
	}




}

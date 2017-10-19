package com.liaoliao.user.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.user.dao.UserDao;
import com.liaoliao.user.entity.Users;
import com.liaoliao.util.StaticKey;

public class UserDaoImpl extends BaseDaoImpl<Users,Integer> implements UserDao {

	@Override
	public Users findById(Integer id) {
		return this.get(id);
	}

	@Override
	public Users findByMobile(String mobile) {
		String hql="from Users where mobile = ?0";
		return this.getByHQL(hql, mobile);
	}

	@Override
	public void updateUser(Users user) {
		this.update(user);
	}

	@Override
	public void saveUser(Users user) {
		this.save(user);
	}
 
	@Override
	public void clearUserDayMoney() {
		String sql="UPDATE ll_user_info SET day_money = '0'";
		this.querySql(sql);
	}

	@Override
	public List<Users> findAll(Integer pageNo, Map<String, Object> map) {
		String hql="from Users where 1 = 1";
		String countHql="select count(a) from Users a  where 1 = 1 ";
		if(map.get("vipStatus")!=null){
			Integer vipStatus = (Integer) map.get("vipStatus");
			hql+=" and  vipStatus = "+vipStatus;
			countHql+="and  vipStatus = "+vipStatus;
		}
		if(map.get("mobile")!=null){
			String mobile = (String) map.get("mobile");
			hql+=" and  mobile like  '%"+mobile+"%'";
			countHql+="and  mobile like  '%"+mobile+"%'";
		}
		if(map.get("userName")!=null){
			String userName = (String) map.get("userName");
			hql+=" and  nickName like  '%"+userName+"%'";
			countHql+="and  nickName like  '%"+userName+"%'";
		}
		
		
		if(map.get("isActive")!=null && (Integer)map.get("isActive")==0){//非活跃用户
			hql+=" and dayMoney<=0";
			countHql+="and dayMoney<=0";
		}
		if(map.get("isActive")!=null && (Integer)map.get("isActive")==1){ //活跃用户
			hql+=" and dayMoney > 0";
			countHql+="and dayMoney > 0";
		}
		
		
		
		hql+= " and id>10000 order by id desc";
		countHql+= " and id > 10000 ";
	  //String countHql="select count(a) from Users a";
		PageResults<Users> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public long getInviteCount(Integer userId) {
		String hql = "select count(u) from Users u where parent.id = ?0";
		return this.countByHql(hql, userId);
	}

	@Override
	public List<Users> getInviteList(Integer userId,Integer pageNo) {
		String hqlString = "from Users where parent.id = ?0 order by addTime desc";
		String countHql="select count(u) from Users u where parent.id = ?0";
		PageResults<Users> results=this.findPageByFetchedHql(hqlString, countHql, pageNo, StaticKey.PageNum, userId);
		return results.getResults();
	}

	
	/**
	 * 根据id删除与该用户关联的所有信息
	 */
	@Override
	public void deleteUserAll(Integer userId) {
		String sqlString =null;
			sqlString = "DELETE FROM `ll_article_comment` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_video_comment` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_feedback` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_fenrun_log` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_to_bank` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_bind_pay` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_user_sign` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_user_token` WHERE uid = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_user_login` WHERE liao_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_weinxin_pay_log` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_profit_time` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_ban_user` WHERE user_id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sqlString = "DELETE FROM `ll_user_info` WHERE id = "+userId;
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.querySql(sqlString);
	}
	
	
	/**
	 * 自动创建大量金钱的vip用户
	 */
	@Override
	public void createUserAll() {
		for (int i = 1000; i < 1100; i++) {
			String sqlString = "INSERT INTO `ll_user_info` (`id`,`status`,`vip_status`,`source_type`,`total_money`,`pay_money`,`day_money`,`freeze_money`,`to_bank_money`,`add_time`) VALUES ('"+i+"','1','1','9','1000000','0','0','0','0',NOW());";
			this.querySql(sqlString);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer findCount() {
		String hql="select count(s) from Users s";
		return Integer.valueOf(this.countByHql(hql).toString());
	}

	@Override
	public Integer findCountByParentId(Integer userId) {
		String hql="select count(s) from Users s where parent.id = ?0";
		return Integer.valueOf(this.countByHql(hql,userId).toString());
	}

	@Override
	public List<Users> findByParentId(Integer userId, Integer pageNo, Integer standard) {
		if(standard==null||"".equals(standard)){
			String sql="from Users where parent.id = ?0  order by addTime desc";
			String countHql="select count(a) from Users a where parent.id = ?0 ";
			PageResults<Users> results=this.findPageByFetchedHql(sql, countHql, pageNo, 10,userId);
			return results.getResults();
		}
		if(standard==0){
			String sql="from Users where parent.id = ?0 and totalMoney < ?1 order by addTime desc";
			String countHql="select count(a) from Users a where parent.id = ?0 and totalMoney < ?1";
			PageResults<Users> results=this.findPageByFetchedHql(sql, countHql, pageNo, 10,userId,StaticKey.MinPassMoney);
			return results.getResults();
		}
		if(standard==1){
			String sql="from Users where parent.id = ?0 and totalMoney >= ?1 order by addTime desc";
			String countHql="select count(a) from Users a where parent.id = ?0 and totalMoney >= ?1";
			PageResults<Users> results=this.findPageByFetchedHql(sql, countHql, pageNo, 10,userId,StaticKey.MinPassMoney);
			return results.getResults();
		}
		String sql="from Users where parent.id = ?0  order by addTime desc";
		String countHql="select count(a) from Users a where parent.id = ?0 ";
		PageResults<Users> results=this.findPageByFetchedHql(sql, countHql, pageNo, 10,userId);
		return results.getResults();
	}

	@Override
	public Integer findPassUser(Integer userId) {
		String hql="select count(s) from Users s where parent.id = ?0 and totalMoney >= ?1 and (loginTime - addTime >= "+StaticKey.MinPassTime+") ";
		return Integer.valueOf(this.countByHql(hql,userId,StaticKey.MinPassMoney).toString());
	}
	
	@Override
	public Integer findCountByTime(Integer userId, Date start, Date end) {
		String hql="select count(s) from Users s where parent.id = ?0 and loginTime >= ?1 and loginTime < ?2";
		return Integer.valueOf(this.countByHql(hql,userId,start,end).toString());
	}

	@Override
	public List<Users> findByTime(Integer userId, Date start, Date end, Integer pageNo, Integer standard) {
		if(standard==0){
			String hqlString = "from Users where parent.id = ?0 and loginTime > ?1 and loginTime < ?2 and totalMoney < ?3 order by addTime desc";
			String countHql="select count(u) from Users u where parent.id = ?0 and loginTime > ?1 and loginTime < ?2 and totalMoney < ?3";
			PageResults<Users> results=this.findPageByFetchedHql(hqlString, countHql, pageNo, 10, userId,start,end,StaticKey.MinPassMoney);
			return results.getResults();
		}
		if(standard==1){
		String hqlString = "from Users where parent.id = ?0 and loginTime > ?1 and loginTime < ?2 and totalMoney >= ?3 order by addTime desc";
		String countHql="select count(u) from Users u where parent.id = ?0 and loginTime > ?1 and loginTime < ?2 and totalMoney >= ?3";
		PageResults<Users> results=this.findPageByFetchedHql(hqlString, countHql, pageNo, 10, userId,start,end,StaticKey.MinPassMoney);
		return results.getResults();
		}
		
		String hqlString = "from Users where parent.id = ?0 and loginTime > ?1 and loginTime < ?2 order by addTime desc";
		String countHql="select count(u) from Users u where parent.id = ?0 and loginTime > ?1 and loginTime < ?2";
		PageResults<Users> results=this.findPageByFetchedHql(hqlString, countHql, pageNo, 10, userId,start,end);
		return results.getResults();
	}

	@Override
	public Integer findCountByValue(Map<String, Object> map) {
		String countHql="select count(a) from Users a  where 1 = 1 ";
		if(map.get("vipStatus")!=null){
			Integer vipStatus = (Integer) map.get("vipStatus");
			countHql+="and  vipStatus = "+vipStatus;
		}
		if(map.get("mobile")!=null){
			String mobile = (String) map.get("mobile");
			countHql+="and  mobile like  '%"+mobile+"%'";
		}
		if(map.get("userName")!=null){
			String userName = (String) map.get("userName");
			countHql+="and  nickName like  '%"+userName+"%'";
		}
		
		
		if(map.get("isActive")!=null&&(Integer)map.get("isActive")==1){
			countHql+=" and dayMoney > 0";
		}
		if(map.get("isActive")!=null&&(Integer)map.get("isActive")==0){
			countHql+=" and dayMoney <= 0";
		}
		
		
		countHql+=" and id >10000";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public Integer findCountTest(Map<String, Object> map) {
		String countHql="select count(a) from Users a  where 1 = 1 ";
		if(map.get("userId")!=null){
			Integer userId = (Integer) map.get("userId");
			countHql+=" and  parent.id = "+userId;
		}
		if(map.get("start")!=null&&!("".equals(map.get("start")))){
			Date start = (Date) map.get("start");
			Date end = (Date) map.get("end");
			countHql+=" and  loginTime > '"+start+"' and loginTime < '"+end+"'";
		}
		if(map.get("standard")!=null){
			Integer standard = (Integer) map.get("standard");
			if(standard==0){
				countHql+=" and  totalMoney < "+StaticKey.MinPassMoney;
			}else{
				countHql+=" and  totalMoney >= "+StaticKey.MinPassMoney;
			}
		}
		countHql+=" order by id desc";
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public Integer findTotalPerson(Map<String, Object> map) {
		String countHql="select count(a) from Users a  where 1 = 1 ";
		if(map.get("userId")!=null){
			Integer userId = (Integer) map.get("userId");
			countHql+=" and  parent.id = "+userId;
		}
		return Integer.valueOf(this.countByHql(countHql).toString());
	}

	@Override
	public List<Users> findTotalPerson(Integer userId) {
		String hql = "from Users where parent.id = ?0 order by id";
		return this.getListByHQL(hql, userId);
	}
	
	@Override
	public Integer findNoPassUser(Integer userId) {
		String hql="select count(s) from Users s where parent.id = ?0 and (totalMoney < ?1 or (loginTime - addTime < "+StaticKey.MinPassTime+")) ";
		return Integer.valueOf(this.countByHql(hql,userId,StaticKey.MinPassMoney).toString());
	}

	@Override
	public List<Users> findByNoPass(Map<String, Object> map, Integer pageNo) {
		String hql="from Users where parent.id =  "+map.get("userId")+" and (totalMoney < "+StaticKey.MinPassMoney+" or (loginTime - addTime < "+StaticKey.MinPassTime+")) ";
		String countHql="select count(a) from Users a  where parent.id =  "+map.get("userId")+" and (totalMoney < "+StaticKey.MinPassMoney+" or (loginTime - addTime < "+StaticKey.MinPassTime+")) ";
		if(map.get("start")!=null&&!("".equals(map.get("start")))){
			Date start = (Date) map.get("start");
			Date end = (Date) map.get("end");
			hql+=" and  loginTime > '"+start+"' and loginTime < '"+end+"'";
			countHql+=" and  loginTime > '"+start+"' and loginTime < '"+end+"'";
		}
		hql+= " order by id desc";
	  //String countHql="select count(a) from Users a";
		PageResults<Users> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public List<Users> findAllPass(Map<String, Object> map) {
		String hql="from Users where parent.id = "+map.get("userId")+" and  totalMoney >= "+StaticKey.MinPassMoney+" and (loginTime - addTime >= "+StaticKey.MinPassTime+") order by id desc";
		return this.getListByHQL(hql);
	}

	@Override
	public List<Users> findByException(Map<String, Object> map, Integer pageNo) {
		String hql="from Users where parent.id =  ?0 and  status = ?1";
		String countHql="select count(a) from Users a  where parent.id = ?0 and  status= ?1";
		if(map.get("start")!=null&&!("".equals(map.get("start")))){
			Date start = (Date) map.get("start");
			Date end = (Date) map.get("end");
			hql+=" and  loginTime > ?2 and loginTime < ?3 order by id desc";
			countHql+=" and  loginTime > ?2 and loginTime < ?3";
			PageResults<Users> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10,map.get("userId"),StaticKey.UserStatusException,start,end);
			return results.getResults();
		}else{
			PageResults<Users> results=this.findPageByFetchedHql(hql, countHql, pageNo,10,map.get("userId"),StaticKey.UserStatusException);
			return results.getResults();
		}
	}

	@Override
	public Integer findCountException(Integer userId) {
		String countHql="select count(a) from Users a  where parent.id = ?0 and  status= ?1";
		return Integer.valueOf(this.countByHql(countHql,userId,StaticKey.UserStatusException).toString());
	}

	@Override
	public List<Users> findHasParent(Double profit) {
		String hql="from Users where parent is not null and dayMoney >= ?0";
		return this.getListByHQL(hql,profit);
	}

	@Override
	public List<Users> findTen() {
		String hqlString = "from Users where id >10000 order by totalMoney desc";
		String countHql="select count(u) from Users u where id >10000 ";
		PageResults<Users> results=this.findPageByFetchedHql(hqlString, countHql, 1, 10);
		return results.getResults();
	}

	@Override
	public Users findByNiceName(String nickName) {
		String hql="from Users where nickName = ?0 and id>10000";
		return this.getByHQL(hql, nickName);
	}

	@Override
	public List<Users> findBySex(Integer sex,Integer number,Integer start) {
		
		String hql = "from Users where sex = ? and id>=2000 and id <= 2246";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0,sex);
		query.setFirstResult(start); 
		query.setMaxResults(number);
		return query.list();
	}

	@Override
	public Long accountBySex(Integer sex) {
		String hql = "select count(u) from Users u where sex = ?0 and id>10000";
		Long countByHql = this.countByHql(hql, sex);
		return countByHql;
	}

	@Override
	public Users findByRand(Integer sex) {
	//	String sql ="SELECT * from ll_user_info where `id`>2000 and sex=?0 ORDER BY RAND() LIMIT 1";
			String sql ="SELECT * from ll_user_info where sex=?0 ORDER BY RAND() LIMIT 1";
		return this.getBySQL(sql,sex);
	}

	@Override
	public Users findInventUserByRand() {
		String sql ="SELECT * from ll_user_info where id between 2000 and 2246 ORDER BY RAND() LIMIT 1";
		return this.getBySQL(sql);
	}


	

} 


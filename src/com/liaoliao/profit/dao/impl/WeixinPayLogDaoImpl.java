package com.liaoliao.profit.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.profit.dao.WeixinPayLogDao;
import com.liaoliao.profit.entity.WeixinPayLog;
import com.liaoliao.user.entity.Users;

import cn.jpush.api.report.UsersResult.User;

public class WeixinPayLogDaoImpl extends BaseDaoImpl<WeixinPayLog,Integer> implements WeixinPayLogDao {

	@Override
	public void saveWeixinPayLog(WeixinPayLog wpl) {
		this.save(wpl);
	}

	@Override
	public WeixinPayLog findByOutTradNo(String out_trade_no) {
		String hql="from WeixinPayLog where outTradeNo = ?0";
		return this.getByHQL(hql, out_trade_no);
	}

	@Override
	public void updateWeixinPayLog(WeixinPayLog wpl) {
		this.update(wpl);
	}

	@Override
	public List<WeixinPayLog> findAll(Integer pageNo) {
		String hql="from WeixinPayLog";
		String countHql="select count(a) from WeixinPayLog a";
		PageResults<WeixinPayLog> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public Integer findCount() {
		String hql="select count(s) from WeixinPayLog s";
		return Integer.valueOf(this.countByHql(hql).toString());
	}

	@Override
	public List<WeixinPayLog> queryByBodyAndUser(String body, Users user) {
		String hql="from WeixinPayLog where body=?0 and user=?1 and status=0";
		return this.getListByHQL(hql, body,user);
	}

	@Override
	public List<WeixinPayLog> findAllByUser(Users users) {
		String hql="from WeixinPayLog where  user=?0 and body='充值' ORDER BY addTime desc";
		return this.getListByHQL(hql,users);
	}

	@Override
	public WeixinPayLog queryOne(Integer id) {
		return this.get(id);
	}

	@Override
	public WeixinPayLog queryOneByOrderNum(String id) {
		String hql="from WeixinPayLog where  outTradeNo=?0";
		return this.getByHQL(hql, id);
	}

	@Override
	public List<WeixinPayLog> findAllByUserAndType(Users users){
		String hql="from WeixinPayLog where  user=?0 and body='充值' and status=0 ORDER BY addTime desc";
		return this.getListByHQL(hql,users);
	}
	
}

package com.liaoliao.sys.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.sys.dao.OriginalProfitLogDao;
import com.liaoliao.sys.entity.OriginalProfitLog;
import com.liaoliao.util.StaticKey;

public class OriginalProfitLogDaoImpl extends BaseDaoImpl<OriginalProfitLog,Integer> implements OriginalProfitLogDao {


	@Override
	public void saveOriginalProfitLog(OriginalProfitLog opl) {
		this.save(opl);
	}

	@Override
	public OriginalProfitLog findLastData() {
		return null;
	}

	@Override
	public List<OriginalProfitLog> findData() {
	//	Long date = new Date(new Date().getTime()-StaticKey.sumOriginalMoney).getTime();
		
		Date da = new Date(new Date().getTime()-StaticKey.sumOriginalMoney);
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		String sql="SELECT id,sum(money) as money,user_id as userId,content_id as contentId,type from ll_original_profit_log where add_time >='"+sdf.format(da)+"' GROUP BY content_id";
		System.out.println(sql);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		//设定结果结果集中的每个对象为Map类型   
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List list =query.list();
		System.out.println(list);
		return list;
	}

	@Override
	public List<OriginalProfitLog> findOriginalLogByUserId(Integer userId, Integer pageNo) {
		String hql="from OriginalProfitLog where user.id = ?0 order by addTime desc";
		String countHql="select count(a) from OriginalProfitLog a where user.id = ?0 ";
		PageResults<OriginalProfitLog> results=this.findPageByFetchedHql(hql, countHql, pageNo, StaticKey.PageNum, userId);
		return results.getResults();
	}

}

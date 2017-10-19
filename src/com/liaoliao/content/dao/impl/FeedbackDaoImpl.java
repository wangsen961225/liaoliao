package com.liaoliao.content.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.basedao.PageResults;
import com.liaoliao.content.dao.FeedbackDao;
import com.liaoliao.content.entity.Feedback;
import com.liaoliao.profit.entity.ToBank;

public class FeedbackDaoImpl extends BaseDaoImpl<Feedback,Integer> implements FeedbackDao {

	@Override
	public void saveFeedback(Feedback feedback) {
		this.save(feedback);
	}

	@Override
	public Integer findCount() {
		String hql="select count(f) from Feedback f";
		return Integer.valueOf(this.countByHql(hql).toString());
	}

	@Override
	public List<Feedback> findAll(Integer pageNo) {
		String hql="from Feedback";
		String countHql="select count(a) from Feedback a";
		PageResults<Feedback> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
		return results.getResults();
	}

	@Override
	public Integer findCountByStatus(Integer status) {
		if(status==0){
			String hql="select count(f) from Feedback f";
			return Integer.valueOf(this.countByHql(hql).toString());
		}else{
			String hql="select count(f) from Feedback f where f.status = ?0";
			Long aa=this.countByHql(hql,status);
			return Integer.valueOf(aa.toString());
		}
	}

	@Override
	public List<Feedback> findAllByStatus(Integer pageNo, Integer status) {
		if(status==0){
			String hql="from Feedback";
			String countHql="select count(a) from Feedback a";
			PageResults<Feedback> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10);
			return results.getResults();
		}else{
			String hql="from Feedback where status = ?0";
			String countHql="select count(a) from Feedback a where status = ?0";
			PageResults<Feedback> results=this.findPageByFetchedHql(hql, countHql, pageNo, 10,status);
			return results.getResults();
		}
		
	}

	@Override
	public Feedback findById(Integer id) {
		return this.get(id);
	}

	@Override
	public void updateFB(Feedback fd) {
		this.update(fd);
	}

	@Override
	public List<Feedback> findByUserId(Integer userId) {
		String hql = "from Feedback where user.id=?0";
		return this.getListByHQL(hql, userId);
	}

}

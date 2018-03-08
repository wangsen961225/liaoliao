package com.liaoliao.palyTour.dao.impl;


import java.util.List;


import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.palyTour.dao.PlayHistoryDao;
import com.liaoliao.palyTour.entity.PlayHistory;

public class PlayHistoryDaoImpl extends BaseDaoImpl<PlayHistory,Integer> implements PlayHistoryDao {

	@Override
	public void add(PlayHistory modle) {
		
		this.save(modle);
	}

	@Override
	public PlayHistory revise(PlayHistory modle) {
		return null;
	}

	@Override
	public PlayHistory del(PlayHistory modle) {
		
		return null;
	}


	@Override
	public List<PlayHistory> queryAllRederId(Integer rederId) {
		String hql="from PlayHistory where rederId=?0 order by playDate desc";
		return this.getListByHQL(hql,rederId);
	}
	public List<PlayHistory> queryAllAuthorId(Integer authorId) {
		String hql="from PlayHistory where authorId=?0 order by playDate desc";
		return this.getListByHQL(hql,authorId);
	}

	@Override
	public Integer findCount(Integer articleId) {
		String countHql="select count(a) as nums from PlayHistory a where articleId=?0";
		return Integer.valueOf(this.countByHql(countHql,articleId).toString());
	}

	@Override
	public List<PlayHistory> findSums(Integer articleId) {
		String countHql="select sum(money) as sums from PlayHistory a where articleId=?0";
		return this.getListByHQL(countHql, articleId);
	}

	@Override
	public PlayHistory queryOne(Integer articleId, Integer type, Integer reader) {
		String hql="from PlayHistory where articleId=?0 and type=?1 and rederId=?2";
		return this.getByHQL(hql, articleId,type,reader);
	}
	
	
}

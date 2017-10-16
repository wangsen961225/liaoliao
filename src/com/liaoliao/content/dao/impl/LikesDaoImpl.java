package com.liaoliao.content.dao.impl;

import java.util.List;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.LikesDao;
import com.liaoliao.content.entity.Likes;

public class LikesDaoImpl extends BaseDaoImpl<Likes,Integer> implements LikesDao {

	@Override
	public void saveLikes(Likes li) {
		this.save(li);
	}
	

	@Override
	public List<Likes> findLikesById(Integer userId, Integer type) {
		String hql="from Likes where user.id = ?0 and type=?1";
		return this.getListByHQL(hql, userId,type);
	}

}

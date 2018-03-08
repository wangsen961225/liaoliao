package com.liaoliao.content.dao.impl;

import java.util.List;


import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.ReptileArticleDao;
import com.liaoliao.content.entity.ReptileArticle;

public class ReptileArticleDaoImpl extends BaseDaoImpl<ReptileArticle,Integer> implements ReptileArticleDao {

	@Override
	public List<ReptileArticle> list() {
		String hql="from ReptileArticle ";
		return this.getListByHQL(hql);
	}

	@Override
	public void updaeMoble(ReptileArticle moble) {
		this.update(moble);
	}
	
	


	

}

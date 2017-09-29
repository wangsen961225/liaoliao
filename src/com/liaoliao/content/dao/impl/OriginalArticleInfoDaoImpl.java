package com.liaoliao.content.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.OriginalArticleInfoDao;
import com.liaoliao.content.entity.OriginalArticleInfo;

public class OriginalArticleInfoDaoImpl extends BaseDaoImpl<OriginalArticleInfo,Integer> implements OriginalArticleInfoDao {

	@Override
	public void saveOAI(OriginalArticleInfo oai) {
		this.save(oai);
	}

	@Override
	public OriginalArticleInfo findByArticleId(Integer id) {
		String hql="from OriginalArticleInfo where article.id = ?0";
		return this.getByHQL(hql, id);
	}

	@Override
	public void updateOAI(OriginalArticleInfo oai) {
		this.saveOrUpdate(oai);
	}

}

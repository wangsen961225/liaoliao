package com.liaoliao.content.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.OriginalVideoInfoDao;
import com.liaoliao.content.entity.OriginalVideoInfo;

public class OriginalVideoInfoDaoImpl extends BaseDaoImpl<OriginalVideoInfo,Integer> implements OriginalVideoInfoDao {

	@Override
	public void saveOVI(OriginalVideoInfo ovi) {
		this.save(ovi);
	}

	@Override
	public OriginalVideoInfo findByVideoId(Integer id) {
		String hql="from OriginalVideoInfo where video.id = ?0";
		return this.getByHQL(hql, id);
	}

	@Override
	public void updateOVI(OriginalVideoInfo ovi) {
		this.update(ovi);
	}

	@Override
	public void updateOrSaveOVI(OriginalVideoInfo ovi) {
		this.saveOrUpdate(ovi);
	}

}

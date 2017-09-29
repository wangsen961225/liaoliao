package com.liaoliao.content.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.content.dao.ContentKindDao;
import com.liaoliao.content.entity.ContentKind;

public class ContentKindDaoImpl extends BaseDaoImpl<ContentKind,Long> implements ContentKindDao {

	@Override
	public ContentKind findByName(String name) {
		String hql="from ContentKind where kindName = ?0";
		return this.getByHQL(hql, name);
	}
	

}

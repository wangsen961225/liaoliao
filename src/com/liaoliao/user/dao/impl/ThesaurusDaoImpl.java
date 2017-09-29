package com.liaoliao.user.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.user.dao.ThesaurusDao;
import com.liaoliao.user.entity.Thesaurus;

public class ThesaurusDaoImpl extends BaseDaoImpl<Thesaurus,Integer> implements ThesaurusDao {

	@Override
	public void saveThesaurus(Thesaurus ths) {
		this.save(ths);
	}

}

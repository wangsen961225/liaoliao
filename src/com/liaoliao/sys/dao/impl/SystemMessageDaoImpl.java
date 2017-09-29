package com.liaoliao.sys.dao.impl;

import com.liaoliao.basedao.BaseDaoImpl;
import com.liaoliao.sys.dao.SystemMessageDao;
import com.liaoliao.sys.entity.SystemMessage;

public class SystemMessageDaoImpl extends BaseDaoImpl<SystemMessage, Integer> implements SystemMessageDao {

	@Override
	public void saveMsg(SystemMessage sm) {
		this.save(sm);
	}

}

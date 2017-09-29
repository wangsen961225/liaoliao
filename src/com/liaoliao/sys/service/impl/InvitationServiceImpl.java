package com.liaoliao.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liaoliao.sys.dao.InvitationDao;
import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.sys.service.InvitationService;

@Service("invitationService")
@Transactional
public class InvitationServiceImpl implements InvitationService {
	
	@Autowired
	private InvitationDao invitationDao;

	@Override
	public List<Invitation> findAllNoDeal(Integer inviteChildMinMoney) {
		return invitationDao.findAllNoDeal(inviteChildMinMoney);
	}

	@Override
	public void saveInvitation(Invitation invitation) {
		invitationDao.saveInvitation(invitation);
	}

	@Override
	public List<Invitation> getInviteList(Integer userId, Integer pageNo) {
		return invitationDao.getInviteList(userId, pageNo);
	}

	@Override
	public void updataInvitation(Invitation in) {
		invitationDao.updataInvitation(in);
	}

	@Override
	public Long getInviteCount(Integer userId) {
		return invitationDao.getInviteCount(userId);
	}

}

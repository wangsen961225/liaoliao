package com.liaoliao.sys.service;

import java.util.List;

import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.user.entity.Users;

public interface InvitationService {

	List<Invitation> findAllNoDeal(Integer inviteChildMinMoney);
	
	void saveInvitation(Invitation invitation);

	List<Invitation> getInviteList(Integer userId, Integer pageNo);

	void updataInvitation(Invitation in);

	Long getInviteCount(Integer userId);


}

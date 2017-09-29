package com.liaoliao.sys.dao;

import java.util.List;

import com.liaoliao.sys.entity.Invitation;

public interface InvitationDao {

	List<Invitation> findAllNoDeal(Integer inviteChildMinMoney);

	void saveInvitation(Invitation invitation);

	List<Invitation> getInviteList(Integer userId, Integer pageNo);

	void updataInvitation(Invitation in);

	Long getInviteCount(Integer userId);

}

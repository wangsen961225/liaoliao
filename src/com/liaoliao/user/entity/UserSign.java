package com.liaoliao.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 用户签到表
 * @author vio
 *
 */
@Entity
@Table(name="ll_user_sign")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class UserSign implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Id
	private Integer userId;
	/**
	 * 签到时间
	 */
	private Date signTime;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public UserSign(Integer userId, Date signTime) {
		super();
		this.userId = userId;
		this.signTime = signTime;
	}
	public UserSign() {
		super();
	}
	@Override
	public String toString() {
		return "UserSign [userId=" + userId + ", signTime=" + signTime + "]";
	}

	
	
	

}
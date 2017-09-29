package com.liaoliao.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ll_ban_user")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class BanUser implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 料料用户
	 */
	@JoinColumn(name = "user_id")
	@OneToOne
	private Users user;
	/**
	 * 被查封次数
	 */
	private Integer banTimes;
	/**
	 * 封禁原因
	 */
	private String banCause;
	/**
	 * 封禁状态
	 */
	private Integer userStatus;
	/**
	 * 添加、更新时间
	 */
	private Date addTime;
	/**
	 * 处理时间
	 */
	private Date dealTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Integer getBanTimes() {
		return banTimes;
	}
	public void setBanTimes(Integer banTimes) {
		this.banTimes = banTimes;
	}
	public String getBanCause() {
		return banCause;
	}
	public void setBanCause(String banCause) {
		this.banCause = banCause;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	
	




}














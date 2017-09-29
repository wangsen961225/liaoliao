package com.liaoliao.profit.entity;

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
import com.liaoliao.user.entity.Users;

@Entity
@Table(name="ll_bind_pay")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class BindPay implements Serializable{
	
	
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
	 * 姓名
	 */
	private String trueName;
	
	/**
	 * 提现账号
	 */
	private String payAccount;
	
	/**
	 * 时间
	 */
	private Date addTime;
	
	/**
	 * ip
	 */
	private String addIp;

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

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}



}














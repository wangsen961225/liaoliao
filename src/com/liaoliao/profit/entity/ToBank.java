package com.liaoliao.profit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoliao.user.entity.Users;

@Entity
@Table(name="ll_to_bank")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class ToBank implements Serializable{
	
	
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
	 * 提现金钱数
	 */
	private Integer toBankMoney;
	
	/**
	 * 提现料币数
	 */
	private Integer toBankCoin;
	
	/**
	 * 提现状态
	 */
	private Integer toBankStatus;
	
	/**
	 * 时间
	 */
	private Date addTime;
	
	/**
	 * 支付宝
	 */
	
	@JoinColumn(name="bind_pay_id")
	@ManyToOne
	private BindPay bindPay;
	
	/**
	 * ip
	 */
	private String addIp;

	/**
	 * 时间
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

	public Integer getToBankMoney() {
		return toBankMoney;
	}

	public void setToBankMoney(Integer toBankMoney) {
		this.toBankMoney = toBankMoney;
	}

	public Integer getToBankCoin() {
		return toBankCoin;
	}

	public void setToBankCoin(Integer toBankCoin) {
		this.toBankCoin = toBankCoin;
	}

	public Integer getToBankStatus() {
		return toBankStatus;
	}

	public void setToBankStatus(Integer toBankStatus) {
		this.toBankStatus = toBankStatus;
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

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public BindPay getBindPay() {
		return bindPay;
	}

	public void setBindPay(BindPay bindPay) {
		this.bindPay = bindPay;
	}


}














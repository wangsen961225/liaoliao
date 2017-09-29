package com.liaoliao.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ll_user_login")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class UserLogin implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 料料用户
	 */
	@JoinColumn(name = "liao_id")
	@OneToOne
	private Users user;
	/**
	 * 第三方id
	 */
	private String openId;
	
	/**
	 * 登录来源类型
	 */
	private Integer sourceType;

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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", user=" + user + ", openId=" + openId + ", sourceType=" + sourceType + "]";
	}








}














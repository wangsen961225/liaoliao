package com.liaoliao.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 用户令牌表
 * @author vio
 *
 */
@Entity
@Table(name="ll_user_token")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class UserToken implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Id
	private Integer uid;
	/**
	 * 用户token
	 */
	private String token;

	public UserToken(){
		super();
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Token [uid=" + uid + ", token=" + token + "]";
	}

	public UserToken(Integer uid, String token) {
		super();
		this.uid = uid;
		this.token = token;
	}
	
	

}
package com.liaoliao.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liaoliao.user.entity.Users;

@Entity
@Table(name="ll_count_clicks")
public class AdvertClicks implements Serializable{
	
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private Date addTime;

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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public AdvertClicks(Integer id, Users user, Date addTime) {
		super();
		this.id = id;
		this.user = user;
		this.addTime = addTime;
	}
	
	public AdvertClicks() {
		super();
	}
	
	
}

package com.liaoliao.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ll_focus_log")
public class FocusLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;  //用户
	
	
	@ManyToOne
	@JoinColumn(name="focus_id")
	private Users focusUser; //该用户关注的人
	
	private Integer status;//0:取消关注    1：正常
	
	private Date addTime;
	
	
	private Date endTime;

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

	public Users getFocusUser() {
		return focusUser;
	}

	public void setFocusUser(Users focusUser) {
		this.focusUser = focusUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "FocusLog [id=" + id + ", user=" + user + ", focusUser=" + focusUser + ", status=" + status
				+ ", addTime=" + addTime + "]";
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}

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
@Table(name="ll_task_log")
public class TaskLog  implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name="user_task_id")
	private UserTask userTask;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private Integer status; // 0:未领取任务  1:已领取任务 2:已完成
	
	private Date finishTime;
	
	private Integer obtain;//是否已领取  0：未领取，1：领取

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserTask getUserTask() {
		return userTask;
	}

	public void setUserTask(UserTask userTask) {
		this.userTask = userTask;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "TaskLog [id=" + id + ", userTask=" + userTask + ", user=" + user + ", status=" + status
				+ ", finishTime=" + finishTime + "]";
	}

	public Integer getObtain() {
		return obtain;
	}

	public void setObtain(Integer obtain) {
		this.obtain = obtain;
	}

	
}

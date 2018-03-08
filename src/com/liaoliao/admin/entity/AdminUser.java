package com.liaoliao.admin.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/***
 * 数据库的封装类
 * @author Administrator
 *
 */
@Entity
@Table(name="ll_admin_user")
public class AdminUser implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue
	private Integer id;
	
	private String userName;
	
	private String passWord;
	
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private AdminGroup adminGroup;
	
	private Date addTime;
	
	
	public AdminUser(){
		super();
	}


	public AdminUser(Integer id, String userName, String passWord, Integer status, AdminGroup adminGroup,
			Date addTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.status = status;
		this.adminGroup = adminGroup;
		this.addTime = addTime;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public AdminGroup getAdminGroup() {
		return adminGroup;
	}


	public void setAdminGroup(AdminGroup adminGroup) {
		this.adminGroup = adminGroup;
	}


	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	@Override
	public String toString() {
		return "AdminUser [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", status=" + status
				+ ", adminGroup=" + adminGroup + ", addTime=" + addTime + "]";
	}


	
}

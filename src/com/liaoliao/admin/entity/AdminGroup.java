package com.liaoliao.admin.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ll_admin_group")
public class AdminGroup implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue
	private Integer id;
	
	private String groupName;
	
	private String info;
	
	private Integer status;
	
	private Date addTime;
	
	public AdminGroup(){
		super();
		}

	public AdminGroup(String groupName, String info, Integer status, Date addTime) {
		super();
		this.groupName = groupName;
		this.info = info;
		this.status = status;
		this.addTime = addTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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
		return "AdminGroup [id=" + id + ", groupName=" + groupName + ", info=" + info + ", status=" + status
				+ ", addTime=" + addTime + "]";
	}


}

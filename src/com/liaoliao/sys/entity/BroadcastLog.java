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
@Table(name="ll_broadcast_log")
public class BroadcastLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private String content;
	
	private Double price;
	
	private Integer status;//检测是否有非法用词;0:有   1：没有
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "BroadcastLog [id=" + id + ", user=" + user + ", content=" + content + ", price=" + price + ", addTime="
				+ addTime + "]";
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}

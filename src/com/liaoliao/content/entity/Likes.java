package com.liaoliao.content.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoliao.user.entity.Users;

/**
 * 用户反馈表
 * @author 
 *
 */
@Entity
@Table(name="ll_user_like")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class Likes implements Serializable{
	
	
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 用户id
	 */
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	/**
	 * 文章id
	 */
	private Integer contentId;
	
	
	/**
	 * 给文章或视频点赞
	 */
	private Integer type;//0: 文章  1:视频
	
	/**
	 * 添加时间
	 */
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

	/**
	 * 15465465
	 * @return abd
	 */
	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Likes() {
		super();
	}

	public Likes(Integer id, Users user, Integer contentId, Integer type, Date addTime) {
		super();
		this.id = id;
		this.user = user;
		this.contentId = contentId;
		this.type = type;
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "Likes [id=" + id + ", user=" + user + ", contentId=" + contentId + ", type=" + type + ", addTime="
				+ addTime + "]";
	}
}














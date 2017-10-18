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
 * @author vio
 *
 */
@Entity
@Table(name="ll_feedback")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class Feedback implements Serializable{
	
	
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
	
	private String word;
	
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 添加时间
	 */
	private Date addTime;
	
	/**
	 * 反馈留言状态
	 */
	private Integer status;//1、沟通中   2、未采纳  3、采纳奖励未发放   4、采纳并已发放奖励
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", user=" + user + ", content=" + content + ", addTime=" + addTime + ", status="
				+ status + "]";
	}

	public Feedback(){
		super();
	}
	public Feedback(Integer id, Users user, String content, Date addTime, Integer status) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.addTime = addTime;
		this.status = status;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
}














package com.liaoliao.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ll_read_history")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class ReadHistory implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Integer userId;
	
	private Integer num;
	
	private Double money;
	
	private  Integer articleId;
	
	private  Integer type;  //1:视频 0:文章
	
	private Date addDate;
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public ReadHistory(){
		super();
	}
	
	public ReadHistory(String id, Integer userId, Integer articleId, Integer type, Date addDate, Integer num) {
		super();
		this.id = id;
		this.userId = userId;
		this.articleId = articleId;
		this.type = type;
		this.addDate = addDate;
		this.num = num;
	}
	public ReadHistory(String id, Integer userId, Integer articleId, Integer type, Date addDate, Integer num,Double money) {
		super();
		this.id = id;
		this.userId = userId;
		this.articleId = articleId;
		this.type = type;
		this.addDate = addDate;
		this.num = num;
		this.money=money;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
 


}














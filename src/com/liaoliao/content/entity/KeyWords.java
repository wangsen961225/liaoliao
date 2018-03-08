package com.liaoliao.content.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="ll_keywords")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
public class KeyWords implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	public KeyWords(){
		super();
	}
	public KeyWords(String name, Date addDate, Integer freq) {
		super();
		this.name = name;
		this.addDate = addDate;
		this.freq = freq;
	}
	
	
	public KeyWords(String id, String name, Integer userId, Double score, Double idf, Date addDate, Integer freq) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.score = score;
		this.idf = idf;
		this.addDate = addDate;
		this.freq = freq;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getIdf() {
		return idf;
	}

	public void setIdf(Double idf) {
		this.idf = idf;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	private String name;
	
	private Integer userId;
	
	private Double score;
	
	private Double idf;
	
	private Date addDate;
	
	private Integer freq;
	
	


	
}

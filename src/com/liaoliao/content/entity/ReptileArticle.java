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
@Table(name="article")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
public class ReptileArticle implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private String id;
	
	private String newsType;
	
	private String title;
	
	private String detail;
	
	private Integer createTime;
	
	private Long hashCode;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	private Integer hasProcessed;
	
	private String label;
	
	private Integer type;//-1已加入到ll_article null或0表示未加入
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}


	public Long getHashCode() {
		return hashCode;
	}

	public void setHashCode(Long hashCode) {
		this.hashCode = hashCode;
	}

	public Integer getHasProcessed() {
		return hasProcessed;
	}

	public void setHasProcessed(Integer hasProcessed) {
		this.hasProcessed = hasProcessed;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	
	
	
}

package com.liaoliao.content.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ll_original_article_info")
public class OriginalArticleInfo  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name="article_id")
	private Article article;
	
	private Integer shareCount;
	
	private Integer readCount;
	
	private Integer countMoney;
	
	private Date passTime;
	
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getCountMoney() {
		return countMoney;
	}

	public void setCountMoney(Integer countMoney) {
		this.countMoney = countMoney;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	@Override
	public String toString() {
		return "OriginalArticleInfo [id=" + id + ", article=" + article + ", shareCount=" + shareCount + ", readCount="
				+ readCount + ", countMoney=" + countMoney + ", passTime=" + passTime + "]";
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	

}

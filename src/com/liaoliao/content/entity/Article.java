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
@Table(name="ll_article")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
public class Article implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String title;
	
	private String description;
	
	private String content;
	
	private String imgUrl;
	
	private String imgList;
	
	private Integer status;//状态：0：封禁  1：正常  2：待审核 100:推荐
	
	private Integer type;//是否原创：0:非原创   1:原创
	
	@OneToOne
	@JoinColumn(name="kind_id")
	private  ContentKind contentKind;

	private String keyId;
	
	private Integer readingCount;
	
	private Integer likingCount;//点赞数
	
	private Integer sendingCount;//转发数
	
	private Integer commentCount;
	
	private Integer sourceId;
	
	private String sourceUrl;
	
	private Date addTime;
	
	private Double playMoneySum;
	
	public Double getPlayMoneySum() {
		return playMoneySum;
	}

	public void setPlayMoneySum(Double playMoneySum) {
		this.playMoneySum = playMoneySum;
	}

	public Article(){
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgList() {
		return imgList;
	}

	public void setImgList(String imgList) {
		this.imgList = imgList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ContentKind getContentKind() {
		return contentKind;
	}

	public void setContentKind(ContentKind contentKind) {
		this.contentKind = contentKind;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Integer getReadingCount() {
		return readingCount;
	}

	public void setReadingCount(Integer readingCount) {
		this.readingCount = readingCount;
	}

	public Integer getLikingCount() {
		return likingCount;
	}

	public void setLikingCount(Integer likingCount) {
		this.likingCount = likingCount;
	}

	public Integer getSendingCount() {
		return sendingCount;
	}

	public void setSendingCount(Integer sendingCount) {
		this.sendingCount = sendingCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", imgUrl=" + imgUrl + ", imgList=" + imgList + ", status=" + status + ", contentKind=" + contentKind
				+ ", keyId=" + keyId + ", readingCount=" + readingCount + ", likingCount=" + likingCount
				+ ", sendingCount=" + sendingCount + ", commentCount=" + commentCount + ", sourceId=" + sourceId
				+ ", sourceUrl=" + sourceUrl + ", addTime=" + addTime + "]";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	
}

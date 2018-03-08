package com.liaoliao.content.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="ll_video")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
public class Video implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String title;
	
	private String description;
	
	private String duration;
	
	private String videoUrl;
	
	private String imgUrl;
	
	private Integer status;//状态：0：封禁  1：正常  2：待审核  100:推荐
	
	private Integer type;//是否原创：0:非原创   1:原创
	
	private String keyId;
	
	private Integer playingCount;
	
	private Integer likingCount;
	
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


	public Video(){
		super();
	}

	
	public Video(String title, String description, String duration, String videoUrl, String imgUrl, Integer status,Integer type,
			String keyId, Integer playCount, Integer likingCount, Integer sendingCount, Integer commentCount,
			Integer sourceId, String sourceUrl, Date addTime) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.videoUrl = videoUrl;
		this.imgUrl = imgUrl;
		this.status = status;
		this.keyId = keyId;
		this.playingCount = playCount;
		this.likingCount = likingCount;
		this.sendingCount = sendingCount;
		this.commentCount = commentCount;
		this.sourceId = sourceId;
		this.sourceUrl = sourceUrl;
		this.addTime = addTime;
		this.type=type;
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


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getVideoUrl() {
		return videoUrl;
	}


	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getKeyId() {
		return keyId;
	}


	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}


	public Integer getPlayingCount() {
		return playingCount;
	}


	public void setPlayingCount(Integer playingCount) {
		this.playingCount = playingCount;
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


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}





	
	
}

package com.liaoliao.content.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.liaoliao.user.entity.Users;

@Entity
@Table(name="ll_video_comment")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
public class VideoComment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="video_id")
	private Video video;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private String userName;
	
	private String avatarUrl;
	
	private Integer likingCount;
	
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getLikingCount() {
		return likingCount;
	}

	public void setLikingCount(Integer likingCount) {
		this.likingCount = likingCount;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "VideoComment [id=" + id + ", video=" + video + ", content=" + content + ", user=" + user + ", userName="
				+ userName + ", avatarUrl=" + avatarUrl + ", likingCount=" + likingCount + ", addTime=" + addTime + "]";
	}




}

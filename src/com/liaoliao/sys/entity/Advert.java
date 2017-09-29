package com.liaoliao.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 广告表
 * @author vio
 *
 */
@Entity
@Table(name="ll_advert")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class Advert implements Serializable{
	  
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 广告名称
	 */
	private String sourceName;
	/**
	 * 广告类型
	 */
	private Integer type;
	/**
	 * 广告内容
	 */
	private String content;
	/**
	 * 广告位置
	 */
	private String position;
	/**
	 * 广告排序
	 */
	private Integer sort;
	/**
	 * 广告状态
	 */
	private Integer status;
	/**
	 * 添加时间
	 */
	private Date addTime;

	
	/**
	 * get&set
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	

	
	public Advert(Integer id, String sourceName, Integer type, String content, String position, Integer sort,
			Integer status, Date addTime) {
		super();
		this.id = id;
		this.sourceName = sourceName;
		this.type = type;
		this.content = content;
		this.position = position;
		this.sort = sort;
		this.status = status;
		this.addTime = addTime;
	}
	@Override
	public String toString() {
		return "Advert [id=" + id + ", sourceName=" + sourceName + ", type=" + type + ", content=" + content
				+ ", position=" + position + ", sort=" + sort + ", status=" + status + ", addTime=" + addTime + "]";
	}
	public Advert() {
		super();
	}

	



}














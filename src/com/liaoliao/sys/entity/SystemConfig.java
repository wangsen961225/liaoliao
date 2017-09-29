package com.liaoliao.sys.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ll_system_config")
public class SystemConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String keyName;
	
	private String content;
	
	private String keyTitle;

	private Integer sort;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyTitle() {
		return keyTitle;
	}

	public void setKeyTitle(String keyTitle) {
		this.keyTitle = keyTitle;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}



	

	
}

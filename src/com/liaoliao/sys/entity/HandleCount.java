package com.liaoliao.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ll_handle_count")
public class HandleCount implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String clickName;
	
	private String clickCount;
	
	private Date clickDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClickName() {
		return clickName;
	}

	public void setClickName(String clickName) {
		this.clickName = clickName;
	}

	public String getClickCount() {
		return clickCount;
	}

	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}

	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}


	
	




}

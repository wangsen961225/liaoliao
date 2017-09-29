package com.liaoliao.admin.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ll_permission")
public class Permission implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private AdminGroup adminGroup;
	
	@OneToOne
	@JoinColumn(name="navigation_id")
	private Navigation navigation;
	
	public Permission(){
		super();
	}

	public Permission(Integer id, AdminGroup adminGroup, Navigation navigation) {
		super();
		this.id = id;
		this.adminGroup = adminGroup;
		this.navigation = navigation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdminGroup getAdminGroup() {
		return adminGroup;
	}

	public void setAdminGroup(AdminGroup adminGroup) {
		this.adminGroup = adminGroup;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	@Override
	public String toString() {
		return "permission [id=" + id + ", adminGroup=" + adminGroup + ", navigation=" + navigation + "]";
	}
	
	
	
	

}

package com.liaoliao.admin.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ll_navigation")
public class Navigation  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String navigationName;
	
	private String navigationUrl;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Navigation navigation;
	
	private Integer sort; //排序

	public Navigation(){
		super();
	}
	
	

	public Navigation(Integer id, String navigationName, String navigationUrl, Navigation navigation, Integer sort) {
		super();
		this.id = id;
		this.navigationName = navigationName;
		this.navigationUrl = navigationUrl;
		this.navigation = navigation;
		this.sort = sort;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNavigationName() {
		return navigationName;
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationUrl() {
		return navigationUrl;
	}

	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}



	@Override
	public String toString() {
		return "Navigation [id=" + id + ", navigationName=" + navigationName + ", navigationUrl=" + navigationUrl
				+ ", sort=" + sort + "]";
	}


	

}

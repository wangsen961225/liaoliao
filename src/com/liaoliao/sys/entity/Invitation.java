package com.liaoliao.sys.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liaoliao.user.entity.Users;

@Entity
@Table(name="ll_invitation")
public class Invitation  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Users parent;
	
	@OneToOne
	@JoinColumn(name="child_id")
	private Users child;

	private Integer status;//状态；0：未结算，1：已结算。
	
	private Integer money;
	
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getParent() {
		return parent;
	}

	public void setParent(Users parent) {
		this.parent = parent;
	}



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Users getChild() {
		return child;
	}

	public void setChild(Users child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "Invitation [id=" + id + ", parent=" + parent + ", child=" + child + ", status=" + status + ", money="
				+ money + ", addTime=" + addTime + "]";
	}
	
}

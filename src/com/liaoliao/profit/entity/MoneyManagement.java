package com.liaoliao.profit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ll_money_management")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class MoneyManagement implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private String id;
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 类型 0.有效产品 1.无效产品 2待出产品
	 */
	private String type;
	/**
	 * 起投时间
	 */
	private Date startdate;
	
	/**
	 * 截止时间
	 */
	private Date enddate;
	
	/**
	 * 起投金额
	 */
	private int minamount;
	
	/**
	 * 最大金额
	 */
	private int maxamount;
	
	/**
	 *产品更新时间
	 */
	private Date updatedate;
	
	/**
	 *产品利率
	 */
	private String interestRate;
	/**
	 * 理財週期
	 */
	private  int buyMouth;

	private String returnMoneyWay;
	
	private String porductType;
	
	private String porductDescribe;
	
	private Double floatNumber;
	
	public String getReturnMoneyWay() {
		return returnMoneyWay;
	}

	public void setReturnMoneyWay(String returnMoneyWay) {
		this.returnMoneyWay = returnMoneyWay;
	}

	public String getPorductType() {
		return porductType;
	}

	public void setPorductType(String porductType) {
		this.porductType = porductType;
	}

	public String getProductDescribe() {
		return porductDescribe;
	}

	public void setProductDescribe(String porductDescribe) {
		this.porductDescribe = porductDescribe;
	}

	public Double getFloatNumber() {
		return floatNumber;
	}

	public void setFloatNumber(Double floatNumber) {
		this.floatNumber = floatNumber;
	}

	public int getbuyMouth() {
		return buyMouth;
	}

	public void setbuyMouth(int buyMouth) {
		this.buyMouth = buyMouth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getMinamount() {
		return minamount;
	}

	public void setMinamount(int minamount) {
		this.minamount = minamount;
	}

	public int getMaxamount() {
		return maxamount;
	}

	public void setMaxamount(int maxamount) {
		this.maxamount = maxamount;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}














package com.liaoliao.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoliao.sys.entity.District;

@Entity
@Table(name="ll_user_info")
@DynamicUpdate(true)//更新某个字段时,不会全部更新一遍.
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class Users implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 会员状态
	 */
	private Integer vipStatus;
	
	/**
	 * 来源类型：微信：0 qq：1     微博：2     手机号注册：3
	 */
	private Integer sourceType;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 上级id
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Users parent;
	/**
	 * 总金额--获得的全部金额
	 */
	private Double totalMoney;
	/**
	 * 花费金额---消耗的金额
	 */
	private Double payMoney;
	/**
	 * 今日总额
	 */
	private Double dayMoney;
	
	/**
	 * 下级达到标准分润1000，注册赠送1000
	 */
	private Double unselfMoney;
	
	/**
	 * 冻结额
	 */
	private Double freezeMoney;
	
	/**
	 * 收益总金额
	 */
	private Double managementMoney;
	/**
	 * 昨日收益
	 */
	private Double yesterdayMoney;

	/**
	 * 提现额
	 */
	private Double toBankMoney;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 注册时间
	 */
	private Date addTime;
	/**
	 * 注册ip
	 */
	private String addIp;
	
	
	private Integer sex;//0:待确认   1：男    2：女
	
	
	private Integer age;
	
	private Integer deviceType;
	/**
	 * 想约当前用户的数量
	 */
	private Integer liaoCount ;
	@Column(updatable = false, name ="liaoCount", nullable = false, length=50)
	public Integer getLiaoCount() {
		return this.liaoCount;
	}

	public void setLiaoCount(Integer liaoCount) {
		this.liaoCount = liaoCount;
	}


	


	public Integer getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}


	@ManyToOne
	@JoinColumn(name="district_id")
	private District district;
	
	private String wechat;
	
	private String qq;
	
	private Date birthdate;
	
	
	public Double getYesterdayMoney() {
		return yesterdayMoney;
	}


	public void setYesterdayMoney(Double yesterdayMoney) {
		this.yesterdayMoney = yesterdayMoney;
	}
	
	public Double getManagementMoney() {
		return managementMoney;
	}


	public void setManagementMoney(Double managementMoney) {
		this.managementMoney = managementMoney;
	}


	public Users(){
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public Users getParent() {
		return parent;
	}


	public void setParent(Users parent) {
		this.parent = parent;
	}


	public Double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public Double getPayMoney() {
		return payMoney;
	}


	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}


	public Double getDayMoney() {
		return dayMoney;
	}


	public void setDayMoney(Double dayMoney) {
		this.dayMoney = dayMoney;
	}


	public Double getFreezeMoney() {
		return freezeMoney;
	}


	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}


	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public String getAddIp() {
		return addIp;
	}


	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}


	public void setTobankMoney(Double toBankMoney) {
		this.toBankMoney = toBankMoney;
	}


	public Integer getVipStatus() {
		return vipStatus;
	}


	public void setVipStatus(Integer vipStatus) {
		this.vipStatus = vipStatus;
	}


	public Integer getSourceType() {
		return sourceType;
	}


	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}


	public Double getUnselfMoney() {
		return unselfMoney;
	}


	public void setUnselfMoney(Double unselfMoney) {
		this.unselfMoney = unselfMoney;
	}


	public Double getToBankMoney() {
		return toBankMoney;
	}


	public void setToBankMoney(Double toBankMoney) {
		this.toBankMoney = toBankMoney;
	}


	public Integer getSex() {
		return sex;
	}


	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public District getDistrict() {
		return district;
	}


	public void setDistrict(District district) {
		this.district = district;
	}


	public String getWechat() {
		return wechat;
	}


	public void setWechat(String wechat) {
		this.wechat = wechat;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}




	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}


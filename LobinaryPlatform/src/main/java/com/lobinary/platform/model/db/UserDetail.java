package com.lobinary.platform.model.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用户详细信息实体
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午3:17:46
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Entity
@Table(name="USER_DETAIL_INFO")
public class UserDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8999334879614407381L;
	
	/**
	 * ID
	 */
	private long id;
	
	/**
	 * 用户登录信息表ID
	 */
	private long userId;
	
	/**
	 * 用户姓
	 */
	private String firstUsername;
	
	/**
	 * 用户名
	 */
	private String secondUserName;
	
	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 性别 1男 0女
	 */
	private int sex;
	
	/**
	 * email地址
	 */
	private String email;
	
	/**
	 * 电话
	 */
	private String telephone;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * qq号码
	 */
	private String qqNo;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	public String getFirstUsername() {
		return firstUsername;
	}

	public void setFirstUsername(String firstUsername) {
		this.firstUsername = firstUsername;
	}

	public String getSecondUserName() {
		return secondUserName;
	}

	public void setSecondUserName(String secondUserName) {
		this.secondUserName = secondUserName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

}

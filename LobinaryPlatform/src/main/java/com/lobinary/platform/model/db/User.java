package com.lobinary.platform.model.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用户信息实体
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午3:17:46
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Entity
@Table(name="USER_INFO")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2323558555318391692L;

	/**
	 * ID
	 */
	private long id;
	
	/**
	 * 用户登录信息表ID
	 */
	private long userDetailId;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserDetailId() {
		return userDetailId;
	}

	public void setUserDetailId(long userDetailId) {
		this.userDetailId = userDetailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

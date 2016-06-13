package com.lobinary.platform.model.db.team;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 团队成员
 * 
 * @author Lobinary
 *
 */
@Entity
@Table(name = "TEAM_USER_INFO")
public class Teamer {

	private long id;
	private String name;
	private String qq;
	private String phone;
	private String email;
	private String work;
	private String password;
	private String addReason;

	@Override
	public String toString() {
		return "Teamer [name=" + name + ", qq=" + qq
				+ ", phone=" + phone + ", email=" + email + ", work=" + work
				+ "]";
	}	

	public String toMailString() {
		return "qq:" + qq
				+ ", 电话：" + phone + ", email：" + email + ", 期望职责：" + work;
	}

	public String getAddReason() {
		return addReason;
	}

	public void setAddReason(String addReason) {
		this.addReason = addReason;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	private Date addDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}

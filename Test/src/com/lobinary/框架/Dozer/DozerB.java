package com.lobinary.框架.Dozer;

import java.util.Date;

public class DozerB{
	
	private int id;
	private String value;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DozerB [id=" + id + ", value=" + value + ", date=" + date + "]";
	}
	
}
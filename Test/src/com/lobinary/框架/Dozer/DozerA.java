package com.lobinary.框架.Dozer;

import java.util.Date;

public class DozerA{
	
	private int id;
	private String name;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DozerA [id=" + id + ", name=" + name + ", date=" + date + "]";
	}
}


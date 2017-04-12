package com.l.web.house.model;

import java.util.Date;

public class 房屋交易信息 {

	public String id;
	public String 房屋基本信息id;
	public double 总价;
	public double 首付;
	public double 税费;
	public double 每平米价格;
	public Date 价格更新日期;
	public int 关注人数;
	public int 看房人数;
	public String 有效状态;
	public String get有效状态() {
		return 有效状态;
	}
	public void set有效状态(String 有效状态) {
		this.有效状态 = 有效状态;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get房屋基本信息id() {
		return 房屋基本信息id;
	}
	public void set房屋基本信息id(String 房屋基本信息id) {
		this.房屋基本信息id = 房屋基本信息id;
	}
	public double get总价() {
		return 总价;
	}
	public void set总价(double 总价) {
		this.总价 = 总价;
	}
	public double get首付() {
		return 首付;
	}
	public void set首付(double 首付) {
		this.首付 = 首付;
	}
	public double get税费() {
		return 税费;
	}
	public void set税费(double 税费) {
		this.税费 = 税费;
	}
	public double get每平米价格() {
		return 每平米价格;
	}
	public void set每平米价格(double 每平米价格) {
		this.每平米价格 = 每平米价格;
	}
	public Date get价格更新日期() {
		return 价格更新日期;
	}
	public void set价格更新日期(Date 价格更新日期) {
		this.价格更新日期 = 价格更新日期;
	}
	public int get关注人数() {
		return 关注人数;
	}
	public void set关注人数(int 关注人数) {
		this.关注人数 = 关注人数;
	}
	public int get看房人数() {
		return 看房人数;
	}
	public void set看房人数(int 看房人数) {
		this.看房人数 = 看房人数;
	}
	@Override
	public String toString() {
		return "房屋交易信息 [id=" + id + ", 房屋基本信息id=" + 房屋基本信息id + ", 总价=" + 总价 + ", 首付=" + 首付 + ", 税费=" + 税费 + ", 每平米价格=" + 每平米价格 + ", 价格更新日期=" + 价格更新日期 + ", 关注人数=" + 关注人数 + ", 看房人数=" + 看房人数 + "]";
	}

}

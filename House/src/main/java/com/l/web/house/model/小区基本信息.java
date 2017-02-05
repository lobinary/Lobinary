package com.l.web.house.model;

import java.util.Date;

public class 小区基本信息 {
	
	public String id;
	public String 小区信息来源;
	public String 小区编号;
	public String 小区名称;
	public String 小区坐标;
	public int 小区总楼数;
	public int 小区楼盘均价;
	public Date 建筑日期;
	public String 建筑类型;
	public int 挂牌房源数量;
	public int 出租房源数量;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get小区信息来源() {
		return 小区信息来源;
	}
	public void set小区信息来源(String 小区信息来源) {
		this.小区信息来源 = 小区信息来源;
	}
	public String get小区编号() {
		return 小区编号;
	}
	public void set小区编号(String 小区编号) {
		this.小区编号 = 小区编号;
	}
	public String get小区名称() {
		return 小区名称;
	}
	public void set小区名称(String 小区名称) {
		this.小区名称 = 小区名称;
	}
	public String get小区坐标() {
		return 小区坐标;
	}
	public void set小区坐标(String 小区坐标) {
		this.小区坐标 = 小区坐标;
	}
	public int get小区总楼数() {
		return 小区总楼数;
	}
	public void set小区总楼数(int 小区总楼数) {
		this.小区总楼数 = 小区总楼数;
	}
	public int get小区楼盘均价() {
		return 小区楼盘均价;
	}
	public void set小区楼盘均价(int 小区楼盘均价) {
		this.小区楼盘均价 = 小区楼盘均价;
	}
	public Date get建筑日期() {
		return 建筑日期;
	}
	public void set建筑日期(Date 建筑日期) {
		this.建筑日期 = 建筑日期;
	}
	public String get建筑类型() {
		return 建筑类型;
	}
	public void set建筑类型(String 建筑类型) {
		this.建筑类型 = 建筑类型;
	}
	public int get挂牌房源数量() {
		return 挂牌房源数量;
	}
	public void set挂牌房源数量(int 挂牌房源数量) {
		this.挂牌房源数量 = 挂牌房源数量;
	}
	public int get出租房源数量() {
		return 出租房源数量;
	}
	public void set出租房源数量(int 出租房源数量) {
		this.出租房源数量 = 出租房源数量;
	}

}

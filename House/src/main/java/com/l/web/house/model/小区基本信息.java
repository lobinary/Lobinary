package com.l.web.house.model;

import java.util.Date;

public class 小区基本信息 {
	
	public String id;
	public String 信息来源;
	public String 编号;
	public String 名称;
	public String 坐标;
	public int 总楼数;
	public int 总房屋数;
	public int 楼盘均价;
	public Date 建筑日期;
	public double 物业费用;
	public String 物业公司;
	public String 开发商;
	public String 建筑类型;
	public int 挂牌房源数量;
	public int 出租房源数量;
	public int 关注人数;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get信息来源() {
		return 信息来源;
	}
	public void set信息来源(String 信息来源) {
		this.信息来源 = 信息来源;
	}
	public String get编号() {
		return 编号;
	}
	public void set编号(String 编号) {
		this.编号 = 编号;
	}
	public String get名称() {
		return 名称;
	}
	public void set名称(String 名称) {
		this.名称 = 名称;
	}
	public String get坐标() {
		return 坐标;
	}
	public void set坐标(String 坐标) {
		this.坐标 = 坐标;
	}
	public int get总楼数() {
		return 总楼数;
	}
	public void set总楼数(int 总楼数) {
		this.总楼数 = 总楼数;
	}
	public int get总房屋数() {
		return 总房屋数;
	}
	public void set总房屋数(int 总房屋数) {
		this.总房屋数 = 总房屋数;
	}
	public Date get建筑日期() {
		return 建筑日期;
	}
	public void set建筑日期(Date 建筑日期) {
		this.建筑日期 = 建筑日期;
	}
	public double get物业费用() {
		return 物业费用;
	}
	public void set物业费用(double 物业费用) {
		this.物业费用 = 物业费用;
	}
	public String get物业公司() {
		return 物业公司;
	}
	public void set物业公司(String 物业公司) {
		this.物业公司 = 物业公司;
	}
	public String get开发商() {
		return 开发商;
	}
	public void set开发商(String 开发商) {
		this.开发商 = 开发商;
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
	public int get关注人数() {
		return 关注人数;
	}
	public void set关注人数(int 关注人数) {
		this.关注人数 = 关注人数;
	}
	@Override
	public String toString() {
		return "小区基本信息 [id=" + id + ", 信息来源=" + 信息来源 + ", 编号=" + 编号 + ", 名称=" + 名称 + ", 坐标=" + 坐标 + ", 总楼数=" + 总楼数 + ", 总房屋数=" + 总房屋数 + ", 楼盘均价=" + 楼盘均价 + ", 建筑日期=" + 建筑日期 + ", 物业费用=" + 物业费用
				+ ", 物业公司=" + 物业公司 + ", 开发商=" + 开发商 + ", 建筑类型=" + 建筑类型 + ", 挂牌房源数量=" + 挂牌房源数量 + ", 出租房源数量=" + 出租房源数量 + ", 关注人数=" + 关注人数 + "]";
	}
	public int get楼盘均价() {
		return 楼盘均价;
	}
	public void set楼盘均价(int 楼盘均价) {
		this.楼盘均价 = 楼盘均价;
	}

}

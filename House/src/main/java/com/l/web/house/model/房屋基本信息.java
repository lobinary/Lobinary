package com.l.web.house.model;

import java.util.Date;

public class 房屋基本信息 {

	public String id;
	public String 房屋信息来源;
	public String 房屋唯一标识;//用来判断当价格变动情况下是唯一房屋的标识
	public String 房屋类型;//一手房、二手房
	public String 名称;//自命名
	public String 标题;
	public String 子标题;
	public String 网址;
	public String 坐标;
	public String 所在城市;
	public String 所在区县;// 如 丰台区  
	public String 所在环数;
	public String 所在地点;// 如 首经贸地铁站 或 花乡 等类似地点
	public String 所在小区;
	public String 所在小区编号;
	public String 所在楼号;
	public String 户型;//1室1厅1厨1卫
	public Double 建筑面积;
	public Double 实用面积;
	public String 朝向;
	public String 装修;
	public String 供暖;
	public Integer 产权年限;
	public Integer 所在楼层;//所在楼层一般不会明确标识，所以我们捕获的数据一般都是通过标识的高中低楼层进行计算的估测楼层
	public Integer 总楼层;
	public String 户型结构;//Loft
	public String 建筑类型;
	public String 建筑结构;
	public String 梯户比例;
	public String 电梯;
	public String 用电类型;
	public String 用水类型;
	public double 燃气价格;
	
	public Date 建房时间;
	public Date 挂牌时间;
	public Date 发布时间;
	public Date 上次交易时间;
	public Integer 房本年限;
	public String 抵押信息;
	public String 交易权属;
	public String 房屋用途;
	public String 产权所属;
	public String 是否唯一;
	public String 当前状态 = "0";//概要信息 0 详细信息1 房屋信息不存在-1
	public Date 最后更新日期;
	public String 有效状态;
	
	
	public String get当前状态() {
		return 当前状态;
	}
	public void set当前状态(String 当前状态) {
		this.当前状态 = 当前状态;
	}
	public String get子标题() {
		return 子标题;
	}
	public void set子标题(String 子标题) {
		this.子标题 = 子标题;
	}
	public String get所在小区编号() {
		return 所在小区编号;
	}
	public void set所在小区编号(String 所在小区编号) {
		this.所在小区编号 = 所在小区编号;
	}
	public String get所在环数() {
		return 所在环数;
	}
	public void set所在环数(String 所在环数) {
		this.所在环数 = 所在环数;
	}
	public String get是否唯一() {
		return 是否唯一;
	}
	public void set是否唯一(String 是否唯一) {
		this.是否唯一 = 是否唯一;
	}
	public String 房本备注;
	public String 联系人;
	public String 联系信息;
	public String 备注;
	public Date 创建时间;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get房屋信息来源() {
		return 房屋信息来源;
	}
	public void set房屋信息来源(String 房屋信息来源) {
		this.房屋信息来源 = 房屋信息来源;
	}
	public String get房屋唯一标识() {
		return 房屋唯一标识;
	}
	public void set房屋唯一标识(String 房屋唯一标识) {
		this.房屋唯一标识 = 房屋唯一标识;
	}
	public String get房屋类型() {
		return 房屋类型;
	}
	public void set房屋类型(String 房屋类型) {
		this.房屋类型 = 房屋类型;
	}
	public String get名称() {
		return 名称;
	}
	public void set名称(String 名称) {
		this.名称 = 名称;
	}
	public String get标题() {
		return 标题;
	}
	public void set标题(String 标题) {
		this.标题 = 标题;
	}
	public String get网址() {
		return 网址;
	}
	public void set网址(String 网址) {
		this.网址 = 网址;
	}
	public String get所在小区() {
		return 所在小区;
	}
	public void set所在小区(String 所在小区) {
		this.所在小区 = 所在小区;
	}
	public String get坐标() {
		return 坐标;
	}
	public void set坐标(String 坐标) {
		this.坐标 = 坐标;
	}
	public String get户型() {
		return 户型;
	}
	public void set户型(String 户型) {
		this.户型 = 户型;
	}
	public String get朝向() {
		return 朝向;
	}
	public void set朝向(String 朝向) {
		this.朝向 = 朝向;
	}
	public String get装修() {
		return 装修;
	}
	public void set装修(String 装修) {
		this.装修 = 装修;
	}
	public String get供暖() {
		return 供暖;
	}
	public void set供暖(String 供暖) {
		this.供暖 = 供暖;
	}
	public Integer get产权年限() {
		return 产权年限;
	}
	public void set产权年限(Integer 产权年限) {
		this.产权年限 = 产权年限;
	}
	public Integer get所在楼层() {
		return 所在楼层;
	}
	public void set所在楼层(Integer 所在楼层) {
		this.所在楼层 = 所在楼层;
	}
	public Integer get总楼层() {
		return 总楼层;
	}
	public void set总楼层(Integer 总楼层) {
		this.总楼层 = 总楼层;
	}
	public String get户型结构() {
		return 户型结构;
	}
	public void set户型结构(String 户型结构) {
		this.户型结构 = 户型结构;
	}
	public String get建筑类型() {
		return 建筑类型;
	}
	public void set建筑类型(String 建筑类型) {
		this.建筑类型 = 建筑类型;
	}
	public String get建筑结构() {
		return 建筑结构;
	}
	public void set建筑结构(String 建筑结构) {
		this.建筑结构 = 建筑结构;
	}
	public String get梯户比例() {
		return 梯户比例;
	}
	public void set梯户比例(String 梯户比例) {
		this.梯户比例 = 梯户比例;
	}
	public String get电梯() {
		return 电梯;
	}
	public void set电梯(String 电梯) {
		this.电梯 = 电梯;
	}
	public String get用电类型() {
		return 用电类型;
	}
	public void set用电类型(String 用电类型) {
		this.用电类型 = 用电类型;
	}
	public String get用水类型() {
		return 用水类型;
	}
	public void set用水类型(String 用水类型) {
		this.用水类型 = 用水类型;
	}
	public double get燃气价格() {
		return 燃气价格;
	}
	public void set燃气价格(double 燃气价格) {
		this.燃气价格 = 燃气价格;
	}
	public Date get挂牌时间() {
		return 挂牌时间;
	}
	public void set挂牌时间(Date 挂牌时间) {
		this.挂牌时间 = 挂牌时间;
	}
	public Date get上次交易时间() {
		return 上次交易时间;
	}
	public void set上次交易时间(Date 上次交易时间) {
		this.上次交易时间 = 上次交易时间;
	}
	public Integer get房本年限() {
		return 房本年限;
	}
	public void set房本年限(Integer 房本年限) {
		this.房本年限 = 房本年限;
	}
	public String get抵押信息() {
		return 抵押信息;
	}
	public void set抵押信息(String 抵押信息) {
		this.抵押信息 = 抵押信息;
	}
	public String get交易权属() {
		return 交易权属;
	}
	public void set交易权属(String 交易权属) {
		this.交易权属 = 交易权属;
	}
	public String get房屋用途() {
		return 房屋用途;
	}
	public void set房屋用途(String 房屋用途) {
		this.房屋用途 = 房屋用途;
	}
	public String get产权所属() {
		return 产权所属;
	}
	public void set产权所属(String 产权所属) {
		this.产权所属 = 产权所属;
	}
	public String get房本备注() {
		return 房本备注;
	}
	public void set房本备注(String 房本备注) {
		this.房本备注 = 房本备注;
	}
	public String get联系人() {
		return 联系人;
	}
	public void set联系人(String 联系人) {
		this.联系人 = 联系人;
	}
	public String get联系信息() {
		return 联系信息;
	}
	public void set联系信息(String 联系信息) {
		this.联系信息 = 联系信息;
	}
	public Date get创建时间() {
		return 创建时间;
	}
	public void set创建时间(Date 创建时间) {
		this.创建时间 = 创建时间;
	}
	public String get所在城市() {
		return 所在城市;
	}
	public void set所在城市(String 所在城市) {
		this.所在城市 = 所在城市;
	}
	public String get所在区县() {
		return 所在区县;
	}
	public void set所在区县(String 所在区县) {
		this.所在区县 = 所在区县;
	}
	public String get所在楼号() {
		return 所在楼号;
	}
	public void set所在楼号(String 所在楼号) {
		this.所在楼号 = 所在楼号;
	}
	public Date get发布时间() {
		return 发布时间;
	}
	public void set发布时间(Date 发布时间) {
		this.发布时间 = 发布时间;
	}
	public String get所在地点() {
		return 所在地点;
	}
	public void set所在地点(String 所在地点) {
		this.所在地点 = 所在地点;
	}
	public Double get建筑面积() {
		return 建筑面积;
	}
	public void set建筑面积(Double 建筑面积) {
		this.建筑面积 = 建筑面积;
	}
	public Double get实用面积() {
		return 实用面积;
	}
	public void set实用面积(Double 实用面积) {
		this.实用面积 = 实用面积;
	}
	public Date get建房时间() {
		return 建房时间;
	}
	public void set建房时间(Date 建房时间) {
		this.建房时间 = 建房时间;
	}
	public String get备注() {
		return 备注;
	}
	public void set备注(String 备注) {
		this.备注 = 备注;
	}
	@Override
	public String toString() {
		return "房屋基本信息 [id=" + id + ", 房屋信息来源=" + 房屋信息来源 + ", 房屋唯一标识=" + 房屋唯一标识 + ", 房屋类型=" + 房屋类型 + ", 名称=" + 名称 + ", 标题=" + 标题 + ", 网址=" + 网址 + ", 坐标=" + 坐标 + ", 所在城市=" + 所在城市 + ", 所在区县=" + 所在区县
				+ ", 所在地点=" + 所在地点 + ", 所在小区=" + 所在小区 + ", 所在楼号=" + 所在楼号 + ", 户型=" + 户型 + ", 建筑面积=" + 建筑面积 + ", 实用面积=" + 实用面积 + ", 朝向=" + 朝向 + ", 装修=" + 装修 + ", 供暖=" + 供暖 + ", 产权年限=" + 产权年限
				+ ", 所在楼层=" + 所在楼层 + ", 总楼层=" + 总楼层 + ", 户型结构=" + 户型结构 + ", 建筑类型=" + 建筑类型 + ", 建筑结构=" + 建筑结构 + ", 梯户比例=" + 梯户比例 + ", 电梯=" + 电梯 + ", 用电类型=" + 用电类型 + ", 用水类型=" + 用水类型 + ", 燃气价格=" + 燃气价格
				+ ", 建房时间=" + 建房时间 + ", 挂牌时间=" + 挂牌时间 + ", 发布时间=" + 发布时间 + ", 上次交易时间=" + 上次交易时间 + ", 房本年限=" + 房本年限 + ", 抵押信息=" + 抵押信息 + ", 交易权属=" + 交易权属 + ", 房屋用途=" + 房屋用途 + ", 产权所属=" + 产权所属
				+ ", 房本备注=" + 房本备注 + ", 联系人=" + 联系人 + ", 联系信息=" + 联系信息 + ", 备注=" + "toString的备注被我注释了" + ", 创建时间=" + 创建时间 + "]";
	}
	public Date get最后更新日期() {
		return 最后更新日期;
	}
	public void set最后更新日期(Date 最后更新日期) {
		this.最后更新日期 = 最后更新日期;
	}
	public String get有效状态() {
		return 有效状态;
	}
	public void set有效状态(String 有效状态) {
		this.有效状态 = 有效状态;
	}

}

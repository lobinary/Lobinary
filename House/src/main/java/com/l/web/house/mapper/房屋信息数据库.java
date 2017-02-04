package com.l.web.house.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.l.web.house.model.房屋交易信息;
import com.l.web.house.model.房屋基本信息;
import com.l.web.house.model.房屋户型信息;
import com.l.web.house.model.房屋照片信息;

@MapperScan("userMapper")
public interface 房屋信息数据库 {

	@Insert("INSERT INTO 房屋基本信息 "
			+ "(id, 房屋信息来源, 房屋唯一标识, 房屋类型, 名称, 标题, 网址, 所在城市, 所在区县,所在地点, 所在小区, 所在楼号, 坐标, 户型,"
			+ " 建筑面积, 实用面积, 朝向, 装修, 供暖, 产权年限, 所在楼层, 总楼层, 户型结构, 建筑类型,"
			+ " 建筑结构, 梯户比例, 电梯, 用电类型, 用水类型, 燃气价格, 挂牌时间, 上次交易时间, 房本年限, "
			+ "抵押信息, 交易权属, 房屋用途, 产权所属, 房本备注, 联系人, 联系信息, 建房时间, 发布时间, 备注)"
			+ " VALUES"
			+ " (#{id}, #{房屋信息来源}, #{房屋唯一标识}, #{房屋类型}, #{名称}, #{标题}, #{网址}, #{所在城市}, #{所在区县}, #{所在地点}, #{所在小区}, #{所在楼号}, #{坐标}, #{户型},"
			+ " #{建筑面积}, #{实用面积}, #{朝向}, #{装修}, #{供暖}, #{产权年限}, #{所在楼层}, #{总楼层}, #{户型结构}, #{建筑类型},"
			+ " #{建筑结构}, #{梯户比例}, #{电梯}, #{用电类型}, #{用水类型}, #{燃气价格}, #{挂牌时间}, #{上次交易时间}, #{房本年限},"
			+ " #{抵押信息}, #{交易权属}, #{房屋用途}, #{产权所属}, #{房本备注}, #{联系人}, #{联系信息}, #{建房时间}, #{发布时间}, #{备注})")
	public int 添加房屋基本信息(房屋基本信息 房屋基本信息);

	@Select("SELECT * FROM house.房屋基本信息 where 房屋信息来源 = #{房屋信息来源} and 房屋唯一标识 = #{房屋唯一标识}")
	public 房屋基本信息 查找基本信息根据来源和唯一标识(@Param("房屋信息来源")String 房屋信息来源,@Param("房屋唯一标识")String 房屋唯一标识);
	
	@Insert("INSERT INTO 房屋交易信息 "
			+ "(id, 房屋基本信息id, 总价, 首付, 税费, 每平米价格)"
			+ " VALUES"
			+ " (#{id}, #{房屋基本信息id}, #{总价}, #{首付}, #{税费}, #{每平米价格})")
	public int 添加房屋交易信息(房屋交易信息 房屋交易信息);
	
	@Select("select j.* "+
			"from house.房屋基本信息 b,house.房屋交易信息 j "+
			"where b.id = j.房屋基本信息id "+
			"and b.房屋信息来源 = #{房屋信息来源} and b.房屋唯一标识 = #{房屋唯一标识}"
			+ " order by 价格更新日期 desc limit 1 ")
	public 房屋交易信息 查找最新交易信息根据来源和唯一标识(@Param("房屋信息来源")String 房屋信息来源,@Param("房屋唯一标识")String 房屋唯一标识);

	@Insert("INSERT INTO 房屋照片信息 "
			+ "(id, 房屋基本信息id, 照片网络地址, 照片本地地址, 照片类型, 照片所属位置)"
			+ " VALUES"
			+ " (#{id}, #{房屋基本信息id}, #{照片网络地址}, #{照片本地地址}, #{照片类型}, #{照片所属位置})")
	public int 添加房屋照片信息(房屋照片信息 房屋照片信息);

	@Insert("INSERT INTO 房屋户型信息 "
			+ "(id, 房屋基本信息id, 房间类型, 房间面积, 房间朝向, 窗户类型)"
			+ " VALUES"
			+ " (#{id}, #{房屋基本信息id}, #{房间类型}, #{房间面积}, #{房间朝向}, #{窗户类型})")
	public int 添加房屋户型信息(房屋户型信息 房屋户型信息);
	
}

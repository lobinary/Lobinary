package com.l.web.house.mapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.model.小区基本信息;
import com.l.web.house.model.房屋交易信息;
import com.l.web.house.model.房屋基本信息;
import com.l.web.house.model.房屋户型信息;
import com.l.web.house.model.房屋照片信息;

@MapperScan("userMapper")
public interface 房屋信息数据库 {

	@Insert("INSERT INTO 房屋基本信息 "
			+ "(id, 房屋信息来源, 房屋唯一标识, 房屋类型, 名称, 标题, 子标题, 网址, 所在城市, "
			+ "所在区县,所在地点,所在环数, 所在小区, 所在小区编号, 所在楼号, 坐标, 户型,"
			+ " 建筑面积, 实用面积, 朝向, 装修, 供暖, 产权年限, 是否唯一, 所在楼层, 总楼层, 户型结构, 建筑类型,"
			+ " 建筑结构, 梯户比例, 电梯, 用电类型, 用水类型, 燃气价格, 挂牌时间, 上次交易时间, 房本年限, "
			+ "抵押信息, 交易权属, 房屋用途, 产权所属, 房本备注, 联系人, 联系信息, 建房时间, 发布时间, 备注, 当前状态 ,最后更新日期)"
			+ " VALUES"
			+ " (#{id}, #{房屋信息来源}, #{房屋唯一标识}, #{房屋类型}, #{名称}, #{标题}, #{子标题}, #{网址}, #{所在城市}, "
			+ "#{所在区县}, #{所在地点}, #{所在环数}, #{所在小区}, #{所在小区编号}, #{所在楼号}, #{坐标}, #{户型},"
			+ " #{建筑面积}, #{实用面积}, #{朝向}, #{装修}, #{供暖}, #{产权年限}, #{是否唯一}, #{所在楼层}, #{总楼层}, #{户型结构}, #{建筑类型},"
			+ " #{建筑结构}, #{梯户比例}, #{电梯}, #{用电类型}, #{用水类型}, #{燃气价格}, #{挂牌时间}, #{上次交易时间}, #{房本年限},"
			+ " #{抵押信息}, #{交易权属}, #{房屋用途}, #{产权所属}, #{房本备注}, #{联系人}, #{联系信息}, #{建房时间}, #{发布时间}, #{备注}, #{当前状态},#{最后更新日期} )")
	public int 添加房屋基本信息(房屋基本信息 房屋基本信息);

	@Update("update 房屋基本信息 set 房屋信息来源 = #{房屋信息来源} ,房屋唯一标识 = #{房屋唯一标识},"
			+ "房屋类型 = #{房屋类型},名称 = #{名称},标题 = #{标题},子标题 = #{子标题},网址 = #{网址},所在城市 = #{所在城市},"
			+ "所在区县 = #{所在区县},所在地点 = #{所在地点},所在环数 = #{所在环数},所在小区 = #{所在小区},"
			+ "所在小区编号 = #{所在小区编号},所在楼号 = #{所在楼号},坐标 = #{坐标},户型 = #{户型},"
			+ " 建筑面积 = #{建筑面积},实用面积 = #{实用面积},朝向 = #{朝向},装修 = #{装修},供暖 = #{供暖},产权年限 = #{产权年限},"
			+ "是否唯一 = #{是否唯一},所在楼层 = #{所在楼层},总楼层 = #{总楼层},户型结构 = #{户型结构},建筑类型 = #{建筑类型},"
			+ " 建筑结构 = #{建筑结构},梯户比例 = #{梯户比例},电梯 = #{电梯},用电类型 = #{用电类型},用水类型 = #{用水类型},"
			+ "燃气价格 = #{燃气价格},挂牌时间 = #{挂牌时间},上次交易时间 = #{上次交易时间},房本年限 = #{房本年限},"
			+ "抵押信息 = #{抵押信息},交易权属 = #{交易权属},房屋用途 = #{房屋用途},产权所属 = #{产权所属},房本备注 = #{房本备注},"
			+ "联系人 = #{联系人},联系信息 = #{联系信息},建房时间 = #{建房时间},发布时间 = #{发布时间},备注 = #{备注},当前状态 = #{当前状态},最后更新日期= #{最后更新日期}"
            + " where id=#{id}")
	public int 更新房屋基本信息(房屋基本信息 房屋基本信息);
	
	@Select("SELECT * FROM house.房屋基本信息 where 房屋信息来源 = #{房屋信息来源} and 房屋唯一标识 = #{房屋唯一标识}")
	public 房屋基本信息 查找基本信息根据来源和唯一标识(@Param("房屋信息来源")String 房屋信息来源,@Param("房屋唯一标识")String 房屋唯一标识);
	
	@Select("SELECT * FROM house.房屋基本信息 where 房屋信息来源 = #{房屋信息来源} and 当前状态 = #{当前状态} and 所在小区编号 is null limit #{限制条数}")
	public List<房屋基本信息> 查找基本信息根据当前状态(@Param("房屋信息来源")String 房屋信息来源,@Param("当前状态")String 当前状态,@Param("限制条数")int 限制条数);
	
	@Insert("INSERT INTO 房屋交易信息 "
			+ "(id, 房屋基本信息id, 总价, 首付, 税费, 每平米价格, 批次号)"
			+ " VALUES"
			+ " (#{id}, #{房屋基本信息id}, #{总价}, #{首付}, #{税费}, #{每平米价格}, #{批次号})")
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
	public int 添加房屋照片信息(房屋照片信息 房屋照片信息) throws SQLIntegrityConstraintViolationException;

	@Insert("INSERT INTO 房屋户型信息 "
			+ "(id, 房屋基本信息id, 房间类型, 房间面积, 窗户朝向, 窗户类型)"
			+ " VALUES"
			+ " (#{id}, #{房屋基本信息id}, #{房间类型}, #{房间面积}, #{窗户朝向}, #{窗户类型})")
	public int 添加房屋户型信息(房屋户型信息 房屋户型信息) throws SQLIntegrityConstraintViolationException;

	@Insert("INSERT INTO 小区基本信息 "
			+ "(id, 信息来源, 编号, 名称, 坐标, "
			+ "总楼数, 楼盘均价, 物业费用,物业公司,开发商,总房屋数,"
			+ "建筑日期, 建筑类型, 挂牌房源数量, 出租房源数量, 关注人数)"
			+ " VALUES"
			+ " (#{id}, #{信息来源}, #{编号}, #{名称}, #{坐标},"
			+ " #{总楼数}, #{楼盘均价}, #{物业费用}, #{物业公司}, #{开发商}, #{总房屋数},"
			+ " #{建筑日期}, #{建筑类型}, #{挂牌房源数量}, #{出租房源数量}, #{关注人数})")
	public int 添加小区基本信息(小区基本信息 小区基本信息);

	@Select(" SELECT distinct(所在小区编号) "
			+ " FROM house.房屋基本信息 "
			+ " where 房屋信息来源 = #{房屋信息来源} "
			+ " and 所在小区编号 not in "
			+ " ("
			+ "	select distinct(编号) "
			+ "	from 小区基本信息 "
			+ ") ")
	public List<String> 查找未抓取过的小区编号根据房屋来源(String 房屋信息来源);

	public 房屋统计信息 查询捕获房屋统计信息();

	/**
	 * 
	 * @param 批次号 起始批次号
	 * @return
	 */
	public List<房屋统计信息> 查询房屋价格走势根据批次号(String 批次号);

	public List<房屋统计信息> 查询批次号价格变动数据(String 批次号);

}

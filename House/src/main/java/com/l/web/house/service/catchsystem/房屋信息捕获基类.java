package com.l.web.house.service.catchsystem;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.mapper.房屋信息数据库;
import com.l.web.house.model.房屋基本信息;
import com.lobinary.工具类.date.DateStyle;
import com.lobinary.工具类.date.DateUtil;

public abstract class 房屋信息捕获基类 implements 房屋信息捕获接口{

	private final static Logger logger = LoggerFactory.getLogger(房屋信息捕获基类.class);

	public String 批次号 = DateUtil.getCurrentTime(DateStyle.YYYYMMDD)+"000000";
	
	@Resource
	public 房屋信息数据库 房屋信息数据库;

	@Override
	public 房屋统计信息 查询捕获房屋统计信息() throws Exception {
		房屋统计信息 result = 房屋信息数据库.查询捕获房屋统计信息();
		return result;
	}

	@Override
	public List<房屋统计信息> 查询房屋价格走势根据批次号(String 批次号) throws Exception {
		List<房屋统计信息> list = 房屋信息数据库.查询房屋价格走势根据批次号(批次号);
		return list;
	}

	@Override
	public List<房屋统计信息> 查询批次号价格变动数据(String 批次号) throws Exception {
		if(批次号==null)批次号=this.批次号;
		List<房屋统计信息> list = 房屋信息数据库.查询批次号价格变动数据(批次号);
		return list;
	}

	@Override
	public List<房屋基本信息> 查询房屋基本信息通过创建日期(String date) {
		return 房屋信息数据库.查询房屋基本信息通过创建日期(date);
	}
	
}

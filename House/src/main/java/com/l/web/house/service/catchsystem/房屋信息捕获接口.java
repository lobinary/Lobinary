package com.l.web.house.service.catchsystem;

import java.util.List;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.model.房屋基本信息;

public interface 房屋信息捕获接口 {


	public void 捕获房屋信息(String[] o) throws Exception;
	
	public 房屋统计信息 查询捕获房屋统计信息() throws Exception;
	
	public List<房屋统计信息> 查询房屋价格走势根据批次号(String 批次号) throws Exception;
	
	public List<房屋统计信息> 查询批次号价格变动数据(String 批次号) throws Exception;

	List<房屋基本信息> 查询房屋基本信息通过创建日期(String date);

}

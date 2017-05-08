package com.l.web.house.service.catchsystem;

import com.l.web.house.dto.房屋统计信息;

public interface 房屋信息捕获接口 {

	public void 捕获房屋信息(String[] o) throws Exception;
	
	public 房屋统计信息 查询捕获房屋统计信息() throws Exception;

}

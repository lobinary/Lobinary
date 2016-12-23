package com.l.test.web.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.l.test.web.dto.UserInfo;

@MapperScan("userMapper")
public interface UserMapper {
	
	  @Select("SELECT * FROM user WHERE id = #{id}")
	  public UserInfo getUserInfoById(long id);
	  
}
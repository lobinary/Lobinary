package com.l.test.web.mapper;

import org.apache.ibatis.annotations.Select;

import com.l.test.web.dto.UserInfo;

public interface UserMapper {
	
	  @Select("SELECT * FROM user WHERE id = #{id}")
	  public UserInfo getUserInfoById(long id);
	  
}
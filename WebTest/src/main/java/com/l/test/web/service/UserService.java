package com.l.test.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l.test.web.dto.UserInfo;
import com.l.test.web.mapper.UserMapper;

public class UserService {

	private final static Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserMapper userMapper;
	
	public UserInfo getUserInfoById(long id) {
		logger.info("准备根据ID:{}查询用户信息",id);
		return userMapper.getUserInfoById(id);
	}

}

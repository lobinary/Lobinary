package com.l.test.web.controller;

import com.l.test.web.cache.redis.RedisClientAllTemplate;
import com.l.test.web.dto.UserInfo;
import com.l.test.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/theme")
public class UpUpOo {

	private final static Logger logger = LoggerFactory.getLogger(UpUpOo.class);
	

	@Resource
	RedisClientAllTemplate redisClientTemplate;

	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String getPage(Model model){
		logger.info("准备获取普通页面页面");
		return "/themeData";
	}

	
}

package com.l.test.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.l.test.web.cache.redis.RedisClientAllTemplate;
import com.l.test.web.dto.UserInfo;
import com.l.test.web.service.UserService;

@Controller
@RequestMapping("/test")
public class TestController{

	private final static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource
	RedisClientAllTemplate redisClientTemplate;

	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public String getPage(Model model){
		logger.info("准备获取普通页面页面");
		model.addAttribute("id", "1");
		model.addAttribute("name", "猫驴子");
		model.addAttribute("age", "24");
		model.addAttribute("sex", "女");
		model.addAttribute("hobby", "游泳、台球、羽毛球、旅游、写代码");
		return "/get_page";
	}
	
	@RequestMapping(value = "/getDBPage", method = RequestMethod.GET)
	public String getDBPage(Model model){
		logger.info("准备获取数据库页面页面");
		UserInfo user = userService.getUserInfoById(1);
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("age", user.getAge());
		model.addAttribute("sex", user.getSex());
		model.addAttribute("hobby", user.getHobby());
		return "/get_page";
	}
	
	@RequestMapping(value = "/getCachePage", method = RequestMethod.GET)
	public String getCachePage(Model model){
		logger.info("准备获取缓存页面页面");
		UserInfo user = (UserInfo) redisClientTemplate.getObj("testCache");
		if(user==null){
			logger.info("缓存未命中数据");
			user = userService.getUserInfoById(1);
			redisClientTemplate.setex("testCache", 30, user);
		}
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("age", user.getAge());
		model.addAttribute("sex", user.getSex());
		model.addAttribute("hobby", user.getHobby());
		return "/get_page";
	}
	
}

package com.lobinary.platform.remote.controller.team;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lobinary.platform.model.db.team.Teamer;
import com.lobinary.platform.service.team.TeamService;

@Controller("teamController")
@RequestMapping(value = "/platform/team")
public class TeamController {

	Logger logger = LoggerFactory.getLogger(TeamController.class);

	@Resource(name = "teamService")
	private TeamService teamService;

	/**
	 * 添加文本信息
	 * 
	 * @param sendMessage
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add/register.anonymous")
	public String register(@ModelAttribute("teamer") Teamer teamer, HttpServletRequest request, HttpServletResponse response) {
		teamer.setAddDate(new Date(System.currentTimeMillis()));
		logger.info("系统收到新团队申请，消息内容为：" + teamer.toString());
		boolean addSuccess = teamService.add(teamer);
		try {
			response.sendRedirect("/views/team/home/index.jsp?isSuccess="+addSuccess);
			response.setCharacterEncoding("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 防止因日期为空时,报装配错误
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}

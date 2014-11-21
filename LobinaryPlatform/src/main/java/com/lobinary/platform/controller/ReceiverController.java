package com.lobinary.platform.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lobinary.platform.model.PageParameter;
import com.lobinary.platform.model.db.ReceiverMessage;
import com.lobinary.platform.model.db.UploadFileInfo;
import com.lobinary.platform.service.ReceiverService;
import com.lobinary.platform.util.ReflectsUtil;
import com.lobinary.platform.util.WebUtil;

@Controller("messageController")
@RequestMapping(value = "/platform/receiver")
public class ReceiverController {

	Logger logger = Logger.getLogger(ReceiverController.class);

	@Resource(name = "messageService")
	private ReceiverService receiverService;

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

	/**
	 * 添加文本信息
	 * 
	 * @param sendMessage
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/message/send_message.anonymous")
	public String sendMessage(@ModelAttribute("message") ReceiverMessage message, HttpServletRequest request, HttpServletResponse response) {
		message.setSendDate(new Date(System.currentTimeMillis()));
		logger.info("Receiver系统收到新消息，消息内容为：" + message.getMessageInfo());
		boolean addSuccess = receiverService.addMessage(message);
		String returnStr = "信息发送成功！";
		if (!addSuccess) {
			returnStr = "对不起，您所发送的信息未能成功添加进数据库，请您校验您的数据或联系管理员！";
		}
		WebUtil.returnStr(response, returnStr);
		return null;
	}

	/**
	 * 查看文本信息列表
	 * 
	 * @param sendMessage
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/message/message_list.anonymous")
	public String message_list(@ModelAttribute("message") PageParameter pageParameter,HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ReceiverMessage> messageList = receiverService.listMessage(null,pageParameter);
		model.addAttribute("messageList",messageList);
		model.addAttribute("pageParameter",pageParameter);
		return "receiver/message_list";
	}

	/**
	 * 上传文件
	 * 
	 * @param sendMessage
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/file/sendFile.anonymous")
	public String sendFile(@RequestParam(value="sendFile",required=false)MultipartFile sendFile,@ModelAttribute("uploadFileInfo")UploadFileInfo uploadFileInfo,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(ReflectsUtil.object2String(uploadFileInfo));
		System.out.println(sendFile);
		System.out.println(ReflectsUtil.object2String(sendFile));
		boolean addSuccess = receiverService.addFile(uploadFileInfo,sendFile);
		String returnStr = "文件发送成功！";
		if (!addSuccess) {
			returnStr = "对不起，您所发送的文件发送失败，请您校验您的数据或联系管理员！";
		}
		WebUtil.returnStr(response, returnStr);
		return null;
	}

}

package com.lobinary.platform.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lobinary.platform.dao.MessageDAO;
import com.lobinary.platform.model.PageParameter;
import com.lobinary.platform.model.ReceiverMessage;
import com.lobinary.platform.model.UploadFileInfo;
import com.lobinary.platform.util.DateUtil;
import com.lobinary.platform.util.PropertiesUtil;

/**
 * 信息业务处理
 * 
 * @author Lobinary
 * @since 2014年10月11日13:50:253
 * @version v2.0
 */
@Service("messageService")
public class ReceiverService extends BaseService {

	private MessageDAO messageDAO;

	/**
	 * 删除message对象
	 */
	@Transactional(readOnly = false)
	public boolean delete(ReceiverMessage m) {
		messageDAO.delete(m);
		messageDAO.deleteMessageByID(m.getId());
		return true;
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	@Resource(name = "messageDAO")
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	/**
	 * 添加文本信息
	 * 
	 * @param message
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean addMessage(ReceiverMessage message) {
		return messageDAO.add(message);
	}

	/**
	 * 添加文件
	 * 
	 * @param file
	 * @return
	 */
	public boolean addFile(UploadFileInfo uploadFileInfo,MultipartFile file) {
		try {
			System.out.println("##########" + file);
			// 获得文件名：
			String filename = file.getOriginalFilename();
			// 获得输入流：
			InputStream inputStream = file.getInputStream();
			String localFilePathStr = PropertiesUtil.getValue("receiver_sendFile_filepath");
			File localfile = new File(localFilePathStr + DateUtil.getNowTime() + filename);
			File localFilePath = new File(localFilePathStr);
			if (!localFilePath.exists()) {
				localFilePath.mkdirs();
			}
			localfile.createNewFile();
			FileOutputStream fs = new FileOutputStream(localfile);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = inputStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 查看 信息 列表
	 * 
	 * @return
	 */
	public List<ReceiverMessage> listMessage(ReceiverMessage message, PageParameter pp) {
		List<ReceiverMessage> messageList = messageDAO.queryList(message, ReceiverMessage.class, true, pp.getStartRow(), pp.getPageSize());
		pp.setCountRows(messageDAO.getCount(message, ReceiverMessage.class, true));
		return messageList;
	}

}

package com.lobinary.platform.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lobinary.platform.dao.hibernate.ReceiverMessageDAO;
import com.lobinary.platform.dao.hibernate.UploadFileInfoDAO;
import com.lobinary.platform.model.PageParameter;
import com.lobinary.platform.model.db.message.ReceiverMessage;
import com.lobinary.platform.model.db.message.UploadFileInfo;
import com.lobinary.platform.util.DateUtil;
import com.lobinary.platform.util.PropertiesUtil;

/**
 * 信息业务处理
 * 
 * @author Lobinary
 * @since 2014年10月11日13:50:253
 * @version v2.0
 */
@Service("receiverService")
public class ReceiverService extends BaseService {

	private ReceiverMessageDAO receiverMessageDAO;
	private UploadFileInfoDAO uploadFileInfoDAO;

	/**
	 * 删除message对象
	 */
	@Transactional(readOnly = false)
	public boolean delete(ReceiverMessage m) {
		receiverMessageDAO.delete(m);
		receiverMessageDAO.deleteMessageByID(m.getId());
		return true;
	}

	/**
	 * 添加文本信息
	 * 
	 * @param message
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean addMessage(ReceiverMessage message) {
		return receiverMessageDAO.add(message);
	}

	/**
	 * 添加文件
	 * 
	 * @param file
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean addFile(UploadFileInfo uploadFileInfo,MultipartFile file) {
		try {
			// 获得文件名：
			String filename = file.getOriginalFilename();
			// 获得输入流：
			InputStream inputStream = file.getInputStream();
			String localFilePathStr = PropertiesUtil.getValue("receiver_sendFile_filepath");
			File localfile = new File(localFilePathStr + DateUtil.getNowFormatedTime() + "-" + filename);
			File localFilePath = new File(localFilePathStr);
			if (!localFilePath.exists()) {
				localFilePath.mkdirs();
			}
			localfile.createNewFile();
			logger.info("上传的文件已经接收成功，接收地址为：" + localfile.getAbsolutePath());
			FileOutputStream fs = new FileOutputStream(localfile);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = inputStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			inputStream.close();
			String[] fileTypeArray = filename.split("\\.");
			String fileType = "n/a";
			if(fileTypeArray.length>0){
				fileType = fileTypeArray[fileTypeArray.length-1];
			}
			uploadFileInfo.setFileType(fileType);
			uploadFileInfo.setSendDate(new Date(System.currentTimeMillis()));
			uploadFileInfo.setFileLocation(localfile.getAbsolutePath());
			uploadFileInfoDAO.add(uploadFileInfo);
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
		List<ReceiverMessage> messageList = receiverMessageDAO.queryList(message, ReceiverMessage.class, true, pp.getStartRow(), pp.getPageSize());
		pp.setCountRows(receiverMessageDAO.getCount(message, ReceiverMessage.class, true));
		return messageList;
	}

	public UploadFileInfoDAO getUploadFileInfoDAO() {
		return uploadFileInfoDAO;
	}

	@Resource(name = "uploadFileInfoDAO")
	public void setUploadFileInfoDAO(UploadFileInfoDAO uploadFileInfoDAO) {
		this.uploadFileInfoDAO = uploadFileInfoDAO;
	}

	public ReceiverMessageDAO getReceiverMessageDAO() {
		return receiverMessageDAO;
	}

	@Resource(name="receiverMessageDAO")
	public void setReceiverMessageDAO(ReceiverMessageDAO receiverMessageDAO) {
		this.receiverMessageDAO = receiverMessageDAO;
	}
	
}

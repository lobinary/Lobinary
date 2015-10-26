package com.lobinary.android.common.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.exception.APCSysException;
import com.lobinary.android.common.util.file.FileUtil;

/**
 * <pre>
 * 配置工具类
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月26日 下午2:24:48
 * @version V1.0.0 描述 : 创建文件PropertiesUtil
 * 
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static Properties properties;
	private static String fileStoreLocaltion = "lobinary_apc_store_file.apc";
	private static Map<String, Object> fileStoreMap;
	private static File storeFile = new File(fileStoreLocaltion);

	static {
		try {
			System.out.println("配置工具类:配置信息初始化完成,存储文件(StoreFile)位置:"+storeFile.getAbsolutePath());
		
			String propFile = "apc.properties";
			properties = new Properties();
			InputStream file = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFile);
			properties.load(file);
		
			if (!FileUtil.fileIsExistAndCreate(storeFile)) {
				fileStoreMap = new HashMap<String, Object>();
				FileUtil.saveObj(storeFile, fileStoreMap);
			}

			fileStoreMap = FileUtil.getObj(storeFile,Map.class);
			logger.info("配置工具类:配置信息初始化完成,存储文件(StoreFile)位置:"+storeFile.getAbsolutePath());
		} catch (Exception e) {
			logger.error("配置工具类:初始化配置工具类异常",e);
			throw new APCSysException(CodeDescConstants.PROPERTIES_INITIAL_EXCEPTION);
		}

	}

	/**
	 * 
	 * <pre>
	 * 获取配置文件值
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String key) {
		try {
			return properties.get(key).toString();
		} catch (NullPointerException e) {
			return "未匹配到(" + key + ")的值";
		}

	}

	/**
	 * 
	 * <pre>
	 * 获取文件存储值
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public static Object getFileValue(String key) {
		return fileStoreMap.get(key);
	}
	
	/**
	 * 
	 * <pre>
	 * 保存文件存储值
	 * </pre>
	 *
	 * @param key
	 * @param object
	 */
	public static void saveFileValue(String key,Object object){
		fileStoreMap.put(key,  object);
		logger.info("保存对象("+key+":"+object+")成功");
		FileUtil.saveObj(storeFile, fileStoreMap);
	}

}

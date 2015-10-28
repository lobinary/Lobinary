package com.lobinary.android.common.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.exception.APCSysException;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.common.util.file.FileUtil;
import com.lobinary.android.common.util.log.LogUtil;

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
	private static Map<String, Object> fileStoreMap;
	public static File storeFile;
	public static String propFile = "apc.properties";
	public static boolean androidNewStoreFile = CommonFactory.androidNewStoreFile;

	static {
		try {
			storeFile = CommonFactory.storeFile;
			LogUtil.out("配置工具类:配置信息初始化......");
			properties = new Properties();
			if(Constants.SYSTEM_CODE_WINDOWS.equals(CommonFactory.SYSTEM_CODE)){//如果是windows
				if (!FileUtil.fileIsExistAndCreate(storeFile)) {
					logger.info("存储文件不存在,准备新建存储文件");
					fileStoreMap = new HashMap<String, Object>();
					FileUtil.saveObj(storeFile, fileStoreMap);
				}
				InputStream file = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFile);
				properties.load(file);
			}else{
				System.out.println("安卓系统配置工具初始化");
				if(androidNewStoreFile){
					System.out.println("安卓配置工具类正在新建存储文件");
					fileStoreMap = new HashMap<String, Object>();
					FileUtil.saveObj(storeFile, fileStoreMap);//XXX 安卓需要修复相关BUG
				}
			}
			LogUtil.out("读取storeFile开始"+storeFile);
			fileStoreMap = FileUtil.getObj(storeFile,Map.class);
			logger.info("配置工具类:配置信息初始化完成,存储文件(StoreFile)位置:"+storeFile.getAbsolutePath());
		} catch (Exception e) {
			logger.error("配置工具类:初始化配置工具类异常",e);
			System.out.println("配置工具类:初始化配置工具类异常"+e.getMessage());
			throw new APCSysException(CodeDescConstants.PROPERTIES_INITIAL_EXCEPTION,e);
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
		FileUtil.saveObj(storeFile, fileStoreMap);
		logger.info("保存对象("+key+":"+object+")成功");
	}

}

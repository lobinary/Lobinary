package com.lobinary.platform.util;

import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONReader;


public class JsonUtil {

	private static JSONReader jsonReader = new JSONReader();

	/**
	 * 根据json字符串,返回对应的对象
	 * 
	 * @param jsonStr
	 * @param modelInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2Object(String jsonStr, Class<T> c) {
		T o = null;
		try {
				Map<String, Object> objMap = (Map<String, Object>) jsonReader.read(jsonStr);
				o = ReflectsUtil.map2Object(objMap,c);
		} catch (JSONException e) {
			LogUtil.logException(e);
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return o;
	}

	
}

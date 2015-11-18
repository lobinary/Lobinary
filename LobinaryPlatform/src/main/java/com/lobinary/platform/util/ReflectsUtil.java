package com.lobinary.platform.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.json.JSONException;
import org.slf4j.Logger;

import com.lobinary.platform.constant.Enums;
import com.lobinary.platform.model.DatabaseParameter;

/**
 * 
 * @author Administrator
 * 
 */
public class ReflectsUtil {

	private static Logger logger = LogUtil.getLog(ReflectsUtil.class);

	/**
	 * 普通时间格式器
	 */
	private static SimpleDateFormat simpleDateFormat_Normal = new SimpleDateFormat();
	/**
	 * <pre>
	 * 时间格式 :	2014-05-06T16:00:00.000Z
	 * 表达式格式:	yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 * 
	 * <pre/>
	 */
	private static SimpleDateFormat simpleDateFormat_2016_06_06T06_06_06_000Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	/**
	 * <pre>
	 * 时间格式 :	2014/05/13 16:00:37
	 * 表达式格式:	yyyy/MM/dd HH:mm:ss
	 * 
	 * <pre/>
	 */
	private static SimpleDateFormat simpleDateFormat_2016_06_06_06_06_06 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * 用于识别日期格式的正则表达式
	 */
	private static Map<String, SimpleDateFormat> dateFormaterMap = new HashMap<String, SimpleDateFormat>();
	static {
		System.out.println("初始化中");
		String datePatternStr_2016_06_06T06_06_06_000Z = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z";
		String datePatternStr_2016_06_06_06_06_06 = "[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
		dateFormaterMap.put(datePatternStr_2016_06_06_06_06_06, simpleDateFormat_2016_06_06_06_06_06);
		dateFormaterMap.put(datePatternStr_2016_06_06T06_06_06_000Z, simpleDateFormat_2016_06_06T06_06_06_000Z);
	}

	public static void main(String[] args) throws JSONException {
		// String s =
		// "{\"id\":0,\"forkliftSerialNo\":\"阿斯顿发\",\"models\":\"阿斯顿发\",\"storeTime\":\"2014-05-06T16:00:00.000Z\",\"salesStaff\":\"阿斯顿发\",\"price\":0,\"quantity\":0,\"taxRate\":0,\"total\":0}'";
		// JSONReader jsonReader = new JSONReader();
		// Forklift f = getBean((Map<String,
		// Object>)jsonReader.read(s),Forklift.class);
		// System.out.println(f);
		// Date date = simpleDateFormate.parse("2014-05-06T16:00:00.000Z");
		// System.out.println(date);
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("a", "11111");
		// Forklift f = map2Object(paramMap, Forklift.class);
		// System.out.println(object2String(f));
		string2Date("2014/05/13 16:00:37");
	}

	/**
	 * 通过map(String,String)类型转换成对应的object对象<br/>
	 * 想要赋值,就放到map里,如果没有就可以不put到map里
	 * 
	 * @param paramMap
	 *            参数集合
	 * @param c
	 *            需要转换成的对象类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T map2Object(Map<String, Object> paramMap, Class<T> c) {
		logger.debug("准备将map转换成Object");
		Object o = null;// 需要返回的对象
		Field[] fieldArray = c.getDeclaredFields();// 需要返回的对象的参数集合
		Map<String, String> paramTypeMap = getParamType(c);
		Object valueObj = null;
		String type;
		String fieldName = "";
		try {
			o = c.newInstance();
			for (Field field : fieldArray) {
				field.getName();
				fieldName = field.getName();
				valueObj = paramMap.get(fieldName);
				type = paramTypeMap.get(fieldName);
				logger.debug("fieldName:" + fieldName + ",valueStr:" + valueObj + ",type:" + type);
				if (valueObj == null || valueObj.equals("null")) {
					continue;
				}
				valueObj = string2Object(valueObj + "", typeStr2TypeEnums(type));
				field.setAccessible(true);
				field.set(o, valueObj);
			}
		} catch (InstantiationException e) {
			LogUtil.logException(e);
		} catch (IllegalAccessException e) {
			LogUtil.logException(e);
		} catch (SecurityException e) {
			LogUtil.logException(e);
		} catch (IllegalArgumentException e) {
			LogUtil.logException(e);
		}
		return (T) o;
	}

	public static java.sql.Date utilDate2SQLDate(java.util.Date date) {
		java.sql.Date sql_date = new java.sql.Date(date.getTime());
		return sql_date;
	}

	/**
	 * 将对象转换成字符串的普通方法,类似于toString方法,比toString方法更友好一些
	 * 
	 * @param o
	 * @return
	 */
	public static String object2String(Object o) {
		String className = o.getClass().getSimpleName();
		Map<String, Object> valueMap = getObjectValueMap(o);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("************************************" + className + "类数据输出开始************************************\n\n");
		for (String value_Key : valueMap.keySet()) {
			String tt = "";
			// 本判断能保证大部分参数名和参数值列的对其,但是不能保证长度差异更大的对齐(参数长度支持范围为1~25)
			if (value_Key.length() > 25) {
				tt = "\t";
			} else if (value_Key.length() > 17) {
				tt = "\t\t";
			} else if (value_Key.length() > 9) {
				tt = "\t\t\t";
			} else if (value_Key.length() > 1) {
				tt = "\t\t\t\t";
			} else if (value_Key.length() == 1) {
				tt = "\t\t\t\t\t";
			}
			stringBuffer.append("\t参数名:" + value_Key + "," + tt + "\t参数值:" + valueMap.get(value_Key));
			stringBuffer.append("\n");
		}
		stringBuffer.append("\n");
		stringBuffer.append("************************************" + className + "类数据输出完毕************************************\n");
		return stringBuffer.toString();
	}

	/**
	 * 获取当前实体的参数值对象
	 * 
	 * @param o
	 * @return
	 */
	public static Map<String, Object> getObjectValueMap(Object o) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String paramName = field.getName();
			if (!paramName.equals("serialVersionUID")) {
				Object paramValue;
				try {
					paramValue = field.get(o);
					valueMap.put(paramName, paramValue);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return valueMap;
	}

	/**
	 * 格式的字符串转Enums
	 * 
	 * @param type
	 * @return
	 */
	public static Enums typeStr2TypeEnums(String type) {
		if (type.equals("string")) {
			return Enums.type_string;
		} else if (type.equals("int")) {
			return Enums.type_int;
		} else if (type.equals("long")) {
			return Enums.type_long;
		} else if (type.equals("float")) {
			return Enums.type_float;
		} else if (type.equals("double")) {
			return Enums.type_double;
		} else if (type.equals("date")) {
			return Enums.type_date;
		} else if (type.equals("time")) {
			return Enums.type_time;
		} else {
			return Enums.type_unknow_type;
		}
	}

	/**
	 * 字符串转换成对象
	 * 
	 * @param valueStr
	 * @param type
	 * @return
	 */
	public static Object string2Object(String valueStr, Enums type) {
		try {
			if (type == Enums.type_string) {
				return valueStr;
			} else if (type == Enums.type_int) {
				return Integer.parseInt(valueStr);
			} else if (type == Enums.type_long) {
				return Long.parseLong(valueStr);
			} else if (type == Enums.type_float) {
				return Float.parseFloat(valueStr);
			} else if (type == Enums.type_double) {
				return Double.parseDouble(valueStr);
			} else if (type == Enums.type_date) {
				Date date = string2Date(valueStr);
				date = utilDate2SQLDate(date);
				return date;
			} else if (type == Enums.type_time) {
				Date date = string2Date(valueStr);
				date = utilDate2SQLDate(date);
				return date;
			} else {
				return Enums.type_unknow_type;
			}
		} catch (NumberFormatException e) {
			LogUtil.logException(e);
			return "NumberFormatException";
		}
	}

	/**
	 * 字符串转成日期格式(date为util类型)
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr) {
		Pattern datePattern;
		SimpleDateFormat simpleDateFormat = simpleDateFormat_Normal;
		for (String sdfKey : dateFormaterMap.keySet()) {
			datePattern = Pattern.compile(sdfKey);
			if (datePattern.matcher(dateStr).matches()) {
				simpleDateFormat = dateFormaterMap.get(sdfKey);
			}
		}
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			LogUtil.logException(e);
			return date;
		}
		return date;
	}

	/**
	 * 从类来获取参数类型:为(参数名,参数类型)<br/>
	 * 参数类型格式：String (int、long、float、double、string、date、object)格式均为小写字母<br/>
	 * 
	 * @param c
	 * @return
	 */
	public static Map<String, String> getParamType(Class<?> c) {
		Map<String, String> paramTypeMap = new HashMap<String, String>();
		Field[] fieldArray = c.getDeclaredFields();// 需要返回的对象的参数集合
		for (Field field : fieldArray) {
			field.getName();
			String fieldName = field.getName();
			String fieldType = field.getType().getSimpleName().toLowerCase();
			paramTypeMap.put(fieldName, fieldType);
		}
		return paramTypeMap;
	}

	/**
	 * 获取参数list
	 * 
	 * @param c
	 * @return
	 */
	public static List<String> getParamName(Class<?> c) {
		List<String> paramNameList = new ArrayList<String>();
		Field[] fieldArray = c.getDeclaredFields();// 需要返回的对象的参数集合
		for (Field field : fieldArray) {
			field.getName();
			String fieldName = field.getName();
			paramNameList.add(fieldName);
		}
		return paramNameList;
	}

	/**
	 * 因为存在代理类,只能通过反射方法获取值(不能通过反射fileds获取值)<br>
	 * 将参数名和参数值放入map中
	 * 
	 * @param o
	 * @param c
	 * @return
	 */
	public static Map<String, Object> getParamMap(Object o, Class<?> c) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (o != null) {
			Method[] methods = c.getMethods();
			try {
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().startsWith("get")) {
						String methodName = method.getName();
						String endStr = methodName.substring(4);
						String firstStr = methodName.substring(3, 4).toLowerCase();
						methodName = firstStr + endStr;
						resultMap.put(methodName, method.invoke(o));
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}// 得到get 方法的值
			// LogUtil.systemOut(resultMap);
		return resultMap;
	}

	public static List<DatabaseParameter> object2Map(Object o, Class<?> c) {
		logger.info("正在将" + c.getSimpleName() + "类 对象解析成List<ParameterMap>对象!:" + o);
		List<DatabaseParameter> paramsList = new ArrayList<DatabaseParameter>();
		if(o==null){
			logger.info("参数对象为null， 直接返回空的List<DatabaseParameter>");
			return paramsList;
		}
		
		Map<String, Object> paramMap = getParamMap(o, c);
		List<String> paramNameList = getParamName(c);
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String paramName = field.getName();
			if (!paramName.equals("serialVersionUID") && paramNameList.contains(paramName)) {
				DatabaseParameter om = new DatabaseParameter();
				om.setName(paramName);
				om.setIsEmpty(false);
				String type = field.getType().getSimpleName().toLowerCase();
				om.setType(type);
				Object parameter;
				try {
					parameter = paramMap.get(paramName);
					om.setValue(parameter);
					// System.out.println(paramName + ":" + parameter + type);
					if (parameter == null) {
						om.setIsEmpty(true);
					}
					if (type.equals("int") || type.equals("float") || type.equals("double") || type.equals("long")) {
						Double d = Double.parseDouble(parameter.toString());
						if (d == -999999999) {
							om.setIsEmpty(true);
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				logger.debug(om.toString());
				paramsList.add(om);
			}
		}
		return paramsList;
	}

	/**
	 * 本方法用于解析任意Object对象<br/>
	 * 可以将任意对象解析成List&#60;ParameterMap>对象<br/>
	 * 该对象包含四个属性分别为：<br/>
	 * 属性名：String类型<br/>
	 * 属性格式：String (int、Long、float、double、string、date、object)格式均为小写字母<br/>
	 * 属性值：实例化对象的值<br/>
	 * 是否为空：true/false<br/>
	 * 默认情况下：int,float,double会有默认值0或0.0,因此，当其值为-999999999时，视其无效<br/>
	 * 其余格式均无默认值，没获取到值的时候是null<br/>
	 * 
	 * @param o
	 *            需要解析的对象
	 * @return List&#60;ParameterMap>
	 */
	public static List<DatabaseParameter> object2MapByFileds(Object o, Class<?> c) {
		logger.info("正在将" + c.getSimpleName() + "类 对象解析成List<ParameterMap>对象!:" + o);
		List<String> paramNameList = getParamName(c);
		Field[] fields = c.getDeclaredFields();
		List<DatabaseParameter> paramsList = new ArrayList<DatabaseParameter>();
		for (Field field : fields) {
			field.setAccessible(true);
			String paramName = field.getName();
			if (!paramName.equals("serialVersionUID") && paramNameList.contains(paramName)) {
				DatabaseParameter om = new DatabaseParameter();
				om.setName(paramName);
				om.setIsEmpty(false);
				String type = field.getType().getSimpleName().toLowerCase();
				om.setType(type);
				Object parameter;
				try {
					parameter = field.get(o);
					om.setValue(parameter);
					// System.out.println(paramName + ":" + parameter + type);
					if (parameter == null) {
						om.setIsEmpty(true);
					}
					if (type.equals("int") || type.equals("float") || type.equals("double") || type.equals("long")) {
						Double d = Double.parseDouble(parameter.toString());
						if (d == -999999999) {
							om.setIsEmpty(true);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				logger.info(om.toString());
				paramsList.add(om);
			}
		}
		return paramsList;
	}

}

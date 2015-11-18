package com.lobinary.platform.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.lobinary.platform.constant.Enums;
import com.lobinary.platform.model.DatabaseParameter;

public class HQLUtil {

	static Logger logger = LogUtil.getLog(HQLUtil.class);
	
	/**
	 * 将参数数组转换成list
	 * 
	 * @param array
	 * @return
	 */
	public static List<DatabaseParameter> array2List(DatabaseParameter[] array) {
		if (array == null) {
			return new ArrayList<DatabaseParameter>();
		} else {
			List<DatabaseParameter> list = new ArrayList<DatabaseParameter>();
			for (DatabaseParameter o : array) {
				list.add(o);
			}
			return list;
		}
	}

	/**
	 * 给定对象和设定的执行方式，自动将其装配好，生成想要执行的HQL语句<br/>
	 * 格式如下：<br/>
	 * select : from User u where u.id=6 <br/>
	 * delete : delete User u where u.id = 6 <br/>
	 * update ： update User u set u.unit = 'a' where u.name='b'
	 * 
	 * @param o
	 *            执行对象
	 * @param executeType
	 *            执行格式（增，删，改，查）
	 * @param isFuzzy
	 *            是否模糊查找
	 * @param list
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static String generateHQL(Class<?> c, Enums execute_type, boolean isFuzzy, List<DatabaseParameter> list) {
		StringBuffer hql = new StringBuffer();
		StringBuffer setStr = new StringBuffer();
		StringBuffer whereStr = new StringBuffer();
		boolean whereIsFirst = true;
		boolean setIsFirst = true;
		String fuzzyStr = "";
		String likeStr = "=";
		if (isFuzzy) {
			fuzzyStr = "%%";
			likeStr = " like ";
		}
		if (execute_type == Enums.execute_type_select) {
			hql.append("from ");
		} else if (execute_type == Enums.execute_type_delete) {
			hql.append("delete ");
		} else if (execute_type == Enums.execute_type_count) {
			hql.append("select count(*) from ");
		} else if (execute_type == Enums.execute_type_update) {
			hql.append("update ");
		} else {
			System.out.println("暂时不支持add功能");
			return null;
		}
		hql.append(c.getSimpleName() + " o");
		for (int i = 0; list != null && i < list.size(); i++) {
			DatabaseParameter pm = list.get(i);
			String type = pm.getType();
			String sign = "";
			if (isFuzzy || type.equals("string")) {
				sign = "'";
			}
			if (!pm.getIsEmpty()) {
				if (pm.getPosition().equals("where")) {
					if (!whereIsFirst) {
						whereStr.append(" and o." + pm.getName() + likeStr + sign + fuzzyStr + pm.getValue() + fuzzyStr + sign);
					} else {
						whereStr.append(" where ");
						whereStr.append("o." + pm.getName() + likeStr + sign + fuzzyStr + pm.getValue() + fuzzyStr + sign);
						whereIsFirst = false;
					}
				} else if (pm.getPosition().equals("set")) {
					if (!setIsFirst) {
						setStr.append(" , o." + pm.getName() + "=" + sign + pm.getValue() + sign);
					} else {
						setStr.append(" set ");
						setStr.append("o." + pm.getName() + "=" + sign + pm.getValue() + sign);
						setIsFirst = false;
					}
				}
			}
		}
		hql.append(setStr).append(whereStr);
		logger.info("生成"+execute_type+"的HQL语句:" + hql.toString());
		return hql.toString();
	}

}

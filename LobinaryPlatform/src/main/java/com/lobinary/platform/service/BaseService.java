package com.lobinary.platform.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lobinary.platform.dao.hibernate.BaseDAO;
import com.lobinary.platform.model.DatabaseParameter;
import com.lobinary.platform.util.JsonUtil;
import com.lobinary.platform.util.LogUtil;
import com.lobinary.platform.util.ReflectsUtil;


@Service("baseService")
public class BaseService {

	public Logger logger = LogUtil.getLog(getClass());
	BaseDAO dao;

	public BaseDAO getDao() {
		return dao;
	}

	@Resource(name = "baseDAO")
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * 提交更改
	 * @param addJsonStr
	 * @param removedJsonStr
	 * @param modifiedJsonStr
	 * @param c
	 */
	@Transactional(readOnly = false)
	public String commiteChange(String addJsonStr, String removedJsonStr, String updatedJsonStr, Class<?> c) {
		String[] addArray = addJsonStr.split("#lobinary#");
		String result = "恭喜您,数据提交成功!";
		for (String addStr : addArray) {
			if (addStr.length() < 2)
				continue;
			Object addObj = JsonUtil.json2Object(addStr, c);
			boolean isSuccess = dao.add(addObj);
			if(isSuccess){
				logger.info("\n添加成功:\n" + ReflectsUtil.object2String(addObj));
			}else{
				logger.info("\n添加失败:\n" + ReflectsUtil.object2String(addObj));
				result = "对不起,添加数据有问题,请检查数据后,重新提交已更改的数据!";
			}
		}
		String[] modifiedArray = updatedJsonStr.split("#lobinary#");
		for (String modifiedStr : modifiedArray) {
			if (modifiedStr.length() < 2)
				continue;
			Object modifiedObj = JsonUtil.json2Object(modifiedStr, c);
			boolean isSuccess = dao.update(modifiedObj);
			if(isSuccess){
				logger.info("\n修改成功:\n" + ReflectsUtil.object2String(modifiedObj));
			}else{
				logger.info("\n修改失败:\n" + ReflectsUtil.object2String(modifiedObj));
				result = "对不起,修改数据有问题,请检查数据后,重新提交已更改的数据!";
			}
		}

		String[] removedArray = removedJsonStr.split("#lobinary#");
		for (String removedStr : removedArray) {
			if (removedStr.length() < 2)
				continue;
			Object removedObj = JsonUtil.json2Object(removedStr, c);
			boolean isSuccess = dao.delete(removedObj);
			if(isSuccess){
				logger.info("\n删除成功:\n" + ReflectsUtil.object2String(removedObj));
			}else{
				logger.info("\n删除失败:\n" + ReflectsUtil.object2String(removedObj));
				result = "对不起,删除数据有问题,请检查数据后,重新提交已更改的数据!";
			}
		}
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param o
	 *            对象
	 * @return 是否添加成功（true/false）
	 */
	@Transactional(readOnly = false)
	public boolean add(Object o) {
		try {
			dao.add(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	public boolean update(Object o) {
		try {
			dao.update(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	public boolean delete(Object o) {
		try {
			dao.delete(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}

	public <T> T get(Class<T> c, DatabaseParameter... array) {
		T result = null;
		try {
			result = dao.getEntity(c, array);
			return result;
		} catch (Exception e) {
			LogUtil.logException(e);
			return result;
		}
	}

	/**
	 * 通过对象实体来分页查询,如果对象为null,则按原生态查询方式查询
	 * 
	 * @param o
	 *            实体对象
	 * @param c
	 *            类
	 * @param isFuzzy
	 *            是否模糊查询
	 * @param page
	 *            页数
	 * @param start
	 *            开始
	 * @param limit
	 *            最多
	 * @return
	 */
	public <T> List<T> queryListByPage(Object o, Class<T> c, boolean isFuzzy, int page, int start, int limit) {
		logger.info("相关参数:isFuzzy:" + isFuzzy + "-page:" + page + "-start:" + start + "-limit:" + limit + "准备查询对象:" + o + "");
		List<T> result = null;
		if (o == null) {
			result = dao.queryList(c, start, limit);
		} else {
			result = dao.queryList(o, c, isFuzzy, start, limit);
		}
		return result;
	}

	public <T> List<T> queryListByPage(Class<T> c, boolean isFuzzy, int page, int start, int limit, DatabaseParameter... array) {
		List<T> result = null;
		result = dao.queryList(c, isFuzzy, start, limit, array);
		return result;
	}

	public int getTotalCount(Object o, Class<?> c, boolean isFuzzy) {
		logger.info("准备获取TotalCount:" + o);
		if (o == null) {
			return dao.getCount(c).intValue();
		} else {
			return dao.getCount(o, c, isFuzzy);
		}
	}

	public Long getTotalCount(Class<?> c, boolean isFuzzy, DatabaseParameter... array) {
		return dao.getCount(c, isFuzzy, array);
	}

	public boolean isExist(Class<?> c, DatabaseParameter... array) {
		try {
			return dao.isExists(c, array);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
	}

	public boolean contains(Object o) {
		try {
			return dao.contains(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
	}

}

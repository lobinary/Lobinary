package com.lobinary.platform.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.lobinary.platform.constant.Enums;
import com.lobinary.platform.model.DatabaseParameter;
import com.lobinary.platform.util.HQLUtil;
import com.lobinary.platform.util.LogUtil;
import com.lobinary.platform.util.ReflectsUtil;

@Component("baseDAO")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseDAO {
	
	Logger logger = LogUtil.getLog(getClass());

	public HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**
	 * 添加实体
	 * 
	 * @param o
	 *            需要添加的实体
	 * @return 是否添加成功
	 */
	public boolean add(Object o) {
		try {
			getHibernateTemplate().save(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}

	/**
	 * 仅用于删除id为int型的对象，其余对象均无法删除成功
	 * 
	 * @param o
	 * @return
	 */
	public boolean delete(Object o) {
		try {
			getHibernateTemplate().delete(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}

	/**
	 * 删除对象，本方法可以提供批量删除支持
	 * 
	 * @param c
	 *            实体类型
	 * @param array
	 *            删除条件
	 * @return 是否成功
	 */
	public boolean delete(Class c, DatabaseParameter... array) {
		try {
			final String hql = HQLUtil.generateHQL(c, Enums.execute_type_delete, false, HQLUtil.array2List(array));
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					int row = query.executeUpdate();
					return row;
				}
			});
			return true;
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
	}

	/**
	 * 升级对象实体，仅限于拥有数字id的实体对象
	 * 
	 * @param o
	 * @return
	 */
	public boolean update(Object o) {
		try {
			getHibernateTemplate().update(o);
		} catch (Exception e) {
			LogUtil.logException(e);
			return false;
		}
		return true;
	}
	
	/**
	 * 本方法用于查询id为数字格式的实体
	 * 
	 * @param c
	 *            要查询的实体类型
	 * @param id
	 *            要查询的实体id
	 * @return 查询的实体
	 */
	public <T> T getEntity(Class<T> c, long id) {
		T result = null;
		try {
			result = getHibernateTemplate().get(c, id);
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return result;
	}

	/**
	 * 本方法一般用于查询id不是数字的实体
	 * 
	 * @param c
	 *            要查询的实体类型
	 * @param array
	 *            查询的条件
	 * @return 返回查询的实体对象（如果为空则返回null）
	 * @throws Exception
	 */
	public <T> T getEntity(Class<T> c, DatabaseParameter... array) throws Exception {
		List<T> list = queryList(c, false, 0, 10, array);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			return null;
		} else {
			throw new RuntimeException("温馨提示，您要查询的原本唯一的对象存在多个对象，请注意检查错误数据以及相关问题！");
		}
	}
	
	/**
	 * 通过参数aray来查询实体是否存在
	 * 
	 * @param c
	 *            要查询的实体类型
	 * @param array
	 *            查询的条件
	 * @return 要查询的实体是否存在（true/false）
	 * @throws Exception
	 *             如果查询结果大于1，则报RuntimeException
	 */
	public boolean isExists(Class c, DatabaseParameter... array) throws Exception {
		List<Object> list = (List<Object>) queryList(c, false, 0, 10, array);
		if (list.size() == 1) {
			return true;
		} else if (list.size() == 0) {
			return false;
		} else {
			throw new RuntimeException("温馨提醒，查找存在方法，返回结果数量大于1");
		}
	}
	
	/**
	 * 本方法仅限有该对象拥有数字id 的对象
	 * @param o
	 * @return
	 */
	public boolean contains(Object o){
		return hibernateTemplate.contains(o);
	}

	/**
	 * 通过给定的HQL语句，进行list查询，返回list（不推荐使用）
	 * 
	 * @param hql
	 *            hql语句
	 * @param start
	 *            起始位置
	 * @param limit
	 *            总条数
	 * @return List《T》
	 */
	@Deprecated
	public <T> List<T> queryList(final String hql, final int start, final int limit) {
		logger.info("正在执行查询列表方法,本方法已经被弃用!请注意使用!");
		List<T> list = null;
		try {
			list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					query.setFirstResult(start);
					query.setMaxResults(limit);
					List<T> list = query.list();
					return list;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return list;
	}

	/**
	 * 根据查询对象以及对象相关参数，获取对应List
	 * 
	 * @param <T>
	 * 
	 * @param o 需要查询的对象
	 * @param ob 查询参数ParameterMap放入List
	 * @return List&#60;Object>
	 */
	public <T> List<T> queryList(Class<T> c, boolean isFuzzy, final int start, final int limit, DatabaseParameter... array) {
		logger.info("正在执行原生态查询列表方法!");
		List<T> queryListResult = null;
		try {
			final String hql = HQLUtil.generateHQL(c, Enums.execute_type_select, isFuzzy, HQLUtil.array2List(array));
			queryListResult = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql.toString());
					query.setFirstResult(start);
					query.setMaxResults(limit);
					List<T> result = query.list();
					return result;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return queryListResult;
	}

	/**
	 * 本方法将利用java反射机制，动态生成模糊查找sql，进行查找 <br/>
	 * 您只需要提供对象模板，我们将对其进行模糊搜索，返回结果List结果集<br/>
	 * 生成类似模板如下:<br/>
	 * "from User u where u.unit like '%%" + unit <br/>
	 * + "%%' and u.username like '%%" + username <br/>
	 * + "%%' and u.telephone like '%%" + telephone <br/>
	 * + "%%' and u.department like '%%" + department<br/>
	 * + "%%' and u.address like '%%" + address + "%%' ";<br/>
	 * 如想跳过过滤条件，请置参数为null，该方法会自动跳过该筛选条件<br/>
	 * 当遇到有初始值int float double的参数时，如想跳过筛选条件，请置成-999999999<br/>
	 * 
	 * @param o
	 * @param c 之所以要增加类,是因为存在代理类引起的问题
	 * @param start
	 * @param limit
	 * @return
	 */
	public <T> List<T> queryList(Object o,Class c, boolean isFuzzy, final int start, final int limit) {
		logger.info("正在执行非原生态查询列表方法");
		List<T> queryListResult = null;
		try {
			final String hql = HQLUtil.generateHQL(c, Enums.execute_type_select, isFuzzy, ReflectsUtil.object2Map(o,c));
			queryListResult = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql.toString());
					query.setFirstResult(start);
					query.setMaxResults(limit);
					List<T> result = query.list();
					return result;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return queryListResult;
	}

	/**
	 * 原生态查询
	 * @param c
	 * @param start
	 * @param limit
	 * @return
	 */
	public <T> List<T> queryList(Class<T> c, final int start, final int limit) {
		logger.info("正在执行原生态查询!");
		List<T> queryListResult = null;
		try {
			final String hql = HQLUtil.generateHQL(c, Enums.execute_type_select, false, new ArrayList<DatabaseParameter>());
			queryListResult = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql.toString());
					query.setFirstResult(start);
					query.setMaxResults(limit);
					List<T> result = query.list();
					return result;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return queryListResult;
	}

	public int getCount(Object o,Class c, boolean isFuzzy) {
		logger.info("正在执行非原生态查询Count值!");
		Long result = 0L;
		try {
			final String totalHQL = HQLUtil.generateHQL(c, Enums.execute_type_count, isFuzzy, ReflectsUtil.object2Map(o,c));
			result = getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = null;
					try {
						query = session.createQuery(totalHQL.toString());
						return query.uniqueResult();
					} catch (Exception e) {
						LogUtil.logException(e);
					}
					return query;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return result.intValue();
	}

	public Long getCount(Class<?> c, boolean isFuzzy, DatabaseParameter[] array) {
		logger.info("正在执行原生态查询Count值!");
		Long result = 0l;
		try {
			final String totalHQL = HQLUtil.generateHQL(c, Enums.execute_type_count, isFuzzy, HQLUtil.array2List(array));
			result = (Long) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(totalHQL.toString());
					return query.uniqueResult();
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return result;
	}

	public Long getCount(Class<?> c) {
		logger.info("正在执行单个class查询Count机制!");
		Long result = 0l;
		try {
			final String totalHQL = HQLUtil.generateHQL(c, Enums.execute_type_count, false, new ArrayList<DatabaseParameter>());
			result = (Long) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(totalHQL.toString());
					return query.uniqueResult();
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return result;
	}

	/**
	 * 执行一般HQL语句
	 * 
	 * @param hql HQL语句
	 * @return 改动行数
	 */
	public int executeSQL(final String hql) {
		logger.info("正在执行一般HQL语句!");
		int row = 0;
		try {
			row = getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					int row = query.executeUpdate();
					return row;
				}
			});
		} catch (Exception e) {
			LogUtil.logException(e);
		}
		return row;
	}

}

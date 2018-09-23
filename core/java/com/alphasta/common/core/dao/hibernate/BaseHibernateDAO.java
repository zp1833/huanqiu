package com.alphasta.common.core.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.alphasta.common.core.dao.support.Page;
import com.alphasta.common.core.util.ReflectUtil;

/**
 * <code>DAO</code>的实现类.
 * 
 * @author Sam
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public class BaseHibernateDAO extends HibernateDaoSupport implements DAO {
	/**
	 * Log,子类可以直接使用，不必重新声明
	 */
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see DAO#evict(java.lang.Object)
	 */
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	/**
	 * @see DAO#find(java.lang.String, java.lang.Object[])
	 */
	public List find(String hql, Object... values) {
		if (values == null || values.length == 0) {
			return getHibernateTemplate().find(hql);
		} else {
			return getHibernateTemplate().find(hql, values);
		}
	}

	/**
	 * @see DAO#pagedQuery(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	public Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize) {
		Criteria c = criteria.getExecutableCriteria(getSession());
		CriteriaImpl impl = (CriteriaImpl) c;

		return this.executePagedQuery(impl, pageNo, pageSize);
	}

	/**
	 * @see DAO#pagedQuery(org.hibernate.criterion.Criteria, int, int)
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		if (criteria == null || !(criteria instanceof CriteriaImpl)) {
			throw new IllegalArgumentException(
					""
							+ "'pagedQuery(org.hibernate.criterion.Criteria, int, int)' error,"
							+ " Argument 'criteria' must not be null and it must "
							+ "be instance of CriteriaImpl");
		}
		CriteriaImpl impl = (CriteriaImpl) criteria;

		return this.executePagedQuery(impl, pageNo, pageSize);
	}

	/**
	 * @see DAO#pagedQuery(java.lang.String, int, int, java.lang.Object[])
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... args) {
		Assert.hasText(hql);
		// 创建查询
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		String countQueryString = " select count (*) "
				+ removeSelect(removeOrders(removeFetchs(hql)));
		List countlist = getHibernateTemplate().find(countQueryString, args);

		int totalCount = 0;
		if (countlist.size() > 1) {
			totalCount = countlist.size(); // Group By的情景...
		} else if (countlist.size() == 1) {
			totalCount = ((Long) countlist.get(0)).intValue();
		}

		if (totalCount < 1) {
			return new Page();
		}
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * @see DAO#getObject(java.lang.Class, java.io.Serializable)
	 */
	public <T> T getObject(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * @see DAO#getObjects(java.lang.Class)
	 */
	public <T> List<T> getObjects(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * @see DAO#isAlreadyExists(java.lang.Object, java.lang.String[])
	 */
	public <T> boolean isAlreadyExists(Object entity, String... names) {
		Assert.notEmpty(names);
		Criteria criteria = getSession().createCriteria(entity.getClass())
				.setProjection(Projections.rowCount());

		try {
			// 循环加入
			for (String name : names) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.
			// 通过hibernate的MetaData接口取得主键名
			String idPropertyName = getSessionFactory().getClassMetadata(
					entity.getClass()).getIdentifierPropertyName();
			if (idPropertyName != null) {
				// 通过反射取得entity的主键值
				Object id = PropertyUtils.getProperty(entity, idPropertyName);
				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
				if (id != null) {
					criteria.add(Restrictions.not(Restrictions.eq(
							idPropertyName, id)));
				}
			}

		} catch (IllegalAccessException e) {
			logger.error("Error when reflection on entity," + e.getMessage());
			return false;
		} catch (InvocationTargetException e) {
			logger.error("Error when reflection on entity," + e.getMessage());
			return false;
		} catch (NoSuchMethodException e) {
			logger.error("Error when reflection on entity," + e.getMessage());
			return false;
		}
		return (Integer) criteria.uniqueResult() > 0;
	}

	/**
	 * @see DAO#removeById(java.lang.Class, java.io.Serializable)
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		removeObject(getObject(entityClass, id));
	}

	/**
	 * @see DAO#removeObject(java.lang.Object)
	 */
	public <T> void removeObject(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * @see DAO#saveObject(java.lang.Object)
	 */
	public <T> void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/**
	 * 执行Criteria分页查询.同时执行Count查询，并构建Page对象
	 * 
	 * @param criteria
	 *            <code>Criteria</code>的实现类。
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页的记录数
	 * @return Instance of <code>Code</code>
	 */
	protected Page executePagedQuery(CriteriaImpl criteria, int pageNo,
			int pageSize) {
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = criteria.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) ReflectUtil.getPrivateProperty(criteria,
					"orderEntries");
			ReflectUtil.setPrivateProperty(criteria, "orderEntries",
					new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 执行查询
		int totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		try {
			ReflectUtil.setPrivateProperty(criteria, "orderEntries",
					orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1) {
			return new Page();
		}

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 删除HSQL中的fetch
	 */
	private static String removeFetchs(String hql) {
		Assert.hasText(hql);
		return hql.replaceAll("fetch ", "");
	}

	public List executeSql(final String sql) {
		return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();				 
			}
		});
	}

	public Object executeUpdate(final String sql) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate(); 
			}
		});
	}
	
}

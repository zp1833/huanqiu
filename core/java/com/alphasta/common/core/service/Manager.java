package com.alphasta.common.core.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.alphasta.common.core.dao.hibernate.DAO;
import com.alphasta.common.core.dao.support.Page;

/**
 * 所有Service层的基础接口。
 * @author Sam
 * @version 1.0
 */

@SuppressWarnings("unchecked")
public interface Manager {
  /**
   * 返回<code>HibernateDAO</code>的实现
   */
  DAO getDao();
  
  /**
   * 判断一个实体对象的某几个字段值是否在数据库中存在
   * @param entity 给定的实体对象
   * @param propertyNames 字段名称s
   * @return 如果已经存在，返回true，否则，false
   * @see {@link  com.alphasta.common.dao.BasicCRUD#isAlreadyExists(Object,
   *      String[])}
   */
  boolean exists(Object entity, String... propertyNames);

  /**
   * 返回泛型参数所指出的类别全部对象
   */
  <T> List<T> get(Class<T> entityClass);

  /**
   * 根据id(prmary key),得到泛型参数所指定的类别的某个对象
   * @param id 给出的id
   * @return id和T所指定的对象，如果不存在，返回null
   */
  <T> T get(Class<T> entityClass, Serializable id);

  /**
   * 保存或更新实体对象
   * @param object 被保存或更新的对象
   */
  void save(Object object);

  /**
   * 删除泛型参数指定的对象
   * @param object 被删除的对象
   */
  void remove(Object object);

  /**
   * 执行Criteria查询.
   * @param criteria 给定的<code>DetachedCriteria</code>对象.
   * @return 查询结果
   */
  List query(DetachedCriteria criteria);

  /**
   * 执行HQL查询
   * @param hql 给出HQL
   * @param objects 所需的查询参数.
   * @return 查询结果.
   */
  List query(String hql, Object... objects);

  /**
   * 执行DetachedCriteria分页查询
   * @param criteria 给定的<code>DetachedCriteria</code>对象.
   * @param pageNo 当前页码,第一页为1,第二页为2...
   * @param pageSize 每页多数记录.
   * @return 查询结果，包括总记录数和查询数据.
   * @see {@link Page}
   */
  Page query(DetachedCriteria criteria, int pageNo, int pageSize);

  /**
   * 执行HQL分页查询.
   * @param hql 给出查询所需的HSQL
   * @param pageNo 当前页码,第一页为1,第二页为2...
   * @param pageSize 每页最多多少记录
   * @param objects 查询参数
   * @return 查询结果，包括总记录数和查询数据.
   * @see {@link Page}
   */
  Page query(String hql, int pageNo, int pageSize, Object... objects);
  
  List executeSql(String sql);
  
  Object executeUpdate(String sql);
}

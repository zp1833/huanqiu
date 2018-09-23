package com.alphasta.common.core.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.alphasta.common.core.dao.support.Page;

/**
 * 用于访问数据库的接口，DAO层通过本接口提供服务.DAO接口提供了一系列用于查询和分页查询的方法.
 * @author Sam
 * 
 * @version 2.0.0
 */
@SuppressWarnings("unchecked")
public interface DAO {
  /**
   * 返回<code>HibernateTemplate</code>对象，主要用于简化代码。
   * @return {@link HibernateTemplate}
   */
  HibernateTemplate getHibernateTemplate();

  /**
   * 消除实体对象与Hibernate Session的关联
   */
  void evict(Object entity);
  
  /**
   * 查询指定的类别
   * 
   */
  <T> List<T> getObjects(Class<T> entityClass);

  /**
   * 根据id(primary key)，查询指定类的某个实例
   * @param entityClass 指定被加载的实体类型.
   * @param id 指定id
   * @return 该类别的一个实例，如果不存在返回null.
   */
  <T> T getObject(Class<T> entityClass, Serializable id);

  /**
   * 保存或更新一个对象
   * @param o 指定的对象
   */
  <T> void saveObject(Object o);

  /**
   * 删除指定的对象
   * @param o 被删除对象
   */
  <T> void removeObject(Object o);

  /**
   * 根据指定的主键删除对象
   * @param entityClass 指定被删除的实体类型.
   * @param id 指定id
   */
  <T> void removeById(Class<T> entityClass, Serializable id);

  /**
   * 判断对象某列的值在数据库中不存在重复
   * 
   * @param names 在POJO里相对应的属性名。
   */
  <T> boolean isAlreadyExists(Object entity, String... names);

  /**
   * 执行包含多个条件的SQL查询（变长参数）.
   * 
   * @param hql 包含条件(x=?)的SQL
   * @param values 各个条件的预期值
   * @return 包含查询结果Pojo的List，或EMPTY_LIST
   */
  List find(String hql, Object... values);

  /**
   * DetachedCriteria分页查询.一次调用同时查询总行数和当前页数据，
   * 子类类需要实现执行count查询的逻辑.
   * 
   * @param criteria <code>DetachedCriteria</code>对象.
   * @param pageNo 当前页码,第一页为1,第二页为2...
   * @param pageSize 每页显示记录数.
   * @return Page object.包含当前页数据和总行数。
   * @see {@link Page}
   */
  Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize);
  
  /**
   * Criteria分页查询..一次调用同时查询总行数和当前页数据，
   * 子类类需要实现执行count查询的逻辑.
   * 
   * @param criteria <code>Criteria</code>对象.
   * @param pageNo 当前页码,第一页为1,第二页为2...
   * @param pageSize 每页显示记录数.
   * @return Page object.包含当前页数据和总行数。
   * @see {@link Page}
   */
  Page pagedQuery(Criteria criteria, int pageNo, int pageSize);

  /**
   * HQL分页查询
   * @param hql 查询hql
   * @param pageNo 当前页码,当前页码,第一页为1,第二页为2...
   * @param pageSize 每页显示记录数.
   * @param args 查询参数
   * @return Page
   */
  Page pagedQuery(String hql, int pageNo, int pageSize, Object... args);
  
  List executeSql(String sql);
  
  Object executeUpdate(String sql);
}

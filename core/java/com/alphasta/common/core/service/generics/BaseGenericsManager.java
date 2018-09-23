package com.alphasta.common.core.service.generics;

import java.io.Serializable;
import java.util.List;

import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.util.GenericsUtil;
/**
 * 范型Manager基类，用于操作一个对象的Manager
 * @author Sam Lee
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseGenericsManager<T> extends BaseManager {
  /**
   * Dao所管理的Entity类型.
   */
  protected Class<T> entityClass;

  /**
   * 取得entityClass的函数.
   * JDK1.4不支持泛型的子类可以抛开Class entityClass,重载此函数达到相同效果。
   */
  protected Class<T> getEntityClass() {
    if (entityClass == null) {
      entityClass = GenericsUtil.getGenericClass(getClass());
    }
    return entityClass;
  }
  /**
   * @see GenericsManager#get()
   */
  public List get() {
    return getDao().getObjects(getEntityClass());
  }
  
  /**
   * @see GenericsManager#get(Serializable)
   */
  public T get(Serializable id) {
    return getDao().getObject(getEntityClass(), id);
  }

}

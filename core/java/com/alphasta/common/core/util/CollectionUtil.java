package com.alphasta.common.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sam version 2.0
 */
@SuppressWarnings("unchecked")
public final class CollectionUtil {
  /** Log */
  private static Log log = LogFactory.getLog(CollectionUtil.class);

 
  /**
   * 将List类型集合的转成Set类型
   * @param list 所需转换的List
   * @return
   */
  public static Set listToSet(List list) {
    Set set = new HashSet();
    for (Object object : list) {
      set.add(object);
    }
    return set;
  }

  /**
   * 将Set类型集合的转成List类型
   * @param set 所需转换的Set
   * @return
   */
  public static List setToList(Set set) {
    List list = new ArrayList();
    for (Object object : set) {
      list.add(object);
    }
    return list;
  }

  /**
   * 将Map中为NULL字段替换为一个其他的Value,
   * @param src 原始Map
   * @param nullValue 用于替换的value
   * @return 替换之后的Map
   */
  public static Map cleanoutNullForMap(Map src, Object nullValue) {
    if (src == null || src.isEmpty()) {
      return src;
    }
    Set keys = src.keySet();

    for (Iterator itr = keys.iterator(); itr.hasNext();) {
      Object key = itr.next();
      if (src.get(key) == null) {
        src.put(key, nullValue);
        log.debug("cleanout Null for " + key);
      }
    }

    return src;
  }

  /**
   * private constructor
   */
  private CollectionUtil() {
  }
}

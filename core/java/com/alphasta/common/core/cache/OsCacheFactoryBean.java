package com.alphasta.common.core.cache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.OSCache;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.FactoryBean;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.Config;

/**
 * 生产OSCache实例的工厂类，OsCahe的创建过程比较复杂，所以使用了工厂模式。
 *  这里，应用的是Spring的FactoryBean
 * @author Sam
 * 
 */



public class OsCacheFactoryBean implements FactoryBean {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(OsCacheFactoryBean.class);

  /** 
   * The <tt>OSCache</tt> refresh period property suffix. 
   */
  public static final String OSCACHE_REFRESH_PERIOD = "refresh.period";
  /** 
   * The <tt>OSCache</tt> CRON expression property suffix. 
   */
  public static final String OSCACHE_CRON = "cron";
  /**
   * oscache configuration.
   */
  private static final Properties OSCACHE_PROPERTIES 
    = new Config().getProperties();
  /**
   * Region name of the cache entry.
   */
  private String regionName;

  /**
   * Builds a new {@link Cache} instance, and gets it's properties 
   * from the OSCache {@link Config}
   * which reads the properties file (<code>oscache.properties</code>) 
   * from the classpath.
   * If the file cannot be found or loaded, an the defaults are used.
   *
   * 
   */
  public Object getObject() throws Exception {

    int refreshPeriod = PropertiesHelper.getInt(
        StringHelper.qualify(regionName, OSCACHE_REFRESH_PERIOD), 
        OSCACHE_PROPERTIES, 
        CacheEntry.INDEFINITE_EXPIRY
      );
      String cron = OSCACHE_PROPERTIES.getProperty(
          StringHelper.qualify(regionName, OSCACHE_CRON));

      // construct the cache        
      Cache cache = new OSCache(refreshPeriod, cron, regionName);
   
    if (log.isDebugEnabled()) {
      log.debug("A oscache instance has been created.");
    }
    return cache;
  }

  /**
   * @see {@link FactoryBean#getObjectType()}
   */
  @SuppressWarnings("unchecked")
  public Class getObjectType() {
    return OSCache.class;
  }

  /**
   * @see {@link FactoryBean#isSingleton()}
   */
  public boolean isSingleton() {
    return false;
  }

  /**
   * @return the regionName
   */
  public String getRegionName() {
    return regionName;
  }

  /**
   * @param regionName the regionName to set
   */
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

}

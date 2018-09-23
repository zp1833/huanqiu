package com.alphasta.common.security.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alphasta.common.security.acegi.resourcedetails.ResourceDetails;

/**
 * 用户管理及相关模块的常量类
 * @author Sam Lee
 *
 */
public final class UserConstants {
  /**
   * 权限操作－URL
   */
  public static final String PERMISSION_OPERATION_URL = "target_url";
  /**
   * 权限操作－函数
   */
  public static final String PERMISSION_OPERATION_FUNC = "target_function";
  /**
   * 权限操作列表
   */
  public static final Map<String, String> PERMISSION_OPERATIONS = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_URL, "URL权限");
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_FUNC, "函数权限");
  }
  /**
   * URL资源
   */
  public static final String RESOURCE_TYPE_URL = ResourceDetails.RES_TYPE_URL;
  /**
   * 函数资源
   */
  public static final String RESOURCE_TYPE_FUNCTION 
    = ResourceDetails.RES_TYPE_FUNCTION;
  
  /**
   * 权限操作列表
   */
  public static final Map<String, String> RESOURCE_TYPES = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    RESOURCE_TYPES.put(RESOURCE_TYPE_URL, "URL资源");
    RESOURCE_TYPES.put(RESOURCE_TYPE_FUNCTION, "函数资源");
  }
  /**
   * 缺省密码，在修改的时候，如果输入缺省密码，则表示使用原来的密码。
   */
  public static final String DEFAULT_PASSWORD = "*********";
  /**
   * 防止实例化
   */
  private UserConstants() {    
  }
}

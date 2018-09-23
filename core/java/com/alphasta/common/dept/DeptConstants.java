package com.alphasta.common.dept;

import com.alphasta.common.Constants;
import com.alphasta.common.core.util.ResourceBundleUtil;

/**
 * 部门常量类
 * @author Sam Lee
 *
 */
public final class DeptConstants {
  /**
   * 顶级部门名称
   */
  public static final String TOP_DEPT_NAME = 
    ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        "global.topDeptName", "石家庄鑫津桥教育");
  
  /**
   * 顶级部门ID
   */
  public static final Integer TOP_DEPT_ID = 0;
  /**
   * 第一个部门的编号
   */
  public static final String FIRST_SERIAL_NO = "01";
  /**
   * private constructor
   */
  private DeptConstants() {
    
  }
}

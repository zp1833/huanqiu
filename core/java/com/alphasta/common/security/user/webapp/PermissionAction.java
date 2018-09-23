package com.alphasta.common.security.user.webapp;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;

import com.alphasta.common.core.webapp.struts2.BaseModelAction;
import com.alphasta.common.security.user.UserConstants;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.service.PermissionManager;
/**
 * 权限Action
 * @author Sam Lee
 */
@SuppressWarnings("serial")
public class PermissionAction extends
    BaseModelAction<Permission, PermissionManager> {
  /**
   * 按权限名称和角色查询
   * @see BaseModelAction#query()
   */
  @Override
	public String query() {
  	if (StringUtils.isBlank(model.getName()) 
  			&& StringUtils.isBlank(model.getDescn())) {
  		items = getManager().query("from Permission");
  	} else {
  		items = getManager().query("from Permission p " 
  				+ "where p.name like ? and p.descn like ?", 
  				new Object[]{MatchMode.ANYWHERE.toMatchString(model.getName()),
  						MatchMode.ANYWHERE.toMatchString(model.getDescn())});
  	}
  	return SUCCESS;
	}

	/**
   * 返回权限操作（权限类型）列表
   */
  public Map<String, String> getOperations() {
    return UserConstants.PERMISSION_OPERATIONS;
  }
}

package com.alphasta.common.security.user.webapp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;

import com.alphasta.common.core.webapp.struts2.BaseModelAction;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.service.RoleManager;

/**
 * 角色Action
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
public class RoleAction extends BaseModelAction<Role, RoleManager> {
	/**
	 * 按名称和描述执行的角色查询
	 * @see BaseModelAction#query()
	 */
	@Override
	public String query() {
		if (StringUtils.isBlank(model.getName()) 
				&& StringUtils.isBlank(model.getDescn())) {
			items = getManager().query("from Role");
		} else {
			items = getManager().query("from Role r where r.name like ? " 
					+ "and r.descn like ?", new Object[]{
						MatchMode.ANYWHERE.toMatchString(model.getName()),
						MatchMode.ANYWHERE.toMatchString(model.getDescn())});
		}
		return SUCCESS;
	}
}

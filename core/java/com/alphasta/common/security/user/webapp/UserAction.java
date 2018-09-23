package com.alphasta.common.security.user.webapp;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;

import com.alphasta.common.core.webapp.struts2.BaseECLimitQueryAction;
import com.alphasta.common.security.user.model.User;
import com.alphasta.common.security.user.service.UserManager;

/**
 * <code>User</code>对象的struts2 action。
 * 
 * @author Sam Lee
 * 
 * 
 */
@SuppressWarnings("serial")
public class UserAction extends BaseECLimitQueryAction<User, UserManager> {

	/**
	 * 用户原(旧)密码
	 */
	private String oldPassword;

	/**
	 * @see BaseModelAction#setupDetachedCriteria()
	 */
	@Override
	protected final DetachedCriteria setupDetachedCriteria() {
		Example example = Example.create(model).setPropertySelector(
		/**
		 * 选择不为null的属性作为查询条件
		 */
		new PropertySelector() {
			public boolean include(Object propertyValue, String propertyName,
					Type type) {
				return propertyValue != null;
			}
		}).enableLike(MatchMode.ANYWHERE);

		DetachedCriteria criteria = super.setupDetachedCriteria().add(example);
		return super.setupSort(criteria);
	}

	/**
	 * 更改用户密码
	 * 
	 * @return
	 */
	public String changePassword() {
		try {
			model.setId(getPrincipal().getId());
			getManager().changePassword(model, oldPassword);
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}

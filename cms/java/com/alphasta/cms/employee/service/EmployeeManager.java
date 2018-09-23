package com.alphasta.cms.employee.service;

import java.util.Date;
import java.util.List;

import com.alphasta.cms.model.Employees;
import com.alphasta.common.core.annotation.LogAnnotation;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.dept.DeptConstants;
import com.alphasta.common.dept.model.Dept;
import com.alphasta.common.security.user.model.User;
import com.alphasta.common.security.user.service.UserManager;

/**
 * 员工管理
 * 
 * @author yyf
 * 
 */
public class EmployeeManager extends BaseManager {

	/**
	 * <code>UserManager</code>
	 */
	private UserManager userManager;

	/**
	 * 删除用户(设置用户状态不可用)
	 */
	@Override
	@LogAnnotation(operateDescribe="删除用户")
	public void remove(Object object) {
		if (object == null || !(object instanceof Employees)) {
			return;
		}

		Employees employee = (Employees) object; // 提取为Employee对象
		User user = employee.getUser(); // 从中得到用户User
		user.setLoginId("(" + user.getLoginId() + ")" + new Date());
		userManager.remove(user);
	}

	/**
	 * 保存员工
	 * 
	 * @see BaseManager#save(Object)
	 */
	@Override
	@LogAnnotation(operateDescribe="编辑用户")
	public void save(Object object) {
		assert (this.userManager != null);
		if (object == null || !(object instanceof Employees)) {
			return;
		}
		Employees employee = (Employees) object; // 提取为Employee对象
		User user = employee.getUser(); // 从中得到用户User
		user.setEmployees(employee);
		employee.setUser(user);
		// 如果部门选择的是“省总工会”则数据库保存为空
		if (employee.getDept().getId().equals(DeptConstants.TOP_DEPT_ID)) {
			employee.setDept(null);
		}
		if (employee.getId() == null) { // 添加
			userManager.save(user);
		} else { // 修改，为了记住其所具有的角色
			super.save(employee);
			// 修改Employee后保存User信息
			User old = userManager.get(User.class, user.getId());
			ReflectUtil.copyProperties(old, user, new String[] { "loginId",
					"password","isManager","type"});
			userManager.save(old);
		}
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * 根据员工获得员工所在部门顶级部门
	 * 
	 * @param emp
	 *            员工
	 * @return 员工所在部门顶级部门
	 */
	public Dept getTopDept(Employees emp) {
		Dept topDept = emp.getDept();
		while (topDept.getParentDept() != null) {
			topDept = topDept.getParentDept();
		}
		return topDept;
	}

	/**
	 * 得到排序最大值
	 * 
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getNextOrderValue() {
		String hql = "select max(orderId) from Employees";
		List list = query(hql);
		if (list.isEmpty() || list.get(0) == null) {
			return 1;
		} else {
			return Integer.valueOf(list.get(0).toString()).intValue() + 1;
		}
	}
}

package com.alphasta.common.dept.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.alphasta.cms.model.Employees;
import com.alphasta.common.core.annotation.LogAnnotation;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.dept.model.Dept;

/**
 * 部门管理Manager
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings("unchecked")
public class DeptManager extends BaseManager {
	/**
	 * 用于计算部门编号
	 */
	private DeptSerialNoManager serialNoManager;

	public void setSerialNoManager(DeptSerialNoManager serialNoManager) {
		this.serialNoManager = serialNoManager;
	}

	/**
	 * 保存部门信息
	 * 
	 * @see BaseManager#save(java.lang.Object)
	 */
	@Override
	@LogAnnotation(operateDescribe="编辑")
	public void save(Object obj) {
		if (obj == null || !(obj instanceof Dept)) {
			return;
		}
		Dept dept = (Dept) obj;
		log.debug("Parent dept " + dept.getParentDept());
		// 查询上级部门并建立双向关联
		Dept parent = dept.getParentDept();
		if (parent != null && parent.getId() != null) {
			log.debug("Parent dept Id " + dept.getParentDept().getId());
			parent = getDao().getObject(Dept.class,
					dept.getParentDept().getId());
			if (parent != null) {
				parent.getChildDepts().add(dept);
				dept.setParentDept(parent);
			}
		} else {
			dept.setParentDept(null); // 处理parentId为null的情况
		}
		if (dept.getId() == null) { // 新建的部门设置部门编号
			dept.setSerialNo(serialNoManager.getSerialNo(dept));
		}
		getDao().evict(parent);
		super.save(dept);
	}

	/**
	 * 删除部门，解除关联关系
	 */

	@Override
	@LogAnnotation(operateDescribe="删除")
	public void remove(Object obj) {
		if (obj == null || !(obj instanceof Dept)) {
			return;
		}
		Dept dept = (Dept) obj;
		// 解除部门-员工关联
		Set<Employees> employees = dept.getEmployees();
		for (Employees emp : employees) {
			emp.setDept(null);
		}
		dept.setEmployees(Collections.EMPTY_SET);
		// 解除部门-角色关联
//		dept.setRoles(Collections.EMPTY_SET);
		// 解除父部门关联
		dept.setParentDept(null);
		// 解除子部门关联
		Set<Dept> children = dept.getChildDepts();
		for (Dept child : children) {
			child.setParentDept(null);
		}
		dept.setChildDepts(Collections.EMPTY_SET);

		super.remove(dept);
	}

	/**
	 * 得到排序最大值
	 * 
	 * @return int
	 */
	public int getNextOrderValue() {
		String hql = "select max(orderId) from Dept";
		List list = query(hql);
		if (list.isEmpty() || list.get(0) == null) {
			return 1;
		} else {
			return Integer.valueOf(list.get(0).toString()).intValue() + 1;
		}
	}

	/**
	 * 
	 * @param deptSort
	 * @return
	 */
	public List<Dept> getOrderDeptBySort(String deptSort) {
		String hql = "from Dept where deptSort = ? order by orderId";
		List<Dept> list = query(hql, deptSort);
		return list;
	}
	
	public List<Dept> getOrderDept(){
		String hql = "from Dept d where d.parentDept is null order by orderId";
		List<Dept> list = query(hql);
		return list;
	}
	
	public List<Dept> getOrderDeptById(Integer id){
		String hql = "from Dept d where d.parentDept.id=? order by orderId";
		List<Dept> list = query(hql,id);
		return list;
	}
}

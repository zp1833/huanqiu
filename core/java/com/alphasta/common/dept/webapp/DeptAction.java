package com.alphasta.common.dept.webapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alphasta.cms.CmsConstants;
import com.alphasta.cms.model.Employees;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.core.webapp.struts2.BaseModelAction;
import com.alphasta.common.dept.DeptConstants;
import com.alphasta.common.dept.model.Dept;
import com.alphasta.common.dept.service.DeptManager;
import com.alphasta.common.dept.service.DeptSerialNoManager;

/**
 * 部门管理Action
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class DeptAction extends BaseModelAction<Dept, DeptManager> {
	/**
	 * 当前上级部门ID
	 */
	private Integer parentId;

	/**
	 * 部门序列号管理器
	 */
	private DeptSerialNoManager serialNoManager;

	/**
	 * 用于查询的部门名称
	 */

	private String deptType = CmsConstants.INNER;

	/** 链接排序编号 格式为 编号:序号,编号:序号 */
	private String seqNoList;

	private String up;


	/**
	 * 部门查询。根据指定的上级部门id(通过{@link #parentId}属性)，查询下级部门。 如果{@link #parentId}为null,则查询顶级部门（没有上级部门的）
	 */
	@Override
	public String query() {
		if ("up".equals(up) && null != model.getId()) {
			items = getManager().query("from Dept d where d.id=?",
					model.getId());
			return SUCCESS;
		}
		if (parentId == null) {
			if (model.getId() == null || model.getId() == 0) {
				items = getManager().query(
						"from Dept d where  d.deptSort = ? and d.parentDept is null order by orderId",
						deptType);
			} else {
				items = getManager()
						.query(
								"from Dept d where d.id = ? and d.deptSort = ? order by orderId",
								model.getId(), deptType);
			}
		} else {
			if (model.getId() == null) {
				items = getManager().query(
						"from Dept d where d.parentDept.id = ? "
								+ "and d.deptSort = ? order by orderId",
						new Object[] { parentId, deptType });
			} else {
				items = getManager()
						.query(
								"from Dept d where d.parentDept.id = ? "
										+ " and d.id = ? and d.deptSort = ? order by orderId",
								new Object[] { parentId, model.getId(),
										deptType });
			}
		}

		return SUCCESS;
	}

	/**
	 * 根据指定的父部门id查询子部门
	 */
	private List<Dept> getByParentId(Integer parentDeptId) {
		List list = Collections.EMPTY_LIST;
		if (parentDeptId == null
				|| parentDeptId.equals(DeptConstants.TOP_DEPT_ID)) {
			list = getManager().query("from Dept d where d.parentDept is null order by orderId");
		} else {
			list = getManager().query("from Dept d where d.parentDept.id = ? order by orderId",
					parentDeptId);
		}

		return list;
	}

	/**
	 * 返回部门树形列表，每一个部门用一个<code>java.util.Map</code>表示，子部门
	 * 用Map的“childNodes”key挂接一个<code>java.util.List</code>.<br>
	 * 本方法供DWR调用，Map中key符合jsam dojo Tree的要求。
	 * 
	 * @param parent
	 *            父部门，如果为null，则表示顶级部门
	 * @param nested
	 *            是否递归查询子部门，true表示递归查询子部门
	 * @return
	 */
	public Map getDeptTree(Map parent, boolean nested) {
		if (parent == null || parent.isEmpty() || parent.get("id") == null) {
			parent = new HashMap();
			parent.put("text", DeptConstants.TOP_DEPT_NAME);
			parent.put("id", DeptConstants.TOP_DEPT_ID);
		}
		// 得到子部门
		List<Dept> depts = this.getByParentId((Integer) parent.get("id"));

		log.debug("Dept " + parent.get("text") + " has " + depts.size()
				+ " children.");
		// 转换所有子部门为Map对象，一来防止dwr造成延迟加载，
		// 二来可以符合jsam.Tree的数据要求.
		List children = new ArrayList();
		for (Iterator<Dept> itr = depts.iterator(); itr.hasNext();) {
			Dept dept = itr.next();
			Map child = new HashMap();
			child.put("id", dept.getId());
			child.put("text", dept.getName());
			if (nested) { // 递归查询子部门
				child = this.getDeptTree(child, nested);
			}
			children.add(child);
		}
		if (!children.isEmpty()) {
			parent.put("childNodes", children);
		}

		return parent;
	}
	
	/**
	 * @return 当前上级部门ID
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 得到当前部门（通过{@link #parentId}指定）的上级部门.
	 */
	public Dept getParent() {
		if (this.parentId == null
				|| this.parentId.equals(DeptConstants.TOP_DEPT_ID)) {
			Dept dept = new Dept();
			dept.setName(DeptConstants.TOP_DEPT_NAME);
			return dept;
		}

		return getManager().get(Dept.class, parentId);
	}

	/**
	 * 覆盖父类，处理父部门ID为{@link DeptConstants#TOP_DEPT_ID}的情况。
	 */
	@Override
	public String save() {
		if (null == model.getName() || "".equals(model.getName())) {
			this.addFieldError("model.name", "部门名称不能为空");
			return "input";
		}
		List depts = this.getManager().query(
				"select id from Dept where name=?", model.getName());
		if (depts.size() > 0) {
			Integer params = (Integer) depts.get(0);
			if (!params.equals(model.getId())) {
				this.addFieldError("model.name", "此部门名称已存在");
				return "input";
			}
		}

		// 设置排序值
		if (model.getId() == null) {
			model.setOrderId(getManager().getNextOrderValue());
		}

		// 如果页面选择了顶级部门作为父部门，则设置父部门为null
		if (model.getParentDept() != null
				&& model.getParentDept().getId() != null
				&& model.getParentDept().getId().equals(
						DeptConstants.TOP_DEPT_ID)) {
			model.setParentDept(null);
		}
		if (model.getParentDept() == null
				|| model.getParentDept().getId() == null) {
			log.debug("保存第一级部门.");
		}

		return super.save();
	}

	/**
	 * 得到部门排序列表
	 */
	public List<Dept> getOrderDepts() {
		if(null == model.getId() || "".equals(model.getId()) || 0 == model.getId()){
			return getManager().getOrderDept();
		}else{
			return getManager().getOrderDeptById(model.getId());
		}
		
	}

	/**
	 * 保存部门排序
	 */
	public String saveOrderDept() {
		if (seqNoList == null) {
			return SUCCESS;
		}
		// 得到页面上排序结果数组
		for (String orderDept : seqNoList.split(",")) {
			String[] order = orderDept.split(":");
			Dept dept = getManager().get(Dept.class, Integer.valueOf(order[0]));
			dept.setOrderId(Integer.valueOf(order[1]));
			getManager().save(dept);
		}
		return SUCCESS;
	}

	/**
	 * 处理parentDept为null的情况
	 */
	@Override
	public String edit() {
		Serializable id = (Serializable) ReflectUtil.get(model, "id");
		if (id != null) {
			model = getManager().get(getEntityClass(), id);
			if (model.getParentDept() == null) {
				Dept dept = new Dept(); // 构建一个父部门
				dept.setId(DeptConstants.TOP_DEPT_ID);
				dept.setName(DeptConstants.TOP_DEPT_NAME);
				model.setParentDept(dept);
				getManager().getDao().evict(model); // 将dept脱离hibernate
				log.debug("编辑第一级部门");
			}
			return SUCCESS;
		}

		return INPUT;
	}

	public List<Employees> getEmployeesByDept(Integer deptid) {
		if (deptid == null)
			return Collections.EMPTY_LIST;
		List<Employees> list = this.getManager().query(
				"from Employees e where e.dept.id=?", new Object[] { deptid });
		return list;
	}

	public String remove() {
		String returnValue = super.remove();
		return returnValue;
	}

	/**
	 * 重置所有部门编号
	 */
	public String updateSerialNo() {
		serialNoManager.updateAllSerialNo();
		return SUCCESS;
	}

	public DeptSerialNoManager getSerialNoManager() {
		return serialNoManager;
	}

	public void setSerialNoManager(DeptSerialNoManager serialNoManager) {
		this.serialNoManager = serialNoManager;
	}

	public String getSeqNoList() {
		return seqNoList;
	}

	public void setSeqNoList(String seqNoList) {
		this.seqNoList = seqNoList;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

}

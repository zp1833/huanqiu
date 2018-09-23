package com.alphasta.cms.employee.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alphasta.cms.CmsConstants;
import com.alphasta.cms.employee.service.EmployeeManager;
import com.alphasta.cms.model.Employees;
import com.alphasta.cms.model.StuInfo;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.webapp.struts2.BaseECLimitQueryAction;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.model.User;

/**
 * 员工Action
 * 
 * @author DU
 */
@SuppressWarnings("serial")
public class EmployeeAction extends
		BaseECLimitQueryAction<Employees, EmployeeManager> {
	/**
	 * 上传文件保存路径
	 */
	public static final String EMPLOYEE_PHOTO_FOLDER = "/uploadFiles/employee/";

	/**
	 * 链接排序编号 格式为 编号:序号,编号:序号
	 */
	private String seqNoList;

	/**
	 * 上传照片
	 */
	private File image;

	/**
	 * 照片原来名称
	 */
	private String imageFileName;
	/** 当前页数 */
	@SuppressWarnings("unused")
	private int pno;

	/**
	 * 保存员工
	 * 
	 * @see BaseManager#save(Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String save() {
		if (getManager().exists(model.getUser(), "loginId", "status")) {
			addActionError("登录名：" + model.getUser().getLoginId() + "已存在");
			return INPUT;
		}
		String file = doUpload(image, imageFileName, EMPLOYEE_PHOTO_FOLDER);
		if (model != null && file != null) {
			log.debug("save photo success" + file);
			model.setPhoto(file);
		}
		if (model != null && model.getId() == null) {
			model.setOrderId(getManager().getNextOrderValue());
		}

		String returnValue = super.save();
		
		String insteadUser = this.getRequest().getParameter("insteadUser");
		if(StringUtils.isNotBlank(insteadUser)) {
			List<StuInfo> list = this.getManager().query("from StuInfo where advisor = ?", insteadUser);
			for(StuInfo stu : list) {
				stu.setAdvisor(model.getName());
				this.getManager().save(stu);
			}
		}

		return returnValue;
	}

	/**
	 * @return 测试用户名是否可用
	 */
	public String checkLoginId() {
		if (StringUtils.isBlank(model.getUser().getLoginId())) {
			addActionError("登录名为空");
		} else {
			if (getManager().exists(model.getUser(), "loginId")) {
				addActionError("登录名：" + model.getUser().getLoginId() + "已存在");
			} else {
				addActionError("登录名：" + model.getUser().getLoginId() + "可以使用");
			}
		}
		return INPUT;
	}

	public String remove() {
		String returnValue = super.remove();
		return returnValue;
	}

	@Override
	protected DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria c = DetachedCriteria.forClass(Employees.class);
		c.createAlias("user", "user").add(Restrictions.eq("user.status", "1"));
		if (StringUtils.isNotBlank(model.getName())) {
			c.add(Restrictions.like("name", "%"+model.getName()+"%"));
		}
		if (model.getDept() != null && model.getDept().getId() != null) {
			c.add(Restrictions.eq("dept.id", model.getDept().getId()));
		}
		return c;
	}

	/**
	 * 得到领导的排序列表
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> getOrderEmployee() {
		List<Employees> employeeList = new ArrayList<Employees>();
		List<Role> rolesList = getManager().query(
				"from Role r where r.name = ?", CmsConstants.ROLE_LEADERSHIP);
		if (rolesList.size() != 0) {
			Role role = rolesList.get(0);
			Set<User> users = role.getUsers();
			Iterator itr = users.iterator();
			while (itr.hasNext()) {
				User user = (User) itr.next();
				employeeList.add(user.getEmployees());
			}
		}

		// 对员工的列表进行排序
		for (int i = 0; i < employeeList.size(); i++) {
			for (int j = i + 1; j < employeeList.size(); j++) {
				if (employeeList.get(i).getOrderId() > employeeList.get(j)
						.getOrderId()) {
					Employees emp = employeeList.get(i);
					employeeList.set(i, employeeList.get(j));
					employeeList.set(j, emp);
				}
			}
		}
		return employeeList;
	}

	/**
	 * 保存排序后的员工
	 */
	public String saveOrder() {
		if (seqNoList == null) {
			return SUCCESS;
		}
		// 得到页面上排序结果数组
		for (String ordercCatalog : seqNoList.split(",")) {
			String[] emp = ordercCatalog.split(":");
			Employees employee = getManager().get(Employees.class,
					Integer.valueOf(emp[0]));
			employee.setOrderId(Integer.valueOf(emp[1]));
			getManager().save(employee);
		}
		return SUCCESS;
	}

	/**
	 * 返回学历操作（学历）列表
	 */
	public Map<String, String> getDegreeMap() {
		return CmsConstants.DEGREE_MAP;
	}

	/**
	 * 返回性别操作
	 */
	public Map<String, String> getSexMap() {
		return CmsConstants.SEX_MAP;
	}

	/**
	 * 返回婚姻操作
	 */
	public Map<String, String> getMarriedMap() {
		return CmsConstants.MARRIED_MAP;
	}

	/**
	 * 返回政治面貌操作
	 */
	public Map<String, String> getPoliticalMap() {
		return CmsConstants.POLITICAL_MAP;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getSeqNoList() {
		return seqNoList;
	}

	public void setSeqNoList(String seqNoList) {
		this.seqNoList = seqNoList;
	}

	public int getPno() {
		return this.getPageNo();
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

}
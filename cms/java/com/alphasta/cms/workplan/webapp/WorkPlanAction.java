package com.alphasta.cms.workplan.webapp;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alphasta.cms.model.Employees;
import com.alphasta.cms.model.WorkPlan;
import com.alphasta.cms.workplan.service.WorkPlanManager;
import com.alphasta.common.core.webapp.struts2.BaseECLimitQueryAction;
import com.alphasta.common.security.user.model.Role;

/**
 * 工作计划管理action
 * 
 * @author zhaopeng
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class WorkPlanAction extends
		BaseECLimitQueryAction<WorkPlan, WorkPlanManager> {

	private Date beginStartDate;

	private Date endStartDate;
	
	private Date beginEndDate;
	
	private Date endEndDate;

	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}

	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}

	public Date getBeginEndDate() {
		return beginEndDate;
	}

	public void setBeginEndDate(Date beginEndDate) {
		this.beginEndDate = beginEndDate;
	}

	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}

	public String saveWorkPlan() {
		if (model.getId() == null) {
			model.setEmployee(this.getUser().getEmployees());
		}
		if (model.getEmployee() != null && model.getEmployee().getId() != null) {
			model.setEmployee(this.getManager().get(Employees.class,
					model.getEmployee().getId()));
		}
		this.save();
		return SUCCESS;
	}

	@Override
	protected DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria c = DetachedCriteria.forClass(WorkPlan.class);

		if (beginStartDate != null)
			c.add(Restrictions.ge("startDate", beginStartDate));
		if (endStartDate != null)
			c.add(Restrictions.le("startDate", endStartDate));
		
		if (beginEndDate != null)
			c.add(Restrictions.ge("endDate", beginEndDate));
		if (endEndDate != null)
			c.add(Restrictions.le("endDate", endEndDate));

		if (!this.isAdmin(this.getUser().getRoles())
				&& !this.isOrAdmin(this.getUser().getRoles()))
			c.add(Restrictions.eq("employee", this.getUser().getEmployees()));
		
		if(this.isAdmin(this.getUser().getRoles()) || this.isOrAdmin(this.getUser().getRoles())) {
			if(model.getEmployee() != null && model.getEmployee().getId() != null) {
				c.add(Restrictions.eq("employee.id", model.getEmployee().getId()));
			}
		}
		
		this.setupSort(c);
		
		return c;
	}

	private boolean isAdmin(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_ADMIN")) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	private boolean isOrAdmin(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_ORADMIN")) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	public String removeWorkPlan(String[] planArray) {
		try {
			for (int i = 0; i < planArray.length; i++) {
				Integer planId = Integer.parseInt(planArray[i]);
				WorkPlan workPlan = (WorkPlan) getManager().get(WorkPlan.class,
						planId);
				this.getManager().remove(workPlan);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	public List getAllEmps() {
		return this.getManager().query(
				"from Employees emp where emp.user.status = '1'");
	}

}

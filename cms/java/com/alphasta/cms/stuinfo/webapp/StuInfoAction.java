package com.alphasta.cms.stuinfo.webapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Sort;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alphasta.cms.model.Employees;
import com.alphasta.cms.model.StuInfo;
import com.alphasta.cms.stuinfo.service.StuInfoManager;
import com.alphasta.common.Constants;
import com.alphasta.common.core.webapp.struts2.BaseECLimitQueryAction;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.model.User;

/**
 * 学员信息管理action
 * 
 * @author zhaopeng
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class StuInfoAction extends BaseECLimitQueryAction<StuInfo, StuInfoManager> {

	private String name;

	private String type;

	private String course;

	private String advName;

	private String currPlan;

	private String currGrade;

	private Date beginAdvDate;

	private Date endAdvDate;

	private Date beginSignDate;

	private Date endSignDate;

	private Date beginVisitDate;

	private Date endVisitDate;

	private Date visitDate;

	private Date beginJoinDate;

	private Date endJoinDate;

	private int currentPageNo;

	private String modifyFlag;

	private String showType;

	/**
	 * 自定义显示的字段
	 */
	private String definedField;

	/**
	 * 自定义查询条件
	 */
	private String definedSearch;

	public String saveStuInfo() {
		this.save();

		if (this.isRec(this.getUser().getRoles()) && !"member".equals(showType))
			return null;

		return SUCCESS;
	}

	@Override
	protected DetachedCriteria setupDetachedCriteria() {

		if ("modify".equals(modifyFlag)) {
			if (getRequest().getSession().getAttribute("name") != null) {
				this.name = new String(getRequest().getSession().getAttribute("name").toString());
			}
			if (getRequest().getSession().getAttribute("course") != null) {
				this.course = getRequest().getSession().getAttribute("course").toString();
			}
			if (getRequest().getSession().getAttribute("advName") != null) {
				this.advName = getRequest().getSession().getAttribute("advName").toString();
			}
			if (getRequest().getSession().getAttribute("type") != null) {
				this.type = getRequest().getSession().getAttribute("type").toString();
			}
			if (getRequest().getSession().getAttribute("currPlan") != null) {
				this.currPlan = getRequest().getSession().getAttribute("currPlan").toString();
			}
			if (getRequest().getSession().getAttribute("currGrade") != null) {
				this.currGrade = getRequest().getSession().getAttribute("currGrade").toString();
			}
		} else {
			this.getRequest().getSession().removeAttribute("name");
			this.getRequest().getSession().removeAttribute("course");
			this.getRequest().getSession().removeAttribute("advName");
			this.getRequest().getSession().removeAttribute("type");
			this.getRequest().getSession().removeAttribute("currPlan");
			this.getRequest().getSession().removeAttribute("currGrade");
		}

		DetachedCriteria c = DetachedCriteria.forClass(StuInfo.class);

		if ("0".equals(showType)) {
			c.add(Restrictions.or(Restrictions.not(Restrictions.in("stuType", new String[] { "已签约", "沉默客户", "长期客户" })), Restrictions
					.isNull("stuType")));

			c.add(Restrictions.eq("isStu", "1"));
		} else if ("1".equals(showType)) {
			c.add(Restrictions.eq("stuType", "已签约"));
			c.add(Restrictions.eq("isStu", "1"));
		} else if ("2".equals(showType)) {
			c.add(Restrictions.eq("stuType", "沉默客户"));
			c.add(Restrictions.eq("isStu", "1"));
		} else if ("3".equals(showType)) {
			c.add(Restrictions.eq("stuType", "长期客户"));
			c.add(Restrictions.eq("isStu", "1"));
		} else if ("member".equals(showType)) {
			c.add(Restrictions.eq("isMember", "1"));
		} else {
			c.add(Restrictions.eq("isStu", "1"));
		}

		if (this.isAdv(this.getUser().getRoles())) {
			if ("1".equals(getUser().getEmployees().getIsDeptAdmin())) {
				List<Employees> deptEmps = this.getManager().query("from Employees where dept.id = ?", getUser().getEmployees().getDept().getId());
				List deptEmpNames = new ArrayList();
				for (Employees emp : deptEmps) {
					deptEmpNames.add(emp.getName());
				}
				c.add(Restrictions.in("advisor", deptEmpNames));
			} else {
				c.add(Restrictions.eq("advisor", getUser().getEmployees().getName()));
			}
		}
		
		if(this.isRec(this.getUser().getRoles()) && !this.isRecAll(this.getUser().getRoles())) {
			c.add(Restrictions.eq("recEmp", getUser().getEmployees().getName()));
		}

		if (StringUtils.isNotBlank(advName)) {
			c.add(Restrictions.eq("advisor", advName));
		}

		if (StringUtils.isNotBlank(currPlan)) {
			c.add(Restrictions.eq("currentPlan", currPlan));
		}

		if (StringUtils.isNotBlank(currGrade)) {
			c.add(Restrictions.eq("currentGrade", currGrade));
		}

		if (StringUtils.isNotBlank(type)) {
			c.add(Restrictions.eq("stuType", type));
		}

		if (StringUtils.isNotBlank(name)) {
			c.add(Restrictions.like("stuName", "%" + name + "%"));
		}

		if (StringUtils.isNotBlank(course)) {
			c.add(Restrictions.like("advCourse", "%" + course + "%"));
		}

		if (beginAdvDate != null)
			c.add(Restrictions.ge("advDate", beginAdvDate));
		if (endAdvDate != null)
			c.add(Restrictions.le("advDate", endAdvDate));

		if (beginSignDate != null)
			c.add(Restrictions.ge("dealDate", beginSignDate));
		if (endSignDate != null)
			c.add(Restrictions.le("dealDate", endSignDate));

		if (beginVisitDate != null)
			c.add(Restrictions.ge("nextVisitTime", beginVisitDate));
		if (endVisitDate != null)
			c.add(Restrictions.le("nextVisitTime", endVisitDate));

		if (StringUtils.isNotBlank(this.definedSearch)) {
			String[] params = this.definedSearch.split("\\|\\$\\|");
			if (params.length >= 2) {
				if ("recentTraceTime".equals(params[0])) {
					try {
						Date traceDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[1]);
						c.add(Restrictions.eq(params[0], traceDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if("contact".equals(params[0])) {
					c.add(Restrictions.or(Restrictions.like("contact", "%" + params[1] + "%"), Restrictions.like("contact1", "%" + params[1] + "%")));
				} else {
					c.add(Restrictions.like(params[0], "%" + params[1] + "%"));
				}
			}
		}

		// c.addOrder(Order.desc("advDate"));

		this.setupSort(c);

		return c;
	}

	public String edit() {

		String value = super.edit();

		this.getRequest().getSession().setAttribute("name", name);
		this.getRequest().getSession().setAttribute("course", course);
		this.getRequest().getSession().setAttribute("advName", advName);
		this.getRequest().getSession().setAttribute("type", type);
		this.getRequest().getSession().setAttribute("currPlan", currPlan);
		this.getRequest().getSession().setAttribute("currGrade", currGrade);

		return value;
	}

	public String listReturnVisitDetails() {

		User user = getUser();
		Sort sort = getSort();

		if (visitDate == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			visitDate = new Date(c.getTime().getTime() - 24 * 60 * 60 * 1000);
		}

		String hql = "from StuInfo where recentTraceTime = ?";

		if (this.isAdv(user.getRoles())) {
			if ("1".equals(user.getEmployees().getIsDeptAdmin())) {
				List<String> advNames = getManager().query("select name from Employees where dept.id = ?", user.getEmployees().getDept().getId());
				StringBuffer advNameStr = new StringBuffer();
				advNameStr.append("(");
				for (int i = 0; i < advNames.size(); i++) {
					advNameStr.append("'").append(advNames.get(i)).append("'");
					if (i < advNames.size() - 1)
						advNameStr.append(",");
				}
				advNameStr.append(")");
				hql += " and advisor in " + advNameStr;
			} else {
				hql += " and advisor = '" + user.getEmployees().getName() + "'";
			}
		}
		
		if (sort != null && StringUtils.isNotBlank(sort.getProperty())) {
			if (sort.getSortOrder().equals("asc")) {
				hql += " order by " + sort.getProperty() + " asc";
			} else {
				hql += " order by " + sort.getProperty() + " desc";
			}
		}

		this.items = this.getManager().query(hql, visitDate);
		return SUCCESS;
	}

	/**
	 * 判断登录用户角色是否为顾问
	 * 
	 * @return
	 */
	private boolean isAdv(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals(Constants.ROLE_ADV)) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	private boolean isRec(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_REC")) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	private boolean isRecAll(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_REC_ALL")) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@SuppressWarnings("unused")
	private boolean isAdmin(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals(Constants.ROLE_ADMIN)) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	public String removeStuInfos(String[] stusArray) {
		try {
			for (int i = 0; i < stusArray.length; i++) {
				Integer stuId = Integer.parseInt(stusArray[i]);
				StuInfo stuInfo = (StuInfo) getManager().get(StuInfo.class, stuId);
				this.getManager().remove(stuInfo);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	/**
	 * 批量转顾问
	 * @param stusArray
	 * @param advName
	 * @return
	 */
	public String changeStuInfos(String[] stusArray, String advName) {
		try {
			for (int i = 0; i < stusArray.length; i++) {
				Integer stuId = Integer.parseInt(stusArray[i]);
				StuInfo stuInfo = (StuInfo) getManager().get(StuInfo.class, stuId);
				stuInfo.setAdvisor(advName);
				this.getManager().save(stuInfo);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	public List getAdvEmps() {
		return this.getManager().query("from Employees emp where (emp.dept.id=? or emp.dept.parentDept.id=?) and emp.user.status = '1'",
				new Object[] { Constants.ADV_DEPT_ID, Constants.ADV_DEPT_ID });
	}

	public List getDocEmps() {
		return this.getManager()
				.query("from Employees emp where (emp.dept.id=? or emp.isDoDoc='1') and emp.user.status = '1'", Constants.DOC_DEPT_ID);
	}

	public List getAllEmps() {
		return this.getManager().query("from Employees emp where emp.user.status = '1'");
	}

	public Date getBeginAdvDate() {
		return beginAdvDate;
	}

	public void setBeginAdvDate(Date beginAdvDate) {
		this.beginAdvDate = beginAdvDate;
	}

	public Date getEndAdvDate() {
		return endAdvDate;
	}

	public void setEndAdvDate(Date endAdvDate) {
		this.endAdvDate = endAdvDate;
	}

	public Date getBeginSignDate() {
		return beginSignDate;
	}

	public void setBeginSignDate(Date beginSignDate) {
		this.beginSignDate = beginSignDate;
	}

	public Date getEndSignDate() {
		return endSignDate;
	}

	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}

	public String getDefinedField() {
		return definedField;
	}

	public void setDefinedField(String definedField) {
		this.definedField = definedField;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getDefinedSearch() {
		return definedSearch;
	}

	public void setDefinedSearch(String definedSearch) {
		this.definedSearch = definedSearch;
	}

	public Date getBeginVisitDate() {
		return beginVisitDate;
	}

	public void setBeginVisitDate(Date beginVisitDate) {
		this.beginVisitDate = beginVisitDate;
	}

	public Date getEndVisitDate() {
		return endVisitDate;
	}

	public void setEndVisitDate(Date endVisitDate) {
		this.endVisitDate = endVisitDate;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	public String getCurrPlan() {
		return currPlan;
	}

	public void setCurrPlan(String currPlan) {
		this.currPlan = currPlan;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getCurrGrade() {
		return currGrade;
	}

	public void setCurrGrade(String currGrade) {
		this.currGrade = currGrade;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Date getBeginJoinDate() {
		return beginJoinDate;
	}

	public void setBeginJoinDate(Date beginJoinDate) {
		this.beginJoinDate = beginJoinDate;
	}

	public Date getEndJoinDate() {
		return endJoinDate;
	}

	public void setEndJoinDate(Date endJoinDate) {
		this.endJoinDate = endJoinDate;
	}

}

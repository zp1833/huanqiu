package com.alphasta.cms.stuinfo.webapp.taglibs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alphasta.cms.model.Employees;
import com.alphasta.cms.model.StuInfo;
import com.alphasta.common.Constants;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.security.user.UserUtil;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.model.User;

/**
 * 每日回访提醒
 * 
 * @author zp
 * 
 */
@SuppressWarnings("serial")
public class RemindTag extends BodyTagSupport {

	private Integer isDeptAdmin;

	/*
	 * @see BodyTagSupport#doEndTag()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			User user = UserUtil.getPrincipal(request);
			BaseManager manager = (BaseManager) getBean("stuInfoManager");
			String username = user.getEmployees().getName();

			user = manager.get(User.class, user.getId());

			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			List<StuInfo> list = null;

			if(isDeptAdmin != null && isDeptAdmin.intValue() == 1) { // 部门主管显示本部门下工作考核情况
				list = manager.query("from StuInfo where nextVisitTime = ? order by advisor", new Date(c.getTime().getTime() - 24 * 60 * 60 * 1000));
				Object[] deptIds = new Object[] { user.getEmployees().getDept().getId(), Constants.ADV_DEPT_ID };// 九派校区、中山校区、移民
				List<Employees> advisors = manager.query("from Employees e where (e.dept.id = ? or e.dept.id = ?)and e.user.status='1'", deptIds);

				for (int i = 0; i < advisors.size(); i++) {
					pageContext.getOut().print("<tr><td class='tdborder' height='30px'><b><font color='red' size='2'>" + advisors.get(i).getName() + ":</font></b></td></tr>");
					int count = 1;

					for (int j = 0; j < list.size(); j++) {
						if (advisors.get(i).getName().equals(list.get(j).getAdvisor())) {
							pageContext.getOut().print("<tr><td class='tdborder' height='30px'><span style='float:left' class='STYLE10'>");
							pageContext.getOut().print(
									"&nbsp;&nbsp;&nbsp;&nbsp;" + (count++) + "、<a href='../admin/stuinfo/editStuInfo.do?flag=stu&model.id=" + list.get(j).getId() + "'>姓名：" + list.get(j).getStuName());
							pageContext.getOut().print("，联系方式：" + list.get(j).getContact() + "</a></span>");
							if (c.getTime().equals(list.get(j).getRecentTraceTime())) {
								pageContext.getOut().print("<font color='green' size='2' style='float:right'>&nbsp;(已完成)</font>");
							} else {
								pageContext.getOut().print("<font color='red' size='2' style='float:right'>&nbsp;(未完成)</font>");
							}
							pageContext.getOut().print("</td></tr>");
						}
					}
				}
				
			} else if (this.isAdmin(user.getRoles()) || this.isOrAdmin(user.getRoles()) || this.isAudit(user.getRoles())) { // 工作考核
				list = manager.query("from StuInfo where nextVisitTime = ? order by advisor", new Date(c.getTime().getTime() - 24 * 60 * 60 * 1000));
				Object[] deptIds = new Object[] { 61407232, 61407233, Constants.ADV_DEPT_ID };// 九派校区、中山校区、移民
				List<Employees> advisors = manager.query("from Employees e where (e.dept.id = ? or e.dept.id = ? or e.dept.id = ? )and e.user.status='1'", deptIds);

				for (int i = 0; i < advisors.size(); i++) {
					pageContext.getOut().print("<tr><td class='tdborder' height='30px'><b><font color='red' size='2'>" + advisors.get(i).getName() + ":</font></b></td></tr>");
					int count = 1;

					for (int j = 0; j < list.size(); j++) {
						if (advisors.get(i).getName().equals(list.get(j).getAdvisor())) {
							pageContext.getOut().print("<tr><td class='tdborder' height='30px'><span style='float:left' class='STYLE10'>");
							pageContext.getOut().print(
									"&nbsp;&nbsp;&nbsp;&nbsp;" + (count++) + "、<a href='../admin/stuinfo/editStuInfo.do?flag=stu&model.id=" + list.get(j).getId() + "'>姓名：" + list.get(j).getStuName());
							pageContext.getOut().print("，联系方式：" + list.get(j).getContact() + "</a></span>");
							if (c.getTime().equals(list.get(j).getRecentTraceTime())) {
								pageContext.getOut().print("<font color='green' size='2' style='float:right'>&nbsp;(已完成)</font>");
							} else {
								pageContext.getOut().print("<font color='red' size='2' style='float:right'>&nbsp;(未完成)</font>");
							}
							pageContext.getOut().print("</td></tr>");
						}
					}
				}

			} else { // 工作提醒
				list = manager.query("from StuInfo where advisor=? and nextVisitTime = ?", new Object[] { username, c.getTime() });

				if (list.size() == 0) {
					pageContext.getOut().print("<tr><td class='tdborder' height='30px'>&nbsp;&nbsp;");
					pageContext.getOut().print("<span class='STYLE10'><font color='red'>您今天没有要回访的学生！</front></span></td></tr>");
				} else {
					pageContext.getOut().print("<tr><td class='tdborder' height='30px'>&nbsp;&nbsp;<span class='STYLE10' style='float:left'>");
					pageContext.getOut().print("&nbsp;&nbsp;您今天有<font color='red'>" + list.size() + "</font>位要回访的学生:</span></td></tr>");
					for (int i = 0; i < list.size(); i++) {
						pageContext.getOut().print("<tr><td class='tdborder' height='30px'><span class='STYLE10' style='float:left'>");
						pageContext.getOut().print(
								"&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + "、<a href='../admin/stuinfo/editStuInfo.do?model.id=" + list.get(i).getId() + "'>姓名：" + list.get(i).getStuName());
						pageContext.getOut().print("，联系方式：" + list.get(i).getContact() + "</a></span>");
						if (c.getTime().equals(list.get(i).getRecentTraceTime())) {
							pageContext.getOut().print("<span style='float:right'><font color='green' size='2' style='float:right'>&nbsp;(已完成)</font>");
						} else {
							pageContext.getOut().print("<font color='red' size='2' style='float:right'>&nbsp;(未完成)</font></span>");
						}
						pageContext.getOut().print("</td></tr>");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 判断登录用户角色是否为管理员
	 * 
	 * @return
	 */
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

	private boolean isOrAdmin(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals(Constants.ROLE_ORADMIN)) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	private boolean isAudit(Set<Role> roles) {
		boolean flag = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_AUDIT")) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	/**
	 * 从spring中获取一个bean.
	 */
	public Object getBean(String beanName) {
		ServletContext servletCtx = pageContext.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
		return ctx.getBean(beanName);
	}

	public Integer getIsDeptAdmin() {
		return isDeptAdmin;
	}

	public void setIsDeptAdmin(Integer isDeptAdmin) {
		this.isDeptAdmin = isDeptAdmin;
	}

}

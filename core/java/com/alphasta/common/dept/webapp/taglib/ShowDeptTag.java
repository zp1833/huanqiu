package com.alphasta.common.dept.webapp.taglib;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alphasta.common.dept.model.Dept;
import com.alphasta.common.dept.service.DeptManager;

/**
 * 文章审核状态
 * 
 * @author zp
 * 
 */
@SuppressWarnings("serial")
public class ShowDeptTag extends BodyTagSupport {

	private Integer deptId;

	/*
	 * @see BodyTagSupport#doEndTag()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		try {
			DeptManager deptManager = (DeptManager) this.getBean("deptManager");
			if (deptId != null && !deptId.equals(0)) {
				Dept dept = deptManager.get(Dept.class, deptId);
				if (dept.getChildDepts().size() > 0) {
					pageContext.getOut()
							.println("<a href='javascript:void(0)' onclick='showChilds(" + deptId + ")'>");
					pageContext.getOut().println("<font color='blue'>查看子部门</font></a>");
				} else {
					pageContext.getOut().println("无");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 从spring中获取一个bean.
	 */
	public Object getBean(String beanName) {
		ServletContext servletCtx = pageContext.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletCtx);
		return ctx.getBean(beanName);
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}

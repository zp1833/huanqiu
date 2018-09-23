package com.alphasta.common.core.webapp.struts2;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alphasta.common.security.user.UserUtil;
import com.alphasta.common.security.user.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction of the struts2
 * 
 * @author Sam
 * 
 */
public abstract class BaseAction extends ActionSupport {

	/**
	 * 为子类提供Log功能，方便子类使用
	 */
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * struts2的ActionContext
	 */
	private ActionContext actionContext;

	/**
	 * 获得struts2的ActionContext
	 */
	protected final ActionContext getActionContext() {
		return (actionContext == null) ? actionContext = ActionContext
				.getContext() : actionContext;
	}

	/**
	 * @return DWR {@link org.directwebremoting.WebContext}
	 */
	protected final WebContext getDwrWebContext() {
		return WebContextFactory.get();
	}

	/**
	 * 获得当前Action的名称
	 */
	protected final String getActionName() {
		return getActionContext().getName();
	}

	/**
	 * 获得当前Http Servlet Request
	 */
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		if (getActionContext() != null) { // 从Struts2中取得Request
			request = (HttpServletRequest) getActionContext().get(
					StrutsStatics.HTTP_REQUEST);
		}
		if (request == null) { // 从DWR中取得Request
			request = getDwrWebContext().getHttpServletRequest();
		}

		return request;
	}

	/**
	 * 获得当前HttpServletResponse.
	 */
	protected HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		if (getActionContext() != null) { // 从Struts2中取得Response
			response = (HttpServletResponse) getActionContext().get(
					StrutsStatics.HTTP_RESPONSE);
		}
		if (response == null) { // 从DWR中取得Response
			response = getDwrWebContext().getHttpServletResponse();
		}

		return response;
	}

	/**
	 * 返回Spring的ApplicationContext实例。
	 * 
	 * @return
	 */
	protected ApplicationContext getSpring() {
		ServletContext ctx = null;

		if (getActionContext() != null) {
			ctx = (ServletContext) getActionContext().get(
					StrutsStatics.SERVLET_CONTEXT);
		} else {
			ctx = getDwrWebContext().getServletContext();
		}
		return WebApplicationContextUtils.getWebApplicationContext(ctx);
	}
	/**
	 *通过名称的道Spring bean
	 * 
	 */
	public Object getBean(String BeanName){
		ApplicationContext ctx =getSpring();
		return ctx.getBean(BeanName);
	}
	/**
	 * 
	 * @return ServletContext
	 */
	protected ServletContext getServletContext() {
		return (ServletContext) getActionContext().get(
				StrutsStatics.SERVLET_CONTEXT);
	}

	/**
	 * 在Action函数return之前调用，可以防止浏览器缓存页面，从而使页面失效。
	 */
	protected final void setPageExpired() {
		getResponse().addHeader("Cache-Control", "no-cache");
		getResponse().addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
	}
	
	/**
	 * 获取当前登录的用户信息
	 * 
	 * @see {@link UserUtil#getPrincipal(HttpServletRequest)}
	 */
	public User getPrincipal() {
		return UserUtil.getPrincipal(getRequest());
	}
}

package com.alphasta.common.core.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.FilterDispatcher;

import com.alphasta.common.core.model.CmsThreadBean;

public class CmsFilterDispatcher extends FilterDispatcher {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		CmsThreadBean.setContext((HttpServletRequest)arg0);
		super.doFilter(arg0, arg1, arg2);
	}
	
	

}

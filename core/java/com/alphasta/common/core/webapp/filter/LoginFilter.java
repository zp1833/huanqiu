package com.alphasta.common.core.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String uri = req.getRequestURI();

		if (uri.indexOf("/admin/article/downloadFile.do") != -1) {
			chain.doFilter(request, response);
		} else {
			if (req.getSession().getAttribute("userInSession") == null) {
				HttpServletResponse resp = (HttpServletResponse) response;
				req.getRequestDispatcher("/common/500.jsp").forward(req, resp);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

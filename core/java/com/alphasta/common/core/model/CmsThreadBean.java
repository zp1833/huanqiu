package com.alphasta.common.core.model;

import javax.servlet.http.HttpServletRequest;

public class CmsThreadBean {

	private static ThreadLocal<HttpServletRequest> thread = new ThreadLocal<HttpServletRequest>();
	
	public static HttpServletRequest getContext(){
		return thread.get();
	}
	
	public static void setContext(HttpServletRequest request){
		thread.set(request);
	}
	
	public static void clearContext(){
		thread.set(null);
	}
}

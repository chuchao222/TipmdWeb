package com.tipmd.webapp.presentation.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * @author bowee2010
 * 继承此类可以获取Servlet的几个常用对象；request,response,session,servletContext
 */
@Controller
public class BaseController 
{
	protected Logger log = Logger.getLogger(getClass());
	protected HttpSession session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext servletContext;
	
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	@Override
	public String toString() {
		return "[session = " + session + ", request = " + request +
				", response = " + response + ", servletContext = " + servletContext+"]";
	}
}

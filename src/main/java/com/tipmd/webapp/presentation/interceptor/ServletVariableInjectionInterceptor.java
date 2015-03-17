package com.tipmd.webapp.presentation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tipmd.webapp.presentation.controller.BaseController;

/**
 * 
 * @author bowee2010
 * inject request,response,session,servletContext to BaseController
 */
public class ServletVariableInjectionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception 
	{
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Object bean = handlerMethod.getBean();
			if(bean instanceof BaseController) {
				BaseController baseController = (BaseController)bean;
				baseController.setRequest(request);
				baseController.setResponse(response);
				baseController.setSession(request.getSession());
				baseController.setServletContext(request.getServletContext());
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}

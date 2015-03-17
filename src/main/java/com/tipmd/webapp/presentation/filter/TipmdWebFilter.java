package com.tipmd.webapp.presentation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TipmdWebFilter implements Filter
{
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		//TODO: initialize...
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI(); 
		String contextPath = req.getContextPath();
 		String reqUriExcludeContextPath = uri.replace(contextPath, "");
 		reqUriExcludeContextPath = reqUriExcludeContextPath.replaceFirst("/", "");
 		
 		chain.doFilter(request, response);
 		
//		if("".equals(reqUriExcludeContextPath)  
//				|| reqUriExcludeContextPath.contains("login")
//				|| "loginPage".equals(reqUriExcludeContextPath)
//				|| reqUriExcludeContextPath.contains("static")) {
//			chain.doFilter(request, response);
//		} else {
//			if(!SessionHelper.isSessionAvailable(request)) {
//				//session is invalid
//				String fromJquery = req.getParameter("fromJquery");
//				if(!StringUtils.isEmpty(fromJquery) && fromJquery.equalsIgnoreCase("true")) {
//					BaseJsonObject json = BaseJsonObject.generateSessionInvalidJsonObject("Session is invalid");
//					json.setUrl(contextPath);
//					ObjectMapper mapper = new ObjectMapper();
//					String value = mapper.writeValueAsString(json);
//					response.setContentType("text/json;charset=UTF-8"); 
//					response.getWriter().write(value);
//				} else {
//					HttpServletResponse resp = (HttpServletResponse)response;
//					resp.sendRedirect(contextPath);
//				}
//			} 
//		}
	}

	@Override
	public void destroy() { }
	
}

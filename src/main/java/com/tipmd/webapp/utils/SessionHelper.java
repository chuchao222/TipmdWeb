package com.tipmd.webapp.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bowee2010
 *
 */
public final class SessionHelper {
	private static final String SESSION_USER_KEY = "sessionUser";
	
	public static boolean isSessionAvailable(HttpSession session) {
		return true;
	}
	
	public static boolean isSessionAvailable(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		return isSessionAvailable(session);
	}
//	TODO:
//	public static UserVo getSessionUser(HttpSession session) {
//		return (UserVo)session.getAttribute(SESSION_USER_KEY);
//	}
	
//	public static void setSessionUser(HttpSession session, UserVo user) {
//		session.setAttribute(SESSION_USER_KEY, user);
//	}
	
}
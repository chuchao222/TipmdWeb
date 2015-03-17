package com.tipmd.webapp.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.tipmd.webapp.utils.Constants;


@SuppressWarnings("serial")
public class InitServlet extends HttpServlet 
{
	private final Logger log = Logger.getLogger(SessionListener.class);
	public void init() throws ServletException {
		log.info("InitServlet init()...");
		Constants.APP_PATH = this.getServletContext().getRealPath("/");
		log.info(Constants.APP_PATH);
	}
}

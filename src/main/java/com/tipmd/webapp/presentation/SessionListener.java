package com.tipmd.webapp.presentation;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener,HttpSessionBindingListener,HttpSessionAttributeListener {
    private final Logger log = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionevent) {
        //log.info("======sessionCreated invoke sessionid ======" +sessionevent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionevent) {
    	//log.info("======sessionDestroyed invoke sessionid ====== " +sessionevent.getSession().getId());
    }

    @Override
    public void valueBound(HttpSessionBindingEvent bindingEvent) {
        //log.info("valueBound invoke sessionid = " +bindingEvent.getName() +" :" +bindingEvent.getValue());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent bindingEvent) {
        //log.info("valueUnbound invoke sessionid = " +bindingEvent.getName() +" :" +bindingEvent.getValue());
        
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent bindingEvent) {
        //log.info("attributeAdded invoke sessionid = " +bindingEvent.getName() +" :" +bindingEvent.getValue());
        
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent bindingEvent) {
       //log.info("attributeRemoved invoke sessionid = " +bindingEvent.getName() +" :" +bindingEvent.getValue());
        
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent bindingEvent) {
        //log.info("attributeReplaced invoke sessionid = " +bindingEvent.getName() +" :" +bindingEvent.getValue());
    }
}

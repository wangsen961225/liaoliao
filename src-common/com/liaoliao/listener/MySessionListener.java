package com.liaoliao.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

public class MySessionListener implements HttpSessionListener {
	
	@Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    MySessionContext.AddSession(httpSessionEvent.getSession());
    }
	
	@Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        MySessionContext.DelSession(session);
    }
}

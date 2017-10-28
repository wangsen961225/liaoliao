package com.liaoliao.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class MyContextLoaderListener extends ContextLoaderListener {
	public static ServletContext context ;
	 public void contextInitialized(ServletContextEvent event) { 
		    ServletContext context = event.getServletContext(); 
		    MyContextLoader.setContext(context);
	 }
}

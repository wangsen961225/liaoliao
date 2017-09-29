package com.liaoliao.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet(name="InitServlet",urlPatterns="/InitServlet",loadOnStartup=5)
public class InitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public void init() { 
		   ApplicationContext ctx = new ClassPathXmlApplicationContext("quartz.xml");
	}
	
}


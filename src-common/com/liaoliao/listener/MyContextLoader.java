package com.liaoliao.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoaderListener;

public class MyContextLoader extends ContextLoaderListener {
	public static ServletContext context ;
	 
	public static void setContext(ServletContext context){
		MyContextLoader.context = context;
	}
	
	 public static ServletContext getContext(){
		return context;
	 }
}

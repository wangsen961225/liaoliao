package com.liaoliao.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.liaoliao.util.CommonUtil;

public class AccessFilter implements Filter {

	@Override
	public void destroy() {
		// System.out.println("destory销毁");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setContentType("text/html;charset=utf-8");
		// 设置不过滤的uri
		String path = request.getRequestURI();
//		if (path.indexOf("/api") > -1) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		if (path.indexOf("/welcome.jsp") > -1) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/test") > -1) {
			response.reset();
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/style") > -1) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/upload") > -1) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/share") > -1) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/content") > -1) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.indexOf("/sys") > -1) {
			response.reset();
			filterChain.doFilter(request, response);
			return;
		}
		PrintWriter out = response.getWriter();
		// 拦截GET请求
		String method = request.getMethod();
		if ("GET".equals(method)) {
			out.print("404 not found");
			return;
		}

		// 拦截非法UA
		String userAgent = request.getHeader("user-agent");
		if (StringUtils.isBlank(userAgent) || !userAgent.contains("liao")) {
			out.print("404 Not found");
			return;
		}

		
		
		// 拦截非法timeStamp
		String timeStampString = request.getHeader("timeStamp");
		if (StringUtils.isBlank(timeStampString)) {
			out.print("404 Not Found");
			return;
		}
		long timeStamp = Long.valueOf(timeStampString);
		long nowTimeStamp = System.currentTimeMillis() / 1000L;
		long dValue = Math.abs(timeStamp - nowTimeStamp);
		System.out.println(timeStamp+","+nowTimeStamp+","+dValue);
		if (dValue > 300) {
			out.print("{\"code\":\"404\",\"msg\":\"4 0 4 Not Found\"}");
			return;
		}
		
//		拦截非法token&sign
		String token = request.getHeader("token");
		String sign = request.getHeader("sign");
		if(StringUtils.isBlank(token)||StringUtils.isBlank(sign)){
			out.print("4o4 Not Found");
			return;
		}
		String validateSign = CommonUtil.md5(token+timeStamp+"liao");
		if(!sign.equals(validateSign)){
			out.print("4O4 Not Found");
			return;
		}else {
			response.reset();
			filterChain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// System.out.println("init被调用");
	}

	
	
	
	
	
	
}
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isErrorPage="true"%>
<%
	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	response.setContentType("application/json");
	response.getWriter().write("欢迎");
%>














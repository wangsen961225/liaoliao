<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<body>  
<form action="${ctx}/upload/avatar" method="post" enctype="multipart/form-data">  
<input type="file" name="uploadFile" /> <input type="submit" value="Submit" /></form>  
</body>  
</html> 
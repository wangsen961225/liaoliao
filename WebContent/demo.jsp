<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<title>${sys_title}</title>
<link href="${ctx}/style/css/style.css" rel="stylesheet">
</head>
<body class="sticky-header">

<div>
	<form action="/mobilePush/send" method="post">
		标题:<input type="text" id="title" ></input>
		文本:<input type="text" id="wenben"/>
		<input type="submit" name="发布">
	</form>
</div>

<script type="text/javascript">
/*
$.getJSON("",function(data){
	alert(1);
}) */
</script>
</body>
</html>











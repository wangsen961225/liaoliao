<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<title>${sys_title}</title>
<link href="${ctx}/style/css/signin.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<form class="form-signin" action="${ctx}/sys/Login" method="post" target="_top">
			<h2 class="form-signin-heading">用户登录</h2>
				<label for="inputUser" class="sr-only">用户名</label>
				<input name="userName" type="text" id="inputUser" class="form-control" placeholder=" 用户名 " required autofocus>
				<label for="inputPassword" class="sr-only"> 密码 </label>
				<input name="passWord" type="password" id="inputPassword" class="form-control" placeholder=" 密码 " required>
				<div class="checkbox">
					<label><input type="checkbox" value="remember-me">记住我</label>
				</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		</form>
	</div> <!-- /container -->
</body>
</html>
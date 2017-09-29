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
	
	<div class="data_list">
		<div class="data_list_title">用户世界发言记录</div>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>用户ID</th>
					<th>价格</th>
					<th>评论时间</th>
					<th>评论内容</th>
				</tr>
				<c:forEach var="li" items="${bList}">
					<tr>
						<td>${li.user.id }</td>
						<td>${li.price }</td>
						<td>${li.addTime }</td>
						<td>${li.content }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
</body>

</html>
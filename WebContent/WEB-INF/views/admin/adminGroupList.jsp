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
		<div class="data_list_title">用户组列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location=&#39;dormManager?action=preSave&#39;">添加</button>
				<button class="btn btn-primary" type="button" style="margin-right: 50px;" onclick="javascript:window.location=&#39;dormManager?action=preSave&#39;">提交表单样式</button>
				<label for="exampleInput">输入框示例</label>
				<input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" />
				<span class="data_search">
					<select class="form-control" id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="userName">用户名</option>
					</select>
					<input id="s_dormManagerText" name="s_dormManagerText" type="text" style="border-radius:50px" class="form-control" value="">
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th>用户组</th>
					<th>描述</th>
					<th>状态</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.groupName}</td>
						<td>${li.info}</td>
						<td>
						  <c:if test="${li.status==1}">正常</c:if>
						  <c:if test="${li.status==0}">封禁</c:if>
						</td>
						<td>${li.addTime}</td>
						<td>
						  <button class="btn btn-info" type="button" 
						  onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=1&#39;">修改</button>&nbsp;
						  <button class="btn btn-danger" type="button" onclick="dormManagerDelete(1)">删除</button>&nbsp;
						  <button class="btn btn-danger" type="button" onclick="dormManagerDelete(1)">修改权限</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
</body>
</html>
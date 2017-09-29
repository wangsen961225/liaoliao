<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<script type="text/javascript">
function permission(id){
	window.location.href="${ctx}/sys/changePermission?id="+id;
}


</script>
<body>
	<div class="data_list">
		<div class="data_list_title">权限列表1</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addNav"> 添加</button>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody id="tb">
				<tr>
					<th>编号</th>
					<th>用户组名</th>
					<th>状态</th>
					<th>备注</th>
					<th>添加时间</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.groupName}</td>
						<td>${li.status}</td>
						<td>${li.info}</td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td>
						  <button class="btn btn-info" type="button" onclick="">修改</button>&nbsp;
						  <button class="btn btn-danger" type="button" onclick="dormManagerDelete(1)">删除</button>&nbsp;
						  <button class="btn btn-info" type="button" onclick="permission(${li.id})">权限修改</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div> 
	</div>
	<!-- 添加新用户组 -->	
 <form class="form-signin" action="${ctx}/sys/addAdminGroup" method="post" id="myForm">
<div class="modal fade" id="addNav" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加用户组</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">用户组名</td>
					<td><input type="text" name="groupName" /></td>
				</tr>
				<tr>
					<td width="10%">备注</td>
					<td><input type="text" name="info" /></td>
				</tr>
				</tbody>
			</table>
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <input type="submit" class="btn btn-primary" value="保存" />
      </div>
    </div>
  </div>
</div>
</form>
</body>
</html>
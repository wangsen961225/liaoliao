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
		<div class="data_list_title">添加权限</div>
		<form class="form-signin" action="${ctx}/sys/addPermission" method="post">
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="10%">父级菜单</td>
					<td>
						<select style="width:10%" name="parentId">
							<option value="">无上级</option>
						  <c:forEach var="li" items="${list}">
						     <option value ="${li.id}">${li.navigationName}</option>
						  </c:forEach>
						</select><P style="color:red;font-size:1em">*无父类不用选*</P>
					</td>
				</tr>
				<tr>
					<td width="10%">菜单名</td>
					<td><input type="text" name="navigationName" /></td>
				</tr>
				<tr>
					<td width="10%"> 路径url</td>
					<td><input type="text" name="navigationUrl" /><P style="color:red;font-size:1em">*无父类不用填*</P></td>
				</tr>
				</tbody>
			</table>
			     <div style="margin-left:80%">
			     <input type="reset"  class="btn btn-info"  value="重置" />
			     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input class="btn btn-info" type="submit"  value="提交"/></div>
		</div>
		</form>
	</div>
</body>
</html>
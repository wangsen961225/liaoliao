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
		<div class="data_list_title">今日推荐列表</div>
	
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody id="tb">
				<tr>
					<!-- <th>编号</th> -->
					<th>标题</th>
					
					<!-- <th>类型</th>
					<th>时间</th> -->
					<!-- <th width="20%">操作</th> -->
				</tr>
				
				<c:forEach var="li" items="${list}">
					<tr>
						<%-- <td>${li.num}</td> --%>
						<td>${li}</td>
						<%-- <td>${li}</td>
						<td>${li}</td>
						<td>${li}</td>  --%>
						<%-- <td>
						 <button class="btn btn-info" type="button" onclick="childContent(${li.read})">子菜单详情</button>
						</td> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%@ include file="/style/public/footPage.jsp"%>
		</div> 
	</div>
	

</body>
</html>
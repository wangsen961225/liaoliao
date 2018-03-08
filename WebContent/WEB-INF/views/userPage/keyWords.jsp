<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/layDate-v5.0.9/laydate/laydate.js"></script>
<title>${sys_title}</title>
</head>
<script>
//执行一个laydate实例
laydate.render({
  elem: '#startDate' //指定元素
});
laydate.render({
	  elem: '#endDate' //指定元素
	});
	
</script>
<body>
	<div class="data_list">
		<div class="data_list_title">关键字列表</div>
	<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/keyWordsHistory">
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="用户ID" id="userId" name="userId" />
				&nbsp;&nbsp;&nbsp;
				<!-- <input type="text" class="form-control" placeholder="开始时间" id="startDate" name="startDate" value=""/>&nbsp;
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="截止时间" id="endDate" name="endDate" value=""/>&nbsp; -->
				<%-- <input type="text" class="form-control" placeholder="今日活跃用户" id="userName" name="activeUsersToday" value="${userName}"/>&nbsp; --%>
				<button type="submit" class="btn btn-info"  >搜索</button>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody id="tb">
				<tr>
					<th>关键字</th>
					<th>时间</th>
					<th>总次数</th>
				</tr>
				<c:forEach var="li" items="${lists}">
					<tr>
						<td>${li.name}</td>
						<td>${li.addDate}</td>
						<td>${li.freq}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%@ include file="/style/public/footPage.jsp"%>
		</div> 
		<hr/>
	</div>

</body>
</html>
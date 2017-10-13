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
		
		<!-- 支出明细开始 -->
	
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 >料&nbsp;币&nbsp;明&nbsp;细</h4>
	     
	        <table class="table table-hover table-striped table-bordered">
				<tr>
					<th>序号</th>
					<th>料币数</th>
					<th>类型</th>
					<th>操作时间</th>
				</tr>
				<c:forEach var="lis" items="${expenditureDetailsList }" varStatus="status">
					<tr>
						<td>${status.count}</td> 
						<td>${lis.money}</td>
						<td>${lis.type}</td>
						<%-- <td>
						了解type，修改
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
						</td> --%>
						<td><fmt:formatDate value="${lis.addTime}" type="both"/></td>
					</tr>
				</c:forEach>
				</table>
			<div style="text-align:center;"><h3>~完~</h3></div>
		
		<!-- 支出明细结束 -->
	
		<%@ include file="/style/public/footPage.jsp"%>
	
</body>

</html>
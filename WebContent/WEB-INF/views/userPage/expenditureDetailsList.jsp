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
						<%-- <td>${lis.type}</td> --%>
						<td>
							<c:if test="${lis.type==0 }">文章</c:if>
							<c:if test="${lis.type==1 }">视频</c:if>
							<c:if test="${lis.type==2 }">提现</c:if>
							<c:if test="${lis.type==3 }">发广播</c:if>
							<c:if test="${lis.type==4 }">新用户红包</c:if>
							<c:if test="${lis.type==5 }">签到</c:if>
							<c:if test="${lis.type==6 }">vip被抢红包</c:if>
							<c:if test="${lis.type==7 }">普通用户抢红包</c:if>
							<c:if test="${lis.type==8 }">邀请注册</c:if>
							<c:if test="${lis.type==9 }">每日下级分润</c:if>
							<c:if test="${lis.type==10 }">原创video通过审核</c:if>
							<c:if test="${lis.type==11 }">原创Article通过审核</c:if>
							<c:if test="${lis.type==12 }">原创文章</c:if>
							<c:if test="${lis.type==13 }">原创视频</c:if>
							<c:if test="${lis.type==14 }">每日任务</c:if>
							<c:if test="${lis.type==15 }">原创作品十分钟统计</c:if>
							<c:if test="${lis.type==16 }">土豪世界发红包</c:if>
							<c:if test="${lis.type==17 }">查看用户详情(约她)</c:if>
							<c:if test="${lis.type==18 }">退款</c:if>
							<c:if test="${lis.type==19 }">阅读翻倍</c:if>
						</td>
						<td><fmt:formatDate value="${lis.addTime}" type="both"/></td>
					</tr>
				</c:forEach>
			</table>
		<div style="text-align:center;"><h3>~完~</h3></div>
	<!-- 支出明细结束 -->
	<%@ include file="/style/public/footPage.jsp"%>
</body>

</html>
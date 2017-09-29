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
		<div class="data_list_title">用户升级列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='${ctx}/content/toGetArticle'">添加</button>
				<button class="btn btn-primary" type="button" style="margin-right: 50px;" onclick="">提交表单样式</button>
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
					<th>用户昵称</th>
					<th>内容</th>
					<th>费用</th>
					<th>类型</th>
					<th>订单号</th>
					<th>用户ip</th>
					<th>状态</th>
					<th>创建时间</th>
					<th>支付时间</th>
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.user.nickName}</td>
						<td>${li.body}</td>
						<td>${li.totalFee/100}￥</td>
						<td>${li.tradeType}</td>
						<td>${li.outTradeNo}</td>
						<td>${li.spbillCreateIp}</td>
						<c:if test="${li.status==1}">
						<td style="color:red;font-weight:bolder;">已支付</td>
						</c:if>
						<c:if test="${li.status==0}">
						<td>未支付</td>
						</c:if>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td><fmt:formatDate value="${li.payTime}" type="both"/></td>
						<!-- <td>
						  <button class="btn btn-info" type="button" onclick="#">修改</button>&nbsp;&nbsp;&nbsp;&nbsp;
						</td> -->
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
</body>

<script type="text/javascript">
function modifyArticle(id){
	alert("感觉没啥可修改的,别点了,还没写~");
	/* $.ajax({
		type:"GET",
		url:"${ctx}/content/modifyVideo?id="+id,
		success:function(data){
			if(data.code==200){
				alert(data.msg);
			}
		},
		error:function(){
			alert("出错啦~~");
		}
	}) */
}

function deleteArticle(id){
	$.ajax({
		type:"GET",
		url:"${ctx}/sys/deleteArticle?id="+id,
		success:function(data){
			if(data.code==200){
				alert(data.msg);
				window.location.href="${ctx}/content/toArticle";
			}
		},
		error:function(){
			alert("出错啦~~");
		}
	})
}


function banArticle(id){
	$.ajax({
		type:"GET",
		url:"${ctx}/sys/banUser?id="+id,
		success:function(data){
			if(data.code==200){
				alert(data.msg);
				window.location.href="${ctx}/sys/userList";
			}
		},
		error:function(){
			alert("出错啦~~");
		}
	})
}
</script>
</html>
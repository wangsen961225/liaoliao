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
		<div class="data_list_title">用户列表</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/userList">
				<select class="form-control" id="vipStatus" name="vipStatus" style="width: 100px;">
				    <c:if test="${empty vipStatus ||vipStatus eq  null}">
				      <option selected="selected" value="">请选择</option>
					  <option value="1">会员</option>
					  <option value="0">非会员</option>
				    </c:if>
				     <c:if test="${vipStatus==1 }">
				      <option  value="">请选择</option>
					  <option  selected="selected" value="1">会员</option>
					  <option value="0">非会员</option>
				    </c:if>
				     <c:if test="${vipStatus==0}">
				      <option value="">请选择</option>
					  <option value="1">会员</option>
					  <option selected="selected" value="0">非会员</option>
				    </c:if>
				</select>
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="手机号" id="mobile" name="mobile" value="${mobile }"/>
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="用户名" id="userName" name="userName" value="${userName}"/>&nbsp;
				<button type="submit" class="btn btn-info" >搜索</button>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>用户ID</th>
					<th>来源</th>
					<th>头像</th>
					<th>昵称</th>
					<th>总金额</th>
					<th>花费金额</th>
					<th>今日总额</th>
					<th>状态</th>
					<th>vip</th>
					<th>登录时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>
							<c:if test="${li.sourceType==0 }">微信用户</c:if>
							<c:if test="${li.sourceType==1 }">QQ用户</c:if>
							<c:if test="${li.sourceType==2 }">微博用户</c:if>
							<c:if test="${li.sourceType==3 }">${li.mobile }</c:if>
						</td>
						<td>
						    <c:if test="${not empty li.avatar}"><img src="${li.avatar}" width="50px"/></c:if>
						    <c:if test="${empty li.avatar}"></c:if>
						</td>
						<td>
						    <c:if test="${not empty li.nickName}">${li.nickName}</c:if>
						    <c:if test="${empty li.nickName}">----</c:if>
						</td>
						<td>${li.totalMoney}</td>
						<td>${li.payMoney}</td>
						<td>${li.dayMoney}</td>
						<td>
						  <c:if test="${li.status==1}">正常</c:if>
						  <c:if test="${li.status==0}"><p style="color:red">封禁</p></c:if>
						</td>
						<td>
						  <c:if test="${li.vipStatus==1}">会员</c:if>
						  <c:if test="${li.vipStatus==0}">非会员</c:if>
						</td>
						<td><fmt:formatDate value="${li.loginTime}" type="both"/></td>
						<td>
						  <button class="btn btn-info" type="button" onclick="#">修改</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <c:if test="${li.status==1}"><button class="btn btn-danger" type="button" onclick="banArticle(${li.id})">封禁</button></c:if>
						  <c:if test="${li.status==0}"><button class="btn btn-danger" type="button" onclick="banArticle(${li.id})">解禁</button></c:if>
						</td>
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
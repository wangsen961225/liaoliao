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
		是否会员：
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
				
				&nbsp;&nbsp;&nbsp;&nbsp;今日是否活跃：
				<select class="form-control" id="isActive" name="isActive" style="width: 150px;">
				    <c:if test="${empty isActive ||isActive eq  null}">
				      <option selected="selected" value="">请选择</option>
					  <option value="1">今日活跃用户</option>
					  <option value="0">今日非活跃用户</option>
				    </c:if>
				     <c:if test="${isActive==1 }">
				      <option  value="">请选择</option>
					  <option  selected="selected" value="1">今日活跃用户</option>
					  <option value="0">今日非活跃用户</option>
				    </c:if>
				     <c:if test="${isActive==0}">
				      <option value="">请选择</option>
					  <option value="1">今日活跃用户</option>
					  <option selected="selected" value="0">今日非活跃用户</option>
				    </c:if>
				</select>
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="手机号" id="mobile" name="mobile" value="${mobile }"/>
				&nbsp;&nbsp;&nbsp;
				<input type="text" class="form-control" placeholder="用户名" id="userName" name="userName" value="${userName}"/>&nbsp;
				<%-- <input type="text" class="form-control" placeholder="今日活跃用户" id="userName" name="activeUsersToday" value="${userName}"/>&nbsp; --%>
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
					<th>今日点击</th>
					<th>总共点击</th>
					<th>签到次数</th>
					<th>签到收益</th>
					<th>登录时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.user.id}</td>
						<td>
							<c:if test="${li.user.sourceType==0 }">微信用户</c:if>
							<c:if test="${li.user.sourceType==1 }">QQ用户</c:if>
							<c:if test="${li.user.sourceType==2 }">微博用户</c:if>
							<c:if test="${li.user.sourceType==3 }">${li.user.mobile }</c:if>
						</td>
						<td>
						    <c:if test="${not empty li.user.avatar}"><img src="${li.user.avatar}" width="50px"/></c:if>
						    <c:if test="${empty li.user.avatar}"></c:if>
						</td>
						<td>
						    <c:if test="${not empty li.user.nickName}">${li.user.nickName}</c:if>
						    <c:if test="${empty li.user.nickName}">----</c:if>
						</td>
						<td>${li.user.totalMoney}</td>
						<td>${li.user.payMoney}</td>
						<%-- <td>${li.user.dayMoney}</td> --%>
						<td>${li.todayTotal}</td>
						<td>
						  <c:if test="${li.user.status==1}">正常</c:if>
						  <c:if test="${li.user.status==0}"><p style="color:red">封禁</p></c:if>
						</td>
						<td>
						  <c:if test="${li.user.vipStatus==1}">会员</c:if>
						  <c:if test="${li.user.vipStatus==0}">非会员</c:if>
						</td>
						<td>${li.dayCount }</td>
						<td>${li.totalCount }</td>
						<td>${li.totalSign }</td>
						<td>${li.totalProfit }</td>
						
						<td><fmt:formatDate value="${li.user.loginTime}" type="both"/></td>
						<td>
						  <button class="btn btn-info" type="button" onclick="#">修改</button>
						  <c:if test="${li.user.status==1}"><button class="btn btn-danger" type="button" onclick="banArticle(${li.user.id})">封禁</button></c:if>
						  <c:if test="${li.user.status==0}"><button class="btn btn-danger" type="button" onclick="banArticle(${li.user.id})">解禁</button></c:if>
						  <!-- <button class="btn btn-info" type="button" onclick="#">料币收入支出明细</button> -->
						  <button type="button" class="btn btn-primary"  href="${ctx}/sys/expenditureDetails?userId=${li.user.id}"  data-toggle="modal" data-target="#expenditureDetails" > 料币明细</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		
		
		
		
		<!-- 支出明细开始 -->
	<div class="modal fade" id="expenditureDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document" style="width:1150px">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">料&nbsp;币&nbsp;明&nbsp;细</h4>
	      </div>
	      <<%-- div class="modal-body">
	        <table class="table table-hover table-striped table-bordered">
					<tbody>
					<tr>
					<!-- <th>序号</th> -->
					<th>料币数</th>
					<th>类型</th>
					<th>操作时间</th>
				</tr>
				
				<c:forEach var="lis" items="${expenditureDetailsList }" >
					<tr>
						<td>${status.index}</td>
						<td>${lis.money}</td>
						<td>${lis.type}</td>
						<td>
						了解type，修改
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
							<c:if test="${li.type==0 }"></c:if>
						</td>
						<td><fmt:formatDate value="${lis.addTime}" type="both"/></td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			
	      </div> --%>
	      
	      <!-- <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <input type="submit" class="btn btn-primary" value="保存" />
	      </div> -->
	      
	    </div>
	  </div>
	</div>
		
		<!-- 支出明细结束 -->
		
		
		
	
		
		
		
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
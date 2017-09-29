<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}/style/bootstrap/timePulg/daterangepicker-bs3.css" />
<script type="text/javascript" src="${ctx}/style/bootstrap/timePulg/moment.js"></script>
<script type="text/javascript" src="${ctx}/style/bootstrap/timePulg/daterangepicker.js"></script>
<title>${sys_title}</title>
<style type="text/css">
 th{
 text-align:center;	
}
 td{
 text-align:center;	
}
</style>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">
		        用户列表   
		   <span >
				<button  class="btn btn-info"  style="float:right;margin-right:10px;margin-top:-5px;">
				<a href="${ctx}/test/logout" target="_top">注销登录</a></button>
		   </span>
		</div>
		 <div style="padding-top: 1%;">
		      总人数(不包含非法用户)：<c:if test="${empty total}">暂无达标</c:if><c:if test="${not empty total}">${total}人</c:if>  &nbsp;&nbsp;&nbsp;
		      达标人数：<c:if test="${empty passNum}">暂无达标</c:if><c:if test="${not empty passNum}">${passNum}人</c:if> &nbsp;&nbsp;&nbsp;
		      未达标人数：<c:if test="${empty noPassNum}">暂无达标</c:if><c:if test="${not empty noPassNum}">${noPassNum }人</c:if>&nbsp;&nbsp;&nbsp;
		  <span style="color:red"> 
		          非法人数：<c:if test="${empty exceptionNum}">暂无非法用户</c:if><c:if test="${not empty exceptionNum}">${exceptionNum }人</c:if>
		  </span>
		  <span style="float:right;color:red;margin-right:10px">*达标规则：注册用户留存七天为达标用户*</span>
		</div>
		<hr/>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search" style="margin-top: -1%;">
			<%-- 	<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='${ctx}/content/toGetArticle'">添加</button>
				<button class="btn btn-primary" type="button" style="margin-right: 50px;" onclick="">提交表单样式</button> --%>
				<!-- <label for="exampleInput">输入框示例</label>
				<input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" /> -->
				 <label for="standard">是否达标:</label>
				<select class="form-control" id="standard" name="standard" style="width: 110px;">
				    <c:if test="${standard==0}" >
					  <option selected="selected" value="0">未达标</option>
					  <option value="1">已达标</option>
					  <option value="2">非法用户</option>
					</c:if>
					<c:if test="${standard==1}" >
					  <option  value="0">未达标</option>
					  <option selected="selected" value="1">已达标</option>
					  <option value="2">非法用户</option>
					</c:if>
					<c:if test="${standard==2}" >
					  <option  value="0">未达标</option>
					  <option selected="selected" value="1">已达标</option>
					  <option value="2">非法用户</option>
					</c:if>
					
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="input-prepend input-group" >
				  <label for="reservation">登录时间:</label>
                   <span class="add-on input-group-addon">
                   	 <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                   </span>
                  	 <input type="text" readonly style="width: 200px" name="reservation" id="reservation" class="form-control" placeholder="起始时间  <--> 结束时间 " value="${time}"/>
                      &nbsp;
                   <button type="button" class="btn btn-info" onclick="findByTime()">查询</button> &nbsp;
                   <button type="button" class="btn btn-info" onclick="clearInput()">清空</button>
                </div>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>用户ID</th>
					<th style="width:10%">手机号码</th>
					<th>是否达标</th>
					<th>昵称</th>
					<th>总料币</th>
					<th>上次登录时间</th>
					<th>注册时间</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.mobile}</td>
						<td>
						    <c:if test="${standard==1}"><span style="color:red">已达标</span></c:if>
						    <c:if test="${standard==0}">未达标</c:if>
						    <c:if test="${standard==2}"><span style="color:red">非法用户</span></c:if>
						</td>
						<td>
						    <c:if test="${not empty li.nickName}">${li.nickName}</c:if>
						    <c:if test="${empty li.nickName}"></c:if>
						</td>
						<td>${li.totalMoney}</td>
						<td><fmt:formatDate value="${li.loginTime}" type="both"/></td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
</body>

<script type="text/javascript">

function clearInput(){
	var time =document.getElementById("reservation");
	time.value=""; 
}

function findByTime(){
	var time =document.getElementById("reservation");
	if(time.value.substring(0,3)=="nul"){
		time.value=""; 
		return;
	}
    var startTime=time.value.substring(0,10);
    var endTime=time.value.substring(13,23);
   var url="";
   var standard=document.getElementById("standard").value;
	if(time.value=="起始时间  <--> 结束时间"||time.value==""||time.value=="undefined"){
		url="${ctx}/test/dateList";
		if(standard!=""&&standard!="undefined"){
			url+="?standard="+standard;
		}
	}else{
		url="${ctx}/test/dateList?startTime="+startTime+"&endTime="+endTime;
		if(standard!=""&&standard!="undefined"){
			url+="&standard="+standard;
		}
	}
	window.location.href=url;
	
}



$(document).ready(function() {
   $('#reservation').daterangepicker(null, function(start, end, label) {
     console.log(start.toISOString(), end.toISOString(), label);
   });
});


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
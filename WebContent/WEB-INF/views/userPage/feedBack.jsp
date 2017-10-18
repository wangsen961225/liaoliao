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
		<div class="data_list_title">用户反馈列表</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/userFeedBack">
				<select class="form-control" id="status" name="status" style="width: 100px;">
				    <c:if test="${empty status ||status eq  null||status==0}">
				      <option selected="selected" value="0">请选择</option>
					  <option value="1">沟通中</option>
					  <option value="2">未采纳</option>
					  <option value="3">采纳未发放奖励</option>
					  <option value="4">采纳并发放奖励</option>
				    </c:if>
				     <c:if test="${status==1 }">
				      <option  value="0">请选择</option>
					  <option  selected="selected" value="1">沟通中</option>
					  <option value="2">未采纳</option>
					  <option value="3">采纳未发放奖励</option>
					  <option value="4">采纳并发放奖励</option>
				    </c:if>
				     <c:if test="${status==2}">
				      <option value="0">请选择</option>
					  <option value="1">沟通中</option>
					  <option selected="selected" value="2">未采纳</option>
					  <option value="3">采纳未发放奖励</option>
					  <option value="4">采纳并发放奖励</option>
				    </c:if>
				    <c:if test="${status==3}">
				      <option value="0">请选择</option>
					  <option value="1">沟通中</option>
					  <option  value="2">未采纳</option>
					  <option selected="selected" value="3">采纳未发放奖励</option>
					  <option value="4">采纳并发放奖励</option>
				    </c:if>
				    <c:if test="${status==4}">
				      <option value="0">请选择</option>
					  <option value="1">沟通中</option>
					  <option  value="2">未采纳</option>
					  <option value="3">采纳未发放奖励</option>
					  <option selected="selected" value="4">采纳并发放奖励</option>
				    </c:if>
				</select> 
				&nbsp;&nbsp;&nbsp;
				<button type="submit" class="btn btn-info" >搜索</button>
				<p style="color:red;"></p>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th>用户姓名</th>
					<th>反馈内容</th>
					<th>反馈时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.user.nickName}</td>
						<td>${li.word}</td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td>
						  <c:if test="${li.status==1}"><p style="color:red">沟通中</p></c:if>
						  <c:if test="${li.status==2}">未采纳</c:if>
						  <c:if test="${li.status==3}"><p style="color:orange">采纳未发放奖励</p></c:if>
						  <c:if test="${li.status==4}">采纳并发放奖励</c:if>
						</td>
						<td>
						 <c:if test="${li.status==1}">
						  	<button class="btn btn-danger" type="button" data-toggle="modal" data-target="#dealwithPage" onclick="feedBackInfo(${li.id});">点击处理</button>
						  </c:if>
						  <c:if test="${li.status==2}">
							   <button class="btn btn-info" type="button" data-toggle="modal" data-target="#feedBackInfo" onclick="feedBackInfo(${li.id});">查看详情</button>&nbsp;
						  </c:if>
						  <c:if test="${li.status==3}">
						  	<button class="btn btn-warning" type="button"  onclick="grant(${li.id});">发放奖励</button>
						  </c:if>
						   <c:if test="${li.status==4}">
						  	<button class="btn btn-info" type="button" data-toggle="modal" data-target="#feedBackInfo" onclick="feedBackInfo(${li.id});">查看详情</button>
						  </c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
	<script type="text/javascript">
	
	function feedBackInfo(id){
		 $.ajax({
			type: "POST",
	        url: "${ctx}/sys/feedBackInfo",
	        data:{"id":id},
	        success: function(data){
	        	if(data.code==0){
	        		alert(data.msg);
	        	}else{
	        		var id=data.id;
		        	var content = data.content;
		        	$('#id').val(id);
		        	$('#iid').val(id);
		        	$('#content').html(content);
		        	$('#ccontent').html(content);
	        	}
	        },
	        error:function(data){
	        	alert("出错啦~");
	        }
		}); 
	}
	
	function grant(id){
	 	$.ajax({
			type:"POST",
			url: "${ctx}/sys/feedBackGrant",
		    data:{"id":id},
		    success: function(data){
	        	if(data.code==0){
	        		alert(data.msg);
	        	}else{
					alert("发放成功!");
					window.location.href="${ctx}/sys/userFeedBack";
	        	}
	        },
	        error:function(data){
	        	alert("出错啦~");
	        }
		})	 
	}
	function dealwith(){
		var id = $("#iid").val();
		 $.ajax({
			type:"POST",
			url: "${ctx}/sys/dealwith",
		    data:{"id":id}, 
		    success: function(data){
	        	if(data.code==0){
	        		alert(data.msg);
	        	}else{
	        		//window.location.href="${ctx}/sys/userFeedBack";
	        		location.reload(); 
	        	}
	        },
	        error:function(data){
	        	alert("出错啦~");
	        }
		})	 
	}
	function passFeedback(){
		var id = $("#iid").val();
		 $.ajax({
			type:"POST",
			url: "${ctx}/sys/passFeedback",
		    data:{"id":id}, 
		    success: function(data){
	        	if(data.code==0){
	        		alert(data.msg);
	        	}else{
	        		//window.location.href="${ctx}/sys/userFeedBack";
	        		location.reload(); 
	        	}
	        },
	        error:function(data){
	        	alert("出错啦~");
	        }
		})	 
	}
	
	
	</script>
<!-- 查看详情 -->
<div class="modal fade" id="feedBackInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel" style="text-align:center;">
         		反馈内容
        </h4>
      </div>
      <div class="modal-body" id="content" >
      </div> 
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <input type="text" id="id" name="id" hidden="hidden"/>
      </div>
    </div>
  </div>
</div>

<!-- 处理 -->
<div class="modal fade" id="dealwithPage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel" style="text-align:center;">
         		反馈内容
        </h4>
      </div>
      <div class="modal-body" id="ccontent"  >
      </div> 
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         <input type="submit" class="btn btn-primary" value="不采纳" onclick="dealwith();"/> 
         <input type="submit" class="btn btn-danger" value="反馈通过" onclick="passFeedback();"/> 
        <input type="text" id="iid" name="id" hidden="hidden"/>
      </div>
    </div>
  </div>
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
/* 	$.ajax({ 
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
	}) */
}


function banArticle(id){
/* 	$.ajax({
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
	}) */
}
</script>
</html>
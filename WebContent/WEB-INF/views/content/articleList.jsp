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
		<div class="data_list_title">文章列表</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/content/toArticle">
				<span class="data_search" style="float:left">
					<label for="exampleInput">状&nbsp;态:</label>
					<select class="form-control" id="searchType" name="searchType" style="width: 93px;">
						<c:if test="${empty searchType ||searchType eq  null}">
					    	<option selected="selected" value="">请选择</option>
							<option value="0">封禁</option>
							<option value="1">正常</option>
							<option value="2">待审核</option>
					    </c:if>
						
						<c:if test="${searchType==0 }">
						    <option value="">请选择</option>
							<option selected="selected" value="0">封禁</option>
							<option value="1">正常</option>
							<option value="2">待审核</option>
					    </c:if>
					    
						<c:if test="${searchType==1 }">
						    <option  value="">请选择</option>
							<option  value="0">封禁</option>
							<option selected="selected" value="1">正常</option>
							<option value="2">待审核</option>
					    </c:if>
					    
						<c:if test="${searchType==2 }">
						    <option  value="">请选择</option>
							<option  value="0">封禁</option>
							<option value="1">正常</option>
							<option selected="selected" value="2">待审核</option>
					    </c:if>
				    </select>
					
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th width="15%">标题</th>
					<th width="30%">文章描述</th>
					<th width="10%">封面</th>
					<th>状态</th>
					<th>种类</th>
					<th>阅读数</th>
					<th>评论数</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.title}</td>
						<td>${li.description}</td>
						<td><img src="${li.imgUrl}" width="100px"/></td>
						<td>
						  <c:if test="${li.status==1}">正常</c:if>
						  <c:if test="${li.status==0}"><p style="color:red">封禁</p></c:if>
						  <c:if test="${li.status==100}"><p style="color:#FFD700">推荐</p></c:if>
						</td>
						<td>${li.contentKind.kindName}</td>
						<td>${li.readingCount}</td>
						<td>${li.commentCount}</td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td>
						  <button class="btn btn-info" type="button" onclick="modifyArticle(${li.id})">修改</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button class="btn btn-danger" type="button" onclick="javascript:return confirm(deleteArticle,${li.id},'确定删除吗?');">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button class="btn btn-danger" type="button" onclick="banArticle(${li.id})">封禁</button>
						  <button class="btn btn-danger" type="button" onclick="tuiArticle(${li.id})">推荐</button>
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
		url:"${ctx}/content/deleteArticle?id="+id,
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
		url:"${ctx}/content/banArticle?id="+id,
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

function tuiArticle(id){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/tuiArticle?id="+id,
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
</script>
</html>
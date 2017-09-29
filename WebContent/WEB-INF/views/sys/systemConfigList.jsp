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
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addSystemConfig"> 添加</button>
				<button class="btn btn-primary" type="button" style="margin-right: 50px;" >提交表单样式</button>
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
					<th width="25%">名称</th>
					<!-- <th>参数</th> -->
					<th width="25%">设置值</th>
					<th width="25%">排序</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td><p style="text-align:center">${li.keyTitle}</p></td>
						<%-- <td><p style="text-align:center">${li.keyName}</p></td> --%>
						<td>
						<c:choose>
						<c:when test="${li.keyName!='unrealBroadcastMessage'&&li.keyName!='unrealBroadcastName'&&li.keyName!='agreement'}">
						<p style="text-align:center">${li.content}</p>
						</c:when>
						<%-- <c:otherwise test="${li.keyName=='unrealBroadcastMessage'||li.keyName=='unrealBroadcastName'}"> --%>
						<c:otherwise>
						<textarea rows="6" cols="70" disabled >${li.content}</textarea>
						</c:otherwise>
						</c:choose>
						</td>
						<td><p style="text-align:center">${li.sort}</p></td>
						<td>
						 <button class="btn btn-info" type="button" data-toggle="modal" data-target="#modifySystemConfig" onclick="modifySystemConfig('${li.keyName}');">修改</button>&nbsp;
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>



<script>
function modifySystemConfig(keyName){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/findEntityByKey?keyName="+keyName,
        success: function(data){
        	var keyTitle=data.keyTitle;
        	var content=data.content;
        	var sort=data.sort;
        	 $('#keyTitle').val(keyTitle);
        	 $('#content').val(content);
        	 $('#sort').val(sort);
        	 $('#keyName').val(keyName);
        }
	});
}

</script>

<!-- 修改 -->	
<form class="form-signin" action="${ctx}/sys/modifySystemConfig" method="post" id="myForm">
<div class="modal fade" id="modifySystemConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改配置</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">名称</td>
					<td>
						<input type="text" name="keyTitle"  id="keyTitle"/>
					</td>
				</tr>
				<tr>
					<td width="15%">设置值</td>
					<td>
						<!-- <input type="text" name="content"  id="content"/> -->
						<textarea rows="6" cols="60" name="content"  id="content"></textarea>
					</td>
				</tr>
				<tr>
					<td width="10%">排序</td>
					<td><input type="text" name="sort" id="sort"/></td>
				</tr>
				<input type="hidden" name="keyName" id="keyName"/>
				</tbody>
			</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <input type="submit" class="btn btn-primary" value="保存" />
      </div>
    </div>
  </div>
</div>
</form>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
</body>
</html>
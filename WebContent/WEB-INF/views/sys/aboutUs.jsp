<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<script>
function modifySystemConfig(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/findContentById?id="+id,
        success: function(data){
        	var title=data.au.title;
        	var content=data.au.content;
        	var type=data.au.type;
        	var sort=data.au.sort;
        	var id=data.au.id;
        	 $('#title').val(title);
        	 $('#content').val(content);
        	 $('#type').val(type);
        	 $('#sort').val(sort);
        	 $('#id').val(id);
			 if(type==1){
				 $('#typeDiv').html("<input name='type' type='radio' value='1' checked='checked' >常见问题</input>");
			 };
        }
	});
}
/* 
 *	confirm()函数已在meta.jsp中重写
 */
function deleteAboutUs(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/deleteAboutUs?id="+id,
        success: function(data){
    		alert("删除成功!");
    		 window.location.href = "${ctx}/sys/aboutUs";
        }
	});
}
</script>
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
					<th>编号</th>
					<th>标题</th>
					<th style="width:40%">内容</th>
					<th>类型</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td><p style="text-align:center">${li.id}</p></td>
						<td><p style="text-align:center">${li.title}</p></td>
						<td><textarea rows="3" cols="90" disabled >${li.content}</textarea></td>
						<td><p style="text-align:center">
						<c:if test="${li.type==1}">常见问题</c:if>
						</p></td>
						<td><p style="text-align:center">${li.sort}</p></td>
						<td>
						 <button class="btn btn-info" type="button" data-toggle="modal" data-target="#modifySystemConfig" onclick="modifySystemConfig('${li.id}');">修改</button>&nbsp;
						 <button class="btn btn-danger" type="button" onclick="javascript:return confirm(deleteAboutUs,${li.id},'确定要删除吗?');">删除</button>&nbsp;
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>


<!-- 修改 -->	
 <form class="form-signin" action="${ctx}/sys/modifyAboutUs" method="post" id="myForm">
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
					<td width="15%">标题</td>
					<td>
						<input type="text" name="title"  id="title"/>
					</td>
				</tr>
				<tr>
					<td width="15%">内容</td>
					<td>
					    <textarea rows="6" cols="60" name="content"  id="content"></textarea>
					</td>
				</tr>
				<tr>
					<td width="10%">类型</td>
					<td>
					<div id="typeDiv">
					</div>
					</td>
				</tr>
				<tr>
					<td width="15%">排序</td>
					<td>
						<input type="number" name="sort" id="sort" step="1" min="1"/>
					</td>
				</tr>
				<input type="hidden" name="id" id="id"/>
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

<!-- 添加 -->

<form class="form-signin" action="${ctx}/sys/addAboutUs" method="post" id="myForm">
<div class="modal fade" id="addSystemConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
					<td width="15%">标题</td>
					<td>
						<input type="text" name="title"  id="title"/>
					</td>
				</tr>
				<tr>
					<td width="15%">内容</td>
					<td>
					    <textarea rows="6" cols="60" name="content"  id="content"></textarea>
					</td>
				</tr>
				<tr>
					<td width="10%">类型</td>
					<td>
					<input name="type" type="radio" value="1" checked="checked">常见问题</input>&emsp;&emsp;
					</td>
				</tr>
				<tr>
					<td width="15%">排序</td>
					<td>
						<input type="number" name="sort" id="sort" step="1" min="1"/>
					</td>
				</tr>
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
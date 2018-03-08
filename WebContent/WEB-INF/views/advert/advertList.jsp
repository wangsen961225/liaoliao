<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<script type="text/javascript">

/* 
 *	confirm()函数已在meta.jsp中重写
 */
function deleteAdvert(id){
		$.ajax({
			type: "GET",
	        url: "${ctx}/sys/deleteAdvert?id="+id,
	        success: function(data){
        		alert("删除成功!");
        		 window.location.href = "${ctx}/sys/advertList";
	        }
		});
}
function banAdvert(id){
		$.ajax({
			type: "GET",
	        url: "${ctx}/sys/banAdvert?id="+id,
	        success: function(data){
	        	alert("已封禁 ！");
	          window.location.href = "${ctx}/sys/advertList";
	        }
		});
}
function openAdvert(id){
		$.ajax({
			type: "GET",
	        url: "${ctx}/sys/openAdvert?id="+id,
	        success: function(data){
	        	alert("已解禁!");
	          window.location.href = "${ctx}/sys/advertList";
	        }
		});
}
</script>
<body>
	<div class="data_list">
		<div class="data_list_title">文章列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addAdvert"> 添加</button>
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
		<div>
				<button  class="guanggao" name="off" value="off">开启广告</button>
				<button  class="guanggao1" name="on" value="on">关闭广告</button>
		</div>
		<script type="text/javascript">
			$(".guanggao").click(function(){
					$.ajax({
						type: "post",
				        url: "${ctx}/api/getADStatu?name="+$(".guanggao").val(),
				        success: function(data){
				        }
					});
			})
			$(".guanggao1").click(function(){
					$.ajax({
						type: "post",
				        url: "${ctx}/api/getADStatu?name="+$(".guanggao1").val(),
				        success: function(data){
				        }
					});
			})
		</script>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th>名称</th>
					<th>类型</th>
					<th width="20%">内容</th>
					<th>位置</th>
					<th>状态</th>
					<th>排序</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td><p style="text-align:center">${li.id}</p></td>
						<td><p style="text-align:center">${li.sourceName}</p></td>
						<td><p style="text-align:center">${li.type}</p></td>
						<td><textarea rows="5" cols="70" disabled >${li.content}</textarea></td>
						<td>
						  <%-- <c:if test="${li.position=='top'}">顶部</c:if>
						  <c:if test="${li.position=='bottom'}">底部</c:if> --%>
						  <p style="text-align:center">${li.position}</p>
						</td>
						<td>
						  <c:if test="${li.status==1}"><p style="text-align:center">正常</p></c:if>
						  <c:if test="${li.status==0}"><p style="color:red;font-size:3em;text-align:center">**封禁**</p></c:if>
						</td>
						<td>
						  <c:if test="${ empty li.sort}"><p style="text-align:center">空</p></c:if>
						  <c:if test="${not empty li.sort}"><p style="text-align:center">${li.sort }</p></c:if>
						</td>
						<td><p style="text-align:center"><fmt:formatDate value="${li.addTime}" type="both"/></p></td>
						<td>
						 <button class="btn btn-info" type="button" data-toggle="modal" data-target="#modifyAdvert" onclick="modifyAdvert(${li.id});">修改</button>&nbsp;
						<button class="btn btn-danger" type="button" onclick="javascript:return confirm(deleteAdvert,${li.id},'确定要删除吗?');">删除</button>&nbsp;
						 <c:if test="${li.status==1}"><button class="btn btn-danger" type="button" onclick="javascript:return confirm(banAdvert,${li.id},'确定要封禁吗?');">封禁</button>&nbsp;</c:if> 
						 <c:if test="${li.status==0}"><button class="btn btn-info" type="button" onclick="javascript:return confirm(openAdvert,${li.id},'确定要解禁吗?');">解禁</button>&nbsp;</c:if> 
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
<!-- 添加新广告 -->	
<form class="form-signin" action="${ctx}/sys/addAdvert" method="post" id="myForm">
<div class="modal fade" id="addAdvert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加新广告</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">名称</td>
					<td>
						<input type="text" name="sourceName"  />
					</td>
				</tr>
				<tr>
					<td width="15%">类型</td>
					<td>
						<input type="text" name="type"  />
					</td>
				</tr>
				<tr>
					<td width="15%">排序</td>
					<td>
						<input type="text" name="sort"  />
					</td>
				</tr>
				<tr>
					<td width="10%">广告代码</td>
					<td><textarea rows="5" cols="55" name="content" ></textarea></td>
				</tr>
				<tr>
					<td width="10%"> 位置</td>
					<td><input type="text" name="position"  /></td>
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


<script>
function modifyAdvert(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/getAdvert?id="+id,
        success: function(data){
        	var id=data.id;
        	var type=data.type;
        	var sourceName=data.sourceName;
        	var sort=data.sort;
        	var content=data.content;
        	var position=data.position;
        	var status=data.status;
        	 $('#id').val(id);
        	 $('#sourceName').val(sourceName);
        	 $('#sort').val(sort);
        	 $('#type').val(type);  
        	 $('#content').val(content); 
        	 $('#position').val(position); 
        	 $('#status').val(status); 
        }
	});
}

</script>

<!-- 修改广告 -->	
<form class="form-signin" action="${ctx}/sys/modifyAdvert" method="post" id="myForm">
<div class="modal fade" id="modifyAdvert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改广告</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">名称</td>
					<td>
						<input type="text" name="sourceName"  id="sourceName"/>
					</td>
				</tr>
				<tr>
					<td width="15%">类型</td>
					<td>
						<input type="text" name="type"  id="type"/>
					</td>
				</tr>
				<tr>
					<td width="10%">排序</td>
					<td><input type="text" name="sort"  id="sort"/></td>
				</tr>
				<tr>
					<td width="10%">广告代码</td>
					<td><textarea rows="5" cols="55" name="content"   id="content"></textarea></td>
				</tr>
				<tr>
					<td width="10%">位置</td>
					<td><input type="text" name="position"  id="position"/></td>
				</tr>
				</tbody>
			</table>
			<input type="text" value="" name="id" id="id" hidden="hidden"/>
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
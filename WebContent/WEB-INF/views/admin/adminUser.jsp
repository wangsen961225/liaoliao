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
		<div class="data_list_title">管理员用户列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addAdminUser"> 添加</button>
			<!-- 	<button class="btn btn-primary" type="button" style="margin-right: 50px;" 
				onclick="javascript:window.location=&#39;dormManager?action=preSave&#39;">提交表单样式</button> -->
				<!-- <label for="exampleInput">输入框示例</label>
				<input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" /> -->
			<!-- 	<span class="data_search">
					<select class="form-control" id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="userName">用户名</option>
					</select>
					<input id="s_dormManagerText" name="s_dormManagerText" type="text" style="border-radius:50px" class="form-control" value="">
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span> -->
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th>用户名</th>
					<th>状态</th>
					<th>用户组</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.userName}</td>
						<td>
						  <c:if test="${li.status==1}">正常</c:if>
						  <c:if test="${li.status==0}">封禁</c:if>
						</td>
						<td>${li.adminGroup.groupName}</td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td>
						  <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modAdminUser" onclick="modAdminUser(${li.id});"> 修改</button>
						  &nbsp;
						  <button class="btn btn-danger" type="button" onclick="dormManagerDelete(1)">删除</button>&nbsp;
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
	
<!-- 添加新用户 -->	
<form class="form-signin" action="${ctx}/sys/addAdminUser" method="post" id="myForm">
<div class="modal fade" id="addAdminUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加新权限</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">父级菜单</td>
					<td>
						<select style="width:120px;height:28px" name="groupId">
						 <option value="">用户组</option>
						   <c:forEach var="li" items="${agList}">
						     <option value ="${li.id}">${li.groupName}</option>
						  </c:forEach> 
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%">用户名</td>
					<td><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td width="10%"> 密码</td>
					<td><input type="text" name="passWord" /></td>
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


<script type="text/javascript">
function modAdminUser(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/getAdminUser?id="+id,
        success: function(data){
        	if(data.msg==0){
        		alert("用户组选择错误!");
        	}
        	if(data.msg==1){
        		 //   	alert(data.count);
            	var id=data.id;
            	var name=data.userName;
            	var groupName=data.groupName;
            	var groupId=data.groupId;
            	 $('#nid').val(id);
            	 $('#userName').val(name);  
            	 $('#groupName').val(groupName); 
            	 $('#groupId').val(groupId); 
            	 var obj=document.getElementById('groupId');
            	 for(var i=0;i<obj.options.length;i++){
                     if(groupId == obj.options[i].value){  
                    	 obj.options[i].selected = 'selected';
                         break;  
                       }  
                    }
        	}
    
        }
	});
}
</script>
<!-- 修改用户 -->	
<form class="form-signin" action="${ctx}/sys/modAdminUser" method="post" id="myForm">
<div class="modal fade" id="modAdminUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加新权限</h4>
      </div>
      <div class="modal-body">
         <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">父级菜单</td>
					<td>
						<select style="width:120px;height:28px" id="groupId" name="groupId">
						  <c:forEach var="li" items="${agList}">
						     <option value ="${li.id}">${li.groupName}</option>
						  </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%">用户名</td>
					<td><input type="text" name="userName"  id="userName"/></td>
				</tr>
				<tr>
					<td width="10%"> 密码</td>
					<td><input type="text" name="passWord"  id="passWord"/></td>
				</tr>
				</tbody>
			</table>
			<input type="text" id="nid" name="userId" value="" hidden="hidden"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <input type="submit" class="btn btn-primary" value="保存" />
      </div>
    </div>
  </div>
</div>
</form>



	
</body>
</html>
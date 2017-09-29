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
/* function add(id){
	window.open("${ctx}/sys/toAddPermission", "newwindow", 
	 "height=580, width=770, top=220, left=600, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	 window.location.reload();
} */
/**
 *控制子菜单选项 
 */
function childContent(id){
	 $(".tr1").remove();
	 $("#childContent").toggle();
	 $.ajax({
		type: "GET",
        url: "${ctx}/sys/childNavigation?id="+id,
        success: function(data){
        	var length=data.list.length;
        	if(length<=0){
        	  $("#aa").after("<tr class='tr1'><td colspan='6'><p align='center' style='color:red'>无下级 </p></td></tr>");
        	}else
        	for(i=0;i<length;i++){
  		      $("#aa").after("<tr class='tr1'> "+
  				    "<td>"+data.list[i].id+"</td>"+
  				    "<td>"+data.list[i].parentName+"</td>"+
  					"<td>"+data.list[i].navigationName+"</td>"+
  					"<td>"+data.list[i].navigationUrl+"</td>"+
  					"<td>"+data.list[i].sort+"</td>"+
  					"<td>"+
  					"<button class='btn btn-info' type='button' data-toggle='modal' data-target='#modifyNav' onclick='modifyPer("+data.list[i].id+");'>修改</button>&nbsp;"+
  					"<button class='btn btn-danger' type='button' onclick='javascript:return confirm(deleteNavigation,"+data.list[i].id+",&quot;确定要删除吗?&quot;);'>删除"+
  		            "</button>&nbsp;"+
  				    "</td></tr>");
        	}
          }
    });
}

/* 
 *	confirm()函数已在meta.jsp中重写
 */
function deleteNavigation(id){
		$.ajax({
			type: "GET",
	        url: "${ctx}/sys/deleteNavigation?id="+id,
	        success: function(data){
	        	if(data.msg==1){
	        		alert("删除成功!");
	        		 window.location.href = "${ctx}/sys/permissionList";
	        	}else{
	        		alert("删除失败! ");
	        	}
	        }
		});
}

/* function modifyPermission(id){
	 window.open ("page.html", "newwindow", 
			 "height=580, width=770, top=220, left=600, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
} */
</script>
<body>
	<div class="data_list">
		<div class="data_list_title">权限列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addNav"> 添加</button>
				<!-- <button class="btn btn-primary" type="button" style="margin-right: 50px;" onclick="">提交表单样式</button>
				<label for="exampleInput">输入框示例</label>
				<input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" />
				<span class="data_search">
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
				<tbody id="tb">
				<tr>
					<th>编号</th>
					<th>父权限名</th>
					<th>序号</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.navigationName}</td>
						<td>${li.sort}</td>
						<td>
						  <button class="btn btn-info" type="button" data-toggle="modal" data-target="#modifyNav" onclick="modifyPer(${li.id});">修改</button>&nbsp;
						  <button class="btn btn-danger" type="button" onclick="javascript:return confirm(deleteNavigation,${li.id},'确定要删除吗?');">删除</button>&nbsp;
						  <button class="btn btn-info" type="button" onclick="childContent(${li.id})">子菜单详情</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div> 
		<hr/>
		<div id="childContent" style="display: none;">
		 <input class="btn btn-info" type="button" value="子菜单详情" />
		   <table class="table table-hover table-striped table-bordered">
				<tbody>
				   <tr id="aa">
				    <th>编号</th>
				    <th>父权限名</th>
					<th>权限名</th>
					<th>url</th>
					<th>序号</th>
					<th width="20%">操作</th>
				   </tr>
				</tbody>
			</table>
		</div>
	</div>
	
<!-- 添加新权限 -->	
 <form class="form-signin" action="${ctx}/sys/addPermission" method="post" id="myForm">
<div class="modal fade" id="addNav" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
						<select style="width:120px;height:28px" name="parentId">
							<option value="">无上级</option>
						  <c:forEach var="li" items="${list}">
						     <option value ="${li.id}">${li.navigationName}</option>
						  </c:forEach>
						</select><P style="color:red;font-size:1em">*无父类不用选*</P>
					</td>
				</tr>
				<tr>
					<td width="10%">菜单名</td>
					<td><input type="text" name="navigationName" /></td>
				</tr>
				<tr>
					<td width="10%"> 路径url</td>
					<td><input type="text" name="navigationUrl" /><P style="color:red;font-size:1em">*无父类不用填*</P></td>
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

function modifyPer(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/getNavigation?id="+id,
        success: function(data){
     //   	alert(data.count);
        	var id=data.id;
        	var name=data.navigationName;
        	var url=data.navigationUrl;
        	var parentName=data.parentName;
        	var parentId=data.parentId;
        	 $('#nid').val(id);
        	 $('#navigationName').val(name);  
        	 $('#navigationUrl').val(url); 
        	 var obj=document.getElementById('parentId');
        	 
			/* if(obj.options.length != data.count){
				obj.options.remove(data.count);	
			} */
        	 for(var i=0;i<obj.options.length;i++){  
                 if(parentId == obj.options[i].value){  
                	 obj.options[i].selected = 'selected';
                     break;  
                   }  
                }
			
        /* 	 for(var i=0;i<obj.options.length;i++){  
                 if(""==obj.options[i].value){  
                	 obj.options[i].selected = 'selected';  
                     break;  
                   }  
                } */
        	
        	/*   if(parentName != "1"){
        		  $("#parentId").append("<option value='"+parentId+"' id='remoId' name='remoId'>"+parentName+"</option>");
        	 }else{
        		 $("#parentId").append("<option value='' id='remoId' name='remoId'>"+"无上级 "+"</option>");
        	 }  */
        }
	});
}


</script>

<!-- 修改权限 -->
<form class="form-signin" action="${ctx}/sys/modifyNavigation" method="post" id="myForm">
<div class="modal fade" id="modifyNav" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改权限</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">父级菜单</td>
					<td>
						<select style="width:120px;height:28px" id="parentId" name="parentId">
							<option  value="">无上级</option>
						  <c:forEach var="li" items="${list}">
						     <option value ="${li.id}">${li.navigationName}</option>
						  </c:forEach>
						</select><P style="color:red;font-size:1em">*无父类不用选*</P>
					</td>
				</tr>
				<tr>
					<td width="10%">菜单名</td>
					<td><input type="text" name="navigationName"  id="navigationName"/></td>
				</tr>
				<tr>
					<td width="10%"> 路径url</td>
					<td><input type="text" name="navigationUrl"  id="navigationUrl"/><P style="color:red;font-size:1em">*无父类不用填*</P></td>
				</tr>
				</tbody>
			</table>
			<input type="text" id="nid" name="nid" value="" hidden="hidden"/>
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
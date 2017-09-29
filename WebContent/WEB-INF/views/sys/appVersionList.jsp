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
function deleteAppVersion(id){
		$.ajax({
			type: "GET",
	        url: "${ctx}/sys/deleteAppVersion?id="+id,
	        success: function(data){
        		alert("删除成功!");
        		 window.location.href = "${ctx}/sys/appVersionList";
	        }
		});
}
</script>
<body>
	<div class="data_list">
		<div class="data_list_title">文章列表</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addAppVersion"> 添加</button>
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
					<th>内部版本号</th>
					<th>版本号</th>
					<th width="20%">更新信息</th>
					<th>下载链接</th>
					<th>类型</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td><p style="text-align:center">${li.id}</p></td>
						<td><p style="text-align:center">${li.versionCode}</p></td>
						<td><p style="text-align:center">${li.versionName}</p></td>
						<td><textarea rows="5" cols="70" disabled >${li.updateInfo}</textarea></td>
						<td><p style="text-align:center">${li.downloadUrl}</p></td>
						<td><p style="text-align:center">
						<c:if test="${li.type==0}">非强制更新</c:if>
						<c:if test="${li.type==1}">强制更新</c:if>
						</p></td>
						<td><p style="text-align:center"><fmt:formatDate value="${li.addTime}" type="both"/></p></td>
						<td>
						 <button class="btn btn-info" type="button" data-toggle="modal" data-target="#modifyAppVersion" onclick="modifyAppVersion(${li.id});">修改</button>&nbsp;
						<button class="btn btn-danger" type="button" onclick="javascript:return confirm(deleteAppVersion,${li.id},'确定要删除吗?');">删除</button>&nbsp;
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
<!-- 添加 -->	
<form class="form-signin" action="${ctx}/sys/addAppVersion" method="post" id="myForm">
<div class="modal fade" id="addAppVersion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">内部版本号</td>
					<td>
						<input type="text" name="versionCode"  />
					</td>
				</tr>
				<tr>
					<td width="15%">版本号</td>
					<td>
						<input type="text" name="versionName"  />
					</td>
				</tr>
				<tr>
					<td width="15%">下载链接</td>
					<td>
						<input type="text" name="downloadUrl"  />
					</td>
				</tr>
				<tr>
					<td width="10%">更新信息</td>
					<td><textarea rows="5" cols="55" name="updateInfo" ></textarea></td>
				</tr>
				<tr>
					<td width="10%">类型</td>
					<td>
					<input name="type" type="radio" value="0" checked="checked">非强制更新</input>&emsp;&emsp;
                    <input name="type" type="radio" value="1">强制更新</input>
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


<script>
function modifyAppVersion(id){
	$.ajax({
		type: "GET",
        url: "${ctx}/sys/getAppVersion?id="+id,
        success: function(data){
        	var id=data.id;
        	var versionCode=data.versionCode;
        	var versionName=data.versionName;
        	var updateInfo=data.updateInfo;
        	var downloadUrl=data.downloadUrl;
        	var type=data.type;
        	 $('#id').val(id);
        	 $('#versionCode').val(versionCode);
        	 $('#versionName').val(versionName);
        	 $('#updateInfo').val(updateInfo);
        	 $('#downloadUrl').val(downloadUrl);
			 if(type==0){
				 $('#typeDiv').html("<input name='type' type='radio' value='0' checked='checked' >非强制更新</input>&emsp;&emsp;<input name='type' type='radio' value='1' >强制更新</input>");
			 };
			 if(type==1){
				 $('#typeDiv').html("<input name='type' type='radio' value='0' >非强制更新</input>&emsp;&emsp;<input name='type' type='radio' value='1' checked='checked' >强制更新</input>");
			 };
        }
	});
}

</script>

<!-- 修改 -->	
<form class="form-signin" action="${ctx}/sys/modifyAppVersion" method="post" id="myForm">
<div class="modal fade" id="modifyAppVersion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改</h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<td width="15%">内部版本号</td>
					<td>
						<input type="text" name="versionCode"  id="versionCode"/>
					</td>
				</tr>
				<tr>
					<td width="15%">版本号</td>
					<td>
						<input type="text" name="versionName"  id="versionName"/>
					</td>
				</tr>
				<tr>
					<td width="10%">下载链接</td>
					<td><input type="text" name="downloadUrl"  id="downloadUrl"/></td>
				</tr>
				<tr>
					<td width="10%">更新信息</td>
					<td><textarea rows="5" cols="55" name="updateInfo"  id="updateInfo"></textarea></td>
				</tr>
				<tr>
					<td width="10%">类型</td>
					<td>
					<div id="typeDiv">
					</div>
					</td>
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
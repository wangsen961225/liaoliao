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
function subForm(){
$.ajax({
    cache: true,
    type: "POST",
    url:"${ctx}/sys/sendMessage",
    data:$('#myForm').serialize(), 
    success:function(data){
    	
    	alert(1);
    	alert("发送成功 ");
    }
});
}
</script>
<body>
	<div class="data_list">
		<div class="data_list_title">发送系统消息</div>
		<form name="myForm" class="form-inline" method="post" action="#" id="myForm">
				<label for="title">标题</label>
				<input type="text" class="form-control" placeholder="标题" id="title"  name="title"/>
				<label for="content">内容</label>
				<input type="text" class="form-control" placeholder="内容" id="content" name="content"/>
				<button  class="btn btn-info"  onclick="subForm()">发送</button>
		</form>
	</div>
</body>
</html>
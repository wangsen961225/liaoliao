<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/style/public/multiselect.min.js"></script>
<title>${sys_title}</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章抓取</title>
</head>
<body>
<input class="btn btn-info" type="button" onclick="meipaiVideo()"  value="获取美拍视频" style="margin-top: 20%;margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="neihanVideo()"  value="获取内涵段子视频" style="margin-top: 20%;margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="kuaishipinVideo()"  value="获取360快视频" style="margin-top: 20%;margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="xiaokaxiuVideo()"  value="获取小咖秀视频" style="margin-top: 20%;margin-left: 20%;"/>
</body>
<script type="text/javascript">
function meipaiVideo(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/meipaiVideo",
		success:function(data){
			if(data.msg=="success"){
				alert("已从美拍获取:"+data.count+"段视频啦 !");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}

function neihanVideo(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/neihanVideo",
		success:function(data){
			if(data.msg=="success"){
				alert("已从内涵段子获取:"+data.count+"段视频啦 !");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}

function kuaishipinVideo(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/kuaishipinVideo",
		success:function(data){
			if(data.msg=="success"){
				alert("已从360快视频获取:"+data.count+"段视频啦!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
	}
	});
}

function xiaokaxiuVideo(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/xiaokaxiuVideo",
		success:function(data){
			if(data.msg=="success"){
				alert("已从小咖秀获取:"+data.count+"段视频啦!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}




</script>
</html>
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
<input class="btn btn-info" type="button" onclick="toutiaoArticle()" value="获取今日头条文章" style="margin-top: 20%; margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="dongfangArticle()" value="获取东方头条文章" style="margin-top: 20%; margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="qutoutiaoArticle()" value="获取趣头条文章" style="margin-top: 20%; margin-left: 20%;"/>
<input class="btn btn-info" type="button" onclick="mobileSinaArticle()" value="获取手机新浪文章" style="margin-top: 20%; margin-left: 20%;"/>

</body>
<script type="text/javascript">

function toutiaoArticle(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/toutiaoArticle",
		success:function(data){
			if(data.msg == "success"){
				alert("已从今日头条获取文章:"+data.count+"篇!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}


function dongfangArticle(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/dongfangArticle",
		success:function(data){
			if(data.msg == "success"){
				alert("已从东方头条获取文章:"+data.count+"篇!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}

function qutoutiaoArticle(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/qutoutiaoArticle",
		success:function(data){
			if(data.msg == "success"){
				alert("已从趣头条获取文章:"+data.count+"篇!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}
function mobileSinaArticle(){
	$.ajax({
		type:"GET",
		url:"${ctx}/content/mobileSinaArticle",
		success:function(data){
			if(data.msg == "success"){
				alert("已从手机新浪获取文章:"+data.count+"篇!");
			}else{
				alert("阿偶,出错咯,重新获取吧~");
			}
		}
	});
}

</script>
</html>
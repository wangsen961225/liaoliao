<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<link href="${ctx}/style/css/video.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">原创视频列表</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/userSelfVideo">
		
				<!-- <button class="btn btn-primary" type="button" style="margin-right: 50px;"  >提交表单样式</button> -->
				<!-- <label for="exampleInput">输入框示例</label> -->
				<!-- <input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" /> -->
				
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
					
					<!-- <input id="s_dormManagerText" name="s_dormManagerText" type="text" style="border-radius:50px" class="form-control" value=""> -->
					
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th width="15%">标题</th>
					<th width="15%">视频描述</th>
					<th>视频时长</th>
					<th width="10%">封面</th>
					<th>状态</th>
					<th>播放数</th>
					<th>评论数</th>
					<th>添加时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.id}</td>
						<td>${li.title}</td>
						<td>${li.description}</td>
						<td>${li.duration}</td>
						<td><img src="${li.imgUrl}" height="80px"/></td>
						<td>
						  <c:if test="${li.status==2}">待审核</c:if>
						  <c:if test="${li.status==1}">正常</c:if>
						  <c:if test="${li.status==0}"><p style="color:red">封禁</p></c:if>
						</td>
						<td>${li.playingCount}</td>
						<td>${li.commentCount}</td>
						<td><fmt:formatDate value="${li.addTime}" type="both"/></td>
						<td>
						 <%--  <button class="btn btn-info" type="button" onclick="videoInfo(${li.id})" id="video">查看详情</button>&nbsp;&nbsp;&nbsp;&nbsp; --%>
						  <c:if test="${li.status==2}">
						    <button class="btn btn-warning" type="button" data-toggle="modal" data-target="#videoInfo" onclick="videoInfo(${li.id});">进入审核</button>&nbsp;
						  	<button class="btn btn-danger" type="button" onclick="banOriginalVideo(${li.id});">封禁</button>
						  	<button class="btn btn-danger" type="button" onclick="javascript:return confirm(delOriginalVideo,${li.id},'确定删除吗?');">删除</button>
						  </c:if>
						  <c:if test="${li.status==1}">
						  <button class="btn btn-info" type="button" data-toggle="modal" data-target="#videoInfo" onclick="videoInfo(${li.id});">查看详情</button>
						  <button class="btn btn-danger" type="button" onclick="banOriginalVideo(${li.id});">封禁</button>&nbsp;
						  </c:if>
						  <c:if test="${li.status==0}">
						    <button class="btn btn-info" type="button" data-toggle="modal" data-target="#videoInfo" onclick="videoInfo(${li.id});">查看详情</button>
						    <button class="btn btn-danger" type="button" onclick="unlockOriginalVideo(${li.id});">解封</button>&nbsp;
						    <button class="btn btn-danger" type="button" onclick="javascript:return confirm(delOriginalVideo,${li.id},'确定删除吗?');">删除</button>&nbsp;
						  </c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				<div id="aaa"> 
				</div>
			</table>
		</div>
		<%@ include file="/style/public/footPage.jsp"%>
	</div>
<script type="text/javascript">
	function videoInfo(id){
	 	$.ajax({
			type: "GET",
	        url: "${ctx}/sys/videoInfo?id="+id,
	        success: function(data){
	        	if(data.code==0){
	        		alert(data.msg);
	        	}else{
	        		var id=data.id;
		        	var title=data.title;
		        	var src=data.src;
		        	var status=data.status;
		        	$("#video").prop("src",src);
		        	$('#videoId').val(id);
		        	if(status!=2){
		        		$("#passVideoButton").hide();
		        	}
	        	}
	        }
		}); 
	}
	
	function passOriginalVideo(){
		var id = $('#videoId').val();
		$.ajax({
			type:"POST",
			url:"${ctx}/sys/passOriginalVideo",
			data:{"id":id},
			success:function(data){
				if(data.code==1){
					alert("审核通过!");
					window.location.href="${ctx}/sys/userSelfVideo";
				}else{
					alert(data.msg);
				}
			}
		})
	}
	
	function banOriginalVideo(id){
		$.ajax({
			type:"GET",
			url:"${ctx}/sys/banOriginalVideo?id="+id+"&type="+1,
			success:function(data){
				if(data.code==1){
					alert(data.msg);
					window.location.href="${ctx}/sys/userSelfVideo";
				}else{
					alert(data.msg);
				}
			},
			error:function(){
				alert("出错啦~~");
			}
		})
	}
	function unlockOriginalVideo(id){
		$.ajax({
			type:"GET",
			url:"${ctx}/sys/banOriginalVideo?id="+id+"&type="+2,
			success:function(data){
				if(data.code==1){
					alert(data.msg);
					window.location.href="${ctx}/sys/userSelfVideo";
				}else{
					alert(data.msg);
				}
			},
			error:function(){
				alert("出错啦~~");
			}
		})
	}
	
	
	
	
</script>
	<!-- 查看详情 -->
<div class="modal fade" id="videoInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel" style="text-align:center;"><p id="articleTitle"></p></h4>
      </div>
      <div class="modal-body" id="content">
	  	<body>
			<video  id="video" controls="controls" style="width:100%;height:100%" >
				<source src="" type="video/mp4">
			</video>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeVideo()">关闭</button>
        <input type="submit" class="btn btn-primary" value="通过审核" onclick="passOriginalVideo()" id="passVideoButton"/>
        <input type="text" id="videoId" name="videoId" hidden="hidden"/>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
function closeVideo(){
	var video = document.getElementById("video");
	video.pause();
}
  function passVideo(id){
	  $.ajax({
			type:"GET",   	 
	    	url:"${ctx}/sys/passVideo?id="+id,
	    	success:function(data){
	    		if(data.code==1){
	    			alert("审核通过");
	    			var ovideo=document.getElementById('video-btn'); 
	    			var oshadow=document.getElementById('shadow');
	    			ovideo.style.display='none';
	    			oshadow.style.display='none';
	    		}else{
	    			alert(data.msg);
	    		}
	    	},
	    	error:function(){
	    		alert("出错啦~~");
	    	}
	     })  
    }
	  

function banVideo(id){
	$.ajax({
		type:"GET",
	//	url:"${ctx}/content/banVideo?id="+id,
		success:function(data){
			if(data.code==200){
				alert(data.msg);
				window.location.href="${ctx}/content/toVideo";
			}
		},
		error:function(){
			alert("出错啦~~");
		}
	})
}

/* 删除未通过审核,禁封的用户原创视频 */
 function delOriginalVideo(id){
		$.ajax({
			type:"GET",
			url:"${ctx}/sys/delOriginalVideo?id="+id+"&type=1",
			success:function(data){
				if(data.flag==0){
					alert(data.msg);
					window.location.href="${ctx}/sys/userSelfVideo";
				}
				if(data.flag==1){
					alert(data.msg);
				}
			},
			error:function(){
				alert("出错啦~~");
			}
		})
	}

</script>

</html>


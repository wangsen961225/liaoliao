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




</script>
<body>
	<div class="data_list">
		<div class="data_list_title">阅读记录列表</div>
	
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody id="tb">
				<tr>
					<th>用户昵称</th>
					<th>阅读名称</th>
					<th>类型</th>
					<th>次数</th>
					<th>获得料币</th>
					<th>阅读时间</th>
					<!-- <th width="20%">操作</th> -->
				</tr>
				<c:forEach var="li" items="${list}">
					<tr>
						<td>${li.read}</td>
						<td>${li.title}</td>
						<td>${li.type}</td>
						<td>${li.num}</td>
						<td>${li.money}</td>
						<td>${li.addDate}</td>
						<%-- <td>
						 <button class="btn btn-info" type="button" onclick="childContent(${li.read})">子菜单详情</button>
						</td> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%@ include file="/style/public/footPage.jsp"%>
		</div> 
		<hr/>
	<!-- <div id="childContent" >
			 <input class="btn btn-info" type="button" value="关键字详情" />
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
		</div> -->
	</div>
	


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
        }
	});
}


</script>



</body>
</html>

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
<script type="text/javascript">
function formReset(){
	location.reload();   
}
</script>
<body>
<div class="row" style="margin-top:40px;width:70%;height:800px;margin-left: 239px;">
    <div class="col-sm-5">
    <h3>未拥有权限</h3>
        <select name="from" id="optgroup" class="form-control" size="8" multiple="multiple" style="height:600px">
               <c:forEach var="n" items="${nList}">
				 <c:if test="${(n.navigation.id == n.id) || (empty n.navigation)}">
				  <optgroup label="${n.navigationName}">
				  	<c:forEach var="nl" items="${nList}">
				  		<c:if test="${nl.navigation.id != nl.id && nl.navigation.id == n.id }">
				  		<option value="${n.id}+${nl.id}">${nl.navigationName }</option> 
				  		</c:if>
				  	</c:forEach>
				  </optgroup>
				 </c:if>
				</c:forEach>
        </select>
    </div>
     <h3>&nbsp;</h3>
    <div class="col-sm-2" style="margin-top:200px">
        <button type="button" id="optgroup_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
        <button type="button" id="optgroup_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
        <button type="button" id="optgroup_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
        <button type="button" id="optgroup_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
    </div>
    
    <div class="col-sm-5" style="margin-top: -55px;" >
     <h3>当前拥有权限</h3>
        <select name="to" id="optgroup_to" class="form-control" size="8" multiple="multiple" style="height:600px">
            <c:forEach var="p" items="${pList}">
				 <c:if test="${(p.navigation.navigation.id == p.navigation.id) || (empty p.navigation.navigation)}">
				  <optgroup label="${p.navigation.navigationName}">
				  	<c:forEach var="pl" items="${pList}">
				  		<c:if test="${pl.navigation.navigation.id != pl.navigation.id && pl.navigation.navigation.id == p.navigation.id }">
				  		<option value="${p.navigation.id}+${pl.navigation.id}">${pl.navigation.navigationName }</option> 
				  		</c:if>
				  	</c:forEach>
				  </optgroup>
				 </c:if>
				</c:forEach>
        </select>
    </div>
</div>
   <div style="float:right;margin-right: 273px;margin-top: -90px;">
	 <button type="button" onclick="formReset()" class="btn btn-danger" >重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <button class="btn btn-info" type="button" onclick="submit()" >提交</button>
     </div>
</body>
<script type="text/javascript">
$(document).ready(function($) {
    $("#optgroup").multiselect();
});
function submit(){
	var selectValueStr = [];  
	$("#optgroup_to option").each(function () {  
	    selectValueStr.push($(this).val());  
	});
	window.location.href="${ctx}/sys/updateGroupPermission?selVal="+selectValueStr+"&groupId=${groupId}";
}
</script>
</html>


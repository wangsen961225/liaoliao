<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<link
	href="${ctx}/style/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx}/style/public/multiselect.min.js"></script>
<title>${sys_title}</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章抓取</title>
</head>
<body>

	<div style="margin-left: 42%; margin-top: 2%; width: 15%;">
		<input class="btn btn-danger" type="button" id="timer"
			onclick="removeQuartz('抓取文章')" value="开启用户分润"
			style="margin-top: 1%; margin-left: 26%;" />
		<hr />
		<input class="btn btn-info" type="button" id="openVideo"
			onclick="startQuartz('抓取视频')" value="开启抓取视频" /> <input
			class="btn btn-info" type="button" id="closeVideo"
			onclick="removeQuartz('抓取视频')" value="关闭抓取视频" />
		<hr />
		<input class="btn btn-info" type="button" id="openArticle"
			onclick="startQuartz('抓取文章')" value="开启抓取文章" /> <input
			class="btn btn-info" type="button" id="closeArticle"
			onclick="removeQuartz('抓取文章')" value="关闭抓取文章" />
		<hr />
		<input class="btn btn-info" type="button" id="openVipLogin"
			onclick="startQuartz('霸屏')" value="开启会员霸屏" /> <input
			class="btn btn-info" type="button" id="closeVipLogin"
			onclick="removeQuartz('霸屏')" value="关闭会员霸屏" />
		<hr />
		<input class="btn btn-info" type="button" id="openPlay"
			onclick="startQuartz('理财收益')" value="开启理财收益" /> <input
			class="btn btn-info" type="button" id="closePlay"
			onclick="removeQuartz('理财收益')" value="关闭理财收益" />
		<hr />
		<input class="btn btn-info" type="input" id="sysRedNum" value="红包个数"
			onfocus="if(value=='红包个数'){value=''}"
			onblur="if(value==''){value='红包个数'}"
			style="margin-top: 4px; margin-left: 8px;" /> <input
			class="btn btn-info" type="input" id="sysRedMoney" value="金额"
			onfocus="if(value=='金额'){value=''}"
			onblur="if(value==''){value='金额'}"
			style="margin: 4px; margin-left: 8px;" /> <input class="btn btn-info"
			type="button" id="openSysRed" onclick="startQuartz('系统红包')"
			value="开启系统红包" /> <input class="btn btn-info" type="button"
			id="closeSysRed" onclick="removeQuartz('系统红包')" value="关闭系统红包" />
		<hr />

		<form name="myForm" class="form-inline" method="post"
			action="${ctx}/sys/setReadDoubleTime">
			<div class="form-group">
				<label for="dtp_input2" class="control-label">设置开始日期</label>
				<div class="input-group date form_date" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
					data-link-format="yyyy-mm-dd">
					<input name="beginReadDoubleTime" id="beginReadDoubleTime"
						class="form-control" size="16" type="text"
						value="${beginReadDoubleTime}" readonly> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>

			<div class="form-group">
				<label for="dtp_input2" class="control-label">设置结束日期</label>
				<div class="input-group date form_date" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
					data-link-format="yyyy-mm-dd">
					<input name="closeReadDoubleTime" id="closeReadDoubleTime"
						class="form-control" size="16" type="text"
						value="${closeReadDoubleTime}" onblur="compareTime()" readonly>
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-remove"></span></span> <span
						class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			<button class="btn btn-info" type="button" id="submitTime"
				onclick="javascript:submitForm()">提交设置时间</button>
			<br />
			<br />
			<br />
		</form>
		<script>
			function submitForm() {
				myForm.submit();
				//removeQuartz('阅读翻倍');
			}
		</script>
		<!-- <script>
			function compareTime(){
				$("#beginReadDoubleTime").val();
				$("#closeReadDoubleTime").val();
				if($("#beginReadDoubleTime").val()>$("#closeReadDoubleTime").val()){
					$("#submitTime").attr("disabled",true);
				}else{
					$("#submitTime").attr("disabled",false);
				}
			}
		</script> -->
		<input class="btn btn-info" type="button" id="openReadDouble"
			onclick="startQuartz('阅读翻倍')" value="开启阅读翻倍" /> <input
			class="btn btn-info" type="button" id="closeReadDoble"
			onclick="removeQuartz('阅读翻倍')" value="关闭阅读翻倍" />
		<hr />
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : "${ctx}/sys/findQuartz",
			success : function(data) {
				if (data.sysRed == 0) {
					$("#openSysRed").attr("disabled", false);
					$("#closeSysRed").attr("disabled", true);
				}
				if (data.sysRed == 1) {
					$("#openSysRed").attr("disabled", true);
					$("#closeSysRed").attr("disabled", false);
				}
				if (data.play == 0) {
					$("#openPlay").attr("disabled", false);
					$("#closePlay").attr("disabled", true);
				}
				if (data.play == 1) {
					$("#openPlay").attr("disabled", true);
					$("#closePlay").attr("disabled", false);
				}
				if (data.video == 0) {
					$("#openVideo").attr("disabled", false);
					$("#closeVideo").attr("disabled", true);
				}
				if (data.video == 1) {
					$("#openVideo").attr("disabled", true);
					$("#closeVideo").attr("disabled", false);
				}
				if (data.article == 0) {
					$("#openArticle").attr("disabled", false);
					$("#closeArticle").attr("disabled", true);
				}
				if (data.article == 1) {
					$("#openArticle").attr("disabled", true);
					$("#closeArticle").attr("disabled", false);
				}
				if (data.VIPLogin == 0) {
					$("#openVipLogin").attr("disabled", false);
					$("#closeVipLogin").attr("disabled", true);
				}
				if (data.VIPLogin == 1) {
					$("#openVipLogin").attr("disabled", true);
					$("#closeVipLogin").attr("disabled", false);
				}
				if (data.timer == 1) {
					$("#timer").attr("disabled", true);
				}
				if (data.readDouble == 0) {
					$("#openReadDouble").attr("disabled", false);
					$("#closeReadDoble").attr("disabled", true);
				}
				if (data.readDouble == 1) {
					$("#openReadDouble").attr("disabled", true);
					$("#closeReadDoble").attr("disabled", false);
				}

			}
		})
	})

	function startQuartz(data) {
		var time = "";
		if (data == "抓取文章" || data == "抓取视频") {
			time = "0 0/10 * * * ?";
		}
		if (data == "霸屏") {
			time = "0 0/15 * * * ?";
		}
		if (data == "阅读翻倍") {
			time = "0 0/1 * * * ?";
		}
		if (data == "理财收益") {
			time = "0 0 18 * * ?";
		}
		if (data == "系统红包") {
			time = "0 30 9 * * ?";
			$.ajax({
				type : "GET",
				url : "${ctx}/sys/startQuartz?jobName=" + data
						+ "&jobGroup=1&time=" + time,
				success : function(data) {
					location.reload();
					alert(data.msg);
				}
			});
		} else {
			$.ajax({
				type : "GET",
				url : "${ctx}/sys/startQuartz?jobName=" + data
						+ "&jobGroup=1&time=" + time,
				success : function(data) {
					location.reload();
					alert(data.msg);
				}
			});
		}
	}

	function removeQuartz(data) {
		$.ajax({
			type : "GET",
			url : "${ctx}/sys/removeQuartz?jobName=" + data + "&jobGroup=1",
			success : function(data) {
				location.reload();
				alert(data.msg);
			}
		});
	}
</script>
<script type="text/javascript"
	src="${ctx}/style/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="${ctx}/style/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript">
	$('.form_datetime').datetimepicker({
		//language:  'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});
	$('.form_date').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
	$('.form_time').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 1,
		minView : 0,
		maxView : 1,
		forceParse : 0
	});
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, maximum-scale=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
<title>料料 - 邀请注册</title>
<link href="${pageContext.request.contextPath}/style/layui/css/layui.css" rel="stylesheet">
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/style/layui/layui.js"></script>
<style type="text/css">

</style>
</head>
<div style='display:none'><script language="javascript" type="text/javascript" src="//js.users.51.la/19247461.js"></script></div>
<body style="background-image: url('https://i.loli.net/2017/07/24/5975c53bb8312.png'); background-size: 100% auto; height: 100%">

<div id="header" class="uh bc-text-head ub bc-head" style="position: relative; background-color: rgba(1, 84, 142, 0.88); height: 9%">
	<span class="ut ub-f1 ut-s tx-c" tabindex="0" style="margin: 0 auto; font-size: 20px; display: block; width: 100%; position: absolute; left: 0; top: 0; padding: 0; height: 3em; line-height: 2em;color: #FFF;text-align: center;">
		邀请好友拿红包 
	</span>
</div>

	<span style="position: absolute; top: 50%; left: 10%; color: white; width: 80%;" align="center">
	<c:if test="${userId==null}">
		<b>哇哦,您还不是会员,快来注册吧~</b>
	</c:if>
	<c:if test="${userId!=null}">
		<b>亲,您的好友 ${userNickName} 邀请您来料料嗨皮啦~</b>
	</c:if>
	</span>
	
	<div align="left"></div>
	
<form class="layui-form" action="" onsubmit="return registerCommit()">
	<div style="width: 88%; background-color: rgba(0, 0, 0, 0); position: absolute; top: 56.8%; left: 11%; height: 100px;" class="layui-form-item">
		<input type="hidden" name="userId" value="${userId}">
		<input type="text" name="mobile" lay-verify="phone" placeholder="请输入手机号" class="layui-input" style="border-radius: 6px; width: 90%; height: 36%; overflow-x: visible; overflow-y: visible;">
		<div style="width: 88%; height: 10%; background-color: rgba(0, 0, 0, 0);"></div>
		<input type="text" name="passWord" lay-verify="passWord" placeholder="请设置密码" class="layui-input" style="border-radius: 6px; width: 90%; height: 36%; overflow-x: visible; overflow-y: visible;">
		<div style="width: 88%; height: 10%; background-color: rgba(0, 0, 0, 0);"></div>
		<input type="text" name="authCode" lay-verify="authCode" placeholder="请输入验证码" class="layui-input" style="border-radius: 6px; width: 45.8%; height: 36%; overflow-x: visible; overflow-y: visible; float: left;">&nbsp;
		<button type="button" class="layui-btn" style="border-radius: 6px; width: 42%; height: 36%; background-color: red; font-size: 16px;" onclick="getauthCode()" id='authCode'>
		获取验证码
		</button>
		<div style="width: 88%; height: 10%; background-color: rgba(0, 0, 0, 0);"></div>
		<button lay-submit="" style="border-radius: 6px; width: 90%; height: 42%; background-color: rgba(250, 250, 250, 100); color: rgba(2, 109, 165, 100); font-size: 18px">
		立即注册
		</button>
	</div>
</form>

	<!-- 注册成功后提示信息 -->
	<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.sjcm.liaoliao">
	<div id='registerSuccess' style="width:100%;height:100%;margin: 0;padding: 0;position: absolute; display: none; ">
		<div id='registerSuccess' style="width: 100%; height: 100%; z-index: 100; background-color: rgba(0, 0, 0, 0.36);">
			<div style="width: 90%; height: 35%; position: absolute; top: 20%; left: 5%">
				<img src="https://i.loli.net/2017/07/24/5975c8ac578c8.png" style="width: 100%; height: 100%">
			</div>
		</div>
	</div>
	</a>
	
	<!-- 电话已被注册后提示信息 -->
	<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.sjcm.liaoliao">
	<div id='mobileExist' style="width:100%;height:100%;margin: 0;padding: 0;position: absolute; display: none">
		<div style="width: 100%; height: 100%; z-index: 100; background-color: rgba(0, 0, 0, 0.36);">
			<div style="width: 90%; height: 35%; position: absolute; top: 20%; left: 5%">
				<img src="https://i.loli.net/2017/07/24/5975ca3b4db78.png" style="width: 100%; height: 100%">
			</div>
		</div>
	</div>
	</a>


	<script>
		layui.use([ 'form' ], function() {
			var form = layui.form();
			var layer = layui.layer;
			//自定义验证规则
			form.verify({
				phone : [ /^1[3|4|5|7|8]\d{9}$/, '请输入正确的手机号！' ],
				authCode : [ /([0-9]+){6}$/, '请输入正确的验证码' ]
			});
		});
		//控制短信时间
		function sendMessage() {
			var x = 80;
			$('#authCode').attr('disabled', "true");//添加disabled属性 
			$("#authCode").html(x + "秒后可重发");
			var t = setInterval(function() {
				x--;
				if (x >= 0) {
					$("#authCode").html(x + "秒后可重发");
				} else {
					clearInterval(t);
					$('#authCode').removeAttr("disabled"); //移除disabled属性 
					$("#authCode").html("重新获取");
				}
			}, 1000);
		}
		//获取注册短信验证码
		function getauthCode() {
			var mobile = $("input[name='mobile']").val();
			var br = mobile.search(/^1[3|4|5|7|8]\d{9}$/);
			if (br === 0) {
				var index = layer.load(0, {
					shade : false
				});
				$.ajax({
		            type: 'POST',
		            url: '${pageContext.request.contextPath}/share/existPhone',
/* 		            headers:{
		            	"appId":appId,
		            	"user-agent":"liao",
		            	"Content-Type":"text/plain;charset=UTF-8",
		            }, */
		            data:{mobile:mobile},
		            success: function(map){
						layer.close(index);
						if (map.code === 0) {
							sendMessage();
							layer.msg("料料君已经把验证码发送到您手机了~", {
								icon : 6
							});
						} else if (map.code === 110) {
							$("#mobileExist").show();
						} else {
							sendMessage();
							layer.msg(map.code, {
								icon : 5
							});
						}
		            },
		            error: function(){
		                window.location.reload();
		            }
				});
			} else {
				layer.msg("啊哦，手机号有误呐！", {
					icon : 5
				});
			}
		}
		//注册提交
		function registerCommit() {
			var index = layer.load(0, {
				shade : false
			});
			var mobile = $("input[name='mobile']").val();
			var authCode = $("input[name='authCode']").val();
			var passWord = $("input[name='passWord']").val();
			var userId = $("input[name='userId']").val();
			if (mobile && authCode) {
				$.ajax({
		            type: 'POST',
		            url: '${pageContext.request.contextPath}/share/invitationPost',
/* 		            headers:{
		            	"appId":appId,
		            	"user-agent":"liao",
		            	"Content-Type":"text/plain;charset=UTF-8",
		            }, */
		            data:{mobile:mobile,authCode:authCode,passWord:passWord,userId:userId},
		            success: function(map){
		            	layer.close(index);
						if (map.code === 0) {
							$("#registerSuccess").show();
						} else {
							layer.msg(map.message, {
								icon : 5
							});
						}
		            },
		            error: function(){
		                window.location.reload();
		            }
				});

			} else {
				layer.close(index);
				layer.msg("缺少参数！请检测手机号是否正确.", {
					icon : 5
				});
			}
			return false;
		}
	</script>

</body>
</html>
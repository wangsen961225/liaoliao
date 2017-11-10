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
	<style type="text/css">
	.box2 img{border-radius:5px} 
	</style>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
	<title>料料 - 分享下载</title>
	<link href="${pageContext.request.contextPath}/style/layui/css/layui.css" rel="stylesheet">
	<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/style/layui/layui.js"></script>
</head>
	<div style='display:none'><script language="javascript" type="text/javascript" src="//js.users.51.la/19247461.js"></script></div>
<body style="background-image: url('https://i.loli.net/2017/07/24/5975c53bb8312.png'); background-size: 100% auto; height: 100%">
	<div id="header" class="uh bc-text-head ub bc-head" style="position: relative; background-color: rgba(1, 84, 142, 0.88); height: 9%">
		<span class="ut ub-f1 ut-s tx-c" tabindex="0" style="margin: 0 auto; font-size: 20px; display: block; width: 100%; position: absolute; left: 0; top: 0; padding: 0; height: 3em; line-height: 2em;color: #FFF;text-align: center;">
			料料君在等你哟!
		</span>
	</div>
	
	<center>
		<span style="position: absolute; top: 30%; left: 10%; color: white; width: 80%;" align="center">
		
			<center><p style="font-size:1.5em">我在料料获得了<font size="15em" color="red"><strong><i>${map.money }</i></strong></font>&nbsp;料币,首次注册,立即获得1元新手红包,阅读文章,看视频,发原创,邀请好友都可获得料币哟!<br/>在娱乐中把钱赚,你也一起来吧!</p></center>
			<!--软件下载 -->
			<center>
				<div id="ceb331">
					<span class="ut ub-f1 ut-s tx-c" tabindex="0" style="margin: 0 auto; font-size: 20px; display: block; width: 100%; position: absolute; left: 45; top: 60; padding: 0; height: 5em; line-height: 3em;color: #fff;text-align: center;">
						<a href="${map.downloadUrl}" target="_self"><font color="#fff">点击加入我们!</font>
							<div class="box">
							<img src="http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/ll_share_redpackage/ll_shareRedpackage_023cc8429fec11e78c5400163e031982.png?x-oss-process=style/blank_style">
							</div>
						</a>
					</span>
				</div>
			</center>
		</span>
	</center>
		
	<!-- 底部悬浮-软件下载 -->
	<div id="ceb33">
	<a href="${map.downloadUrl}" target="_self" style="text-decoration:none;outline:none;-webkit-tap-highlight-color: transparent;">
		<div id="ceb33_1">
			<div class="ceb33_2"></div>
			<div class="ceb33_3"><p>料料</p><p>读文章、看视频、抢红包。</p></div>
			<a href="${map.downloadUrl}" target="_self" class="ceb33_4">打开</a>
		</div>
		<div id="ceb33_5"></div>
	</a>
	</div>
	<style type="text/css">
	#ceb33 {
	    position: fixed;
	    bottom: 0;
	    left: 0;
	    z-index: 20;
	    width: 100%;
	    height: 53px;
	    background: #ffffff;
	    opacity: 1;
	    border-top: 1px solid #e0e0e0;
	    box-sizing: border-box;
	}
	#ceb33_1 {
	    pointer-events: auto;
	    height: 53px;
	    box-sizing: border-box;
	}
	.ceb33_2 {
	    border: none;
	    height: 36px;
	    width: 36px;
	    position: absolute;
	    bottom: 7px;
	    left: 46px;
	    background: url(${pageContext.request.contextPath}/style/images/logo64.png) no-repeat;
	    background-size: 36px 36px;
	    -webkit-background-size: 36px 36px;
	}
	.ceb33_3 {
	    position: absolute;
	    padding: 11px 0 0 10px;
	    left: 84px;
	    color: #333;
	    font-family: 微软雅黑;
	    text-align: left;
	}
	.ceb33_3 p:first-child {
	    font-weight: 800;
	    font-size: 16px;
	    line-height: 16px;
	    margin: 0;
	}
	.ceb33_3 p:last-child {
	    color: #999;
	    font-size: 12px;
	    line-height: 20px;
	    margin: 0;
	}
	.ceb33_4 {
	    display: inline-block;
	    height: 26px;
	    line-height: 26px;
	    width: 62px;
	    position: absolute;
	    bottom: 12px;
	    right: 16px;
	    box-sizing: border-box;
	    z-index: 99;
	    border: 1px solid #e0e0e0;
	    border-radius: 2px;
	    color: #333;
	    font-size: 13px;
	    text-align: center;
	    text-decoration:none;
	}
	#ceb33_5 {
	    pointer-events: auto;
	    position: absolute;
	    left: 5px;
	    bottom: 0px;
	    height: 53px;
	    width: 40px;
	    background-size: 20px 20px;
	    -webkit-background-size: 20px 20px;
	}
	</style>
</body>
</html>
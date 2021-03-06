<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/style/public/base.jsp"%> 
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<script type="text/javascript" src="style/public/jquery-1.12.4.min.js"></script>
		<script src="js/rem.js"></script>
		<script type="text/javascript" src="style/public/devicetype.js"></script>
		<script type="text/javascript">
		$(function(){
			$("#kai").click(function(){
				get_device_info();
			});
		});
		
		</script>
		<title></title>
		<style>
			* {
				margin: 0;
				padding: 0;
				text-decoration: none;
				list-style: none;
				font-size: .16rem;
				font-family: "微软雅黑";
				text-align: center;
				color: #fff;
			}
			
			.clear {
				clear: both;
			}
			
			#header,
			#content,
			#footer {
				margin: 0 auto;
				margin-top: .1rem;
			}
			
			#header,
			#footer {
				margin-top: .1rem;
				height: 100%;
			}
			
			#header,
			#footer,
			.center {
				background: url(images/bg.png) no-repeat center;
				background-size: cover;
				margin-left: 1.1rem;
			}
			.font1 {
				position: relative;
				bottom: -1.44rem;

			}
			
			.font1 span {
				font-size: .24rem;
				font-family: "黑体";
				font-weight: bolder;
			}
			
			.font2 {
				position: relative;
				top: .85rem;
			}
			
			span img {
				width: 1.43rem;
				margin-top: 2.63rem;
				position: relative;
				top: -.7rem;
			}
			
			.liao img {
				position: relative;
				top: .71rem;
			}
			
			.foot span {
				position: relative;
				top: -.51rem;
			}
			/*通用样式*/
			/*手机*/
			
			@media screen and (max-width:6rem) {
				#header,
				#content,
				#footer {
					width: 100%;
				}
				.center {
					margin-top: .1rem;
				}
				.center {
					height: 100%;
				}
			}
			/*PC*/
			
			@media screen and (min-width:9.6rem) {
				#header,
				#content,
				#footer {
					width: 9.6rem;
				}
				.center {
					height: 100%;
					float: left;
				}
				.center {
					width: 5.4rem;
					margin-right: .1rem;
				}
			}
		</style>
	</head>

	<body>
		<div id="content" >

			<div class="center">
				<div class="liao" >
					<img src="images/icon.png" style="width:1.5rem;height: 1.5rem; "><br /></div>
				<div class="font2">
					<span>料料</span><br />
					<span>给您发了一个红包</span><br />
				</div>
				<div class="font1">
					<span>下载料料&nbsp;A&nbsp;P&nbsp;P</span><br />
					<span>好礼送不停&nbsp;</span>&nbsp;<span>&nbsp;&nbsp;人人都有份</span><br />
				</div>
				<span><img src="images/kai.png" style="margin-top: 2.63rem;" id="kai"/></span>
				<div class="foot">
					<span>下载“料料”APP</span><br />
					<span>看视频&nbsp;&nbsp;赚金币（还可以提现哦）</span><br />
					<span>更多精彩&nbsp;&nbsp;&nbsp;尽在“料料”</span><br />
				</div>
				<div style="margin-top:.15rem;"></div>
			</div>
			<div class="clear"></div>
		</div>

	</body>

</html>
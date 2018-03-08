<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/style/public/base.jsp"%> 
<html>
<head>
    <meta charset="UTF-8">  
    <title></title>  
    <link rel="stylesheet" href="css/swiper.min.css">  
    <link rel="stylesheet" href="css/style.css">
    <script src="/liaoliao/js/rem.js"></script>
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>  
    <script type="text/javascript" src="js/swiper.js"></script>  
     <script type="text/javascript" src="style/public/devicetype.js"></script>
</head>  
<body>  
	<header>
		<div class="header-icon"></div>
		<div class="swiper-container" ">
	 			<div class="swiper-wrapper">  
	 				<div class="swiper-slide"><img alt="" src="/liaoliao/images/lunbo.png" class="lunbo"/></div>  
        			<div class="swiper-slide"><img alt="" src="/liaoliao/images/lunbo.png" class="lunbo"/></div>  
        			<div class="swiper-slide"><img alt="" src="/liaoliao/images/lunbo.png" class="lunbo"/></div>  
       			</div>
  		</div>
  		<button type="submit" class="tip" id="btn_submit">打开</button>
	</header>
		
<script type="text/javascript">  
    var mySwiper = new Swiper(".swiper-container",{  
        direction:"horizontal",/*横向滑动*/  
        loop:true,/*形成环路（即：可以从最后一张图跳转到第一张图*/  
        // pagination:".swiper-pagination",/*分页器*/  
        // prevButton:".swiper-button-prev",/*前进按钮*/  
        // nextButton:".swiper-button-next",/*后退按钮*/  
        autoplay:1000/*每隔3秒自动播放*/  
    })  
   var mySwiper = new Swiper('.swiper-container',
   {
   	zoom : true,
	zoomMax :5,
	zoomMin :2,
}) 
    $(function(){
    	$('#btn_submit').click(function(){
    		get_device_info();
    	});
    });
</script>  
</body>  
</html>  
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/style/public/base.jsp"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/swiper.css">
    <script src="js/rem.js"></script>
    <script type="text/javascript" src="style/public/jquery-1.12.4.min.js"></script>
     <script type="text/javascript" src="js/swiper.js"></script> 
     <script type="text/javascript" src="style/public/devicetype.js"></script>
    <title>移动app</title>
    <script type="text/javascript">
        $(function(){
        	$('.download').click(function(){
        		get_device_info();
        	});
        	$('.concern').click(function(){
        		get_device_info();
        	});
        	$('.more-comment').click(function(){
        		get_device_info();
        	});
        	
        });
    </script>
</head>
<body>
	<div class="top">
	<iframe src="head.jsp" style="height: 100%;width: 100%"></iframe>
	</div>
    <section>
        <h2 class="title">
            景甜穿高定吊带礼裙, 网友： 她才是最适合当Dior大使的人。
        </h2>
        <div class="user">
            <img src="images/head_r6_c3.png" alt="">
            <div class="user-message">
                <span class="name">严婷婷</span>
                <span class="time">2017-12-12 09:07:13</span>
            </div>
            <button class="concern">关注</button>
        </div>
        
        <p>近日景甜出席Dior酒会活动</p>
        <p class="second">
            她身着Chirstian Dior2018早春度假印动物款吊带礼裙，搭配Dior首饰、手包和高跟鞋，十分优雅大气。
        </p>
        <img src="images/first_r8_c2.png" alt="" class="first">
        <p class="third">
            高扎丸子头配以一身Dior装扮的景甜，无论是颜值、气质，还是自身时尚度，都美到移不开眼睛。
        </p>
        <img src="images/seond_r10_c3.png" alt="" class="second">
        <p class="third">
           都知道Dior中国区的大使是"双颖"(杨颖、赵丽颖)，而此番亮相的景甜更是让不少人直呼：原来这才是最适合当前Dior大使的人啊。
        </p>
        <img src="images/third_r8_c3.png" alt="" class="third">
        <p>景甜与Dior缘分由来已久，先前她曾穿Dior丝绒小黑裙，搭配利落丸子头出席电影首映礼，被大方而又自信的景甜再一次美哭。</p>
        <img src="images/forth_r8_c3.png" alt="" class="forth">
        <p>
            如果说上一张景甜还是自信御姐模样，那么一秒变俏公举的她，是不是这身小黑裙也同样很闲可爱风呢？
            <br>
            景甜身穿Dior黑色镂空薄纱长裙，大方秀出白
        </p>
        <button class="download">
            下载  “料料” APP查看全部内容 
        </button>
    </section>
   <section class="row">

        <div class="more">
            <h2>更多精彩</h2>
            <img src="images/bar_r12_c2.png" alt="" class="bar">
        </div>


        <div class="video">
            <img src="images/huge_r14_c3.png" alt="" class="video-left">
            <p class="video-left video-right" style="margin-left: .27rem;">
               胡歌霍建华胡歌霍建华胡歌霍建华胡歌霍建华胡歌霍建华 
            </p>
        </div>
        <div class="up-down">
            <p class="up-down-message">小伙小伙小伙小伙小伙小伙小伙小伙小伙小伙小伙</p>
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo first">
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo">
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo">
        </div>
        <div class="video">
            <img src="images/huge_r14_c3.png" alt="" class="video-left">
            <p class="video-left video-right" style="margin-left: .27rem;">
               胡歌霍建华胡歌霍建华胡歌霍建华胡歌霍建华胡歌霍建华 
            </p>
        </div>
        <div class="up-down">
            <p class="up-down-message">小伙小伙小伙小伙小伙小伙小伙小伙小伙小伙小伙</p>
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo first">
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo">
            <img src="images/xiaohuo_r16_c2.png" alt="" class="xiaohuo">
        </div>


        <div class="comment">
            <h2>评论</h2>
            <img src="images/bar_r12_c2.png" alt="" class="bar">
        </div>


        <div class="user comment-user">
            <img src="images/comment-head_r18_c5.png" alt="" class="head-sculpture">
            <div class="user-message">
                <span class="name">是非成败转</span>
                <span class="time">12-12 12：00 <b></b> <span>1426</span></span>
            </div>
            <div style="clear:both;"></div>
            <p class="summery">漂亮！说的一点都没错</p>
        </div>
       <button class="more-comment">更多评论</button>
   </section>
</body>
</html>
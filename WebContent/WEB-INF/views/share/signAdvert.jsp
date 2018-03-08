<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<style>
html{
	width:100%;
	height:auto;
	 margin: 0px 0px 0px 0px;	
}
body{
	  width:100%;
	  height:auto;
      margin: 0px 0px 0px 0px;	   
	}
	img{width:110%;text-align:center;margin:0px 0px 0px 0px;}
	div{
		width:100%;
	  height:auto;
      margin: 0px 0px 0px 0px;	
	}
	
	<style>
	#sign {
		position: fixed;
        z-index: 1;
	}
      /* #advert
      	{
      		position: fixed;
      		background-color:black;
            width: 8%;
            height: 24%;
            right: 0;
            top: 0;
            opacity: 0.2;
            z-index: 2;
            border-radius:5px;
            color:#C0C0C0;
            font-family:黑体;
            font-size: 0.6em;
      	}  */ 
      	
      	/* #cover {
      	position: fixed;
      		background-color:#99FFFF;
            width: 19%;
            height: 34%;
            right: 0%;
            bottom:0%;
            opacity: 0.9;
            z-index: 2;
            border-radius:5px;
            color:#C0C0C0;
      	} */
</style>
</style>
</head>
<body>
<div id="sign">
 ${map.signAdvert}
 </div>
 <div id="advert">
		<!-- <b>&nbsp;广&nbsp;告</b> -->
	</div>
	
	<!-- 遮挡自带 -->
	<div id="cover">
	</div>
</body>
</html>
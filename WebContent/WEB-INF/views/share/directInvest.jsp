<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, maximum-scale=1">
</head>
<body>
${map.directInvest}



<!-- <style type="text/css">
img{width:100%;text-align:center;/* margin:10px 0px; */}
/* body{font-size:15px}
p{margin:5px 5px;} */
div{width:100%}
</style> -->


<div style="width: 95%;max-height:78px;position: relative;left: -7px;overflow: hidden;">
<img src="http://pic1.16pic.com/00/05/71/16pic_571842_b.jpg">
</div>







<script type="text/javascript">
     $(document).ready(function(){
         var random_bg=Math.floor(Math.random()*6+1);
         var bg='url(images/new_feed/bg_'+random_bg+'.jpg)';
         $("img").setAttribute("src",bg);
});
</script>








</body>
</html>
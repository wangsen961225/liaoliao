<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript" src="${pageContext.request.contextPath}/style/public/jquery-1.12.4.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0, maximum-scale=1">
	
	<style>
	#directInvest {
		position: fixed;
        z-index: 1;
	}
      #advert
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
      	}  
</style>
	
</head>
<body>
	${map.directInvest} 
	
	
	 <div id="advert">
		<b>&nbsp;广&nbsp;告</b>
	</div>
	
	
</body>
</html>
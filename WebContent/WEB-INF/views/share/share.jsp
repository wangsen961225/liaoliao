<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, maximum-scale=1">

<%-- <meta itemprop="name" content="${map.title}"/>
<c:if test="${map.type==1}">
<meta itemprop="image" content="${map.articleImgUrl}" />
</c:if>
<c:if test="${map.type==2}">
<meta itemprop="image" content="${map.videoAva}" />
</c:if>
<meta name="description" itemprop="description" content="${map.title}" /> --%>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
<%-- <link rel="icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
<link rel="bookmark" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" /> --%>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- <script>
$(function () {
    var ua = '${map.ua}';
    var idStr = '${map.idStr}';
    var type = ${map.type};
    if(type==1){
    	var typeStr = 'article/';
    }
    if(type==2){
    	var typeStr = 'video/';
    }
    if (ua=='ios') {
        window.location.href = 'https://app.127120.com:8443/share/'+typeStr+idStr;
    }
});
</script> -->
<title>${map.title}-料料</title>
</head>
<body>
<%-- <center><p style="border-bottom: 1px solid #e7e7eb;padding-bottom: 2px;">料料 - 你的生活中应该有料<p></center> --%>
<!-- <marquee behavior=alternate direction=left align=middle style="font-size:15px;width:100%;border-bottom: 1px solid #e7e7eb;padding-bottom: 2px;">料料 - 你的生活中应该有料~</marquee> -->

<!-- 标题 -->
<%-- <h2 style="border-bottom: 1px solid #e7e7eb;padding-bottom: 10px;font-weight:normal;font-size：1.5em;margin-top:5px;">${map.title}</h2> --%>
<p style="padding: -1px;font-weight:normal;font-size:1.3em;margin-top:-9px;margin-bottom:0px;margin-left:-10px;margin-right:-20px;background-color:#ef4520;color:white;white-space:nowrap;word-wrap:break-word;word-break:break-all;" >${map.title}</p>
<div style="border-bottom: 1px solid #e7e7eb;"></div>
<%-- <jsp:include page="/share/111.jsp" flush="true" /> --%>

<!-- top广告位 -->
${map.topAdvert}

${map.directInvest}
	
	
<%-- ${map.topFloatAdvert} --%>

<c:if test="${map.type==1}">
<div style="height:40px;width:100%;color:red;margin-top: -24px;padding-bottom: 10px;">
<table style="width:100%">
<%-- <tr>
<td style="width:3%;height:38px"><img style="height:100%;width:100%;" src="${map.avatar}"></td>
<td style="width:16%;height:38px">${map.name }<br/>人气：${map.focusNum}</td>
<td style="width:3%;height:38px"><button type="button"><a  style="text-decoration:none;color:red" href="${map.downloadUrl}" >+ 关注</a></button></td>
</tr> --%>
</table>
<input type="text" value="${map.userId}" hidden="hidden" > 
</div>
</c:if>
<c:if test="${map.type==2}">
<div style="height:40px;width:100%;color:red;margin-top: -24px;padding-bottom: 10px;">
<table style="width:100%">
<tr>
<td style="width:3%;height:38px"><img style="height:100%;width:100%;" src="${map.avatar}"></td>
<td style="width:16%;height:38px"><span style="float:left">${map.name }<br/><span style="margin-left: -8%;">人气：${map.focusNum}</span></span></td>
<td style="width:3%;height:38px"><button type="button"><a  style="text-decoration:none;color:red" href="${map.downloadUrl}" >+ 关注</a></button></td>
</tr>
</table>
</div>
</c:if>


<!-- 文章 -->
<c:if test="${map.type==1}">
<style type="text/css">
img{width:100%;text-align:center;margin:10px 0px;}
body{font-size:15px}
p{margin:5px 5px;}
</style>
<div id="js_content" style="min-height: 500px;max-height: 20000px;overflow-y:hidden">
${map.content}
</div>

<!-- <div id='alpha' style="height: 30px;margin-top: -24px;position: relative;width: 100%;background: rgba(255,255,255,0.8);"></div>
<div id='zhankai' style="width: 126px;height: 36px;border-radius:15px; border: 2px solid rgba(1,84,142,0.6); position: relative;left: 32%;font-size: 18px;text-align:center">
<b style="color: rgba(1,84,142,0.6);line-height:36px;">↓展 开 全 文↓</b></div>
<div id='shouqi' style="display:none;width: 126px;height: 36px;border-radius:15px;border: 2px solid rgba(1,84,142,0.6); position: relative;left: 32%;font-size: 18px; text-align:center">
<b style="color: rgba(1,84,142,0.6);line-height:36px;">↑全 文 收 起↑</b></div> -->

<!-- <script>
$(function(){
 $("#zhankai").click(function(){
		$(this).hide();
		$("#alpha").hide();
		$("#js_content").css({"max-height":"20000px"});
		$("#shouqi").show();
	});
	$("#shouqi").click(function(){
		$(this).hide();
		$("#js_content").css({"max-height":"1500px"});
		$("#alpha").show();
		$("#zhankai").show();
	});
})
</script> -->


<br />
</c:if>

<!-- 视频 -->
<c:if test="${map.type==2}">
<style>
/* body{background-color: #123456;color: #FFFFFF} */
video{width:100%;text-align:center;}
div{text-align:center;/* color: #FFFFFF */}
img{width:100%;text-align:center;margin: 10px 0px;}
body{font-size:15px}
p{margin:5px 5px;}
</style>
<br />
<div>
<video controls="controls" poster="${map.videoAva}">
<source src="${map.videoUrl}" type="video/mp4">
</video>
</div>
<br />
<br />
</c:if>

<!-- 更多推荐开始 -->

<!-- <div style="padding-bottom:2px;width:100%;text-align:center;margin:auto;border-style:none none dashed none"></div> -->
<div style="width:100%;text-align:center;color:red;background-color:#F0f0f0;">----料料引擎为您精心推荐----<br/><br/></div>
<div>
<!-- baidu guanggao  -->
<c:forEach var="li" items="${map.list}">

<a href="${pageContext.request.contextPath}${li.articleShareUrl}" style="text-decoration:none;color: #222;outline: none;-webkit-tap-highlight-color: transparent;">
<div style="text-align:left;padding-top:6px;">${li.articleTitle}</div>
<c:forEach var="liImg" items="${li.articleImgList}">
<img src="${liImg.url}" style="width:32%;margin-bottom:0px;border-radius:4px;max-height:90px;">
</c:forEach>
<div style="padding-right:10px;font-size:11px;text-align:center">
<center>
<table style="border:0;table-layout:fixed;text-align:center">
  <tr>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:center;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAChElEQVRYR82W8XnTQAzFnyaADRomIJ2AyAtQJqCdgHYCwgSkE1AmoCxgmQkoE9BsABOI77k6c3Fs7KQx6f2VL7ZPv3sn6Ulw5CVHjo+nD2BmM3c/EZG1qt6PUWyXbzoVMLPnAN4BOAcwy4L+AnAL4LOqVjmMmS0AvAVwBoDfp0XoGwDXqsrvN9YWgJlxg0/cxN1/A7gTkcrdGWAmIiexAze9it8fAxbuvgZwn30zF5FnABj8TRt8A8DMeGIG5/oAYNWm5jvuvgyQdCW8prWILFWVYM0KNS8BvI8/L/J3GgAzmwP4zlOLyEJV7/ruOzZdheR8jfIySO/i/u5eiYgDOE351ACUZcmHrwBsEA5sSsXQPvU/wAnJ66pUVfleDcCsBfDT3X8URUElJltlWTKnXgJ4QRUSABPMeO+qupws+sNhuT/zgSJUCSD9+T8BrlR19WQUqHOANa+qp1NeQWcOMGBWBfXdTAER3dLc/VtRFMy7v2aUHk6pQnb65pDtTsg+/5q9W1UvDqmCmbFD0iu+qirbfb3aAOz/bEis04NBpODsM9FlG1PqMiP2dTYLGsijIbLgbPHztqX32TEhbkMJJiRdbMtKB9o0LfkLgEWc/KxrnuidiGg42XXQmJg4oyDCrNhZaUBbsufggyNZljy0XirR65LhK/QSBqcCHFxqw+pbgwCxKa2XExIVoBKdEFHKlJ3BBy16qwpGWG8aVrYsuzXMjLb0UQokMI5r7n4TFXKpqtehUO3zMcycqyr7yai1E0C640jOukwjCse0wUmqi2hngIDIy5SDKDO9s8yGZNgLICDqMq0T6WGGHFWibaC9ARLEvoE7vWBIrimeP0qBQwAdHeAPXaNnMIoOysUAAAAASUVORK5CYII=);"></td>
    <td style="width:20%">喜欢: ${li.articleLikingCount}</td>
  	<td style="width:4%"></td>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:center;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAABN1JREFUWAntl01oJEUUx6d7ZpLMjLMKQ9wRVFzX+AEqYuKsBA8GLxrIJxgR8bIHc/AiCCLuwSBCvLgeRNgoCEtyMSzkUy9CiLuCKyQsEogHPWTdTYwQwibZzWTSM93+XicVqme7Oz27eBBSUFPVVe//6l+v3ntVE4sdlf+ZBYxa+Y6Ojtatrq4a+XzeqRWr5AXf2tpqt7S0WGpMtZEJjY2NnUwkEmdM03ymUqlExqmFqtt4PO6gZ576YU9Pzw01n1CdsHZiYuK4YRhTDQ0NTzmOE6uvrw8TjzRnWZboadne3v4HwMcKFIkQwq8KmZ2dnRiE5qhLELwjK4F1gGbQ+TLWTvFZUGSkjURIFAAWMiXqO11dXVd0JbX25+bmkisrKz/jAgV02zre1D+C+pCw6+rqZNrgzHeC5KKOb21tSUBU/OQjWahUKv2wubn5Lbv5o7e393c/RbWMZbNZA1K+EA8hCWkc7TSmPFUul5MQcEMbC2GYiuyoCQcfZtxXWfUgOCOZTLKf0iWceLivr8/XKjrOQwjHfZd6VhaUSNIXRrmL08d0RX59wdi2HcP/ToMrIvOdn5w+5iGEgudlQUKxSHsVQXG4aObQte71ZQdJ6gk2mYDUc/RrIwSgQsISyyyk0+n23d1dLG05mHzPPAgcVhobG2PFYtENFvAPQmSGehzcocclun2jDELl9fX1G+3t7ZuQ2U6lUmIpUXhoXVhYsAQnFUIbYCJvBln/PCTOuLi46IyPjz+Exc4j14hPRVLc1NRkgRvs7u6+QKrw3bAsHFQ8PqQLDQwM2Ch+IpPJtCmH1ueD+vhLbG1t7XXmLwTJhI0HEoJEfHZ29jL5Z5A0cJKoj2Ihg+ulgs98Xb0o+g7wcstPTk56MrSSDyQ0Pz9vtrW13UTwIyV8p+0+l3t4MXSzObnH4ozdLymB4oniQEJq8enp6UfppwEf7FDN6S1WsZeXl//s7++/7Y2Dc0vkvkmSfI/Whe2Tkfvxlq4nkFBzc3OZrPwswBmUZAAGEpJFcH6bR9tnKP9UX0D6kI2R+f9GVwLZnKiitTneJXBf6PKBhAA4nHMaZfcCSoTwcfWx+xg56wFduerLHFaagtA36D0h40TgBv55jetkXclJG0hoaGgo2dHR8SukXkFRXnakA6v7JEMx/6XqcfUN3uTZco1vqYHFlxCKHfEFqgAvBqIPmcAqFkk18Kj94LcRkqPhmLJY5gVC3aJvUG/mcrnrPMzlgjwo5CqzUCg8yVHVY0XPwkSTvAzzjCdFZ9TiISRmRYGY/mnqT/iOq4nztkh2Z1H6iVLMU+VhkuA55F4Ch2hcTbmtkKBKSDXsT3jC2yOsfXgIMT6Dmd/imjBZRN674qhulDCXUzis9yL9YeQeExlx2rDCfSivh5kwGTXnIdTZ2Xme6+I6izy+n5mzKPoAYQnVsoCYf4OxryCRE2tCeIrh7+l7TSTCFKzrMHcF3Zf3RsJ/Q804MjJyjOfmbzxFHiGKvkTVX/jToKQBeUBSP4fYGW72Uvgy0Wc9FqqGsXgKa5hybJS36d8nvgKRW+z6fcJ4qBpzt9+hhIgU+YexK4sIGcwvR7QEIfkr9OPdLu6HD32vkEU38J1zkLEgJ2Qu8v3af0XG3bgfy+oxHPkUpI4RLb9AUl4AR+XIAsoC/wKrCzo8MOUVVQAAAABJRU5ErkJggg==);"></td>
    <td style="width:18%">评论: ${li.articleCommentCount}</td>
    <td style="width:4%"></td>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:right;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAAA6dJREFUWAntlk1rU0EUhpPcfIIKWhRaRbpQ60JFqGAXuhGysW3SJqUbpRtXRX+BoovudOF/0E0h5KMJJQUXLgTpSgQXjVUsgi4CFVRqsWm+fM4lVybpTNOkWZUOzJ25Z86ceeec98yMy3VYDj1wwDzg3m0/8/PzA6FQaNCyrLpJz01hrDg2Nrbm6KTT6VN+v/9cneLIpPX5fFVsrYbD4d+qXO0bAWWz2TsYeFatVk/WajV1TlOfBVyMr1PDExMTK4lE4kgwGHzl9XqvlcvlJl2Px1OnriF/iG6mabDx49EJATPAxp9TBzDgYwFjxRM+quifbdgK0Q6J3JnHxnxiB7mf9iJgXywuLg7r1vbqhOx6EM/0iccrlcpXdpTHkE5VZBbeWd3e3n4tP9PT0+u5XG52a2vrFvIqQMvYGWb+Df5d4jWAHmX8LurvZI5atIAasa8BzALY+/Hx8fvqpHb9SCSSQEeqK5lMXgHAbenLpgQUNuV3SD6txbhtRdFS+h11U6nUSCAQWALIeTwlYH6yWZtYtNq1tcKOVjUoLywsXIdHKfgi/BKvfKCdpZbk31S0ITMp71UuYACSIuI2GHjzEUAxvOJHLuQ2lp57qAEmSZhOiycEDKtPUr6QbCElVFoq9BQQ2TWCV9KAOdMAUwBMhKQQUC5kv6h/Ibn0v4mstfQMkBAYIEknTIRoBRJPAuazs2jjNJ/Z3Nx8jOceOXK17QmHnDABxg4TZ1dBOBONRlfVxaTPkZClkaot+wbkgFE5I57RgVEQSJo13XPO2L4ASZjwSgowdjbhmRVCMRmLxT45C6htPp8PMP6U4+ASoOdGR0ffqOPS7xpQ49CzOSOGhDMSJhMY0SHDLkDmBxyW1sbGxh9EvQHkhMnhjKQ2O9ZyRoAoRVK9wr1nAUwu4R2l4yzjrdOHsZcCRqwJmFKp1I4zOxbGW1oOdQwIOyEAncDt8hIoACg6NTVlnzM7Vu1C0DGgeDz+Ha7M4PY5whTZjTNd4OmO1FwDSywmteelYw/1HEGLwb0AMj+oW4y1+yUR7LeQ6MFDrV3tOYSynKRuSQTq8Uwmc5XDrN16u47DOclIeSW6ASa6/+84daIWEKSVQ6tOFgmgmxhYRmZ+VakWDX1s1EkCD+8hP8eEvLPtJ26ruhZQsVgs9Pf350jtuBhhUrB1Yjf/XDEuPFVic0+4697qbBh3zb1zjMn32NllQGkPMZ1Bk0xogK0f2MrwJFk26R3KDz1w4DzwD8mvnIEMTFd1AAAAAElFTkSuQmCC);"></td>
    <td style="width:20%">转发: ${li.articleSendingCount}</td>
    <td style="width:4%"></td>
    </div>
  </tr>
</table>
</center>
</div>
</a>

<!-- top广告位 -->
${map.topAdvert}
<div style="border-bottom: 7px solid #F0F0F0;padding-bottom: 6px;width:100%;text-align:center;margin: auto;"></div>
<div>
<!-- <div style="position:relative;min-height:93px"> -->
<a href="${pageContext.request.contextPath}${li.videoShareUrl}" style="text-decoration:none;color: #222;outline: none;-webkit-tap-highlight-color: transparent;">
<div style="position:absolute; z-index:1;">
<!-- <img  src="https://i.loli.net/2017/07/21/59716f03319f0.png"/> -->
<img style="width:50%;margin-left:250%;margin-top: 110%;" src="https://i.loli.net/2017/07/21/59716f03319f0.png"/>
</div>
<div style="background:#000;text-align:center;padding:0; margin:0;max-height:190px;">
<img src="${li.videoImgUrl}" style="float:center;position:relative;width:auto;background-repeat:no-repeat;height:auto;max-height:190px;border-radius:0px;padding:0; margin:0;">
</div>
<div style="line-height: 1.4;font-size:14px;margin-left:2%;margin-top: 1%;"text-align:left;">
<c:if test="${fn:length(li.videoTitle)>15 }">
${fn:substring(li.videoTitle, 0, 15)}...
</c:if>
<c:if test="${fn:length(li.videoTitle)<=15}">
${li.videoTitle}
</c:if>
</div>
<div style="bottom:0px;right:0px;text-align:right;padding-right:5px;padding-top:1%;font-size:10px;">
<center>
<table style="border:0;table-layout:fixed;text-align:center">
  <tr>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:left;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAChElEQVRYR82W8XnTQAzFnyaADRomIJ2AyAtQJqCdgHYCwgSkE1AmoCxgmQkoE9BsABOI77k6c3Fs7KQx6f2VL7ZPv3sn6Ulw5CVHjo+nD2BmM3c/EZG1qt6PUWyXbzoVMLPnAN4BOAcwy4L+AnAL4LOqVjmMmS0AvAVwBoDfp0XoGwDXqsrvN9YWgJlxg0/cxN1/A7gTkcrdGWAmIiexAze9it8fAxbuvgZwn30zF5FnABj8TRt8A8DMeGIG5/oAYNWm5jvuvgyQdCW8prWILFWVYM0KNS8BvI8/L/J3GgAzmwP4zlOLyEJV7/ruOzZdheR8jfIySO/i/u5eiYgDOE351ACUZcmHrwBsEA5sSsXQPvU/wAnJ66pUVfleDcCsBfDT3X8URUElJltlWTKnXgJ4QRUSABPMeO+qupws+sNhuT/zgSJUCSD9+T8BrlR19WQUqHOANa+qp1NeQWcOMGBWBfXdTAER3dLc/VtRFMy7v2aUHk6pQnb65pDtTsg+/5q9W1UvDqmCmbFD0iu+qirbfb3aAOz/bEis04NBpODsM9FlG1PqMiP2dTYLGsijIbLgbPHztqX32TEhbkMJJiRdbMtKB9o0LfkLgEWc/KxrnuidiGg42XXQmJg4oyDCrNhZaUBbsufggyNZljy0XirR65LhK/QSBqcCHFxqw+pbgwCxKa2XExIVoBKdEFHKlJ3BBy16qwpGWG8aVrYsuzXMjLb0UQokMI5r7n4TFXKpqtehUO3zMcycqyr7yai1E0C640jOukwjCse0wUmqi2hngIDIy5SDKDO9s8yGZNgLICDqMq0T6WGGHFWibaC9ARLEvoE7vWBIrimeP0qBQwAdHeAPXaNnMIoOysUAAAAASUVORK5CYII=);"></td>
    <td style="width:22%">点赞: ${li.videoLikingCount}</td>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:center;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAABN1JREFUWAntl01oJEUUx6d7ZpLMjLMKQ9wRVFzX+AEqYuKsBA8GLxrIJxgR8bIHc/AiCCLuwSBCvLgeRNgoCEtyMSzkUy9CiLuCKyQsEogHPWTdTYwQwibZzWTSM93+XicVqme7Oz27eBBSUFPVVe//6l+v3ntVE4sdlf+ZBYxa+Y6Ojtatrq4a+XzeqRWr5AXf2tpqt7S0WGpMtZEJjY2NnUwkEmdM03ymUqlExqmFqtt4PO6gZ576YU9Pzw01n1CdsHZiYuK4YRhTDQ0NTzmOE6uvrw8TjzRnWZboadne3v4HwMcKFIkQwq8KmZ2dnRiE5qhLELwjK4F1gGbQ+TLWTvFZUGSkjURIFAAWMiXqO11dXVd0JbX25+bmkisrKz/jAgV02zre1D+C+pCw6+rqZNrgzHeC5KKOb21tSUBU/OQjWahUKv2wubn5Lbv5o7e393c/RbWMZbNZA1K+EA8hCWkc7TSmPFUul5MQcEMbC2GYiuyoCQcfZtxXWfUgOCOZTLKf0iWceLivr8/XKjrOQwjHfZd6VhaUSNIXRrmL08d0RX59wdi2HcP/ToMrIvOdn5w+5iGEgudlQUKxSHsVQXG4aObQte71ZQdJ6gk2mYDUc/RrIwSgQsISyyyk0+n23d1dLG05mHzPPAgcVhobG2PFYtENFvAPQmSGehzcocclun2jDELl9fX1G+3t7ZuQ2U6lUmIpUXhoXVhYsAQnFUIbYCJvBln/PCTOuLi46IyPjz+Exc4j14hPRVLc1NRkgRvs7u6+QKrw3bAsHFQ8PqQLDQwM2Ch+IpPJtCmH1ueD+vhLbG1t7XXmLwTJhI0HEoJEfHZ29jL5Z5A0cJKoj2Ihg+ulgs98Xb0o+g7wcstPTk56MrSSDyQ0Pz9vtrW13UTwIyV8p+0+l3t4MXSzObnH4ozdLymB4oniQEJq8enp6UfppwEf7FDN6S1WsZeXl//s7++/7Y2Dc0vkvkmSfI/Whe2Tkfvxlq4nkFBzc3OZrPwswBmUZAAGEpJFcH6bR9tnKP9UX0D6kI2R+f9GVwLZnKiitTneJXBf6PKBhAA4nHMaZfcCSoTwcfWx+xg56wFduerLHFaagtA36D0h40TgBv55jetkXclJG0hoaGgo2dHR8SukXkFRXnakA6v7JEMx/6XqcfUN3uTZco1vqYHFlxCKHfEFqgAvBqIPmcAqFkk18Kj94LcRkqPhmLJY5gVC3aJvUG/mcrnrPMzlgjwo5CqzUCg8yVHVY0XPwkSTvAzzjCdFZ9TiISRmRYGY/mnqT/iOq4nztkh2Z1H6iVLMU+VhkuA55F4Ch2hcTbmtkKBKSDXsT3jC2yOsfXgIMT6Dmd/imjBZRN674qhulDCXUzis9yL9YeQeExlx2rDCfSivh5kwGTXnIdTZ2Xme6+I6izy+n5mzKPoAYQnVsoCYf4OxryCRE2tCeIrh7+l7TSTCFKzrMHcF3Zf3RsJ/Q804MjJyjOfmbzxFHiGKvkTVX/jToKQBeUBSP4fYGW72Uvgy0Wc9FqqGsXgKa5hybJS36d8nvgKRW+z6fcJ4qBpzt9+hhIgU+YexK4sIGcwvR7QEIfkr9OPdLu6HD32vkEU38J1zkLEgJ2Qu8v3af0XG3bgfy+oxHPkUpI4RLb9AUl4AR+XIAsoC/wKrCzo8MOUVVQAAAABJRU5ErkJggg==);"></td>
    <td style="width:18%">评论: ${li.videoCommentCount}</td>
    <td style="width:1%"></td>
  	<td style="width:7%;background-size:80%;background-repeat:no-repeat;background-position:right;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAAA6dJREFUWAntlk1rU0EUhpPcfIIKWhRaRbpQ60JFqGAXuhGysW3SJqUbpRtXRX+BoovudOF/0E0h5KMJJQUXLgTpSgQXjVUsgi4CFVRqsWm+fM4lVybpTNOkWZUOzJ25Z86ceeec98yMy3VYDj1wwDzg3m0/8/PzA6FQaNCyrLpJz01hrDg2Nrbm6KTT6VN+v/9cneLIpPX5fFVsrYbD4d+qXO0bAWWz2TsYeFatVk/WajV1TlOfBVyMr1PDExMTK4lE4kgwGHzl9XqvlcvlJl2Px1OnriF/iG6mabDx49EJATPAxp9TBzDgYwFjxRM+quifbdgK0Q6J3JnHxnxiB7mf9iJgXywuLg7r1vbqhOx6EM/0iccrlcpXdpTHkE5VZBbeWd3e3n4tP9PT0+u5XG52a2vrFvIqQMvYGWb+Df5d4jWAHmX8LurvZI5atIAasa8BzALY+/Hx8fvqpHb9SCSSQEeqK5lMXgHAbenLpgQUNuV3SD6txbhtRdFS+h11U6nUSCAQWALIeTwlYH6yWZtYtNq1tcKOVjUoLywsXIdHKfgi/BKvfKCdpZbk31S0ITMp71UuYACSIuI2GHjzEUAxvOJHLuQ2lp57qAEmSZhOiycEDKtPUr6QbCElVFoq9BQQ2TWCV9KAOdMAUwBMhKQQUC5kv6h/Ibn0v4mstfQMkBAYIEknTIRoBRJPAuazs2jjNJ/Z3Nx8jOceOXK17QmHnDABxg4TZ1dBOBONRlfVxaTPkZClkaot+wbkgFE5I57RgVEQSJo13XPO2L4ASZjwSgowdjbhmRVCMRmLxT45C6htPp8PMP6U4+ASoOdGR0ffqOPS7xpQ49CzOSOGhDMSJhMY0SHDLkDmBxyW1sbGxh9EvQHkhMnhjKQ2O9ZyRoAoRVK9wr1nAUwu4R2l4yzjrdOHsZcCRqwJmFKp1I4zOxbGW1oOdQwIOyEAncDt8hIoACg6NTVlnzM7Vu1C0DGgeDz+Ha7M4PY5whTZjTNd4OmO1FwDSywmteelYw/1HEGLwb0AMj+oW4y1+yUR7LeQ6MFDrV3tOYSynKRuSQTq8Uwmc5XDrN16u47DOclIeSW6ASa6/+84daIWEKSVQ6tOFgmgmxhYRmZ+VakWDX1s1EkCD+8hP8eEvLPtJ26ruhZQsVgs9Pf350jtuBhhUrB1Yjf/XDEuPFVic0+4697qbBh3zb1zjMn32NllQGkPMZ1Bk0xogK0f2MrwJFk26R3KDz1w4DzwD8mvnIEMTFd1AAAAAElFTkSuQmCC);"></td>
    <td style="width:20%">转发: ${li.videoSendingCount}</td>
    <td style="width:1%"></td>
    </div>
  </tr>
</table>
</center>
</div>
</a>
</div>
<!-- <hr /> -->
<div style="border-bottom: 7px solid #F0F0F0;padding-bottom: 6px;width:100%;text-align:center;margin: auto;"></div>
<c:if test="${li.advertUrl!=null&&li.advertUrl!=''}">
${li.advertUrl}
<!-- <hr /> -->
<div style="border-bottom: 7px solid #F0F0F0;padding-bottom: 6px;width:100%;text-align:center;margin: auto;"></div>
</c:if>

</c:forEach>

</div>
<!-- 更多推荐结束 -->

<div style="height: 6px;"></div>
<!-- bottom广告位 -->
${map.bottomAdvert}
<br />
<br />

<!-- 底部悬浮-软件下载 -->
<div id="ceb33">
<a href="${map.downloadUrl}" target="_self" style="text-decoration:none;outline:none;-webkit-tap-highlight-color: transparent;">
	<div id="ceb33_1">
		<div class="ceb33_2"></div>
		<div class="ceb33_3"><p>料料</p><p>读文章、看视频、抢红包。</p></div>
		<!-- <a href="http://zhushou.360.cn/detail/index/soft_id/3875226" target="_self" class="ceb33_4">打开</a> -->
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
<script>
console.log("料料,有料才精彩.");
console.log("  -----------             丿         乀");
console.log("       |                 丿           乀");
console.log("   --------             丿             乀");
console.log("       |               丿                乀");
console.log("---------------       丿                   乀");
console.log("破解联系：s5h46eeyw45g#$^6Q##");
</script>
</body>
</html>
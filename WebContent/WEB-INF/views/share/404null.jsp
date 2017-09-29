<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html mip class="dead-doc" style="font-size: 100px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, maximum-scale=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/style/images/favicon.png" type="image/x-icon" />
<title>料料 - 你的生活中应该有料</title>
</head>
<body class="dead-doc" style="opacity: 1; animation: none;">

<div id="detail-page" class="dead-doc dead-page">
	<div class="item-wrap dead-notice scale" style="height: 100%;">
		<div class="empty-notice">
			<div class="empty-img scale-img"></div>
			<div class="empty-text scale-txt">料料君走丢了，看看其他内容吧~</div>
		</div>
	</div>
</div>

<style type="text/css">
body {
    font-family: Arial, Helvetica, sans-serif;
    text-align: left;
    -webkit-text-size-adjust: none;
    background: #f5f5f5;
}
* {
    margin: 0;
    padding: 0;
}
.dead-page {
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    -webkit-flex-direction: column;
    justify-content: space-between;
    -webkit-justify-content: space-between;
}
.dead-doc {
    height: 100%;
}
.empty-notice {
    position: relative;
    top: 50%;
    transform: translateY(-50%);
    -webkit-transform: translateY(-50%);
}
.scale-img {
    width: 90px;
    height: 90px;
}
.empty-img {
    background: url(data:image/png;base64,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) no-repeat;
    background-size: 100% auto;
    margin: 0 auto;
}
.scale-txt {
    font-size: 20px;
}
.empty-text {
    line-height: 1;
    margin-top: 7px;
    color: #333;
    white-space: nowrap;
    text-align: center;
}
</style>

<!-- 底部悬浮-软件下载 -->
<div id="ceb33">
	<div id="ceb33_1">
		<div class="ceb33_2"></div>
		<div class="ceb33_3"><p>料料</p><p>读文章、看视频、抢红包。</p></div>
		<a href="http://app.qq.com/#id=detail&appid=1101071880" target="_self" class="ceb33_4">打开</a>
	</div>
	<div id="ceb33_5"></div>
	<!-- <div id="ceb33_5" onclick="$('#ceb33').hide();"></div> -->
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
    /* background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA4ZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowOTIwOTVhOS1lMzQyLTQxZGUtYmFkOS05MTJjZjFlOGM5OTEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NjZEMzdGRkNENDUxMTFFNUE3MTU5Nzg3OUFFNTYwQkIiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NjZEMzdGRkJENDUxMTFFNUE3MTU5Nzg3OUFFNTYwQkIiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo0Njc3Yzg2NS0wNTAzLTRlODktYmRmMi0wOWNmMzJiZjgxY2YiIHN0UmVmOmRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo3MjQwYjY4Yy0xYzM4LTExNzktODU3Zi05Mzg5NjM1Y2UwOTIiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz43tZVvAAADD0lEQVR42tSYzasSURTAdSpTE0N5tpM+eCG1CQpcuOtRiCsXiaDr4MH7A5L8CwRXbV5E4FJBbOHKTWvBFkGbSnp97/LZsxzHSdTpnLhT07yZufc6jvoOHGfEO/f+PHfOxz1uRVFcmyyCa8PltHrjdrt5nz0LGgQNgHpBPaCnyG8z0AmoDCqC/gT9xTO5urPuvzdsgDgoBBohYDyCoD3QI1zfCUC0VpRYy46gVb8Sqy4FUCBgW0t+vQ4J6NwOIL6j26DnHPKBEegB6NQMUKA4UMxBOBeZO6Z1VtYwIxDLeVcQSbxkLYEHMOqw5YwsGWUFDDrgECyyRdamAkatZmm32/fS6XSId/VkMhns9/v75XI5ZjEsSmLtv8Cr8+Iw6GWzpzudTjYejz+QJOlDPp/fbTabR6xwjUbjcSAQiA0GgxehUGjPYvhH0O9mXhyxWqhUKj1HOL/ff6VarT5hsaQWThTFLvyxh5RHIsfiDaHF3HqLpgB1ZzQavYdnFLzid7OxAHd7OBy+xbF4TaVSOyxrIIvKJeicgyq4rbi9NEvqLZfNZvdardYPxlc2aOQkzMmfBmkT7j8WrZNcg4ufxzMRCuEQUnUcWZZnNuFQJOB6owe8YZVyWCDH4/EnmG8K99s24FCmMM8rPeBNfQzigazVak99Pt+lP39fkg4ymczugnDEd5WXSyv5cVvRcprq/IzH41nK3NpJZotMoDoEbitaDrb5M1jyImucNJGZEeCEdxaIa+e1DoHbmsvl7vMGcwOZGAHKvHD1en1f762scZLhWHAMULQLxxvMKQesxVIdpivW9MWTFs1Snb6aidEyCpZM4XA4zhrnjII5pQpC63XNqpkezfaVSuURlkysQVi/3cVi8S7lkZ5VPYgf1504i6AlC4XCTiKReEZxjtckUJseO7GSuLqmVsw79UBvdezEAf01wB0adRvM0tEXcqhelYxIl4H52DknJ355BXAyWWvO2x/E5N/F4sRBOImsMTVtp5305tGJaL8ZNTAvLNAaQUf45mQDc70t4E2Vje/y/xZgABJILegYHuZ9AAAAAElFTkSuQmCC) no-repeat 50% 50%; */
    background-size: 20px 20px;
    -webkit-background-size: 20px 20px;
}
</style>

</body>
</html>
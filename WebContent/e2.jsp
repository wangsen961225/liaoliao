<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<html>
<head>
<script type="text/javascript" src="style/public/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="style/public/devicetype.js"></script>
<!-- <script type="text/javascript">
var matc;
	$(function(){
		 matc=get_device_info(); 
		if(matc==1){
			window.location.replace("http://android.myapp.com/myapp/detail.htm?apkName=com.sjcm.liaoliao&ADTAG=mobile");
		}else if(matc==2||matc==3){
			window.location.replace("https://itunes.apple.com/us/app/%E6%96%99%E6%96%99-%E8%AE%A9%E4%BD%A0%E7%9A%84%E7%94%9F%E6%B4%BB%E6%9B%B4%E6%9C%89%E6%96%99/id1312307735?l=zh&ls=1&mt=8");
		}
	})
    function get_device_info(){  
        var ua = navigator.userAgent;  
        if(ua.match('Android')){  
            return 1;  
        }else if(ua.match('iPhone') || ua.match('iPod')){  
            return 2;  
        }else if(ua.match('iPad')){  
            return 3;  
        }else{  
            return 0;  
        }  
    } 
    
    </script> -->
    <script type="text/javascript">
    $(function(){
    	d();
    });
    //第一种
     function d(){
    	 var openUrl = window.location.search;
    	 try{
	    	 openUrl = openUrl.substring(1,openUrl.length);
	    	 }catch(e){
	    	 }
    		         if(navigator.userAgent.match(/(iPhone|iPod|iPad);?/i))
    		            {
    		             window.location.href = "liaoliao://?url="+openUrl;//ios app协议
    		             window.setTimeout(function() {
    		                 window.location.href = "https://itunes.apple.com/us/app/%E6%96%99%E6%96%99-%E8%AE%A9%E4%BD%A0%E7%9A%84%E7%94%9F%E6%B4%BB%E6%9B%B4%E6%9C%89%E6%96%99/id1312307735?l=zh&ls=1&mt=8";
    		             }, 3000) 
    		            }
    		         if(navigator.userAgent.match(/android/i))
    		         {
    		             window.location.href = "liaoliao://app?url="+openUrl; //android app协议
    		             window.setTimeout(function() {
    		                 window.location.href = "http://android.myapp.com/myapp/detail.htm?apkName=com.sjcm.liaoliao&ADTAG=mobile";//android 下载地址
    		             }, 3000)    
    		         }
    		     };
    		    /* // 第二种 
    		     (function() {
    		    	 var openUrl = window.location.search;
    		    	 try{
    		    	 openUrl = openUrl.substring(1,openUrl.length);
    		    	 }catch(e){
    		    	 }
    		    	 var isiOS = navigator.userAgent.match('iPad')
    		    	 || navigator.userAgent.match('iPhone')
    		    	 || navigator.userAgent.match('iPod'), isAndroid = navigator.userAgent
    		    	 .match('Android'),isDesktop = !isiOS&&!isAndroid;
    		    	 if (isiOS) {
    		    	 setTimeout(function () { window.location = "itms-apps://itunes.apple.com/app/[name]/[id]?mt=8"; },25);
    		    	 window.location = "[scheme]://[host]?url="+openUrl;
    		    	 }else if(isAndroid){
    		    	 window.location = "intent://[host]/"+"url="+openUrl+"#Intent;scheme=[scheme];package=[package_name];end";
    		    	 }else{
    		    	 window.location.href = openUrl;
    		    	 }
    		    	 })(); */
 </script>
</head>
<body>
你好
</body></html>
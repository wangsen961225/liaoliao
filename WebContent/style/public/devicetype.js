function get_device_info(){  
        var ua = navigator.userAgent;  
        if(ua.match('Android')){  
        	window.location.replace("http://android.myapp.com/myapp/detail.htm?apkName=com.sjcm.liaoliao&ADTAG=mobile"); 
        }else if(ua.match('iPhone') || ua.match('iPod')||ua.match('iPad')){  
        	window.location.replace("https://itunes.apple.com/us/app/%E6%96%99%E6%96%99-%E8%AE%A9%E4%BD%A0%E7%9A%84%E7%94%9F%E6%B4%BB%E6%9B%B4%E6%9C%89%E6%96%99/id1312307735?l=zh&ls=1&mt=8");
            //return 2;  
        }else{  
          window.location.replace("https://itunes.apple.com/us/app/%E6%96%99%E6%96%99-%E8%AE%A9%E4%BD%A0%E7%9A%84%E7%94%9F%E6%B4%BB%E6%9B%B4%E6%9C%89%E6%96%99/id1312307735?l=zh&ls=1&mt=8");;  
        }  
    } 
    
   
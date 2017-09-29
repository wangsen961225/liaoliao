<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<title>${sys_title}</title>
<link href="${ctx}/style/css/style.css" rel="stylesheet">
</head>
<body class="sticky-header">
<section>
    <!--左侧栏开始-->
    <div class="left-side sticky-left-side">

        <!--logo开始-->
        <div class="logo">
            <a href="${ctx}/welcome.jsp" target="mainIframe"><img src="${ctx}/style/images/logo.png" alt=""></a>
        </div>

        <div class="logo-icon text-center">
            <a href="${ctx}/welcome.jsp" target="mainIframe"><img src="${ctx}/style/images/logo_icon.png" alt=""></a>
        </div>
        <!--logo结束-->

        <div class="left-side-inner">

            <!--sidebar nav start-->
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li class="active"><a href="${ctx}/welcome.jsp" target="mainIframe"><i class="fa fa-home"></i> <span>料料管理系统</span></a></li>
                <c:forEach var="var" items="${list}">
				 <c:if test="${(var.navigation.navigation.id == var.navigation.id) || (empty var.navigation.navigation)}">
				 <li class="menu-list"><a href="${ctx}${var.navigation.navigationUrl}" target="mainIframe">
				  <i class="fa fa-laptop"></i> <span>${var.navigation.navigationName}</span></a>
				  
				   <ul class="sub-menu-list">
				  	<c:forEach var="va" items="${list}">
				  		<c:if test="${va.navigation.navigation.id != va.navigation.id && va.navigation.navigation.id == var.navigation.id }">
				  		   <li><a href="${ctx}${va.navigation.navigationUrl} " target="mainIframe"> ${va.navigation.navigationName }</a></li>
				  		</c:if>
				  	</c:forEach>
				  	</ul>
				  	
				 </c:if>
				 
				</c:forEach>
                
 <!--                <li class="menu-list"><a href=""><i class="fa fa-laptop"></i> <span>功能一</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="blank_page.html" target="mainIframe">菜单一</a></li>
                        <li><a href="boxed_view.html" target="mainIframe">菜单一</a></li>
                        <li><a href="leftmenu_collapsed_view.html" target="mainIframe">菜单一</a></li>
                        <li><a href="horizontal_menu.html" target="mainIframe">菜单一</a></li>

                    </ul>
                </li>
                
                <li class="menu-list"><a href=""><i class="fa fa-book"></i> <span>功能一</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="general.html" target="mainIframe">菜单一</a></li>
                        <li><a href="buttons.html" target="mainIframe">菜单一</a></li>
                        <li><a href="tabs-accordions.html" target="mainIframe">菜单一s</a></li>
                        <li><a href="typography.html" target="mainIframe">菜单一</a></li>
                        <li><a href="slider.html" target="mainIframe">菜单一</a></li>
                        <li><a href="panels.html" target="mainIframe">菜单一</a></li>
                    </ul>
                </li>
                
                <li class="menu-list"><a href=""><i class="fa fa-cogs"></i> <span>功能一</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="grids.html" target="mainIframe">菜单一</a></li>
                        <li><a href="gallery.html" target="mainIframe">菜单一</a></li>
                        <li><a href="calendar.html" target="mainIframe">菜单一</a></li>
                        <li><a href="tree_view.html" target="mainIframe">菜单一</a></li>
                        <li><a href="nestable.html" target="mainIframe">菜单一</a></li>

                    </ul>
                </li>

                <li class="menu-list"><a href=""><i class="fa fa-file-text"></i> <span>功能一</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="profile.html" target="mainIframe">菜单一</a></li>
                        <li><a href="invoice.html" target="mainIframe">菜单一</a></li>
                        <li><a href="pricing_table.html" target="mainIframe">菜单一</a></li>
                        <li><a href="timeline.html" target="mainIframe">菜单一</a></li>
                        <li><a href="blog_list.html" target="mainIframe">菜单一</a></li>
                        <li><a href="blog_details.html" target="mainIframe">菜单一</a></li>
                        <li><a href="directory.html" target="mainIframe">菜单一</a></li>
                        <li><a href="chat.html" target="mainIframe">菜单一</a></li>
                        <li><a href="404.html" target="mainIframe">菜单一</a></li>
                        <li><a href="500.html" target="mainIframe">菜单一</a></li>
                        <li><a href="registration.html" target="mainIframe">菜单一</a></li>
                        <li><a href="lock_screen.html" target="mainIframe">菜单一</a></li>
                    </ul>
                </li>

                <li><a href="fontawesome.html" target="mainIframe"><i class="fa fa-bullhorn"></i> <span>Fontawesome</span></a></li>
 -->
                <li><a href="${ctx}/sys/logout" target="_top"><i class="fa fa-sign-in"></i> <span>注销登录</span></a></li>

            </ul>
            <!--sidebar nav end-->

        </div>
    </div>
    <!--左侧栏结束-->
    
    <!-- 主面板开始 -->
    <div class="main-content">

        <!-- header开始 -->
        <div class="header-section">

            <!--菜单收起按钮开始-->
            <a class="toggle-btn"><i class="fa fa-bars"></i></a>
            <!--菜单收起按钮结束-->

            <!--个人开始-->
            <div class="menu-right">
                <ul class="notification-menu">
                    <li>
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <img src="${ctx}/style/images/photos/user-avatar.png" alt="" />
                            	管理员
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="#" target="mainIframe"><i class="fa fa-user"></i>个人资料</a></li>
                            <li><a href="#" target="mainIframe"><i class="fa fa-cog"></i>设置</a></li>
                            <li><a href="${ctx}/sys/logout" target="mainIframe"><i class="fa fa-sign-out"></i>注销登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            
            <!--个人结束-->

       </div>
        <!-- header结束 -->


		<script>
		function changeFrameHeight(){
			var ifm= document.getElementById("iframepage"); 
			ifm.height=document.documentElement.clientHeight-100;
		}
		window.onresize=function(){  
			 changeFrameHeight();  
		} 
		</script>

		<!--iframe开始-->
		<div class="show_iframe">
			<iframe scrolling="yes" frameborder="0" src="${ctx}" onload="changeFrameHeight()" id="iframepage" name="mainIframe" width="100%" style="margin-top:50px;margin-bottom:50px">
			</iframe>
		</div>
		<!--iframe结束-->




	<div class="header-section">
        <!--footer开始-->
        <footer>
            2017 &copy; LiaoLiao by <a href="#" target="_blank">仕杰传媒</a>
        </footer>
        <!--footer结束-->
	</div>

    </div>
    <!-- 主面板结束 -->
</section>

<script src="${ctx}/style/public/jquery.nicescroll.js"></script>
<script src="${ctx}/style/js/scripts.js"></script>
<script type="text/javascript" src="${ctx}/style/bootstrap/js/bootstrap.min.js"></script>


</body>
</html>
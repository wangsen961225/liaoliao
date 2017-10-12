<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<link href="${ctx}/style/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<title>${sys_title}</title>

<style type="text/css">
.class_1{
color:red;
font-size:3em;
font-family:'STKaiti';
}
.class_2{
color:orangeRed;
font-size:2em;
font-family:'STKaiti'
}

.class_3{
color:coral;
font-size:1.4em;
font-family:'STKaiti'
}


</style>


</head>
<body>
	<div class="data_list">
		<div class="data_list_title">数据统计</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/handleCountList">
			<div class="form-group">
                <label for="dtp_input2" class="control-label">日期</label>
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input name="datTimeStr" id="datTimeStr" class="form-control" size="16" type="text" value="${time}" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
			<button class="btn btn-info" type="button" onclick="javascript:submitForm()">提交</button>
		  <button class="btn btn-info" type="button" data-toggle="modal" data-target="#rankList" >料币排行榜</button>
		</form>
		<script>
		//检测时间输入框是否为空
		function submitForm(){
			if(document.getElementById("datTimeStr").value==""){
				return;
			}
			else{
				myForm.submit();
			}
		}
		</script>
	</div>
<!-- 查看详情 -->
<div class="modal fade" id="rankList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <br/>
        <h4 class="modal-title" id="myModalLabel" style="text-align:center;font-size:3em;font-family:'STKaiti'">
            <p >料&nbsp;&nbsp;币&nbsp;&nbsp;排&nbsp;&nbsp;行&nbsp;&nbsp;榜</p>
        </h4>
      </div>
      <div class="modal-body">
        <table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>排名</th>
					<th>id</th>
					<th>用户名</th>
					<th>总料币</th>
				</tr>
				<c:set var="xcount" value="0" />
				<c:forEach var="li" items="${list}">
				   <c:set var="xcount" value="${xcount+1}"/>
					<tr class="class_${xcount}">
						<td>${xcount}</td>
						<td>${li.id}</td>
						<td>${li.nickName}</td>
						<td>${li.totalMoney}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
		<div>
		<p>${time} 统计数据</p>
		</div>

			<div style="float:left;">
				<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
					<tbody>
					<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
					</tr>
					<tr>
					<td><p style="text-align:center">文章列表</p></td>
					<td><p style="text-align:center">
					<c:if test="${articleList==null}">0</c:if>
					<c:if test="${articleList!=null}">${articleList}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">文章详情</p></td>
					<td><p style="text-align:center">
					<c:if test="${articleContent==null}">0</c:if>
					<c:if test="${articleContent!=null}">${articleContent}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">文章下一篇</p></td>
					<td><p style="text-align:center">
					<c:if test="${articleNext==null}">0</c:if>
					<c:if test="${articleNext!=null}">${articleNext}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">评论列表</p></td>
					<td><p style="text-align:center">
					<c:if test="${commentList==null}">0</c:if>
					<c:if test="${commentList!=null}">${commentList}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">视频列表</p></td>
					<td><p style="text-align:center">
					<c:if test="${videoList==null}">0</c:if>
					<c:if test="${videoList!=null}">${videoList}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">视频播放</p></td>
					<td><p style="text-align:center">
					<c:if test="${videoContent==null}">0</c:if>
					<c:if test="${videoContent!=null}">${videoContent}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">料币记录</p></td>
					<td><p style="text-align:center">
					<c:if test="${profitList==null}">0</c:if>
					<c:if test="${profitList!=null}">${profitList}</c:if>
					 次</p></td>
					</tr>
					 <tr>
					<td><p style="text-align:center">料币分润总金额</p></td>
					<td><p style="text-align:center">
					<c:if test="${totalProfitMoney==null}">0</c:if>
					<c:if test="${totalProfitMoney!=null}">${totalProfitMoney}</c:if>
					 料币</p></td>
					</tr> 
					<tr>
					<td><p style="text-align:center">统计在线人数</p></td>
					<td><p style="text-align:center">
					<c:if test="${accountOnline==null}">0</c:if>
					<c:if test="${accountOnline!=null}">${accountOnline}</c:if>
					 次</p></td>
					</tr> 
					</tbody>
				</table>
			</div>
					
			<div style="float:left;">
				<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
					<tbody>
					<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
					</tr>
					<tr>
					<td><p style="text-align:center">发送广播界面</p></td>
					<td><p style="text-align:center">
					<c:if test="${broadcastShow==null}">0</c:if>
					<c:if test="${broadcastShow!=null}">${broadcastShow}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">开启App</p></td>
					<td><p style="text-align:center">
					<c:if test="${startApp==null}">0</c:if>
					<c:if test="${startApp!=null}">${startApp}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">成功签到</p></td>
					<td><p style="text-align:center">
					<c:if test="${signSuccess==null}">0</c:if>
					<c:if test="${signSuccess!=null}">${signSuccess}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">开通vip界面</p></td>
					<td><p style="text-align:center">
					<c:if test="${vipShow==null}">0</c:if>
					<c:if test="${vipShow!=null}">${vipShow}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">提交手机验证码</p></td>
					<td><p style="text-align:center">
					<c:if test="${msgCode==null}">0</c:if>
					<c:if test="${msgCode!=null}">${msgCode}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">提交用户注册</p></td>
					<td><p style="text-align:center">
					<c:if test="${registerUser==null}">0</c:if>
					<c:if test="${registerUser!=null}">${registerUser}</c:if>
					 次</p></td>
					</tr>
					<tr>
				    <td><p style="text-align:center">签到之后跳转任务列表</p></td>
					<td><p style="text-align:center">
					<c:if test="${jumpToTaskList==null}">0</c:if>
					<c:if test="${jumpToTaskList!=null}">${jumpToTaskList}</c:if>
					 次</p></td> 
					</tr>
					<%-- <tr>
					<td><p style="text-align:center">提交找回密码</p></td>
					<td><p style="text-align:center">
					<c:if test="${findLosspass==null}">0</c:if>
					<c:if test="${findLosspass!=null}">${findLosspass}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">提交修改昵称</p></td>
					<td><p style="text-align:center">
					<c:if test="${changeNickname==null}">0</c:if>
					<c:if test="${changeNickname!=null}">${changeNickname}</c:if>
					 次</p></td>
					</tr> --%>
					<%-- <tr>
					<td><p style="text-align:center">协议展示界面</p></td>
					<td><p style="text-align:center">
					<c:if test="${agreementShow==null}">0</c:if>
					<c:if test="${agreementShow!=null}">${agreementShow}</c:if>
					 次</p></td>
					</tr> --%>
					<%-- <tr>
					<td><p style="text-align:center">提交用户反馈</p></td>
					<td><p style="text-align:center">
					<c:if test="${userFeedback==null}">0</c:if>
					<c:if test="${userFeedback!=null}">${userFeedback}</c:if>
					 次</p></td>
					</tr> --%>
					</tbody>
				</table>
			</div>
			
			<div style="float:left;">
				<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
					<tbody>
					<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
					</tr>
					<tr>
					<td><p style="text-align:center">提交发送广播</p></td>
					<td><p style="text-align:center">
					<c:if test="${sendBroadcast==null}">0</c:if>
					<c:if test="${sendBroadcast!=null}">${sendBroadcast}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">霸屏上线</p></td>
					<td><p style="text-align:center">
					<c:if test="${vipEffect==null}">0</c:if>
					<c:if test="${vipEffect!=null}">${vipEffect}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">分润成功</p></td>
					<td><p style="text-align:center">
					<c:if test="${profitSuccess==null}">0</c:if>
					<c:if test="${profitSuccess!=null}">${profitSuccess}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">抢红包成功</p></td>
					<td><p style="text-align:center">
					<c:if test="${graspSuccess==null}">0</c:if>
					<c:if test="${graspSuccess!=null}">${graspSuccess}</c:if>
					 次</p></td>
					</tr>
					<%-- <tr>
					<td><p style="text-align:center">提交申请提现</p></td>
					<td><p style="text-align:center">
					<c:if test="${drawMoney==null}">0</c:if>
					<c:if test="${drawMoney!=null}">${drawMoney}</c:if>
					 次</p></td>
					</tr> --%>
					<tr>
					<td><p style="text-align:center">文章分享成功</p></td>
					<td><p style="text-align:center">
					<c:if test="${shareArticle==null}">0</c:if>
					<c:if test="${shareArticle!=null}">${shareArticle}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">视频分享成功</p></td>
					<td><p style="text-align:center">
					<c:if test="${shareVideo==null}">0</c:if>
					<c:if test="${shareVideo!=null}">${shareVideo}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">邀请分享成功</p></td>
					<td><p style="text-align:center">
					<c:if test="${shareInvite==null}">0</c:if>
					<c:if test="${shareInvite!=null}">${shareInvite}</c:if>
					 次</p></td>
					</tr>
					<tr>
					<td><p style="text-align:center">邀请好友界面</p></td>
					<td><p style="text-align:center">
					<c:if test="${inviteShow==null}">0</c:if>
					<c:if test="${inviteShow!=null}">${inviteShow}</c:if>
					 次</p></td>
					</tr>
					</tbody>
				</table>
			</div>

<%@ include file="/style/public/footPage.jsp"%>

<script type="text/javascript" src="${ctx}/style/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/style/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
    $('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    });
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<link href="${ctx}/style/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">数据统计</div>
		<form name="myForm" class="form-inline" method="post" action="${ctx}/sys/handleCountByDay">
			<div class="form-group">
                <label for="dtp_input2" class="control-label">日期</label>
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input name="datTimeStr" id="datTimeStr" class="form-control" size="16" type="text" value="${time}" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
			<button class="btn btn-info" type="button" onclick="javascript:submitForm()">提交</button>
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
	
		<div>
		<p>${time} 统计数据</p>
		</div>

		<c:forEach items="${list}" var="li" varStatus="status">
		<!-- 0~9数据 -->
		<c:if test="${status.index<10}">
			<c:if test="${status.index==0}">
			<div style="float:left;">
				<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
					<tbody>
					<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
					</tr>
			</c:if>
					<tr>
					<td><p style="text-align:center">
						<c:if test="${status.index<10}">
						<c:if test="${li.clickName=='articleList'}">文章列表</c:if>
						<c:if test="${li.clickName=='articleContent'}">文章详情</c:if>
						<c:if test="${li.clickName=='articleNext'}">文章下一篇</c:if>
						<c:if test="${li.clickName=='commentList'}">评论列表</c:if>
						<c:if test="${li.clickName=='videoList'}">视频列表</c:if>
						<c:if test="${li.clickName=='videoContent'}">视频播放</c:if>
						<c:if test="${li.clickName=='profitList'}">料币记录</c:if>
						<c:if test="${li.clickName=='tobankShow'}">兑换料币界面</c:if>
						<c:if test="${li.clickName=='broadcastShow'}">发送广播界面</c:if>
						<c:if test="${li.clickName=='inviteShow'}">邀请好友界面</c:if>
						<c:if test="${li.clickName=='startApp'}">开启App</c:if>
						<c:if test="${li.clickName=='signSuccess'}">成功签到</c:if>
						<c:if test="${li.clickName=='vipShow'}">开通vip界面</c:if>
						<c:if test="${li.clickName=='msgCode'}">提交手机验证码</c:if>
						<c:if test="${li.clickName=='registerUser'}">提交用户注册</c:if>
						<c:if test="${li.clickName=='changePassword'}">提交改密码</c:if>
						<c:if test="${li.clickName=='findLosspass'}">提交找回密码</c:if>
						<c:if test="${li.clickName=='changeNickname'}">提交修改昵称</c:if>
						<c:if test="${li.clickName=='agreementShow'}">协议展示界面</c:if>
						<c:if test="${li.clickName=='userFeedback'}">提交用户反馈</c:if>
						<c:if test="${li.clickName=='sendBroadcast'}">提交发送广播</c:if>
						<c:if test="${li.clickName=='vipEffect'}">霸屏上线</c:if>
						<c:if test="${li.clickName=='profitSuccess'}">分润成功</c:if>
						<c:if test="${li.clickName=='graspSuccess'}">抢红包成功</c:if>
						<c:if test="${li.clickName=='drawMoney'}">提交申请提现</c:if>
						<c:if test="${li.clickName=='shareArticle'}">文章分享成功</c:if>
						<c:if test="${li.clickName=='shareVideo'}">视频分享成功</c:if>
						<c:if test="${li.clickName=='shareInvite'}">邀请分享成功</c:if>
						</p></td>
						<td><p style="text-align:center">${li.clickCount} 次</p></td>
						</c:if>
					</tr>
					<c:if test="${status.index==9}">
				</tbody>
			</table>
		</div>
		</c:if>
		</c:if>
		<!-- 10~19数据 -->
		<c:if test="${status.index>=10}">
				<c:if test="${status.index==10}">
		<div style="float:left;">
			<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
				<tbody>
				<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
				</tr>
				</c:if>
					<tr>
					<td><p style="text-align:center">
						<c:if test="${status.index>=10}">
						<c:if test="${li.clickName=='articleList'}">文章列表</c:if>
						<c:if test="${li.clickName=='articleContent'}">文章详情</c:if>
						<c:if test="${li.clickName=='articleNext'}">文章下一篇</c:if>
						<c:if test="${li.clickName=='commentList'}">评论列表</c:if>
						<c:if test="${li.clickName=='videoList'}">视频列表</c:if>
						<c:if test="${li.clickName=='videoContent'}">视频播放</c:if>
						<c:if test="${li.clickName=='profitList'}">料币记录</c:if>
						<c:if test="${li.clickName=='tobankShow'}">兑换料币界面</c:if>
						<c:if test="${li.clickName=='broadcastShow'}">发送广播界面</c:if>
						<c:if test="${li.clickName=='inviteShow'}">邀请好友界面</c:if>
						<c:if test="${li.clickName=='startApp'}">开启App</c:if>
						<c:if test="${li.clickName=='signSuccess'}">成功签到</c:if>
						<c:if test="${li.clickName=='vipShow'}">开通vip界面</c:if>
						<c:if test="${li.clickName=='msgCode'}">提交手机验证码</c:if>
						<c:if test="${li.clickName=='registerUser'}">提交用户注册</c:if>
						<c:if test="${li.clickName=='changePassword'}">提交改密码</c:if>
						<c:if test="${li.clickName=='findLosspass'}">提交找回密码</c:if>
						<c:if test="${li.clickName=='changeNickname'}">提交修改昵称</c:if>
						<c:if test="${li.clickName=='agreementShow'}">协议展示界面</c:if>
						<c:if test="${li.clickName=='userFeedback'}">提交用户反馈</c:if>
						<c:if test="${li.clickName=='sendBroadcast'}">提交发送广播</c:if>
						<c:if test="${li.clickName=='vipEffect'}">霸屏上线</c:if>
						<c:if test="${li.clickName=='profitSuccess'}">分润成功</c:if>
						<c:if test="${li.clickName=='graspSuccess'}">抢红包成功</c:if>
						<c:if test="${li.clickName=='drawMoney'}">提交申请提现</c:if>
						<c:if test="${li.clickName=='shareArticle'}">文章分享成功</c:if>
						<c:if test="${li.clickName=='shareVideo'}">视频分享成功</c:if>
						<c:if test="${li.clickName=='shareInvite'}">邀请分享成功</c:if>
						</p></td>
						<td><p style="text-align:center">${li.clickCount} 次</p></td>
						</c:if>
					</tr>
					<c:if test="${status.index==19}">
				</tbody>
			</table>
		</div>
		</c:if>
		</c:if>
		<!-- 20~29数据 -->
		<c:if test="${status.index>=20}">
				<c:if test="${status.index==20}">
		<div style="float:left;">
			<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
				<tbody>
				<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
				</tr>
				</c:if>
					<tr>
					<td><p style="text-align:center">
						<c:if test="${status.index>=20}">
						<c:if test="${li.clickName=='articleList'}">文章列表</c:if>
						<c:if test="${li.clickName=='articleContent'}">文章详情</c:if>
						<c:if test="${li.clickName=='articleNext'}">文章下一篇</c:if>
						<c:if test="${li.clickName=='commentList'}">评论列表</c:if>
						<c:if test="${li.clickName=='videoList'}">视频列表</c:if>
						<c:if test="${li.clickName=='videoContent'}">视频播放</c:if>
						<c:if test="${li.clickName=='profitList'}">料币记录</c:if>
						<c:if test="${li.clickName=='tobankShow'}">兑换料币界面</c:if>
						<c:if test="${li.clickName=='broadcastShow'}">发送广播界面</c:if>
						<c:if test="${li.clickName=='inviteShow'}">邀请好友界面</c:if>
						<c:if test="${li.clickName=='startApp'}">开启App</c:if>
						<c:if test="${li.clickName=='signSuccess'}">成功签到</c:if>
						<c:if test="${li.clickName=='vipShow'}">开通vip界面</c:if>
						<c:if test="${li.clickName=='msgCode'}">提交手机验证码</c:if>
						<c:if test="${li.clickName=='registerUser'}">提交用户注册</c:if>
						<c:if test="${li.clickName=='changePassword'}">提交改密码</c:if>
						<c:if test="${li.clickName=='findLosspass'}">提交找回密码</c:if>
						<c:if test="${li.clickName=='changeNickname'}">提交修改昵称</c:if>
						<c:if test="${li.clickName=='agreementShow'}">协议展示界面</c:if>
						<c:if test="${li.clickName=='userFeedback'}">提交用户反馈</c:if>
						<c:if test="${li.clickName=='sendBroadcast'}">提交发送广播</c:if>
						<c:if test="${li.clickName=='vipEffect'}">霸屏上线</c:if>
						<c:if test="${li.clickName=='profitSuccess'}">分润成功</c:if>
						<c:if test="${li.clickName=='graspSuccess'}">抢红包成功</c:if>
						<c:if test="${li.clickName=='drawMoney'}">提交申请提现</c:if>
						<c:if test="${li.clickName=='shareArticle'}">文章分享成功</c:if>
						<c:if test="${li.clickName=='shareVideo'}">视频分享成功</c:if>
						<c:if test="${li.clickName=='shareInvite'}">邀请分享成功</c:if>
						</p></td>
						<td><p style="text-align:center">${li.clickCount} 次</p></td>
						</c:if>
					</tr>
					<c:if test="${status.index==29}">
				</tbody>
			</table>
		</div>
		</c:if>
		</c:if>
		<!-- 30~39数据 -->
		<c:if test="${status.index>=30}">
				<c:if test="${status.index==30}">
		<div style="float:left;">
			<table class="table table-hover table-striped table-bordered" style="width:300px;margin-left:100px;margin-top:50px;">
				<tbody>
				<tr>
						<th width="25%" style="text-align:center">统计名称</th>
						<th width="25%" style="text-align:center">统计次数</th>
				</tr>
				</c:if>
					<tr>
					<td><p style="text-align:center">
						<c:if test="${status.index>=30}">
						<c:if test="${li.clickName=='articleList'}">文章列表</c:if>
						<c:if test="${li.clickName=='articleContent'}">文章详情</c:if>
						<c:if test="${li.clickName=='articleNext'}">文章下一篇</c:if>
						<c:if test="${li.clickName=='commentList'}">评论列表</c:if>
						<c:if test="${li.clickName=='videoList'}">视频列表</c:if>
						<c:if test="${li.clickName=='videoContent'}">视频播放</c:if>
						<c:if test="${li.clickName=='profitList'}">料币记录</c:if>
						<c:if test="${li.clickName=='tobankShow'}">兑换料币界面</c:if>
						<c:if test="${li.clickName=='broadcastShow'}">发送广播界面</c:if>
						<c:if test="${li.clickName=='inviteShow'}">邀请好友界面</c:if>
						<c:if test="${li.clickName=='startApp'}">开启App</c:if>
						<c:if test="${li.clickName=='signSuccess'}">成功签到</c:if>
						<c:if test="${li.clickName=='vipShow'}">开通vip界面</c:if>
						<c:if test="${li.clickName=='msgCode'}">提交手机验证码</c:if>
						<c:if test="${li.clickName=='registerUser'}">提交用户注册</c:if>
						<c:if test="${li.clickName=='changePassword'}">提交改密码</c:if>
						<c:if test="${li.clickName=='findLosspass'}">提交找回密码</c:if>
						<c:if test="${li.clickName=='changeNickname'}">提交修改昵称</c:if>
						<c:if test="${li.clickName=='agreementShow'}">协议展示界面</c:if>
						<c:if test="${li.clickName=='userFeedback'}">提交用户反馈</c:if>
						<c:if test="${li.clickName=='sendBroadcast'}">提交发送广播</c:if>
						<c:if test="${li.clickName=='vipEffect'}">霸屏上线</c:if>
						<c:if test="${li.clickName=='profitSuccess'}">分润成功</c:if>
						<c:if test="${li.clickName=='graspSuccess'}">抢红包成功</c:if>
						<c:if test="${li.clickName=='drawMoney'}">提交申请提现</c:if>
						<c:if test="${li.clickName=='shareArticle'}">文章分享成功</c:if>
						<c:if test="${li.clickName=='shareVideo'}">视频分享成功</c:if>
						<c:if test="${li.clickName=='shareInvite'}">邀请分享成功</c:if>
						</p></td>
						<td><p style="text-align:center">${li.clickCount} 次</p></td>
						</c:if>
					</tr>
					<c:if test="${status.index==39}">
				</tbody>
			</table>
		</div>
		</c:if>
		</c:if>
	</c:forEach>
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